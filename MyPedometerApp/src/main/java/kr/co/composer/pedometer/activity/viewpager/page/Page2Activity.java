package kr.co.composer.pedometer.activity.viewpager.page;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import de.greenrobot.event.EventBus;
import kr.co.composer.pedometer.R;
import kr.co.composer.pedometer.activity.viewpager.adapter.WeekChangedEvent;
import kr.co.composer.pedometer.bo.pedometer.PedoHistoryBO;


public class Page2Activity extends Fragment {
    private TextView textView;
    private EventBus eventBus = EventBus.getDefault();
    private int weekCount;
    private PedoHistoryBO pedoHistoryBO;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventBus.register(this);
        pedoHistoryBO = new PedoHistoryBO();
        weekCount = pedoHistoryBO.getWeekCount();
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

}
