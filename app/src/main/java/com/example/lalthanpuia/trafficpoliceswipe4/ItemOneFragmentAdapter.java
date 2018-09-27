package com.example.lalthanpuia.trafficpoliceswipe4;

import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ItemOneFragmentAdapter extends RecyclerView.Adapter<ItemOneFragmentAdapter.ViewHolder> {
    //private ItemData[] mItemData;
    public Context context;
    public Activity activity;
    public ArrayList mAdmin ;
    public ArrayList mDate ;
    public ArrayList mMessage ;
    public AdapterView.OnItemClickListener ClickListener;

    public ItemOneFragmentAdapter(ArrayList admin, ArrayList date, ArrayList message) {

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
        holder.textview_admin.setText((String) mAdmin.get(position));
        holder.textview_date.setText((String) mDate.get(position));
        holder.textview_message.setText((String) mMessage.get(position));

    }

    @Override
    public int getItemCount() {
        return mAdmin.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textview_admin;
        public TextView textview_date;
        public TextView textview_message;

        public ViewHolder(View itemView) {
            super(itemView);
            textview_admin = itemView.findViewById(R.id.textview_admin);
            textview_date = itemView.findViewById(R.id.textview_date);
            textview_message = itemView.findViewById(R.id.textview_message);
        }
        @Override
        public void onClick(View view) {
        }
    }
}
