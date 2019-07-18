package com.example.lalthanpuia.trafficpoliceswipe4.adapters;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.example.lalthanpuia.trafficpoliceswipe4.R;
import com.example.lalthanpuia.trafficpoliceswipe4.entity.NotificationEntity;



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

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        try{

            myHolder.myBody.setText(notificationEntities.get(i).getMessage());
            myHolder.title.setText(notificationEntities.get(i).getTitle());
            myHolder.date.setText(notificationEntities.get(i).getDate());
            if(notificationEntities.get(i).getStatus().equals("")){
                //BLANK
                myHolder.status.setBackgroundColor(R.color.red);
            }else if(notificationEntities.get(i).getStatus().equals("resolved")){
                myHolder.status.setBackgroundColor(R.color.green);

            }else if(notificationEntities.get(i).getStatus().equals("pending")){
                myHolder.status.setBackgroundColor(R.color.yellow);

            }
        }catch (Exception e){}

    }

    @Override
    public int getItemCount() {
        return notificationEntities.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title, date;
        ImageView status;
        ReadMoreTextView myBody;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            myBody = itemView.findViewById(R.id.myBody);
            title = itemView.findViewById(R.id.titleTextView);
            date = itemView.findViewById(R.id.dateTextView);
            status = itemView.findViewById(R.id.statusImageView);


        }

        @Override
        public void onClick(View v) {

        }
    }
}
