package kr.co.composer.pedometer.fragment;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.greenrobot.event.EventBus;
import kr.co.composer.mylocation.aidl.ICountService;
import kr.co.composer.mylocation.aidl.ICountServiceCallback;
import kr.co.composer.pedometer.R;
import kr.co.composer.pedometer.application.PedometerApplication;
import kr.co.composer.pedometer.bo.pedometer.PedoHistoryBO;
import kr.co.composer.pedometer.bo.pedometer.Pedometer;
import kr.co.composer.pedometer.dao.ContentProviderUri;
import kr.co.composer.pedometer.dao.pedometer.PedoSQLiteOpenHelper;
import kr.co.composer.pedometer.format.TimeFormatter;
import kr.co.composer.pedometer.location.bo.LocationManagerInitializer;
import kr.co.composer.pedometer.service.StepService;
import kr.co.composer.pedometer.sharedpref.ConfigPreferenceManager;
import kr.co.composer.pedometer.util.GPSUtil;
import kr.co.composer.pedometer.viewpager.adapter.MyPagerAdapter;
import kr.co.composer.pedometer.viewpager.adapter.TextChangedEvent;

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
	private Pedometer pedometer;
	private PedoHistoryBO pedoHistoryBO;

	long value1;
	long value2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		configPref = ConfigPreferenceManager.getInstance();
		pedometer = new Pedometer();
		pedoHistoryBO = new PedoHistoryBO();

		//////////////////////////////////////////////////////

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date1 = sdf.parse("2015-07-15 00:00:01");
			Date date2 = sdf.parse("2015-07-20 23:59:59");
			value1 = date1.getTime();
			value2 = date2.getTime();
			PedoSQLiteOpenHelper helper = new PedoSQLiteOpenHelper(PedometerApplication.contextWrapper.getApplicationContext());
			SQLiteDatabase db = helper.getWritableDatabase();
			Cursor cur = db.rawQuery("SELECT * from pedometer where date between " + value1 + " and " + value2 + "", null);
//			Cursor cur = db.rawQuery("select * from pedometer",null);
			while(cur.moveToNext()){
				int columnIndex = cur.getColumnIndex(PedoSQLiteOpenHelper.ROW_ID);
				Log.i("rowId",cur.getInt(columnIndex)+"");
				columnIndex = cur.getColumnIndex(PedoSQLiteOpenHelper.PEDOMETER_COUNT);
				Log.i("count",cur.getInt(columnIndex)+"");
				columnIndex = cur.getColumnIndex(PedoSQLiteOpenHelper.TIME);
				Log.i("날짜 확인", "" + DateFormat.format(
						TimeFormatter.START_DATE_FORMAT,
						cur.getLong(columnIndex)));
			}


//			private List<Pedometer> cursor2HistoryList(Cursor cursor) {
//				List<Pedometer> historyList = new ArrayList<Pedometer>();
//
//				try {
//					while (cur.moveToNext()) {
//						Pedometer pedometer = new Pedometer();
//
//						int columnIndex = cur.getColumnIndex(PedoSQLiteOpenHelper.ROW_ID);
//						pedometer.setRowId(cur.getInt(columnIndex));
//
//						columnIndex = cur.getColumnIndex(PedoSQLiteOpenHelper.PEDOMETER_COUNT);
//						pedometer.setPedometerCount(cur.getInt(columnIndex));
//
//						columnIndex = cur.getColumnIndex(PedoSQLiteOpenHelper.TIME);
//						pedometer.setTime(cur.getInt(columnIndex));
//
//						historyList.add(pedometer);
//					}// while
//				} catch (Exception e) {
//				}
//
//				return historyList;
//			}

		} catch (ParseException e) {
			e.printStackTrace();
		}


//		pedometer.setPedometerCount(71003138);
//		pedometer.setTime(System.currentTimeMillis());
//		insert(pedometer);

//		Date date = new Date(System.currentTimeMillis());
//
//		LogTest.i("현재시간 확인",System.currentTimeMillis());
//		LogTest.i("날짜확인",sdf.format(date));





//		pedometer.setPedometerCount(100);
//		pedometer.setTime(System.currentTimeMillis());
//		pedoHistoryBO.insert(pedometer);





//		pedometer.setPedometerCount(570);
//		pedometer.setTime("2015-07-10 14:20:29");
//		pedoHistoryBO.insert(pedometer);
//
//		pedometer.setPedometerCount(11);
//		pedometer.setTime("2015-07-11 13:00:29");
//		pedoHistoryBO.insert(pedometer);
//
//		pedometer.setPedometerCount(268);
//		pedometer.setTime("2015-07-11 11:00:29");
//		pedoHistoryBO.insert(pedometer);
//
//		pedometer.setPedometerCount(870);
//		pedometer.setTime("2015-07-12 19:00:29");
//		pedoHistoryBO.insert(pedometer);
//
//		pedometer.setPedometerCount(47);
//		pedometer.setTime("2015-07-13 10:00:29");
//		pedoHistoryBO.insert(pedometer);
//
//		pedometer.setPedometerCount(976);
//		pedometer.setTime("2015-07-14 10:00:29");
//		pedoHistoryBO.insert(pedometer);
//
//		pedometer.setPedometerCount(45);
//		pedometer.setTime("2015-07-15 20:00:29");
//		pedoHistoryBO.insert(pedometer);
//
//		pedometer.setPedometerCount(60);
//		pedometer.setTime("2015-07-16 06:06:29");
//		pedoHistoryBO.insert(pedometer);
//
//		pedometer.setPedometerCount(77);
//		pedometer.setTime("2015-07-16 10:00:29");
//		pedoHistoryBO.insert(pedometer);
		/////////////////////////////////////////////////////
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
				EventBus bus = EventBus.getDefault();
				bus.post(new TextChangedEvent(String.valueOf(currentCount)));
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
		pedometer.setPedometerCount(currentCount);
		pedometer.setTime(System.currentTimeMillis());
		pedoHistoryBO.insert(pedometer);
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


	public void insert(Pedometer pedometer) {
		ContentResolver resolver = PedometerApplication.contextWrapper.getContentResolver();
		ContentValues contVal = new ContentValues();
		contVal.put(PedoSQLiteOpenHelper.TIME, pedometer.getTime());
		contVal.put(PedoSQLiteOpenHelper.PEDOMETER_COUNT, pedometer.getPedometerCount());
		resolver.insert(ContentProviderUri.pedometer(getUriAuthority()), contVal);

	}

	private String getUriAuthority() {
		return PedometerApplication.contextWrapper.getString(R.string.url_content_authority);
	}
}
