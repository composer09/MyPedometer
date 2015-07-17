package kr.co.composer.pedometer.viewpager.page;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.greenrobot.event.EventBus;
import kr.co.composer.pedometer.R;
import kr.co.composer.pedometer.application.PedometerApplication;
import kr.co.composer.pedometer.dao.pedometer.PedoSQLiteOpenHelper;
import kr.co.composer.pedometer.format.TimeFormatter;
import kr.co.composer.pedometer.log.LogTest;
import kr.co.composer.pedometer.viewpager.adapter.WeekChangedEvent;


public class Page2Activity extends Fragment {
    private TextView textView;
    private EventBus eventBus = EventBus.getDefault();
    private long range1;
    private long range2;
    private int weekCount;

    PedoSQLiteOpenHelper helper;
    SQLiteDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventBus.register(this);
        helper = new PedoSQLiteOpenHelper(PedometerApplication.contextWrapper.getApplicationContext());
        db = helper.getWritableDatabase();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
            Date date1 = sdf.parse(getWeek()[0]+" 00:00:01");
            Date date2 = sdf.parse(getWeek()[1] + " 23:59:59");
            range1 = date1.getTime();
            range2 = date2.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        LogTest.i("범위 확인",getWeek()[0]+" 00:00:01");
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.viewpager_page2, container, false);
        textView = (TextView) layout.findViewById(R.id.text02);
        textView.setText(String.valueOf(weekCount));
        return layout;
    }

    public void onEvent(WeekChangedEvent weekChange) {
        if(weekChange.event){
            weekCount ++;
        }
        textView.setText(String.valueOf(weekCount));
    }

    private String getUriAuthority() {
        return PedometerApplication.contextWrapper.getString(R.string.url_content_authority);
    }

    private String[] getWeek(){
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
