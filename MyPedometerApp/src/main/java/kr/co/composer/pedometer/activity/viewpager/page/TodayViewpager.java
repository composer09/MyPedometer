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


public class TodayViewpager extends Fragment {
	TextView textView;
	int todayCount;
	PedoHistoryBO pedoHistoryBO;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EventBus.getDefault().register(this);
		pedoHistoryBO = new PedoHistoryBO();
		todayCount = pedoHistoryBO.getTodayCount();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		RelativeLayout layout = (RelativeLayout)inflater.inflate(R.layout.viewpager_page1, container, false);
		textView = (TextView)layout.findViewById(R.id.text01);
		textView.setText(todayCount+"");
		return layout;
	}

	public void onEvent(TextChangedEvent event) {
		textView.setText(event.newText+"");
	}
}
