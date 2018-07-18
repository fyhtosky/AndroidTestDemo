package com.example.administrator.androidtestdemo.gank.adapter;

import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.androidtestdemo.R;
import com.example.network_sdk.movie.bean.GankEntry;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class GankAdapter extends RecyclerView.Adapter {

    private List<GankEntry> mGankEntries;

    public void setData(List<GankEntry> gankEntries) {
        mGankEntries = gankEntries;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.gank_item,null));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        GankEntry entry = mGankEntries.get(position);

        Uri uri =  Uri.parse(entry.getUrl());
//        RoundingParams roundingParams = RoundingParams.fromCornersRadius(4f);
//        roundingParams.setBorder(Color.RED, 1.0f);
//        roundingParams.setRoundAsCircle(true);


        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)
                .build();
        viewHolder.mImageView.setController(controller);
//        viewHolder.mImageView.getHierarchy().setRoundingParams(roundingParams);
        viewHolder.descText.setText(entry.getDesc());
        viewHolder.authorText.setText(entry.getWho());
    }

    @Override
    public int getItemCount() {
        return mGankEntries == null ? 0:mGankEntries.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public SimpleDraweeView mImageView;
        public TextView descText;
        public TextView authorText;
        public MyViewHolder(View itemView) {
            super(itemView);
            mImageView = (SimpleDraweeView) itemView.findViewById(R.id.gank_iamge);
            descText = (TextView) itemView.findViewById(R.id.desc);
            authorText = (TextView) itemView.findViewById(R.id.author);
        }
    }
}
