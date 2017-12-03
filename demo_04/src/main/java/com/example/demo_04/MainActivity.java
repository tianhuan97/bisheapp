package com.example.demo_04;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new ImageAndText());
    }

    private List<Map<String,Object>> list(){
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        for(int i = 1; i <= 16; i++){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("imageView",R.drawable.newcategory);
            map.put("name","名称"+i);
            list.add(map);
        }
        return list;
    }

    class ImageAndText extends BaseAdapter{

        @Override
        public int getCount() {
            return list().size();
        }

        @Override
        public Object getItem(int position) {
            return list().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if(convertView == null){
                view = LayoutInflater.from(MainActivity.this).inflate(R.layout.gridview,null);
            } else {
                view = convertView;
            }
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            TextView textView = (TextView) view.findViewById(R.id.name);
            imageView.setImageResource(Integer.parseInt(list().get(position).get("imageView").toString()));
            textView.setText(list().get(position).get("name").toString());
            return view;
        }

    }
}
