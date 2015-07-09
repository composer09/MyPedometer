package kr.co.composer.pedometer.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.util.Log;

import kr.co.composer.pedometer.listener.GpsStateChange;

public class GpsEventBroadcast extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		 LocationManager mLocationManager;
		 mLocationManager = (LocationManager)context.getSystemService( Context.LOCATION_SERVICE );
		 GpsStateChange gpsChange = new GpsStateChange(context);
		 mLocationManager.addGpsStatusListener(gpsChange);
        if (intent.getAction().matches("android.location.PROVIDERS_CHANGED"))
        { 
        	Log.i("gpsChanged", "gps상태변화");
        }
        
	}

	
	
}
