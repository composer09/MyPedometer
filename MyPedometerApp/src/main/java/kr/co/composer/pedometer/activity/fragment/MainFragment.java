package kr.co.composer.pedometer.activity.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import de.greenrobot.event.EventBus;
import kr.co.composer.mylocation.aidl.ICountService;
import kr.co.composer.mylocation.aidl.ICountServiceCallback;
import kr.co.composer.pedometer.R;
import kr.co.composer.pedometer.activity.viewpager.adapter.MyPagerAdapter;
import kr.co.composer.pedometer.activity.viewpager.adapter.TextChangedEvent;
import kr.co.composer.pedometer.activity.viewpager.adapter.ZoomOutPageTransformer;
import kr.co.composer.pedometer.bo.pedometer.PedoHistoryBO;
import kr.co.composer.pedometer.bo.pedometer.Pedometer;
import kr.co.composer.pedometer.service.StepService;
import kr.co.composer.pedometer.sharedpref.ConfigPreferenceManager;

public class MainFragment extends Fragment {
    private ConfigPreferenceManager configPref = null;
    private View view;
    private ViewPager mViewPager;
    private MyPagerAdapter mPagerAdapter;
    private Button button;
    private ICountService mBinder = null;
    private Intent stepServiceIntent = null;
    private PowerManager powerManager = null;
    private PowerManager.WakeLock wakeLock = null;
    private boolean isPalying = false;
    private int currentCount;
    private LocationManager locationManager = null;
    private Pedometer pedometer;
    private PedoHistoryBO pedoHistoryBO;
    private EventBus bus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configPref = ConfigPreferenceManager.getInstance();
        bus = EventBus.getDefault();
        pedoHistoryBO = new PedoHistoryBO();
        pedometer = new Pedometer();
        powerManager = (PowerManager) getActivity().getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MainFragment");
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        todayDataCheck();

        //강제 insert
//		try {
//			pedoHistoryBO = new PedoHistoryBO();
//			pedometer = new Pedometer();
//			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
//			Date date1 = null;
//			date1 = sdf2.parse("2015-07-23 10:00:00");
//			long range1 = date1.getTime();
//			pedometer.setPedometerCount(20000);
//			pedometer.setTime(range1);
//			pedoHistoryBO.insert(pedometer);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.pager);
        button = (Button) view.findViewById(R.id.test_fragment_btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPalying) {
                    pause();
                } else {
                    play();
                }
                isPalying = !isPalying;
            }
        });

        mPagerAdapter = new MyPagerAdapter(getActivity().getSupportFragmentManager(), getActivity());
//        mViewPager.setPageTransformer(false, new DepthPageTransformer());
        mViewPager.setPageTransformer(false, new ZoomOutPageTransformer());
        mViewPager.setAdapter(mPagerAdapter);
//        mViewPager.setPageMargin(getResources().getDisplayMetrics().widthPixels / -9);
//        mViewPager.setOffscreenPageLimit(3);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!wakeLock.isHeld()) wakeLock.acquire();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (wakeLock.isHeld()) wakeLock.release();
    }

    private void play() {
        //Gps 기능 사용시....
//		if(GPSUtil.isOnGPS(getActivity().getContentResolver(),this.locationManager)){
//			LocationManagerInitializer.start(getActivity(), locationManager);
//		}else{
//			GPSUtil.getGpsAlertDialog(getActivity()).create().show();
//		}
        intentStart();
        getActivity().startService(stepServiceIntent);
        getActivity().bindService(stepServiceIntent, mConnection, Context.BIND_AUTO_CREATE);
        button.setText(R.string.stop_button);
    }

    private void pause() {
        if (mBinder != null) {
            try {
                mBinder.unregisterCountCallback(mCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        getActivity().unbindService(mConnection);
        mBinder = null;
        getActivity().stopService(stepServiceIntent);
        button.setText(R.string.play_button);
        pedometer.setPedometerCount(currentCount);
        pedometer.setTime(System.currentTimeMillis());
        if(!pedoHistoryBO.getTodayCheck()){
            pedoHistoryBO.insert(pedometer);
        }
        pedoHistoryBO.update(pedometer);
    }


    private ICountServiceCallback.Stub mCallback = new ICountServiceCallback.Stub() {

        @Override
        public IBinder asBinder() {
            return mCallback;
        }

        @Override
        public void stepsChanged(int value) throws RemoteException {
            Log.i("Steps", "Steps=" + value);
            Message msg = handler.obtainMessage();
            msg.arg1 = value;
            handler.sendMessage(msg);
        }
    };

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            currentCount = msg.arg1;
            bus.post(new TextChangedEvent(currentCount));
        }
    };

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.i("ServiceConnection", "onServiceConnected()");
            mBinder = ICountService.Stub.asInterface(service);
            try {
                mBinder.registerCountCallback(mCallback);
                mBinder.setSensitivity(configPref.getSensitivityValue());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName className) {
            Log.i("ServiceConnection", "onServiceDisconnected()");
            mBinder = null;
        }
    };

    private void todayDataCheck() {
        if (!pedoHistoryBO.getTodayCheck()) {
            pedometer.setPedometerCount(0);
            pedometer.setTime(System.currentTimeMillis());
            pedoHistoryBO.insert(pedometer);
        } else {
            currentCount = pedoHistoryBO.getTodayCount();
        }
    }

    private void intentStart() {
        Bundle extras = new Bundle();
        extras.putInt("int", currentCount);
        stepServiceIntent = new Intent(getActivity(), StepService.class);
        stepServiceIntent.putExtras(extras);
    }
}
