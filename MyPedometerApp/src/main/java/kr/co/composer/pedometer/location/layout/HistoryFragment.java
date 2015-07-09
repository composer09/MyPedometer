package kr.co.composer.pedometer.location.layout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import kr.co.composer.pedometer.R;
import kr.co.composer.pedometer.bo.pedometer.PedoHistoryBO;
import kr.co.composer.pedometer.bo.pedometer.Pedometer;


/**
 * Created by composer on 2015-07-08.
 */
public class HistoryFragment extends Fragment{
    ListView listView = null;
    ArrayList<Pedometer> pedometerList;
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
        pedometerList = (ArrayList)pedoHistoryBO.getPedometerList();
        HistoryAdapter historyAdapter = new HistoryAdapter(getActivity(), R.layout.location_history_item, pedometerList);
        return view;
    }
}
