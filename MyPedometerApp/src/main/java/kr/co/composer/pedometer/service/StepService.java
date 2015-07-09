package kr.co.composer.pedometer.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.RemoteException;
import android.support.v7.app.NotificationCompat;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

import kr.co.composer.mylocation.aidl.ICountService;
import kr.co.composer.mylocation.aidl.ICountServiceCallback;
import kr.co.composer.pedometer.R;
import kr.co.composer.pedometer.activity.MainActivity;


/**
 * This class extends the Service class. it is in charge of starting and
 * stopping the power, notification, and sensor managers. It also passes
 * information received from the sensor to the StepDetector.
 * <p>
 * This code is losely based on http://code.google.com/p/pedometer/
 *
 * @author bagilevi <bagilevi@gmail.com>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class StepService extends Service implements StepListener {
    private static final Logger logger = Logger.getLogger(StepService.class.getSimpleName());

    private static int NOTIFY = 0x1001;
    private static AtomicBoolean updating = new AtomicBoolean(false);

    private static SensorManager sensorManager = null;
    private static StepDetector stepDetector = null;

    private static PowerManager powerManager = null;
    private static WakeLock wakeLock = null;
    private static NotificationManager notificatioManager = null;
    private static NotificationCompat.Builder notification = null;
    private static Intent passedIntent = null;
    private static List<ICountServiceCallback> mCallbacks = new ArrayList<ICountServiceCallback>();
    ;

    private static int mSteps = 0;
    private static boolean running = false;

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate() {
        super.onCreate();
        logger.info("onCreate");
        notificatioManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        initNotification();

        powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "StepService");
        if (!wakeLock.isHeld()) wakeLock.acquire();

        if (stepDetector == null) {
            stepDetector = StepDetector.getInstance();
            // onStep 을 구현한 StepService 객체 리스트에 저장
            stepDetector.addStepListener(this);
        }

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(stepDetector, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
        running = true;
    }

    private void initNotification() {
        notification = (NotificationCompat.Builder)new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle("MyPedometer")
                .setTicker("Pedometer Started.")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setOngoing(true);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        logger.info("onDestroy");
        running = false;
        mSteps = 0;

        notificatioManager.cancel(NOTIFY);
        if (wakeLock.isHeld()) wakeLock.release();
        sensorManager.unregisterListener(stepDetector);

        stopForegroundCompat(NOTIFY);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        logger.info("onStartCommand");
        passedIntent = intent;
        Bundle extras = passedIntent.getExtras();
        if (extras != null) {
            NOTIFY = extras.getInt("int");
        }

        // Work around a bug where notif number has to be > 0
        updateNotification((mSteps > 0) ? mSteps : 0);
        startForegroundCompat(NOTIFY, notification);
        return START_STICKY;
    }

    /**
     * This is a wrapper around the new startForeground method, using the older
     * APIs if it is not available.
     *
     * @param id    Integer representing the service to start.
     * @param notif Notification to display when service is running.
     */
    public void startForegroundCompat(int id, NotificationCompat.Builder notif) {
        Method mStartForeground = null;
        try {
            mStartForeground = getClass().getMethod("startForeground", new Class[]{int.class, Notification.class});
        } catch (Exception e) {
            // Should happen in Android OS < 2.0
        }

        // If we have the new startForeground API, then use it.
        if (mStartForeground != null) {
            try {
                mStartForeground.invoke(this, id, notif);
            } catch (Exception e) {
                // Should not happen.
            }
            return;
        }

        // Fall back on the old API.
        stopForeground(true);
        notificatioManager.notify(id, notif.build());
    }

    public void stopForegroundCompat(int id) {
        Method mStopForeground = null;
        try {
            mStopForeground = getClass().getMethod("stopForeground", new Class[]{boolean.class});
        } catch (Exception e) {
            // Should happen in Android OS < 2.0
        }

        // If we have the new stopForeground API, then use it.
        if (mStopForeground != null) {
            try {
                mStopForeground.invoke(this, true);
            } catch (Exception e) {
                // Should not happen.
            }
            return;
        }

        // Fall back on the old API. Note to cancel BEFORE changing the
        // foreground state, since we could be killed at that point.
        notificatioManager.cancel(id);
        stopForeground(false);
    }

    private void updateNotification(int steps) {
        if (!updating.compareAndSet(false, true)) {
            return;
        }
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        notification.setContentText("Total steps: " + steps);
        notification.setWhen(System.currentTimeMillis());
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        notification.setContentIntent(pendingIntent);
        notificatioManager.notify(NOTIFY, notification.build());
        updating.set(false);
    }


    @Override
    public void onStep() {
        logger.info("onStep()");
        mSteps++;

        if (!updating.get()) {
            UpdateNotificationAsyncTask update = new UpdateNotificationAsyncTask();
            update.doInBackground(mSteps);
        }

        if (mCallbacks != null) {
            List<ICountServiceCallback> callbacksToRemove = null;
            for (ICountServiceCallback mCallback : mCallbacks) {
                try {
                    mCallback.stepsChanged(mSteps);
                } catch (RemoteException e) {
                    // Remove old callbacks if they failed to unbind
                    callbacksToRemove = new ArrayList<ICountServiceCallback>();
                    callbacksToRemove.add(mCallback);
                    e.printStackTrace();
                }
            }
            if (callbacksToRemove != null) {
                for (ICountServiceCallback mCallback : callbacksToRemove) {
                    mCallbacks.remove(mCallback);
                }
            }
        }
    }


    private class UpdateNotificationAsyncTask extends AsyncTask<Integer, Integer, Boolean> {

        /**
         * {@inheritDoc}
         */
        @Override
        protected Boolean doInBackground(Integer... params) {
            updateNotification(params[0]);
            return true;
        }
    }

    private final ICountService.Stub mBinder = new ICountService.Stub() {

        @Override
        public boolean isRunning() throws RemoteException {
            return running;
        }

        @Override
        public void setSensitivity(int sens) throws RemoteException {
            logger.info("setSensitivity: " + sens);
            StepDetector.setSensitivity(sens);
        }

        @Override
        public void registerCountCallback(ICountServiceCallback serviceCallback) throws RemoteException {
            if (serviceCallback == null) return;

            logger.info("registerCallback: " + serviceCallback.toString());
            serviceCallback.stepsChanged(mSteps);
            if (!mCallbacks.contains(serviceCallback)) mCallbacks.add(serviceCallback);
        }


        @Override
        public void unregisterCountCallback(ICountServiceCallback serviceCallback) throws RemoteException {
            if (serviceCallback == null) return;

            logger.info("unregisterCallback: " + serviceCallback.toString());
            if (mCallbacks.contains(serviceCallback)) mCallbacks.remove(serviceCallback);
        }
    };


    @Override
    public IBinder onBind(Intent intent) {
        logger.info("onBind()");
        return mBinder;
    }
}
