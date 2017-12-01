package com.example.demo_03;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private GridView gridView;

    private int [] data = {R.drawable.buythg,R.drawable.newcategory,R.drawable.nicefood,R.drawable.paymoney,
                            R.drawable.playfun,R.drawable.service,R.drawable.buythg,R.drawable.buythg,R.drawable.buythg,
                            R.drawable.buythg,R.drawable.buythg};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new MyAdapter());
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,""+position,Toast.LENGTH_SHORT);
            }
        });
    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return data.length;
        }

        @Override
        public Object getItem(int position) {
            return data[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if(convertView == null){
                imageView = new ImageView(MainActivity.this);
                imageView.setPadding(8,8,8,8);
            } else {
                imageView = (ImageView) convertView;
            }
            imageView.setImageResource(data[position]);
            return imageView;
        }
    }
}
