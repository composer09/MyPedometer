package kr.co.composer.pedometer.dao.pedometer;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.DateFormat;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import kr.co.composer.pedometer.R;
import kr.co.composer.pedometer.application.PedometerApplication;
import kr.co.composer.pedometer.bo.pedometer.Pedometer;
import kr.co.composer.pedometer.dao.ContentProviderUri;
import kr.co.composer.pedometer.format.TimeFormatter;
import kr.co.composer.pedometer.log.LogTest;

/**
 * Created by composer on 2015-07-09.
 */
public class PedoDAO {

    ContentResolver contentResolver;
    PedoSQLiteOpenHelper helper;
    SQLiteDatabase db;
    int todayCount;

    public void init() {
        this.contentResolver = PedometerApplication.contextWrapper.getContentResolver();
    }

    public void insert(Pedometer pedometer) {
        ContentValues contVal = new ContentValues();
        contVal.put(PedoSQLiteOpenHelper.TIME, pedometer.getTime());
        contVal.put(PedoSQLiteOpenHelper.PEDOMETER_COUNT, pedometer.getPedometerCount());
        contentResolver.insert(ContentProviderUri.pedometer(getUriAuthority()), contVal);

    }

    private String getUriAuthority() {
        return PedometerApplication.contextWrapper.getString(R.string.url_content_authority);
    }

    public ArrayList<Pedometer> getPedometerList() {
        String[] projection = null;
        String selection = null;
        String[] selectionArgs = null;
        String sortOrder = PedoSQLiteOpenHelper.ROW_ID + " desc";
        Cursor cursor = contentResolver.query(ContentProviderUri.pedometer(getUriAuthority()), projection, selection,
                selectionArgs, sortOrder);
        ArrayList<Pedometer> historyList = cursor2HistoryList(cursor);
        cursor.close();

        return historyList;
    }

    public boolean getTodayCheck(){
        long range1 = 0;
        long range2 = 0;
        String today = null;

        helper = new PedoSQLiteOpenHelper(PedometerApplication.contextWrapper.getApplicationContext());
        db = helper.getWritableDatabase();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, 0);
            today = sdf.format(cal.getTime());

            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
            Date date1 = sdf2.parse(today + " 00:00:01");
            Date date2 = sdf2.parse(today + " 23:59:59");
//            Date date1 = sdf2.parse("2015-09-01 00:00:01");
//            Date date2 = sdf2.parse("2015-09-01 23:59:59");
            range1 = date1.getTime();
            range2 = date2.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        LogTest.i("범위 확인", today + " 00:00:01");
        LogTest.i("범위 확인", today + " 23:59:59");
        Cursor cursor = db.rawQuery("select * from pedometer where date between " + range1 + " and " + range2, null);
        LogTest.i("커서카운트", cursor.getCount());
        if (cursor.getCount() != 0) {
                return true;
            }
        return false;
    }

    public int getTodayCount() {
        long range1 = 0;
        long range2 = 0;
        String today = null;

        helper = new PedoSQLiteOpenHelper(PedometerApplication.contextWrapper.getApplicationContext());
        db = helper.getWritableDatabase();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, 0);
            today = sdf.format(cal.getTime());

            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
            Date date1 = sdf2.parse(today + " 00:00:01");
            Date date2 = sdf2.parse(today + " 23:59:59");
//            Date date1 = sdf2.parse("2015-09-01 00:00:01");
//            Date date2 = sdf2.parse("2015-09-01 23:59:59");
            range1 = date1.getTime();
            range2 = date2.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        LogTest.i("범위 확인", today + " 00:00:01");
        LogTest.i("범위 확인", today + " 23:59:59");
        Cursor cursor = db.rawQuery("select * from pedometer where date between " + range1 + " and " + range2, null);
        LogTest.i("커서카운트", cursor.getCount());
        if (cursor.getCount() != 0) {
            LogTest.i("커서카운트", cursor.getCount());
            while (cursor.moveToNext()) {
                int columnIndex = cursor.getColumnIndex(PedoSQLiteOpenHelper.PEDOMETER_COUNT);
                todayCount += cursor.getInt(columnIndex);
            }
        }
        return todayCount;
    }


    public int getWeekCount() {
        int weekCount = 0;
        long range1 = 0;
        long range2 = 0;
        helper = new PedoSQLiteOpenHelper(PedometerApplication.contextWrapper.getApplicationContext());
        db = helper.getWritableDatabase();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
            Date date1 = sdf.parse(getWeek()[0] + " 00:00:01");
            Date date2 = sdf.parse(getWeek()[1] + " 23:59:59");
            range1 = date1.getTime();
            range2 = date2.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        LogTest.i("범위 확인", getWeek()[0] + " 00:00:01");
        LogTest.i("범위 확인", getWeek()[1] + " 23:59:59");
        Cursor cur = db.rawQuery("select * from pedometer where date between " + range1 + " and " + range2, null);
        while (cur.moveToNext()) {
            int columnIndex = cur.getColumnIndex(PedoSQLiteOpenHelper.PEDOMETER_COUNT);
            weekCount += cur.getInt(columnIndex);
            columnIndex = cur.getColumnIndex(PedoSQLiteOpenHelper.TIME);
            Log.i("between-날짜 확인", "" + DateFormat.format(
                    TimeFormatter.HISTORY_DATE_FORMAT,
                    cur.getLong(columnIndex)));
        }

        return weekCount;
    }

    private ArrayList<Pedometer> cursor2HistoryList(Cursor cursor) {
        ArrayList<Pedometer> historyList = new ArrayList<Pedometer>();

        try {
            while (cursor.moveToNext()) {
                Pedometer pedometer = new Pedometer();

                int columnIndex = cursor.getColumnIndex(PedoSQLiteOpenHelper.ROW_ID);
                pedometer.setRowId(cursor.getInt(columnIndex));

                columnIndex = cursor.getColumnIndex(PedoSQLiteOpenHelper.PEDOMETER_COUNT);
                pedometer.setPedometerCount(cursor.getInt(columnIndex));

                columnIndex = cursor.getColumnIndex(PedoSQLiteOpenHelper.TIME);
                pedometer.setTimeToString(DateFormat.format(
                        TimeFormatter.HISTORY_DATE_FORMAT,
                        cursor.getLong(columnIndex)).toString());

                historyList.add(pedometer);
            }// while
        } catch (Exception e) {
        }

        return historyList;
    }

    private String[] getWeek() {
        String[] getWeekArray = new String[2];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.SUNDAY);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        cal.add(Calendar.DAY_OF_MONTH, (-(dayOfWeek - 1)));
        getWeekArray[0] = sdf.format(cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, 6);
        getWeekArray[1] = sdf.format(cal.getTime());
        return getWeekArray;
    }


}
