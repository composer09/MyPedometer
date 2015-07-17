package kr.co.composer.pedometer.bo.pedometer;

/**
 * Created by composer on 2015-07-09.
 */
public class Pedometer {
    private int rowId;

    private int pedometerCount;

    private long time;

    private String timeToString;

    //setter
    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    public void setPedometerCount(int pedometerCount) {
        this.pedometerCount = pedometerCount;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setTimeToString(String time){
        this.timeToString = time;
    }


    /////////////////////////////////////////////////////////////////////////////
    //getter
    public int getRowId(){
        return rowId;
    }

    public int getPedometerCount() {
        return pedometerCount;
    }

    public long getTime() {
        return time;
    }

    public String getTimeToString(){
        return timeToString;
    }
}
