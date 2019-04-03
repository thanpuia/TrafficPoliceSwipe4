package com.example.lalthanpuia.trafficpoliceswipe4;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lalthanpuia.trafficpoliceswipe4.entity.GlobalNotifyEntity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class GlobalNotifyAdapter extends RecyclerView.Adapter<GlobalNotifyAdapter.MyHolder>  {


    Context c;
    ArrayList<GlobalNotifyEntity> GNE;
    public GlobalNotifyAdapter(Context applicationContext, ArrayList<GlobalNotifyEntity> strings){

        this.c = applicationContext;
        this.GNE = strings;
        Log.v("TAG","On Global notify Adapter");

    }

    //INITIALIE VH
    @Override
    public GlobalNotifyAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.global_notify_model,parent,false);
        GlobalNotifyAdapter.MyHolder holder=new GlobalNotifyAdapter.MyHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {

        myHolder.dateTv.setText(GNE.get(i).getTimeStamp());
        myHolder.messageTV.setText(GNE.get(i).getMessage());
        Log.v("TAG","On Global notify Adapter");
    }

/*    //BIND DATA
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final MyAdapter.MyHolder holder, int position) {
        // holder.nametxt.setText(spaceships.get(position).toString());
        holder.adminTV.setText(adminList.get(position).toString());
        holder.dateTV.setText(dateList.get(position).toString());
        holder.messageTV.setText(messageList.get(position).toString());

        String maplocation = "http://maps.google.com/maps/api/staticmap?center=" + latitudeList.get(position) + "," + longitudeList.get(position) + "&zoom=16&size=200x100&scale=2&markers=size:tiny%7Ccolor:red%7Clabel:S%7C"+ latitudeList.get(position) + "," + longitudeList.get(position) +"&key=AIzaSyARsaFMey84nKb-_wlxkkXhRseNak1fSRY";
        Glide.with(holder.imageView.getContext())
                .load(maplocation)
                .into(holder.mapIV);
    }*/

    /*
    TOTAL ITEMS
     */
    @Override
    public int getItemCount() {
        // return spaceships.size();
        return GNE.size();
    }

    public void add(GlobalNotifyEntity globalNotifyEntity) {

        GNE.add(globalNotifyEntity);
        Log.v("TAG","On Global notify Adapter");

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
    VIEW HOLDER CLASS
     */
    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        //  TextView nametxt;
        TextView dateTv;
        TextView messageTV;


        TextView police_incharge;


        public MyHolder(final View itemView) {
            super(itemView);

            // this.nametxt= (TextView) itemView.findViewById(R.id.nameTxt);
           this.dateTv= itemView.findViewById(R.id.globalDateTv);
           this.messageTV = itemView.findViewById(R.id.globalMessageTv);
            Log.v("TAG","On Global notify Adapter");

        }

        @Override
        public void onClick(View view) {

         /*   int position = getAdapterPosition();
            Intent intent = new Intent(view.getContext(),ItemFiveActivity.class);

            intent.putExtra("message",messageList.get(position));
            intent.putExtra("downloadUrl",downloadURLList.get(position));

            intent.putExtra("longitude",longitudeList.get(position));
            intent.putExtra("latitude",latitudeList.get(position));

            intent.putExtra("uniqueKey",uniqueKeyList.get(position));
            intent.putExtra("userUniqueKey",userUniqueKeyList.get(position));

            intent.putExtra("police_incharge",police_inchargeList.get(position));

            c.startActivity(intent);*/
        }
    }
}
