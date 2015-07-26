package kr.co.composer.pedometer.application;

import android.app.Application;
import android.content.ContextWrapper;

import kr.co.composer.pedometer.sharedpref.ConfigPreferenceManager;
import kr.co.composer.pedometer.sharedpref.PedoPreferenceManager;

/**
 * Created by composer on 2015-06-05.
 */
public class PedometerApplication extends Application {
    public static ContextWrapper contextWrapper;

    @Override
    public void onCreate() {
        super.onCreate();
        contextWrapper = this;
        ConfigPreferenceManager.getInstance().initPreferenceData(contextWrapper);
        PedoPreferenceManager.getInstance().initPreferenceData(contextWrapper);
    }
}