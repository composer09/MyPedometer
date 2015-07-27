package kr.co.composer.pedometer.dao.pedometer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

/**
 * Created by composer on 2015-07-09.
 */
public class PedoSQLiteOpenHelper extends SQLiteOpenHelper {
    public static final String DATABASE_FILE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myPedometer/pedometer.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "pedometer";
    public static final String ROW_ID = "rowId";
    public static final String DATE = "date";
    public static final String PEDOMETER_COUNT = "pedometerCount";

    public PedoSQLiteOpenHelper(Context context) {
        super(context, DATABASE_FILE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + TABLE_NAME + " ("
                + ROW_ID + " integer primary key autoincrement, "
                + DATE + " text, "
                + PEDOMETER_COUNT + " integer "
                +");";
        Log.i("데이터베이스 테이블생성", sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table if exists " + TABLE_NAME + ";";
        db.execSQL(sql);
        onCreate(db);
    }
}
