package kr.co.composer.pedometer.util;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.util.Log;

public class GPSUtil {
	public static boolean isOnGPS(ContentResolver contentResolver, LocationManager locationManager) {
		String gs = android.provider.Settings.Secure.getString(contentResolver, android.provider.Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
		Log.d("GPSUtil", gs);
		
		boolean enabled = gs.indexOf("gps") > 0;
		
		if (enabled == true) {
			return true;
		}
		
		return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

	}
	
	public static AlertDialog.Builder getGpsAlertDialog(final Context context) {
		return new AlertDialog.Builder(context)
			.setTitle("GPS On/Off 확인")
			.setMessage("GPS가 Off 상태입니다. 경로 그리기 기능은 GPS On상태에서만 이용하실 수 있습니다. GPS를 켜시겠습니까?")
			.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(
								android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						intent.addCategory(Intent.CATEGORY_DEFAULT);
						context.startActivity(intent);
					}
				})
			.setNegativeButton("No", 
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
	}
}
