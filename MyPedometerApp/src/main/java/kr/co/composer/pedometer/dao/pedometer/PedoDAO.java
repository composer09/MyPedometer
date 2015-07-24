package kr.co.composer.pedometer.dao.pedometer;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.DateFormat;

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

/**
 * Created by composer on 2015-07-09.
 */
public class PedoDAO {
    private static final String TABLE_NAME = PedoSQLiteOpenHelper.TABLE_NAME;
    private static final String ROW_ID = PedoSQLiteOpenHelper.ROW_ID;
    private static final String TIME = PedoSQLiteOpenHelper.TIME;
    private static final String PEDOMETER_COUNT = PedoSQLiteOpenHelper.PEDOMETER_COUNT;

    private ContentResolver contentResolver;
    private PedoSQLiteOpenHelper helper;
    private SQLiteDatabase db;
    private String today;

    public void init() {
        this.contentResolver = PedometerApplication.contextWrapper.getContentResolver();
    }

    public void insert(Pedometer pedometer) {
        ContentValues contVal = new ContentValues();
        contVal.put(PedoSQLiteOpenHelper.TIME, pedometer.getTime());
        contVal.put(PedoSQLiteOpenHelper.PEDOMETER_COUNT, pedometer.getPedometerCount());
        contentResolver.insert(ContentProviderUri.pedometer(getUriAuthority()), contVal);
    }

    public void update(Pedometer pedometer) {
        int rowID = 0;
        ContentValues contVal = new ContentValues();
        contVal.put(PedoSQLiteOpenHelper.TIME, pedometer.getTime());
        contVal.put(PedoSQLiteOpenHelper.PEDOMETER_COUNT, pedometer.getPedometerCount());
        long range1 = 0;
        long range2 = 0;

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
            range1 = date1.getTime();
            range2 = date2.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Cursor cursor = db.rawQuery("select * from pedometer where date between " + range1 + " and " + range2, null);
        while (cursor.moveToNext()) {
            int columnIndex = cursor.getColumnIndex(ROW_ID);
            rowID = cursor.getInt(columnIndex);
        }
        contentResolver.update(ContentProviderUri.pedometer(getUriAuthority()), contVal
                , ROW_ID + " = " + rowID, null);

        cursor.close();
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

    public boolean getTodayCheck() {
        long range1 = 0;
        long range2 = 0;

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
            range1 = date1.getTime();
            range2 = date2.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Cursor cursor = db.rawQuery("select * from pedometer where date between " + range1 + " and " + range2, null);
        if (cursor.getCount() != 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    public int getTodayCount() {
        long range1 = 0;
        long range2 = 0;
        int todayCount = 0;

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
            range1 = date1.getTime();
            range2 = date2.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Cursor cursor = db.rawQuery("select * from pedometer where date between " + range1 + " and " + range2, null);
        while (cursor.moveToNext()) {
            int columnIndex = cursor.getColumnIndex(PedoSQLiteOpenHelper.PEDOMETER_COUNT);
            todayCount += cursor.getInt(columnIndex);
        }
        cursor.close();
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
        Cursor cursor = db.rawQuery("select * from pedometer where date between " + range1 + " and " + range2, null);
        while (cursor.moveToNext()) {
            int columnIndex = cursor.getColumnIndex(PEDOMETER_COUNT);
            weekCount += cursor.getInt(columnIndex);
        }
        cursor.close();
        return weekCount;
    }

    public int getMaxCount() {
        helper = new PedoSQLiteOpenHelper(PedometerApplication.contextWrapper.getApplicationContext());
        db = helper.getWritableDatabase();
        int maxCount = 0;
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + PEDOMETER_COUNT + " = (SELECT max(" + PEDOMETER_COUNT + ") from " + TABLE_NAME + ")", null);
        while (cursor.moveToNext()) {
            int columnIndex = cursor.getColumnIndex(PedoSQLiteOpenHelper.PEDOMETER_COUNT);
            maxCount = cursor.getInt(columnIndex);
        }
        cursor.close();
        return maxCount;
    }

    private ArrayList<Pedometer> cursor2HistoryList(Cursor cursor) {
        ArrayList<Pedometer> historyList = new ArrayList<Pedometer>();

        try {
            while (cursor.moveToNext()) {
                Pedometer pedometer = new Pedometer();

                int columnIndex = cursor.getColumnIndex(ROW_ID);
                pedometer.setRowId(cursor.getInt(columnIndex));

                columnIndex = cursor.getColumnIndex(PEDOMETER_COUNT);
                pedometer.setPedometerCount(cursor.getInt(columnIndex));

                columnIndex = cursor.getColumnIndex(TIME);
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

    private String getUriAuthority() {
        return PedometerApplication.contextWrapper.getString(R.string.url_content_authority);
    }

}
