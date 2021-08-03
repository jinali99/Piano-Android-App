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

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.MyViewHolder> {

    Activity a;
    ArrayList<HashMap<String, String>> arrayList;

    public PaymentAdapter(Activity a, ArrayList<HashMap<String, String>> arrayList) {
        this.a = a;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = a.getLayoutInflater().inflate(R.layout.row_payment, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HashMap<String, String> hashMap = arrayList.get(position);
        holder.tvpaymentdate.setText(hashMap.get(JsonFields.TAG_PAYMENT_DATE));
        holder.tvpaymentamount.setText(hashMap.get(JsonFields.TAG_PAYMENT_AMOUNT));
        holder.tvpaymentmode.setText(hashMap.get(JsonFields.TAG_PAYMENT_MODE));
        holder.tvpaymentstatus.setText(hashMap.get(JsonFields.TAG_PAYMENT_STATUS));
        holder.tvenrollmentid.setText(hashMap.get(JsonFields.TAG_ENROLLMENT_ID));


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        @BindView(R.id.tvpaymentdate)
        TextView tvpaymentdate;

        @BindView(R.id.tvpaymentamount)
        TextView tvpaymentamount;

        @BindView(R.id.tvpaymentmode)
        TextView tvpaymentmode;

        @BindView(R.id.tvpaymentstatus)
        TextView tvpaymentstatus;

        @BindView(R.id.tvenrollmentid)
        TextView tvenrollmentid;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

    }

}
