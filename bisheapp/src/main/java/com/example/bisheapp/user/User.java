package com.example.bisheapp.user;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.os.Handler;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.bisheapp.IpUtils;
import com.example.bisheapp.MyPagerAdapter;
import com.example.bisheapp.R;
import com.example.bisheapp.elderHouse;
import com.example.bisheapp.volunteer.volunteerList;

public class User extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnTouchListener {
    public static final int VIEW_PAGER_DELAY = 2000;
    private MyPagerAdapter mAdapter;
    private List<ImageView> mItems;
    private ImageView[] mBottomImages;
    private LinearLayout mBottomLiner;
    private ViewPager mViewPager;

    private int currentViewPagerItem;
    //是否自动播放
    private boolean isAutoPlay;

    private MyHandler mHandler;
    private Thread mThread;

    private LinearLayout umessage, uolderhouse, uqingqinhudong, uvolunteermessage, uzixun, usetting;
    private Button phone;
    private String phoneNumber, id,message;

    private LocationClient mLocationClient = null;
    private BDLocationListener myListener = new MyLocationListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000 * 300;
        option.setScanSpan(span);//设置发起定位请求的间隔
        option.setOpenGps(true);//可选，默认false,设置是否使用gps

        //可选，是否需要地址信息，默认为不需要，即参数为false
        //如果开发者需要获得当前点的地址信息，此处必须为true
        option.setIsNeedAddress(true);

        mLocationClient = new LocationClient(getApplicationContext());//声明LocationClient类
        mLocationClient.setLocOption(option);
        mLocationClient.registerLocationListener(myListener);
        mLocationClient.start();


        mHandler = new MyHandler(this);
        mViewPager = (ViewPager) findViewById(R.id.live_view_pager);
        mItems = new ArrayList<>();
        mAdapter = new MyPagerAdapter(mItems, this);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnTouchListener(this);
        mViewPager.addOnPageChangeListener(this);
        isAutoPlay = true;


        addImageView();
        mAdapter.notifyDataSetChanged();
        setBottomIndicator();

        uqingqinhudong = (LinearLayout) findViewById(R.id.qinqinghudong);
        uzixun = (LinearLayout) findViewById(R.id.laonianzixun);
        phone = (Button) findViewById(R.id.phone);
        uvolunteermessage = (LinearLayout) findViewById(R.id.uvolunteermessage);
        usetting = (LinearLayout) findViewById(R.id.usetting);
        umessage = (LinearLayout) findViewById(R.id.umessageq);
        uolderhouse = (LinearLayout) findViewById(R.id.uolderhouse);

        uolderhouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(User.this, elderHouse.class));
            }
        });

        umessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User.this, UserMessage.class);
                intent.putExtra("userId", id);
                startActivity(intent);
            }
        });

        uvolunteermessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(User.this, volunteerList.class));
            }
        });

        uqingqinhudong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(User.this, "情亲互动功能暂时未开放", Toast.LENGTH_SHORT).show();
            }
        });

        uzixun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(User.this, "老年咨询动能暂时未开放", Toast.LENGTH_SHORT).show();
            }
        });

        usetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(User.this, userSetting.class));
            }
        });

        Intent intent1 = getIntent();
        id = intent1.getStringExtra("userId");
        Log.i("id内容", "" + id);


        new MyTask().execute(IpUtils.USERSELNUMBER_PATH);
        /*new MyLocationListener().onReceiveLocation(myListener);*/
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*doClick(v);*/
                /*sendSMS(phoneNumber,"wozaizhe");*/
                Toast.makeText(User.this, "地址为"+message, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_CALL);
                Uri data = Uri.parse("tel:" + phoneNumber);
                intent.setData(data);
                if (ActivityCompat.checkSelfPermission(User.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResuqlts)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);
            }
        });


    }

    public class MyLocationListener implements BDLocationListener{

        @Override
        public void onReceiveLocation(BDLocation location) {
            message = "地址为："+ location.getProvince()+location.getCity()+location.getDistrict()+location.getStreet();
        }
    }

    public void sendSMS(String phone, String message){
        String SENT_SMS_ACTION="SENT_SMS_ACTION";
        String DELIVERED_SMS_ACTION="DELIVERED_SMS_ACTION";
        Intent sentIntent = new Intent(SENT_SMS_ACTION);
        PendingIntent sentPI = PendingIntent.getBroadcast(this,0,sentIntent,0);
        Intent deliverIntent = new Intent(DELIVERED_SMS_ACTION);
        PendingIntent deliverPI = PendingIntent.getBroadcast(this, 0, deliverIntent, 0);

        SmsManager sms = SmsManager.getDefault();
        if (message.length() > 70) {
            List<String> msgs = sms.divideMessage(message);
            for (String msg : msgs) {
                sms.sendTextMessage(phone, null, msg, sentPI, deliverPI);
            }
        } else {
            sms.sendTextMessage(phone, null, message, sentPI, deliverPI);
        }
        Toast.makeText(User.this, message, Toast.LENGTH_LONG).show();
    }

    /*public void doClick(View v){
        new LocationUtils().register(this,0,0, new OnLocationChangeListener() {

            @Override
            public void getLastKnownLocation(Location location) {
                Log.e("th","onLocationChanged:"+location.getLatitude());
            }

            @Override
            public void onLocationChanged(Location location) {
                message = new LocationUtils().getStreet(User.this,location.getLatitude(),location.getLongitude());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }
        });

    }*/

    class MyTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                HttpURLConnection hc = (HttpURLConnection) new URL(params[0]).openConnection();
                hc.setRequestMethod("POST");
                hc.setReadTimeout(5000);
                String value = "id=" + id;
                hc.getOutputStream().write(value.getBytes());

                BufferedReader br = new BufferedReader(new InputStreamReader(hc.getInputStream()));
                String str = "";
                StringBuffer sf = new StringBuffer();
                while ((str = br.readLine()) != null) {
                    sf.append(str);
                }
                return sf.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            phoneNumber = s;
            Log.i("紧急电话", "" + s);
        }
    }

    private void addImageView() {
        ImageView view0 = new ImageView(this);
        view0.setImageResource(R.drawable.img1);
        ImageView view1 = new ImageView(this);
        view1.setImageResource(R.drawable.img2);
        ImageView view2 = new ImageView(this);
        view2.setImageResource(R.drawable.img3);

        view0.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view1.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view2.setScaleType(ImageView.ScaleType.CENTER_CROP);

        mItems.add(view0);
        mItems.add(view1);
        mItems.add(view2);
    }

    private void setBottomIndicator() {
        mBottomLiner = (LinearLayout) findViewById(R.id.live_indicator);
        mBottomImages = new ImageView[mItems.size()];
        for (int i = 0; i < mBottomImages.length; i++) {
            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
            params.setMargins(5, 0, 5, 0);
            imageView.setLayoutParams(params);
            if (i == 0) {
                imageView.setImageResource(R.drawable.indicator_select);
            } else {
                imageView.setImageResource(R.drawable.indicator_no_select);
            }
            mBottomImages[i] = imageView;
            mBottomLiner.addView(imageView);
        }
        int mid = MyPagerAdapter.MAX_SCROLL_VALUE / 2;
        mViewPager.setCurrentItem(mid);
        currentViewPagerItem = mid;

        mThread = new Thread() {
            @Override
            public void run() {
                super.run();
                while (true) {
                    mHandler.sendEmptyMessage(0);
                    try {
                        Thread.sleep(User.VIEW_PAGER_DELAY);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        mThread.start();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentViewPagerItem = position;
        if (mItems != null) {
            position %= mBottomImages.length;
            int total = mBottomImages.length;
            for (int i = 0; i < total; i++) {
                if (i == position) {
                    mBottomImages[i].setImageResource(R.drawable.indicator_select);
                } else {
                    mBottomImages[i].setImageResource(R.drawable.indicator_no_select);
                }
            }
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                isAutoPlay = false;
                break;
            case MotionEvent.ACTION_UP:
                isAutoPlay = true;
                break;
        }
        return false;
    }

    private static class MyHandler extends Handler {
        private WeakReference<User> mWeakReference;

        public MyHandler(User activity) {
            mWeakReference = new WeakReference<User>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    User activity = mWeakReference.get();
                    if (activity.isAutoPlay) {
                        activity.mViewPager.setCurrentItem(++activity.currentViewPagerItem);
                    }
                    break;
            }
        }
    }

   /* public class LocationUtils {
        private static final boolean TODO = true;
        private  OnLocationChangeListener mListener;
        private  LocationManager mLocationManager;
        private  MyLocationListener myLocationManager;

        private LocationUtils() {
            throw new UnsupportedOperationException("u cant't instantiate me...");
        }

        //判断gsp是否可用
        public  boolean isGpsEnabled(Context context) {
            LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }

        //判断定位是否可用
        public  boolean isLocationEnabled(Context context) {
            LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            return lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }

        //打开gps设置页面
        public  void openGpsSettings(Context context) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }

        //注册
        public boolean register(Context context, long minTime, long minDistance, OnLocationChangeListener listener) {
            if (listener == null) {
                return false;
            }
            mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            mListener = listener;
            if (!isLocationEnabled(context)) {
                Toast.makeText(context, "无法定位，请打开定位服务", Toast.LENGTH_SHORT).show();
                return false;
            }
            String provider = mLocationManager.getBestProvider(getCriteria(), true);
            if (ActivityCompat.checkSelfPermission(User.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(User.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return TODO;
            }
            Location location = mLocationManager.getLastKnownLocation(provider);

            if (location != null) {
                listener.getLastKnownLocation(location);
            }
            if (myLocationManager == null) {
                myLocationManager = new MyLocationListener();
            }
            mLocationManager.requestLocationUpdates(provider, minTime, minDistance, myLocationManager);
            return true;
        }

        //注销
        public  void unregister() {
            if (mLocationManager != null) {
                if (ActivityCompat.checkSelfPermission(User.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(User.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mLocationManager.removeUpdates(myLocationManager);
                myLocationManager = null;
            }
            mLocationManager = null;
        }

        //设置定位参数
        private  Criteria getCriteria(){
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            criteria.setSpeedRequired(false);
            criteria.setCostAllowed(false);
            criteria.setBearingRequired(false);
            criteria.setAltitudeRequired(false);
            criteria.setPowerRequirement(Criteria.POWER_LOW);
            return criteria;
        }

        //获取经纬度
        public  Address getAddress(Context context, double latitude, double longitudu){
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            try {
                List<Address> address = geocoder.getFromLocation(latitude,longitudu,1);
                if(address.size() > 0){
                    return address.get(0);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        //获取所在国家
        public  String getCountryName(Context context, double latitude, double longitude){
            Address address = getAddress(context,latitude,longitude);
            return address == null ? "unknown" : address.getCountryName();
        }

        //获取所在地
        public  String getLocality(Context context, double latitude, double longitude){
            Address address = getAddress(context,latitude,longitude);
            return address == null ? "unknown" : address.getLocality();
        }

        //获取接到
        public  String getStreet(Context context,double latitude, double longitude){
            Address address = getAddress(context,latitude,longitude);
            return address == null ? "unknown" : address.getAddressLine(0);
        }

        private  class MyLocationListener implements LocationListener {

            @Override
            public void onLocationChanged(Location location) {
                if(mListener != null){
                    mListener.onLocationChanged(location);
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                if(mListener != null){
                    mListener.onStatusChanged(provider,status,extras);
                }
                switch (status){
                    case LocationProvider.AVAILABLE:
                        Log.e("onStatusChanged","当前GPS状态为可见状态");
                        break;
                    case LocationProvider.OUT_OF_SERVICE:
                        Log.e("onStatusChanged","当前GPS状态为服务区外状态");
                        break;
                    case LocationProvider.TEMPORARILY_UNAVAILABLE:
                        Log.e("onStatusChanged","当前GPS状态为暂停服务状态");
                        break;
                }
            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        }

    }*/
    /*public interface OnLocationChangeListener{
        void getLastKnownLocation(Location location);
        void onLocationChanged(Location location);
        void onStatusChanged(String provider, int status, Bundle extras);
    }*/

}

