package com.webserviceprocess.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.webserviceprocess.R;
import com.webserviceprocess.bean.Bean;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.requestedAppointmentItemViewHolder> {
    Context context;
    ArrayList<Bean> arrayItemList;

    public ListAdapter(Activity activity, ArrayList<Bean> arrayItemList) {
        this.context = activity;
        this.arrayItemList = arrayItemList;
    }

    @Override
    public ListAdapter.requestedAppointmentItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_raw, parent, false);
        return new ListAdapter.requestedAppointmentItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListAdapter.requestedAppointmentItemViewHolder holder, final int position) {

        final Bean bean = arrayItemList.get(position);
        holder.txt_name.setText(bean.userName);
        holder.txt_time.setText(bean.startTime + ", " + bean.date);


    }

    @Override
    public int getItemCount() {
        return arrayItemList.size();
    }

    public class requestedAppointmentItemViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_time, txt_name;


        public requestedAppointmentItemViewHolder(View itemView) {
            super(itemView);

            txt_name = (TextView) itemView.findViewById(R.id.txt_name);

            txt_time = (TextView) itemView.findViewById(R.id.txt_time);

        }
    }
}