package kr.co.composer.pedometer.bo.pedometer;

/**
 * Created by composer on 2015-07-09.
 */
public class Pedometer {
    private int rowId;

    private int pedometerCount;

    private String date;

    //setter
    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    public void setPedometerCount(int pedometerCount) {
        this.pedometerCount = pedometerCount;
    }

    public void setDate(String date) {
        this.date = date;
    }

    /////////////////////////////////////////////////////////////////////////////
    //getter
    public int getRowId(){
        return rowId;
    }

    public int getPedometerCount() {
        return pedometerCount;
    }

    public String getDate() {
        return date;
    }

}
