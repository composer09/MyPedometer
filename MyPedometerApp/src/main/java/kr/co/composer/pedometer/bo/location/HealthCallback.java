package kr.co.composer.pedometer.bo.location;

import android.location.Location;

public interface HealthCallback {
	public void updateTime(int time);
	public void updateHealth(float validDistance, float invalidDistance, int point, int calorie, float speedMeterPerSecond, String locationProvider);
	public void updateLocation(Location location);
	public void shake(float speedMeterPerSecond);
	public void alertGps();
}
