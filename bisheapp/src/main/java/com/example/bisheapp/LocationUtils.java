/*
package com.example.bisheapp;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

*/
/**
 * Created by TianHuan on 2019/6/9.
 *//*

public class LocationUtils {
    private static OnLocationChangeListener mListener;
    private static LocationManager mLocationManager;
    private static MyLocationListener myLocationManager;

    private LocationUtils(){
        throw new UnsupportedOperationException("u cant't instantiate me...");
    }

    //判断gsp是否可用
    public static boolean isGpsEnabled(Context context){
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    //判断定位是否可用
    public static boolean isLocationEnabled(Context context){
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    //打开gps设置页面
    public static void openGpsSettings(Context context){
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    //注册
    public static boolean register(Context context,long minTime, long minDistance,OnLocationChangeListener listener){
        if(listener == null){
            return false;
        }
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        mListener = listener;
        if(!isLocationEnabled(context)){
            Toast.makeText(context, "无法定位，请打开定位服务", Toast.LENGTH_SHORT).show();
            return false;
        }
        String provider = mLocationManager.getBestProvider(getCriteria(),true);
        Location location = mLocationManager.getLastKnownLocation(provider);

        if(location != null){
            listener.getLastKnownLocation(location);
        }
        if(myLocationManager == null){
            myLocationManager = new MyLocationListener();
        }
        mLocationManager.requestLocationUpdates(provider,minTime,minDistance,myLocationManager);
        return true;
    }

    //注销
    public static void unregister(){
        if(mLocationManager != null){
            mLocationManager.removeUpdates(myLocationManager);
            myLocationManager = null;
        }
        mLocationManager = null;
    }

    //设置定位参数
    private static Criteria getCriteria(){
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
    public static Address getAddress(Context context, double latitude, double longitudu){
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
    public static String getCountryName(Context context, double latitude, double longitude){
        Address address = getAddress(context,latitude,longitude);
        return address == null ? "unknown" : address.getCountryName();
    }

    //获取所在地
    public static String getLocality(Context context, double latitude, double longitude){
        Address address = getAddress(context,latitude,longitude);
        return address == null ? "unknown" : address.getLocality();
    }

    //获取接到
    public static String getStreet(Context context,double latitude, double longitude){
        Address address = getAddress(context,latitude,longitude);
        return address == null ? "unknown" : address.getAddressLine(0);
    }

    private static class MyLocationListener implements LocationListener{

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

    public interface OnLocationChangeListener{
        void getLastKnownLocation(Location location);
        void onLocationChanged(Location location);
        void onStatusChanged(String provider, int status, Bundle extras);
    }


}
*/
