package com.uniconwebdesign.mia.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uniconwebdesign.mia.R;
import com.uniconwebdesign.mia.util.JsonFields;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.MyViewHolder> {

    Activity a;
    ArrayList<HashMap<String, String>> arrayList;

    public FeedbackAdapter(Activity a, ArrayList<HashMap<String, String>> arrayList) {
        this.a = a;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = a.getLayoutInflater().inflate(R.layout.row_feedback, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HashMap<String, String> hashMap = arrayList.get(position);
        holder.tvfeedbackdate.setText(hashMap.get(JsonFields.TAG_FEEDBACK_DATE));
        holder.tvfeedbackdescription.setText(hashMap.get(JsonFields.TAG_FEEDBACK_DESCRIPTION));
        holder.tvfeedbackrating.setText(hashMap.get(JsonFields.TAG_FEEDBACK_RATING));
        holder.tvuserid.setText(hashMap.get(JsonFields.TAG_USER_ID));
        holder.tvacademyid.setText(hashMap.get(JsonFields.TAG_ACADEMY_ID));


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        @BindView(R.id.tvfeedbackdate)
        TextView tvfeedbackdate;

        @BindView(R.id.tvfeedbackdescription)
        TextView tvfeedbackdescription;

        @BindView(R.id.tvfeedbackrating)
        TextView tvfeedbackrating;

        @BindView(R.id.tvuserid)
        TextView tvuserid;

        @BindView(R.id.tvacademyid)
        TextView tvacademyid;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

    }

}
