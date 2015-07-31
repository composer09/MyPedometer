package kr.co.composer.pedometer.activity.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import kr.co.composer.pedometer.R;
import kr.co.composer.pedometer.bo.pedometer.Pedometer;

/**
 * Created by composer on 2015-07-08.
 */
public class HistoryAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<String> arrayGroup;
    private HashMap<String, ArrayList<Pedometer>> arrayChild;


    public HistoryAdapter(Context context, ArrayList<String> arrayGroup, HashMap<String, ArrayList<Pedometer>> arrayChild) {
        this.context = context;
        this.arrayGroup = arrayGroup;
        this.arrayChild = arrayChild;
    }

    @Override
    public int getGroupCount() {
        return arrayGroup.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return arrayChild.get(arrayGroup.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return arrayGroup.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return arrayChild.get(arrayGroup.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        PedometerViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = (RelativeLayout) inflater.inflate(R.layout.history_group, null);
            holder = new PedometerViewHolder();
            holder.textGroup = (TextView) convertView.findViewById(R.id.textGroup);
            convertView.setTag(holder);
        }else{
            holder = (PedometerViewHolder)convertView.getTag();
        }
        holder.textGroup.setText(arrayGroup.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        PedometerViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = (RelativeLayout) inflater.inflate(R.layout.history_child_item, null);
            holder = new PedometerViewHolder();
            holder.textTime = (TextView) convertView.findViewById(R.id.pedometer_date);
            holder.textWalk = (TextView) convertView.findViewById(R.id.pedometer_walk);
            convertView.setTag(holder);
        }else{
            holder = (PedometerViewHolder)convertView.getTag();
        }
        String date = arrayChild.get(arrayGroup.get(groupPosition)).get(childPosition).getDate();
        int count = arrayChild.get(arrayGroup.get(groupPosition)).get(childPosition).getPedometerCount();
        holder.textTime.setText(date);
        holder.textWalk.setText(String.valueOf(count));

        convertView.setId(arrayChild.get(arrayGroup.get(groupPosition)).get(childPosition).getRowId());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
