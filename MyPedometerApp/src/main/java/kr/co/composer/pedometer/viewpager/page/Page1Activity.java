package kr.co.composer.pedometer.viewpager.page;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import kr.co.composer.pedometer.R;


public class Page1Activity extends Fragment {
	TextView textView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		RelativeLayout layout = (RelativeLayout)inflater.inflate(R.layout.viewpager_page1, container, false);
		textView = (TextView)layout.findViewById(R.id.text01);
		return layout;
	}
	
	public void setTextView(String text){
		textView.setText(text);
	}
}
