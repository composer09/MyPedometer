package kr.co.composer.pedometer.location.bo;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import kr.co.composer.pedometer.util.GPSUtil;


public class LocationManagerInitializer {
	private static boolean gpsInit = false;
	
	public static synchronized void start(Context context, LocationManager locationManager) {
		if (gpsInit == false && GPSUtil.isOnGPS(context.getContentResolver(), locationManager)) {
			locationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, 0, 0, myLocationListener);
			gpsInit = true;
		}
	}
	
	public static synchronized void stop(Context context, LocationManager locationManager) {
		if (gpsInit == true) {
			locationManager.removeUpdates(myLocationListener);
			gpsInit = false;
		}
	}
	
	private static LocationListener myLocationListener = new LocationListener() {
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
}
