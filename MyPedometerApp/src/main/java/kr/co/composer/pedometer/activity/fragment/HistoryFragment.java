package kr.co.composer.pedometer.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;

import kr.co.composer.pedometer.R;
import kr.co.composer.pedometer.activity.fragment.adapter.HistoryAdapter;
import kr.co.composer.pedometer.bo.pedometer.PedoHistoryBO;
import kr.co.composer.pedometer.listener.HistoryClickListener;


/**
 * Created by composer on 2015-07-08.
 */
public class HistoryFragment extends Fragment{
    ExpandableListView listView = null;
    PedoHistoryBO pedoHistoryBO;
    private ArrayList<String> arrayGroup = new ArrayList<String>();
    private HashMap<String, ArrayList<String>> arrayChild = new HashMap<String, ArrayList<String>>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pedoHistoryBO = new PedoHistoryBO();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history,container,false);
        listView = (ExpandableListView)view.findViewById(R.id.location_history_listview);
            HistoryAdapter historyAdapter = new HistoryAdapter(getActivity(), pedoHistoryBO.getGroup(), pedoHistoryBO.getChildList());
        listView.setDivider(null);
        listView.setAdapter(historyAdapter);
        listView.setOnItemClickListener(new HistoryClickListener(getActivity()));
        return view;
    }
}
