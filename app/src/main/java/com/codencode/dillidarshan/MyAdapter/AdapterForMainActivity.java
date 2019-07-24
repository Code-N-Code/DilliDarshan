package com.codencode.dillidarshan.MyAdapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codencode.dillidarshan.R;

public class AdapterForMainActivity extends PagerAdapter {
    Context context;
    LayoutInflater inflater;
    int[] img_res = {R.drawable.background2 , R.drawable.navheader06 , R.drawable.ic_malls_black_24dp , R.drawable.ic_monuments_24dp};
    String[] name_res = {"Image one" , "Image 2 hai ye" , "image 3" , "Image 4"};
    public AdapterForMainActivity(Context context)
    {
        this.context = context;
    }

    @Override
    public int getCount() {
        return img_res.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.main_activity_pager_item_layout , container , false);
        ImageView img = view.findViewById(R.id.place_img_id);
        TextView txt = view.findViewById(R.id.place_name_id);

        img.setImageResource(img_res[position]);
        txt.setText("Name : " + name_res[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }
}
