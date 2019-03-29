package com.example.lalthanpuia.trafficpoliceswipe4;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

    StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    Context c;

    ArrayList<String> adminList;
    ArrayList<String> dateList;
    ArrayList<String> messageList;
    ArrayList<String> downloadURLList;
    ArrayList<String> latitudeList;
    ArrayList<String> longitudeList;
    ArrayList<String> uniqueKeyList;
    ArrayList<String> userUniqueKeyList;
    ArrayList<String> police_inchargeList;
    String fromWhere;

    Bitmap bitmap;

    /*
    CONSTRUCTOR
    */
    //public MyAdapter(Context c, ArrayList<Spaceship> spaceship) {
    public MyAdapter(Context c, ArrayList<String> admin, ArrayList<String> date, ArrayList<String> message, ArrayList<String> downloadURL, ArrayList<String> latitude, ArrayList<String> longitude, ArrayList<String> uniqueKey, ArrayList<String> userUniqueKey, String from, ArrayList<String> police_incharge ) {

        this.c = c;
        this.adminList = admin;
        this.dateList = date;
        this.messageList = message;
        this.downloadURLList = downloadURL;
        this.latitudeList = latitude;
        this.longitudeList = longitude;
        this.uniqueKeyList = uniqueKey;
        this.userUniqueKeyList = userUniqueKey;
        this.fromWhere = from;
        this.police_inchargeList = police_incharge;

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
        holder.adminTV.setText(adminList.get(position).toString());
        holder.dateTV.setText(dateList.get(position).toString());
        holder.messageTV.setText(messageList.get(position).toString());

        String maplocation = "http://maps.google.com/maps/api/staticmap?center=" + latitudeList.get(position) + "," + longitudeList.get(position) + "&zoom=16&size=200x100&scale=2&markers=size:tiny%7Ccolor:red%7Clabel:S%7C"+ latitudeList.get(position) + "," + longitudeList.get(position) +"&key=AIzaSyARsaFMey84nKb-_wlxkkXhRseNak1fSRY";
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

        if(police_inchargeList.get(position).equals("")){

        }else
        {
            holder.cardView.setCardBackgroundColor(R.color.police_handled_color);
        }


    }

    /*
    TOTAL ITEMS
     */
    @Override
    public int getItemCount() {
       // return spaceships.size();
        return adminList.size();
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

    public void add(String ad, String dat, String mes, String dURL, String lat, String lng, String uniqueKey, String userUniqueKey, String from, String police_incharge) {
        adminList.add(ad);
        dateList.add(dat);
        messageList.add(mes);

        downloadURLList.add(dURL);
        latitudeList.add(lat);
        longitudeList.add(lng);

        uniqueKeyList.add(uniqueKey);
        userUniqueKeyList.add(userUniqueKey);

        fromWhere = from;
        police_inchargeList.add(police_incharge);

        notifyDataSetChanged();
    }

    /*
    CLEAR DATA FROM ADAPTER
     */
    public void clear() {
        adminList.clear();
        dateList.clear();
        messageList.clear();

        downloadURLList.clear();
        latitudeList.clear();
        longitudeList.clear();

        userUniqueKeyList.clear();
        uniqueKeyList.clear();
        police_inchargeList.clear();

     //  spaceships.clear();
       notifyDataSetChanged();
    }


    /*
    VIEW HOLDER CLASS
     */
    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

      //  TextView nametxt;
        TextView adminTV;
        TextView dateTV;
        TextView messageTV;
        ImageView imageView;
        ImageView mapIV;
        CardView cardView;

        public MyHolder(final View itemView) {
            super(itemView);

           // this.nametxt= (TextView) itemView.findViewById(R.id.nameTxt);
            this.adminTV= (TextView) itemView.findViewById(R.id.adminTV);
            this.dateTV= (TextView) itemView.findViewById(R.id.dateTV);
            this.messageTV= (TextView) itemView.findViewById(R.id.messageTV);
            this.imageView = itemView.findViewById(R.id.imageView);
            this.mapIV = itemView.findViewById(R.id.mapIV);
            this.cardView = itemView.findViewById(R.id.cardView);

            if(fromWhere.equals("paginator")){
                itemView.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View view) {

            int position = getAdapterPosition();
            Intent intent = new Intent(view.getContext(),ItemFiveActivity.class);

            intent.putExtra("message",messageList.get(position));
            intent.putExtra("downloadUrl",downloadURLList.get(position));

            intent.putExtra("longitude",longitudeList.get(position));
            intent.putExtra("latitude",latitudeList.get(position));

            intent.putExtra("uniqueKey",uniqueKeyList.get(position));
            intent.putExtra("userUniqueKey",userUniqueKeyList.get(position));

            intent.putExtra("police_incharge",police_inchargeList.get(position));

            c.startActivity(intent);
        }
    }
}
