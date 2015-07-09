package kr.co.composer.pedometer.location.layout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import kr.co.composer.pedometer.R;


/**
 * Created by composer on 2015-07-08.
 */
public class HistoryFragment extends Fragment{
    ListView listView = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history,container,false);
        listView = (ListView)view.findViewById(R.id.location_history_listview);
        return view;
    }
}
