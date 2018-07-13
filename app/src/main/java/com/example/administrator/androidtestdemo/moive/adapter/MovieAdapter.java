package com.example.administrator.androidtestdemo.moive.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.androidtestdemo.R;
import com.example.network_sdk.movie.bean.Movie;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter {
    private List<Movie> mMovies;


    public void setMovies(List<Movie> movies) {
        mMovies = movies;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MovieHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.moive_item,null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Movie movie = mMovies.get(position);
        MovieHolder movieHolder = (MovieHolder) holder;
        Uri uri =  Uri.parse(movie.getImages().getSmall());

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)
                .build();
        movieHolder.mImageView.setController(controller);
        movieHolder.time.setText("上映时间："+movie.getYear() + "年");
        movieHolder.title.setText(movie.getTitle());
        movieHolder.subTitle.setText(movie.getOriginal_title());
    }

    @Override
    public int getItemCount() {
        return mMovies == null ? 0:mMovies.size();
    }
    public static class MovieHolder extends RecyclerView.ViewHolder{
        public SimpleDraweeView mImageView;
        public TextView title;
        public TextView subTitle;
        public TextView time;
        public MovieHolder(View itemView) {
            super(itemView);
            mImageView = (SimpleDraweeView) itemView.findViewById(R.id.movie_image);
            title = (TextView) itemView.findViewById(R.id.movie_title);
            subTitle = (TextView) itemView.findViewById(R.id.movie_sub_title);
            time = (TextView) itemView.findViewById(R.id.movie_time);
        }
    }
}
