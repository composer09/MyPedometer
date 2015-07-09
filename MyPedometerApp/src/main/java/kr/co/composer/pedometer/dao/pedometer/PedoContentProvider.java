package kr.co.composer.pedometer.dao.pedometer;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import kr.co.composer.pedometer.R;
import kr.co.composer.pedometer.dao.ContentProviderUri;

/**
 * Created by composer on 2015-07-09.
 */
public class PedoContentProvider extends ContentProvider{
    public static final String TABLE_NAME = PedoSQLiteOpenHelper.TABLE_NAME;
    private SQLiteDatabase db;
    private PedoSQLiteOpenHelper helper;

    @Override
    public boolean onCreate() {
        helper = new PedoSQLiteOpenHelper(getContext());
        db = helper.getWritableDatabase();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        SQLiteDatabase db = helper.getReadableDatabase();
        qb.setTables(TABLE_NAME);
        Cursor cursor = qb.query(db, projection, selection, null, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        db = helper.getWritableDatabase();

        long rowId = db.insert(TABLE_NAME, "", values);
        if (rowId > 0) {
            Uri rowUri = ContentUris.appendId(ContentProviderUri.pedometer(getUriAuthority()).buildUpon(), rowId).build();
            getContext().getContentResolver().notifyChange(rowUri, null);
            return rowUri;
        }
        throw new SQLException("Failed to insert row into " + uri);}

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        String whereClause = selection;
        String[] whereArgs = selectionArgs;
        int result = db.delete(TABLE_NAME, whereClause, whereArgs);
        return result;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        String whereClause = selection;
        String[] whereArgs = selectionArgs;
        int result = db.update(TABLE_NAME, values, whereClause, whereArgs);
        return result;
    }

    private String getUriAuthority() {
        return getContext().getString(R.string.url_content_authority);
    }


    @Override
    public String getType(Uri uri) {
        return null;
    }
}
