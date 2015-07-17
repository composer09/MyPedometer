package kr.co.composer.pedometer.dao.pedometer;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.text.format.DateFormat;

import java.util.ArrayList;
import java.util.List;

import kr.co.composer.pedometer.R;
import kr.co.composer.pedometer.application.PedometerApplication;
import kr.co.composer.pedometer.bo.pedometer.Pedometer;
import kr.co.composer.pedometer.dao.ContentProviderUri;
import kr.co.composer.pedometer.format.TimeFormatter;

/**
 * Created by composer on 2015-07-09.
 */
public class PedoDAO {

    ContentResolver contentResolver;

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

    public List<Pedometer> getPedometerList() {
        String[] projection = null;
        String selection = null;
        String[] selectionArgs = null;
        String sortOrder = PedoSQLiteOpenHelper.ROW_ID + " desc";
        Cursor cursor = contentResolver.query(ContentProviderUri.pedometer(getUriAuthority()), projection, selection,
                selectionArgs, sortOrder);
        List<Pedometer> historyList = cursor2HistoryList(cursor);
        cursor.close();

        return historyList;
    }

    private List<Pedometer> cursor2HistoryList(Cursor cursor) {
        List<Pedometer> historyList = new ArrayList<Pedometer>();

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



}
