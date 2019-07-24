package com.codencode.dillidarshan.MyAdapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.codencode.dillidarshan.R;

import java.util.ArrayList;

public class MyViewPagerAdapter extends PagerAdapter {
     Context context;
     ArrayList<String> mList;
     public MyViewPagerAdapter(Context context , ArrayList<String> mlist)
     {
         this.context = context;
         mList = mlist;
     }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (RelativeLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.monitor_layout_pager_item , container , false);
        ImageView img = view.findViewById(R.id.monitor_layout_pager_item_img_id);
        Glide.with(context).load(mList.get(position)).into(img);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }
}
