package kr.co.composer.pedometer.listener;

import android.content.Context;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import java.util.Iterator;

public class GpsStateChange implements GpsStatus.Listener {
	private LocationManager mLocationManager;
	private int usedSatellite;
	Context context;
	
	public GpsStateChange(Context context){
		this.context = context;
		mLocationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
	}
	
	@Override
	public void onGpsStatusChanged(int event){
		         switch(event){
//			      gps를 잡으려고 시도시
		         case GpsStatus.GPS_EVENT_FIRST_FIX:
		        	 Log.i("GPS_EVENT_FIRST_FIX","GpsStatus.GPS_EVENT_FIRST_FIX");
		            break;
		         
//		         처음으로 gps data를 받았을 때
		         case GpsStatus.GPS_EVENT_STARTED:
		        	 Log.i("GPS_EVENT_STARTED","GpsStatus.GPS_EVENT_STARTED");
		        	 mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
		        	 
		             break;

// 				gps 지속적으로 체크
		         case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
		        	 Log.i("GPS_EVENT_SATELLITE_STATUS","GPS_EVENT_SATELLITE_STATUS");
		        	 Log.i("usedSatellite", String.valueOf(getGpsSatelliteCount()));
//		        	 사용가능한 gps가 3개 이상일때...
		        	 if(getGpsSatelliteCount() > 3){
		        		 
		        	 }
//		        	 Toast.makeText(context, "사용가능 \nGPS "+getGpsSatelliteCount(), 100).show();
		        	 if(getGpsSatelliteCount() == 0){
		        		 mLocationManager.removeUpdates(locationListener);
		        	 }
		             break;

// 				gps 종료 시
		         case GpsStatus.GPS_EVENT_STOPPED:
		        	 Log.i("GPS_EVENT_STOPPED","GPS_EVENT_STOPPED");
		             break;
		         }
		     }

	LocationListener locationListener = new LocationListener() {
		@Override
		public void onLocationChanged(Location location) {
			
		}
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			
		}
		
		@Override
		public void onProviderEnabled(String provider) {
			
		}
		
		@Override
		public void onProviderDisabled(String provider) {
			
		}
	};
	
	private int getGpsSatelliteCount()
	{
	    GpsStatus gs = mLocationManager.getGpsStatus(null);
	 
	    int checkSatellite = 0;
	    int usedSatellite = 0;
	    final Iterator<GpsSatellite> it = gs.getSatellites().iterator();
	 
	    while(it.hasNext()) {
	        GpsSatellite satellite = it.next();
	 
	        if (satellite.usedInFix()) {
	        	usedSatellite++; // 사용가능한 gps갯수 확인
	        }
//	        체크되는 gps위성 확인
	        Log.i("checkSatellite", String.valueOf(checkSatellite++));
	    }
	 
	    return usedSatellite;
	}
}
