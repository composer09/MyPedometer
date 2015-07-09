package kr.co.composer.pedometer.fragment;

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
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import kr.co.composer.mylocation.aidl.ICountService;
import kr.co.composer.mylocation.aidl.ICountServiceCallback;
import kr.co.composer.pedometer.R;
import kr.co.composer.pedometer.location.LocationManagerInitializer;
import kr.co.composer.pedometer.service.StepService;
import kr.co.composer.pedometer.sharedpref.ConfigPreferenceManager;
import kr.co.composer.pedometer.util.GPSUtil;
import kr.co.composer.pedometer.viewpager.adapter.MyPagerAdapter;

public class MainFragment extends Fragment {
	private ConfigPreferenceManager configPref = null;
	private View view;
	private ViewPager mViewPager;
	private MyPagerAdapter mPagerAdapter;
	private TextView text;
	private Button button;
	private Handler handler;
	private ICountService mBinder = null;
	private Intent stepServiceIntent = null;
	private PowerManager powerManager = null;
	private PowerManager.WakeLock wakeLock = null;
	private boolean isPalying = false;
	private FragmentActivity activity = null;
	private int currentCount;
	private LocationManager locationManager = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		configPref = ConfigPreferenceManager.getInstance();
		powerManager = (PowerManager) getActivity().getSystemService(Context.POWER_SERVICE);
		wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MainFragment");
		locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
		if (stepServiceIntent == null) {
			Bundle extras = new Bundle();
			extras.putInt("int", 0);
			stepServiceIntent = new Intent(getActivity(), StepService.class);
			stepServiceIntent.putExtras(extras);
		}
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_main, container, false);
		mViewPager = (ViewPager)view.findViewById(R.id.pager);
		text = (TextView)view.findViewById(R.id.step_text);
		button = (Button)view.findViewById(R.id.test_fragment_btn);


		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(isPalying){
					pause();
				}else{
					play();
				}
				isPalying = !isPalying;
			}
		});

		mPagerAdapter = new MyPagerAdapter(getActivity().getSupportFragmentManager(),getActivity());
		mViewPager.setAdapter(mPagerAdapter);

		handler = new Handler() {
			public void handleMessage(Message msg) {
				currentCount = msg.arg1;
				text.setText("Count = " + currentCount);
			}
		};

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

	private void play(){
		if(GPSUtil.isOnGPS(getActivity().getContentResolver(),this.locationManager)){
			LocationManagerInitializer.start(getActivity(), locationManager);
		}else{
			GPSUtil.getGpsAlertDialog(getActivity()).create().show();
		}
		getActivity().startService(stepServiceIntent);
		getActivity().bindService(stepServiceIntent, mConnection, Context.BIND_AUTO_CREATE);
		button.setText(R.string.stop_button);
	}

	private void pause(){
		if(mBinder != null){
			try {
				mBinder.unregisterCountCallback(mCallback);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		getActivity().unbindService(mConnection);
		mBinder = null;
		getActivity().stopService(stepServiceIntent);
		text.setText(R.string.count_text);
		button.setText(R.string.play_button);
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

}
