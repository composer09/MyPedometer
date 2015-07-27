package kr.co.composer.pedometer.activity.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import kr.co.composer.pedometer.R;
import kr.co.composer.pedometer.bo.pedometer.Pedometer;

/**
 * Created by composer on 2015-07-08.
 */
public class HistoryAdapter extends BaseAdapter {
    Context context;

    LayoutInflater inflater;

    int layout;
    ArrayList<Pedometer> arraySrc;

    public HistoryAdapter(Context context, int layout, ArrayList<Pedometer> arraySrc) {
        this.context = context;
        this.inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layout = layout;
        this.arraySrc = arraySrc;
    }

    @Override
    public int getCount() {
        return arraySrc.size();
    }

    @Override
    public Pedometer getItem(int position) {
        return arraySrc.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
        }
        TextView textWalk = (TextView) convertView.findViewById(R.id.pedometer_walk);
        textWalk.setText(String.valueOf(arraySrc.get(position).getPedometerCount()));

        TextView textTime = (TextView) convertView.findViewById(R.id.pedometer_date);
        textTime.setText(arraySrc.get(position).getDate());

        convertView.setId(arraySrc.get(position).getRowId());
        return convertView;
    }
}
