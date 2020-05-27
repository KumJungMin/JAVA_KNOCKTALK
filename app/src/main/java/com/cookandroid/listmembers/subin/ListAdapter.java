package com.cookandroid.listmembers.subin;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cookandroid.listmembers.R;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {
    ArrayList<ListData> doList;

    public ListAdapter(ArrayList<ListData> data){
        doList=data;
    }


    @Override
    public int getCount(){
        return doList.size();
    }
    @Override
    public long getItemId(int position){
        return position;
    }
    @Override
    public ListData getItem(int position){
        return doList.get(position);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context contexts = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) contexts.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_item, parent, false);
        }


        TextView keyword = (TextView)convertView.findViewById(R.id.i_name);
        TextView memo = (TextView)convertView.findViewById(R.id.i_memo);
        TextView place = (TextView)convertView.findViewById(R.id.i_place);
        TextView time = (TextView)convertView.findViewById(R.id.i_time);
        View colors = (View)convertView.findViewById(R.id.i_color);
        LinearLayout contents = (LinearLayout)convertView.findViewById(R.id.Contents);
        LinearLayout scon = (LinearLayout)convertView.findViewById(R.id.subContents);
        LinearLayout whole = (LinearLayout)convertView.findViewById(R.id.whole);

        contents.setVisibility(View.VISIBLE);




        keyword.setText(doList.get(position).getKeyword());
        place.setText(doList.get(position).getPlace());
        time.setText(doList.get(position).getStart()+"시 ~ "+doList.get(position).getEnd()+"시");
        keyword.setText(doList.get(position).getKeyword());
        colors.setBackgroundColor(Color.parseColor(doList.get(position).getColor()));
        memo.setText(doList.get(position).getMemo());
        if(doList.get(position).getDoor()) {
            scon.setVisibility(View.VISIBLE);
        }
        else {
            scon.setVisibility(View.GONE);
        }



        return convertView;
    }

}
