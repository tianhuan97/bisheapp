package com.example.demo_11;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by TianHuan on 2017/12/1.
 */
public class MyAdapter extends BaseAdapter {
    private LinearLayout tem;
    private List<PhoneInfo> list;
    private Context context;
    public MyAdapter(List<PhoneInfo> list,Context context){
        this.list = list;
        this.context = context;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if(convertView==null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.tem,null);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.number = (TextView) convertView.findViewById(R.id.number);
            viewHolder.name.setText(list.get(position).getName());
            viewHolder.number.setText(list.get(position).getNumber());
        }
        return convertView;
    }
    public class ViewHolder{
        TextView name;
        TextView number;
    }
}
