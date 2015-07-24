package kr.co.composer.pedometer.activity.viewpager.page;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import kr.co.composer.pedometer.R;
import kr.co.composer.pedometer.bo.pedometer.PedoHistoryBO;

public class MaxViewpager extends Fragment {
    private TextView textView;
    private PedoHistoryBO pedoHistoryBO;
    private int maxCount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pedoHistoryBO = new PedoHistoryBO();
        maxCount = pedoHistoryBO.getMaxCount();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.viewpager_page3, container, false);
        textView = (TextView) layout.findViewById(R.id.text03);
        textView.setText(maxCount + "");
        return layout;
    }
}
