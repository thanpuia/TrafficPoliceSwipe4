package com.example.lalthanpuia.trafficpoliceswipe4;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ItemOneFragmentAdapter extends RecyclerView.Adapter<ItemOneFragmentAdapter.ViewHolder> {
    //private ItemData[] mItemData;

    public String[] mAdmin ;
    public String[] mDate ;
    public String[] mMessage ;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textview_admin;
        public TextView textview_date;
        public TextView textview_message;

        public  String[] mAdmin;
        public  String[] mDate;

        public ViewHolder(View itemView) {
            super(itemView);
            textview_admin = itemView.findViewById(R.id.textview_admin);
            textview_date = itemView.findViewById(R.id.textview_date);
            textview_message = itemView.findViewById(R.id.textview_message);
        }
    }

    public ItemOneFragmentAdapter(String[] admin, String[] date, String[] message) {

        mAdmin = admin;
        mDate = date;
        mMessage = message;
    }

    @Override
    public ItemOneFragmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= (View) LayoutInflater.from (parent.getContext()).inflate(R.layout.list_details,parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ItemOneFragmentAdapter.ViewHolder holder, int position) {
        holder.textview_admin.setText(mAdmin[position]);
        holder.textview_date.setText(mDate[position]);
        holder.textview_message.setText(mMessage[position]);

    }

    @Override
    public int getItemCount() {
        return mAdmin.length;
    }


}
