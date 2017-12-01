package com.example.demo_02;

import android.content.Intent;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.example.demo_02.R.id.photo;

public class MainActivity extends AppCompatActivity {
    private ImageView food,play,shenghuo,menu,buy,pay,catage,newcar;
    private TextView gouwu;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new ImageAndText());

        registerForContextMenu(listView);

        food = (ImageView) findViewById(R.id.food);
        play = (ImageView) findViewById(R.id.play);
        shenghuo = (ImageView) findViewById(R.id.shenghuo);
        menu = (ImageView) findViewById(R.id.meun);
        buy = (ImageView) findViewById(R.id.buy);
        pay = (ImageView) findViewById(R.id.pay);
        catage = (ImageView) findViewById(R.id.cate);
        gouwu = (TextView) findViewById(R.id.gouwu);
        newcar = (ImageView) findViewById(R.id.newcar);

        newcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,secondActivity.class);
                /*overridePendingTransition(R.anim.tip_right,R.anim.tip_left);*/
                startActivity(intent);

            }
        });

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = gouwu.getText().toString().trim();
                Intent intent = new Intent(MainActivity.this,secondActivity.class);
                intent.putExtra("gouwu",value);
                startActivityForResult(intent,200);
            }
        });

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"食物",Toast.LENGTH_SHORT).show();
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"娱乐",Toast.LENGTH_SHORT).show();
            }
        });
        shenghuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"生活",Toast.LENGTH_SHORT).show();
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"目录",Toast.LENGTH_SHORT).show();
            }
        });
        /*buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"购物",Toast.LENGTH_SHORT).show();
            }
        });*/
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"支付",Toast.LENGTH_SHORT).show();
            }
        });
        catage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"商家",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 200 && resultCode == 400){
            String backPrice = data.getStringExtra("price");
            Toast.makeText(MainActivity.this,backPrice,Toast.LENGTH_SHORT).show();
        }
    }

    private List<Map<String,Object>> getList(){
        List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
        for(int i = 1; i <= 6 ;i++){
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

    class ImageAndText extends BaseAdapter{

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
                view = LayoutInflater.from(MainActivity.this).inflate(R.layout.list_view,null);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.one,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.one,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int i = item.getItemId();
        switch (i){
            case R.id.save:
                Toast.makeText(MainActivity.this, "保存", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.update:
                Toast.makeText(MainActivity.this, "更新", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.delete:
                Toast.makeText(MainActivity.this, "删除", Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }
}
