package kr.co.composer.pedometer.sharedpref;

/**
 * Created by composer on 2015-07-26.
 */
public class PedoPreferenceManager extends AbstractPreferenceManager {
    private static PedoPreferenceManager pedoPreferenceManager = null;
    public static final String CURRENT_TIME = "currentTime";

    public static synchronized PedoPreferenceManager getInstance() {
        if (pedoPreferenceManager == null) {
            pedoPreferenceManager = new PedoPreferenceManager();
        }
        return pedoPreferenceManager;
    }

    //setter
    public void setCurrentTime(long time) {
        setLongValue(CURRENT_TIME, time);
    }

    //getter
    public long getCurrentTime(){
        return getLongValue(CURRENT_TIME, 0L);
    }
}
