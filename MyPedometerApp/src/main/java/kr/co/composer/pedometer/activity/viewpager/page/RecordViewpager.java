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
import kr.co.composer.pedometer.activity.viewpager.adapter.TextChangedEvent;
import kr.co.composer.pedometer.bo.pedometer.PedoHistoryBO;

public class RecordViewpager extends Fragment {
    TextView textView;
    int recordCount;
    int count;
    PedoHistoryBO pedoHistoryBO;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        pedoHistoryBO = new PedoHistoryBO();
        recordCount = pedoHistoryBO.getMaxCount();
//        count = maxCount- pedoHistoryBO.getTodayCount();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.viewpager_page3, container, false);
        textView = (TextView) layout.findViewById(R.id.recordCountText);
        textView.setText(recordCount + "");
        return layout;
    }

    public void onEvent(TextChangedEvent event) {
        if(event.maxText <= event.newText){
            textView.setText(event.newText+"");
        }
    }
}
