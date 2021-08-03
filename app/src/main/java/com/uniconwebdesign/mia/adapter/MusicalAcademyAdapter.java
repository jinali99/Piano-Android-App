package com.uniconwebdesign.mia.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.uniconwebdesign.mia.R;
import com.uniconwebdesign.mia.ViewCourseActivity;
import com.uniconwebdesign.mia.util.JsonFields;
import com.uniconwebdesign.mia.util.WebUrl;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MusicalAcademyAdapter extends RecyclerView.Adapter<MusicalAcademyAdapter.MyViewHolder> {

    Activity a;
    ArrayList<HashMap<String, String>> arrayList;

    public MusicalAcademyAdapter(Activity a, ArrayList<HashMap<String, String>> arrayList) {
        this.a = a;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = a.getLayoutInflater().inflate(R.layout.row_musicalacademy, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HashMap<String, String> hashMap = arrayList.get(position);
        holder.tvmusicalacademyname.setText(hashMap.get(JsonFields.TAG_MUSICAL_ACADEMY_NAME));
        Glide.with(a).load(WebUrl.IMAGE_URL + hashMap.get(JsonFields.TAG_MUSICAL_ACADEMY_LOGO)).placeholder(R.drawable.music_logo).into(holder.tvmusicalacademylogo);
        holder.cvMusicalAcademy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(a, ViewCourseActivity.class);
                i.putExtra(JsonFields.TAG_MUSICAL_ACADEMY_ID, hashMap.get(JsonFields.TAG_MUSICAL_ACADEMY_ID));
                a.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvmusicalacademyname)
        TextView tvmusicalacademyname;


        @BindView(R.id.tvmusicalacademylogo)
        ImageView tvmusicalacademylogo;

        @BindView(R.id.cvMusicalAcademy)
        CardView cvMusicalAcademy;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}