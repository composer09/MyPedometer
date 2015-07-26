package kr.co.composer.pedometer.sharedpref;

public class ConfigPreferenceManager extends AbstractPreferenceManager {
    private static ConfigPreferenceManager configInstance = null;
    public static final int DEFAULT_INDEX = 4;
    public static final int DEFAULT_VALUE = 85;
    public static final String SENSITIVITY_INDEX = "sensitivityIndex";
    public static final String SENSITIVITY_VALUE = "sensitivityValue";


    public static synchronized ConfigPreferenceManager getInstance() {
        if (configInstance == null) {
            configInstance = new ConfigPreferenceManager();
        }
        return configInstance;
    }

    //setter
    public void setSensitivityIndex(int sensitivity) {
        setIntValue(SENSITIVITY_INDEX, sensitivity);
    }

    public void setSensitivityValue(int sentitivity) {
        setIntValue(SENSITIVITY_VALUE, sentitivity);
    }

    //getter
    public int getSensitivityIndex() {
        return getIntValue(SENSITIVITY_INDEX, DEFAULT_INDEX);
    }

    public int getSensitivityValue(){
        return getIntValue(SENSITIVITY_VALUE, DEFAULT_VALUE);
    }


}
