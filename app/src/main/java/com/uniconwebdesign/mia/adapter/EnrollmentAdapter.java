package com.uniconwebdesign.mia.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.uniconwebdesign.mia.R;
import com.uniconwebdesign.mia.ViewVideoActivity;
import com.uniconwebdesign.mia.util.JsonFields;
import com.uniconwebdesign.mia.util.WebUrl;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EnrollmentAdapter extends RecyclerView.Adapter<EnrollmentAdapter.MyViewHolder> {

    Activity a;
    ArrayList<HashMap<String, String>> arrayList;

    public EnrollmentAdapter(Activity a, ArrayList<HashMap<String, String>> arrayList) {
        this.a = a;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = a.getLayoutInflater().inflate(R.layout.row_enrollment, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HashMap<String, String> hashMap = arrayList.get(position);
        holder.tvcoursename.setText(hashMap.get(JsonFields.TAG_COURSE_NAME));
        holder.tvenrollmentdate.setText(hashMap.get(JsonFields.TAG_ENROLLMENT_DATE));
        holder.tvcoursecost.setText("Rs." + hashMap.get(JsonFields.TAG_ENROLLMENT_AMOUNT));
        Glide.with(a).load(WebUrl.IMAGE_URL + hashMap.get(JsonFields.TAG_INSTRUMENT_PHOTO)).placeholder(R.drawable.music_logo).into(holder.tvinstrumentphoto);
        holder.btnViewVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(a, ViewVideoActivity.class);
                i.putExtra(JsonFields.TAG_COURSE_ID,hashMap.get(JsonFields.TAG_COURSE_ID));
                a.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvcoursename)
        TextView tvcoursename;
        @BindView(R.id.tvcoursecost)
        TextView tvcoursecost;
        @BindView(R.id.tvinstrumentphoto)
        ImageView tvinstrumentphoto;
        @BindView(R.id.tvenrollmentdate)
        TextView tvenrollmentdate;
        @BindView(R.id.btnViewVideos)
        Button btnViewVideos;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

    }

}
