package com.example.lalthanpuia.trafficpoliceswipe4.police;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.bumptech.glide.Glide;
import com.example.lalthanpuia.trafficpoliceswipe4.R;
import com.example.lalthanpuia.trafficpoliceswipe4.adapters.MyAdapter;
import com.example.lalthanpuia.trafficpoliceswipe4.entity.NotificationEntity;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import static com.example.lalthanpuia.trafficpoliceswipe4.police.PoliceIncharge.post_assigned;

public class PoliceAdapter extends RecyclerView.Adapter<PoliceAdapter.MyHolder> {

    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    ArrayList<NotificationEntity> notificationEntities;

    String fromWhere;

    Bitmap bitmap;

    public PoliceAdapter(ArrayList<NotificationEntity> myNoti, String from) {

        notificationEntities = myNoti;
        fromWhere = from;

    }

    //INITIALIE VH
    @Override
    public PoliceAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.model,parent,false);
        PoliceAdapter.MyHolder holder=new PoliceAdapter.MyHolder(v);
        return holder;

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, final int position) {
        try{
         /*   holder.senderNameTV.setText(adminList.get(position).toString());

            holder.dateTV.setText(dateList.get(position).toString());
            holder.messageTV.setText(messageList.get(position).toString());*/
            holder.senderName.setText(notificationEntities.get(position).getSender_name());
            holder.title.setText(notificationEntities.get(position).getTitle());
            holder.myBody.setText(notificationEntities.get(position).getMessage());
            holder.date.setText(notificationEntities.get(position).getDate());

            if(notificationEntities.get(position).getStatus().equals("unresolve")){
                //BLANK
                holder.status.setBackgroundColor(R.color.red);
            }else if(notificationEntities.get(position).getStatus().equals("resolved")){
                holder.status.setBackgroundColor(R.color.green);
            }else if(notificationEntities.get(position).getStatus().equals("pending")){
                holder.status.setBackgroundColor(R.color.yellow);
            }



            holder.changeStatusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String postKey = PoliceIncharge.post_assigned.get(position);

                    PoliceIncharge.changeStatus(postKey);

                    Intent i = new Intent(holder.context,PoliceIncharge.class);
                    holder.context.startActivity(i);
                    holder.context.stopService(i);
                   /* holder.context.startActivity(new Intent(holder.context,PoliceIncharge.class));
                    holder.context.finish();*/
//                     v.startActivity(new Intent(this,PoliceIncharge.class));

//                    PoliceAdapter.this.notify();
                    //notifyAll();
                  //  notifyDataSetChanged();
                }
            });

        }catch(Exception e){

        }


    }

    /*
    TOTAL ITEMS
     */
    @Override
    public int getItemCount() {
        // return spaceships.size();
        return notificationEntities.size();
    }




    /*
    VIEW HOLDER CLASS
     */
    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        TextView title, date,senderName;
        ImageView status;
        ReadMoreTextView myBody;
        ImageView imageView;
        ImageView mapIV;
        CardView cardView;
        Context context;
        Button changeStatusButton;
        TextView police_incharge;

        public MyHolder(final View itemView) {
            super(itemView);

            context = itemView.getContext();

            // this.nametxt= (TextView) itemView.findViewById(R.id.nameTxt);
            this.title=  itemView.findViewById(R.id.titleTextViewAdmin);
            this.date=  itemView.findViewById(R.id.dateTextViewAdmin);
            this.myBody=  itemView.findViewById(R.id.myBodyAdmin);
            this.status=  itemView.findViewById(R.id.statusImageViewAdmin);
            this.senderName=  itemView.findViewById(R.id.senderNameAdmin);

            this.imageView = itemView.findViewById(R.id.imageView);
            this.mapIV = itemView.findViewById(R.id.mapIV);
            this.cardView = itemView.findViewById(R.id.cardView);
            this.police_incharge = itemView.findViewById(R.id.police_incharge);
            this.changeStatusButton = itemView.findViewById(R.id.changeStatusButton);

            if(fromWhere.equals("admin")){
                itemView.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View view) {

            /*intent.putExtra("message",messageList.get(position));
            intent.putExtra("downloadUrl",downloadURLList.get(position));

            intent.putExtra("longitude",longitudeList.get(position));
            intent.putExtra("latitude",latitudeList.get(position));

            intent.putExtra("uniqueKey",uniqueKeyList.get(position));
            intent.putExtra("userUniqueKey",userUniqueKeyList.get(position));

            intent.putExtra("police_incharge",police_inchargeList.get(position));*/
        }
    }
}

