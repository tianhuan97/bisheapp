package com.example.bisheapp;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by TianHuan on 2019/4/26.
 */
public class MyPagerAdapter extends PagerAdapter {
    public static final int MAX_SCROLL_VALUE = 10000;

    private List<ImageView> mItems;
    private Context mContext;
    private LayoutInflater mInflater;

    public MyPagerAdapter(List<ImageView> items, Context context) {
        mContext = context;
        mItems = items;
        mInflater = LayoutInflater.from(context);
    }


    /**
     * @param container
     * @param position
     * @return 对position进行求模操作
     * 因为当用户向左滑时position可能出现负值，所以必须进行处理
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View ret = null;

        position %= mItems.size();
        Log.d("Apapter","instantiate: position:" + position);
        ret = mItems.get(position);
        ViewParent viewParent = ret.getParent();
        if(viewParent != null){
            ViewGroup parent = (ViewGroup) viewParent;
            parent.removeView(ret);
        }

        container.addView(ret);
        return ret;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }

    @Override
    public int getCount() {
        int ret = 0;
        if(mItems.size() > 0){
            ret = MAX_SCROLL_VALUE;
        }
        return ret;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (View)object;
    }
}
