package com.example.lalthanpuia.trafficpoliceswipe4.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lalthanpuia.trafficpoliceswipe4.R;
import com.example.lalthanpuia.trafficpoliceswipe4.entity.NotificationEntity;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SingleUserAdapter extends RecyclerView.Adapter<SingleUserAdapter.MyHolder> {

    ArrayList<NotificationEntity> notificationEntities;
    public SingleUserAdapter(ArrayList<NotificationEntity> myNoti) {

        notificationEntities = myNoti;

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_user_model, viewGroup, false);
        MyHolder holder = new MyHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
            myHolder.myBody.setText(notificationEntities.get(i).getMessage());
    }

    @Override
    public int getItemCount() {
        return notificationEntities.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView myBody;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            myBody = itemView.findViewById(R.id.myBody);

        }

        @Override
        public void onClick(View v) {

        }
    }
}
