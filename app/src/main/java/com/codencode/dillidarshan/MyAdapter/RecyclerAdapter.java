package com.codencode.dillidarshan.MyAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codencode.dillidarshan.DataPacket;
import com.codencode.dillidarshan.Monitor;
import com.codencode.dillidarshan.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    Context context;
    ArrayList<DataPacket> mList;

    public RecyclerAdapter(Context context, ArrayList<DataPacket> mList)
    {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.main_activity_recycler_view_items , parent , false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final DataPacket dp = mList.get(position);
        holder.rating.setText("Rating : " + dp.getRating());
        holder.name.setText(dp.getName());
        Glide.with(context).load(dp.getImgRef()).into(holder.img);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context , Monitor.class);
                i.putExtra("uid" , dp.getUid());
                i.putExtra("busStop" , dp.getBusStop());
                i.putExtra("metro" , dp.getMetro());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name , rating;
        ImageView img;
        RelativeLayout relativeLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.recycler_view_item_name);
            rating = itemView.findViewById(R.id.recycler_view_item_rating);
            img = itemView.findViewById(R.id.recycler_view_item_img);
            relativeLayout = itemView.findViewById(R.id.cardview_container_id);
        }
    }
}
