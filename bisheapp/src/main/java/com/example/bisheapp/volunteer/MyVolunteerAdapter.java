package com.example.bisheapp.volunteer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bisheapp.R;
import com.example.bisheapp.model.volunteer;

import java.util.List;

/**
 * Created by TianHuan on 2019/5/28.
 */
public class MyVolunteerAdapter extends BaseAdapter {
    private List<volunteer> list;
    private Context context;

    public MyVolunteerAdapter(List<volunteer> list, Context context) {
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
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.volunteer_tem, null);
        } else {
            view = convertView;
        }

        TextView vlname = (TextView) view.findViewById(R.id.t1);
        TextView vlnumber = (TextView) view.findViewById(R.id.t2);
        TextView vltype = (TextView) view.findViewById(R.id.t3);

        vlname.setText("志愿者 " + list.get(position).getName());
        vlnumber.setText("tel:" + list.get(position).getId());
        vltype.setText("类型" + list.get(position).getType());

        return view;
    }
}
