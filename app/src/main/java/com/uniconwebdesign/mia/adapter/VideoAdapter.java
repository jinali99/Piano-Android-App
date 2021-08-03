package com.uniconwebdesign.mia.adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.uniconwebdesign.mia.PlayVideoActivity;
import com.uniconwebdesign.mia.R;
import com.uniconwebdesign.mia.util.JsonFields;
import com.uniconwebdesign.mia.util.WebUrl;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {

    Activity a;
    ArrayList<HashMap<String, String>> arrayList;

    public VideoAdapter(Activity a, ArrayList<HashMap<String, String>> arrayList) {
        this.a = a;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = a.getLayoutInflater().inflate(R.layout.row_video, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HashMap<String, String> hashMap = arrayList.get(position);
        holder.tvvideotitle.setText(hashMap.get(JsonFields.TAG_VIDEO_TITLE));
        holder.tvvideodescription.setText(hashMap.get(JsonFields.TAG_VIDEO_DESCRIPTION));
        Glide.with(a).load("http://i3.ytimg.com/vi/" + hashMap.get(JsonFields.TAG_VIDEO_URL).substring(hashMap.get(JsonFields.TAG_VIDEO_URL).indexOf("e/") + 2) + "/maxresdefault.jpg").placeholder(R.drawable.music_logo).into(holder.tvvideothumbnail);
        holder.cvVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(a, PlayVideoActivity.class);
                i.putExtra(JsonFields.TAG_VIDEO_URL,hashMap.get(JsonFields.TAG_VIDEO_URL));
                a.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvvideotitle)
        TextView tvvideotitle;

        @BindView(R.id.tvvideodescription)
        TextView tvvideodescription;

        @BindView(R.id.cvVideo)
        CardView cvVideo;

        @BindView(R.id.tvvideothumbnail)
        ImageView tvvideothumbnail;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

    }

}
