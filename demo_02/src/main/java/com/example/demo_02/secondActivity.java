package com.example.demo_02;

import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class secondActivity extends AppCompatActivity {
    private ListView listView;
    private TextView price;
    private ImageView backPrice,shouye;
    private ActionMode actionMode;
    private ActionMode.Callback callback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.one,menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int i = item.getItemId();
            switch (i){
                case R.id.save:
                    Toast.makeText(secondActivity.this, "保存", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.update:
                    Toast.makeText(secondActivity.this, "更新", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.delete:
                    Toast.makeText(secondActivity.this, "删除", Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        listView = (ListView) findViewById(R.id.listView1);
        listView.setAdapter(new ImageAndText());
        backPrice = (ImageView) findViewById(R.id.photo);
        price = (TextView) findViewById(R.id.price);
        shouye = (ImageView) findViewById(R.id.shouye_off);

        Intent intent = getIntent();
        String value = intent.getStringExtra("gouwu");
        Toast.makeText(secondActivity.this,value,Toast.LENGTH_SHORT).show();

        shouye.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(secondActivity.this,MainActivity.class);

                setResult(400,intent);
                secondActivity.this.finish();
            }
        });



        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(actionMode != null){
                    return false;
                }
                actionMode = secondActivity.this.startActionMode(callback);
                return true;
            }
        });


        /*listView.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });*/
       /* backPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(secondActivity.this,MainActivity.class);
                String value = price.getText().toString().trim();
                intent.putExtra("price",value);
                setResult(400,intent);
                secondActivity.this.finish();
            }
        });*/
    }



    private List<Map<String,Object>> getList(){
        List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
        for(int i = 1; i <= 8 ;i++){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("photo",R.drawable.image1);
            map.put("name","名称"+i);
            map.put("price",0.2+i);
            map.put("rating",i%5);
            map.put("pinpai","小米（MI）");
            list.add(map);
        }
        return list;
    }

    class ImageAndText extends BaseAdapter {

        @Override
        public int getCount() {
            return getList().size();
        }

        @Override
        public Object getItem(int position) {
            return getList().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if(convertView == null){
                view = LayoutInflater.from(secondActivity.this).inflate(R.layout.list_view,null);
            }else{
                view = convertView;
            }
            ImageView imageView = (ImageView) view.findViewById(R.id.photo);
            TextView name = (TextView) view.findViewById(R.id.name);
            TextView price = (TextView) view.findViewById(R.id.price);
            RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
            TextView pinpai = (TextView) view.findViewById(R.id.pinpai);

            imageView.setImageResource(Integer.parseInt(getList().get(position).get("photo").toString()));
            name.setText(getList().get(position).get("name").toString());
            price.setText(getList().get(position).get("price").toString());
            pinpai.setText(getList().get(position).get("pinpai").toString());
            ratingBar.setRating(Float.parseFloat(getList().get(position).get("rating").toString()));
            return view;
        }
    }
}
