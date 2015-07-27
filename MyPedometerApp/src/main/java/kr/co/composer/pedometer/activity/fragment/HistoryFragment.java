package kr.co.composer.pedometer.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import kr.co.composer.pedometer.R;
import kr.co.composer.pedometer.bo.pedometer.PedoHistoryBO;
import kr.co.composer.pedometer.listener.HistoryClickListener;
import kr.co.composer.pedometer.activity.fragment.adapter.HistoryAdapter;


/**
 * Created by composer on 2015-07-08.
 */
public class HistoryFragment extends Fragment{
    ListView listView = null;
    PedoHistoryBO pedoHistoryBO;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pedoHistoryBO = new PedoHistoryBO();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history,container,false);
        listView = (ListView)view.findViewById(R.id.location_history_listview);
            HistoryAdapter historyAdapter = new HistoryAdapter(getActivity(), R.layout.location_history_item, pedoHistoryBO.getPedometerList());
        listView.setDivider(null);
        listView.setAdapter(historyAdapter);
        listView.setOnItemClickListener(new HistoryClickListener(getActivity()));
        return view;
    }
}