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

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.MyViewHolder> {

    Activity a;
    ArrayList<HashMap<String, String>> arrayList;

    public AdminAdapter(Activity a, ArrayList<HashMap<String, String>> arrayList) {
        this.a = a;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = a.getLayoutInflater().inflate(R.layout.row_admin, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HashMap<String, String> hashMap = arrayList.get(position);
        holder.tvadminname.setText(hashMap.get(JsonFields.TAG_ADMIN_NAME));
        holder.tvadminmail.setText(hashMap.get(JsonFields.TAG_ACADEMY_ID));
        }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvadminname)
        TextView tvadminname;
        @BindView(R.id.tvadminmail)
        TextView tvadminmail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}