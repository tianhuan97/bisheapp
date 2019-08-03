package com.example.bisheapp.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bisheapp.R;
import com.example.bisheapp.model.user;

import java.util.List;

/**
 * Created by TianHuan on 2019/5/29.
 */
public class MyUserAdapter extends BaseAdapter {
    private List<user> list;
    private Context context;

    public MyUserAdapter(List<user> list, Context context) {
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
            view = LayoutInflater.from(context).inflate(R.layout.user_tem, null);
        } else {
            view = convertView;
        }

        TextView ulname = (TextView) view.findViewById(R.id.u1);
        TextView ulsex = (TextView) view.findViewById(R.id.u2);

        ulname.setText("用户： " + list.get(position).getName());
        ulsex.setText("性别： " + list.get(position).getSex());

        return view;
    }
}
