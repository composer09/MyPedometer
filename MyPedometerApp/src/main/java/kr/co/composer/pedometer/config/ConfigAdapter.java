package kr.co.composer.pedometer.config;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import kr.co.composer.pedometer.R;


/**
 * Created by composer on 2015-07-09.
 */
public class ConfigAdapter extends BaseAdapter {
    LayoutInflater inflater;
    int layout;
    ArrayList<ConfigItem> arraySrc;

    public ConfigAdapter(Context context, int layout, ArrayList<ConfigItem> arraySrc) {
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layout = layout;
        this.arraySrc = arraySrc;

    }

    @Override
    public int getCount() {
        return arraySrc.size();
    }

    @Override
    public Object getItem(int position) {
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
        TextView textTitle = (TextView)convertView.findViewById(R.id.config_title);
        textTitle.setText(arraySrc.get(position).title);
        TextView textText = (TextView)convertView.findViewById(R.id.config_text);
        textText.setText(arraySrc.get(position).text);
        return convertView;
    }
}
