package com.example.bisheapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.bisheapp.model.elder;

import java.util.ArrayList;
import java.util.List;

public class elderHouse extends AppCompatActivity {
    private ListView elderList;
    private List<elder> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elder_house);

        elderList = (ListView) findViewById(R.id.elderList);

        for(int i = 0; i < 16; i++){
            elder elder = new elder();
            elder.setBz("老年活动中心");
            elder.setName("活动中心"+i);
            elder.setRating(""+i%5);
            list.add(elder);
        }
        elderList.setAdapter(new MyAdapter(list,elderHouse.this));
    }

    class MyAdapter extends BaseAdapter{
        private Context context;
        private List<elder> list;

        public MyAdapter(List<elder> list,Context context){
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
            if(convertView == null){
                view = LayoutInflater.from(context).inflate(R.layout.elder_child,null);
            } else {
                view = convertView;
            }
            TextView sname = (TextView) view.findViewById(R.id.sname);
            TextView bz = (TextView) view.findViewById(R.id.bz);
            RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);

            sname.setText(list.get(position).getName());
            bz.setText(list.get(position).getBz());
            ratingBar.setRating(Float.parseFloat(list.get(position).getRating()));

            return view;
        }
    }
}
