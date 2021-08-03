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
import com.uniconwebdesign.mia.PlayInstrumentActivity;
import com.uniconwebdesign.mia.PlayVideoActivity;
import com.uniconwebdesign.mia.R;
import com.uniconwebdesign.mia.ViewCourseActivity;
import com.uniconwebdesign.mia.util.JsonFields;
import com.uniconwebdesign.mia.util.WebUrl;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InstrumentAdapter extends RecyclerView.Adapter<InstrumentAdapter.MyViewHolder> {

    Activity a;
    ArrayList<HashMap<String, String>> arrayList;

    public InstrumentAdapter(Activity a, ArrayList<HashMap<String, String>> arrayList) {
        this.a = a;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = a.getLayoutInflater().inflate(R.layout.row_instrument, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HashMap<String, String> hashMap = arrayList.get(position);
        holder.tvinstrumentname.setText(hashMap.get(JsonFields.TAG_INSTRUMENT_NAME));
        Glide.with(a).load(WebUrl.IMAGE_URL + hashMap.get(JsonFields.TAG_INSTRUMENT_PHOTO)).placeholder(R.drawable.music_logo).into(holder.tvinstrumentphoto);
        holder.cvInstrument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(a, PlayInstrumentActivity.class);
                i.putExtra(JsonFields.TAG_INSTRUMENT_ID, hashMap.get(JsonFields.TAG_INSTRUMENT_ID));
                a.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvinstrumentname)
        TextView tvinstrumentname;

        @BindView(R.id.tvinstrumentphoto)
        ImageView tvinstrumentphoto;

        @BindView(R.id.cvInstrument)
        CardView cvInstrument;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}