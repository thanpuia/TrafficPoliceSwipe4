package com.example.lalthanpuia.trafficpoliceswipe4.adapters;

/*
*
*
*
*
*
* THIS IS THE ADAPTER FOR ADMIN VIEW
*
*
*
*
*
*
*
* */
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.example.lalthanpuia.trafficpoliceswipe4.ItemFiveActivity;
import com.example.lalthanpuia.trafficpoliceswipe4.R;
import com.example.lalthanpuia.trafficpoliceswipe4.entity.NotificationEntity;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    ArrayList<NotificationEntity> notificationEntities;

    Context c;

    String fromWhere;

    Bitmap bitmap;

    /*
    CONSTRUCTOR
    */

    public MyAdapter() {

    }

    public MyAdapter(ArrayList<NotificationEntity> myNoti) {

        notificationEntities = myNoti;

    }

    //INITIALIE VH
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.model,parent,false);
        MyHolder holder=new MyHolder(v);
        return holder;

    }

    //BIND DATA
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
       // holder.nametxt.setText(spaceships.get(position).toString());

        try{
         /*   holder.senderNameTV.setText(adminList.get(position).toString());

            holder.dateTV.setText(dateList.get(position).toString());
            holder.messageTV.setText(messageList.get(position).toString());*/
            holder.senderName.setText(notificationEntities.get(position).getSender_name());
            holder.title.setText(notificationEntities.get(position).getTitle());
            holder.myBody.setText(notificationEntities.get(position).getMessage());
            holder.date.setText(notificationEntities.get(position).getDate());

            if(notificationEntities.get(position).getStatus().equals("")){
                //BLANK
                holder.status.setBackgroundColor(R.color.red);
            }else if(notificationEntities.get(position).getStatus().equals("resolved")){
                holder.status.setBackgroundColor(R.color.green);

            }else if(notificationEntities.get(position).getStatus().equals("pending")){
                holder.status.setBackgroundColor(R.color.yellow);

            }


        }catch(Exception e){

        }


       /* String maplocation = "http://maps.google.com/maps/api/staticmap?center=" + latitudeList.get(position) + "," + longitudeList.get(position) + "&zoom=16&size=200x100&scale=2&markers=size:tiny%7Ccolor:red%7Clabel:S%7C"+ latitudeList.get(position) + "," + longitudeList.get(position) +"&key=AIzaSyARsaFMey84nKb-_wlxkkXhRseNak1fSRY";
        Glide.with(holder.imageView.getContext())
                .load(maplocation)
                .into(holder.mapIV);

        if(downloadURLList.get(position)!= "0"){
            holder.imageView.setMaxWidth(250);
            holder.imageView.setMaxHeight(250);

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReferenceFromUrl("gs://traffficpoliceswipe4.appspot.com").child(downloadURLList.get(position));

            final long ONE_MEGABYTE = 1024 * 1024;

            //download file as a byte array
            storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    holder.imageView.setImageBitmap(bitmap);
                    //showToast("Download successful!");
                }
            });
        }

        String incharge ="Incharge: "+ police_inchargeList.get(position);
        holder.police_incharge.setText(incharge);
        if(police_inchargeList.get(position).equals("")){
          //  holder.cardView.setCardBackgroundColor(R.color.police_not_handled_color);

        }else
        {
            //
            // holder.cardView.setCardBackgroundColor(R.color.police_handled_color);

        }*/
    }
    /*
    TOTAL ITEMS
     */
    @Override
    public int getItemCount() {
        return notificationEntities.size();
    }

    /*
    ADD DATA TO ADAPTER
     */
//    public void add(Spaceship s) {
//        spaceships.add(s);
    /*public void add(String ad,String dat,String mes) {
        adminList.add(ad);
        dateList.add(dat);
        messageList.add(mes);

        notifyDataSetChanged();
    }*/
/*

    public void add(NotificationEntity notificationEntity, String uniqueKey, String from) {
        adminList.add(notificationEntity.getSender_name());
        dateList.add(notificationEntity.getDate());
        messageList.add(notificationEntity.getMessage());

        downloadURLList.add(notificationEntity.getDownloadURL());
        latitudeList.add(notificationEntity.getLatitude());
        longitudeList.add(notificationEntity.getLongitude());

        uniqueKeyList.add(uniqueKey);
        //userUniqueKeyList.add(userUniqueKey);

        fromWhere = from;
        //police_inchargeList.add(police_incharge);

        notifyDataSetChanged();
    }
*/

    /*
    CLEAR DATA FROM ADAPTER
     */
    public void clear() {
//        adminList.clear();
//        dateList.clear();
//        messageList.clear();
//
//        downloadURLList.clear();
//        latitudeList.clear();
//        longitudeList.clear();
//
//        userUniqueKeyList.clear();
//        uniqueKeyList.clear();
//        police_inchargeList.clear();

//        notificationEntities.clear();
       notifyDataSetChanged();
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

        TextView police_incharge;

        public MyHolder(final View itemView) {
            super(itemView);

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

         //   if(fromWhere.equals("paginator")){
        //        itemView.setOnClickListener(this);
         //   }
        }

        @Override
        public void onClick(View view) {

            int position = getAdapterPosition();
            Intent intent = new Intent(view.getContext(), ItemFiveActivity.class);

            /*intent.putExtra("message",messageList.get(position));
            intent.putExtra("downloadUrl",downloadURLList.get(position));

            intent.putExtra("longitude",longitudeList.get(position));
            intent.putExtra("latitude",latitudeList.get(position));

            intent.putExtra("uniqueKey",uniqueKeyList.get(position));
            intent.putExtra("userUniqueKey",userUniqueKeyList.get(position));

            intent.putExtra("police_incharge",police_inchargeList.get(position));*/

            c.startActivity(intent);
        }
    }
}
