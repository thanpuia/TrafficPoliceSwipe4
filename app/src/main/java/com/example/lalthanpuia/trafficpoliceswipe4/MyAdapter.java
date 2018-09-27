package com.example.lalthanpuia.trafficpoliceswipe4;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

    Context c;
    //ArrayList<Spaceship> spaceships;
    ArrayList<String> adminList;
    ArrayList<String> dateList;
    ArrayList<String> messageList;
    /*
    CONSTRUCTOR
    */
    //public MyAdapter(Context c, ArrayList<Spaceship> spaceship) {
    public MyAdapter(Context c, ArrayList<String> admin, ArrayList<String> date, ArrayList<String> message) {
        this.c = c;
        this.adminList = admin;
        this.dateList = date;
        this.messageList = message;
    }

    //INITIALIE VH
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.model,parent,false);
        MyHolder holder=new MyHolder(v);
        return holder;
    }

    //BIND DATA
    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
       // holder.nametxt.setText(spaceships.get(position).toString());
        holder.adminTV.setText(adminList.get(position).toString());
        holder.dateTV.setText(dateList.get(position).toString());
        holder.messageTV.setText(messageList.get(position).toString());
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

    public void add(String ad, String dat, String mes) {
        adminList.add(ad);
        dateList.add(dat);
        messageList.add(mes);

        notifyDataSetChanged();
    }

    /*
    CLEAR DATA FROM ADAPTER
     */
    public void clear() {
        adminList.clear();
        dateList.clear();
        messageList.clear();

     //  spaceships.clear();
       notifyDataSetChanged();
    }

    /*
    VIEW HOLDER CLASS
     */
    class MyHolder extends RecyclerView.ViewHolder {

      //  TextView nametxt;
        TextView adminTV;
        TextView dateTV;
        TextView messageTV;

        public MyHolder(View itemView) {
            super(itemView);

           // this.nametxt= (TextView) itemView.findViewById(R.id.nameTxt);
            this.adminTV= (TextView) itemView.findViewById(R.id.adminTV);
            this.dateTV= (TextView) itemView.findViewById(R.id.dateTV);
            this.messageTV= (TextView) itemView.findViewById(R.id.messageTV);

        }
    }
}