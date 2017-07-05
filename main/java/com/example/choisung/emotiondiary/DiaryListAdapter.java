package com.example.choisung.emotiondiary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ChoISung on 2017-06-04.
 */

public class DiaryListAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<DiaryText> data;
    private LayoutInflater inflater;

    public DiaryListAdapter(Context context, int layout, ArrayList<DiaryText> data){
        this.context = context;
        this.layout = layout;
        this.data = data;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(layout, parent, false);
        }

        TextView TV_item_entries_title = (TextView) convertView.findViewById(R.id.TV_item_entries_title);
        TextView TV_item_entries_summary = (TextView) convertView.findViewById(R.id.TV_item_entries_summary);
        TextView TV_item_entries_day = (TextView) convertView.findViewById(R.id.TV_item_entries_day);

        TextView TV_item_entries_dayOfWeek = (TextView) convertView.findViewById(R.id.TV_item_entries_dayOfWeek);
        TextView TV_item_entries_time = (TextView) convertView.findViewById(R.id.TV_item_entries_time);

        TV_item_entries_title.setText(data.get(position).getTitle() + "");
        TV_item_entries_summary.setText(data.get(position).getContents() + "");
        TV_item_entries_day.setText(data.get(position).getDate() + "");

        TV_item_entries_dayOfWeek.setText(data.get(position).getDayOfWeek() + "");
        TV_item_entries_time.setText(data.get(position).getTime() + "");

        return convertView;
    }
}
