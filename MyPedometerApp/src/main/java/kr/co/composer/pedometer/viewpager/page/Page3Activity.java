package kr.co.composer.pedometer.viewpager.page;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import de.greenrobot.event.EventBus;
import kr.co.composer.pedometer.R;
import kr.co.composer.pedometer.viewpager.adapter.TextChangedEvent;

public class Page3Activity extends Fragment {
	TextView textView;
	EventBus eventBus = EventBus.getDefault();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		eventBus.register(this);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		RelativeLayout layout = (RelativeLayout)inflater.inflate(R.layout.viewpager_page3, container, false);
		textView = (TextView)layout.findViewById(R.id.text03);
		return layout;
	}

	public void onEvent(TextChangedEvent event) {
		textView.setText(event.newText);
	}
}
