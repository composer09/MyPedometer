package kr.co.composer.pedometer.api;

import java.security.Provider;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;


public class CurrentLocation {
	private LocationManager locationManager;
	private Location location;
	private Context context;
	
	public CurrentLocation(){}
	
	public CurrentLocation(Context context){
		this.context = context;
	}
	
	public double[] getCurrentLocation(){
		double[] result = null;
		Criteria criteria = null;
		
		if(criteria == null){
			criteria = new Criteria();
//			criteria.setAccuracy(Criteria.ACCURACY_COARSE);
//			criteria.setAltitudeRequired(false);
//			criteria.setBearingRequired(false);
//			criteria.setSpeedRequired(false);
		}
		
		locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		
		String provider = locationManager.getBestProvider(criteria, true);
		location = locationManager.getLastKnownLocation(provider);
		result = new double[]{location.getLatitude(),location.getLongitude()};
		
//		if(provider == null){
//			provider = "network";
//			location = locationManager.getLastKnownLocation(provider);
//			result = new double[]{location.getLatitude(),location.getLongitude()};
//		}
		return result;
	}
}
