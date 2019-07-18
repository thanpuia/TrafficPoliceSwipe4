package com.example.lalthanpuia.trafficpoliceswipe4.police;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lalthanpuia.trafficpoliceswipe4.R;

import java.util.ArrayList;

public class PoliceAdapter extends RecyclerView.Adapter<PoliceAdapter.MyHolder> {

    ArrayList<PoliceEntity> policeEntityArrayList;
    /*
    CONSTRUCTOR
    */
    //public MyAdapter(Context c, ArrayList<Spaceship> spaceship) {
    public PoliceAdapter(ArrayList<PoliceEntity> mPolice) {
        this.policeEntityArrayList = mPolice;
    }

    //INITIALIE VH
    @Override
    public PoliceAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.police_view_model,parent,false);
        PoliceAdapter.MyHolder holder=new PoliceAdapter.MyHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int position) {
        myHolder.mSenderNameTv.setText(policeEntityArrayList.get(position).getSenderName());
        myHolder.mDateTv.setText(policeEntityArrayList.get(position).getDate());
        myHolder.mMessageTv.setText(policeEntityArrayList.get(position).getMessage());

        //SET THE MAP
        String maplocation = "http://maps.google.com/maps/api/staticmap?center=" + policeEntityArrayList.get(position).getLatitude() + "," + policeEntityArrayList.get(position).getLongitude() + "&zoom=16&size=200x100&scale=2&markers=size:tiny%7Ccolor:red%7Clabel:S%7C"+ policeEntityArrayList.get(position).getLatitude() + "," + policeEntityArrayList.get(position).getLongitude() +"&key=AIzaSyARsaFMey84nKb-_wlxkkXhRseNak1fSRY";
        Glide.with(myHolder.mImageView.getContext())
                .load(maplocation)
                .into(myHolder.mImageView);

    }

/*    //BIND DATA
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final MyAdapter.MyHolder holder, int position) {
        // holder.nametxt.setText(spaceships.get(position).toString());
        holder.adminTV.setText(adminList.get(position).toString());
        holder.dateTV.setText(dateList.get(position).toString());
        holder.messageTV.setText(messageList.get(position).toString());



    }*/

    /*
    TOTAL ITEMS
     */
    @Override
    public int getItemCount() {
        // return spaceships.size();
        return policeEntityArrayList.size();
    }

    public void add(PoliceEntity mPoliceEntity) {

        policeEntityArrayList.add(mPoliceEntity);
    }

    /*
    CLEAR DATA FROM ADAPTER
     */
    public void clear() {

        policeEntityArrayList.clear();
        notifyDataSetChanged();
    }


    /*
    VIEW HOLDER CLASS
     */
    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        //  TextView nametxt;
        TextView mSenderNameTv, mMessageTv, mDateTv;
        ImageView mImageView;

        public MyHolder(final View itemView) {
            super(itemView);

            this.mSenderNameTv = itemView.findViewById(R.id.police_senderName_Tv);
            this.mMessageTv = itemView.findViewById(R.id.police_message_tv);
            this.mDateTv = itemView.findViewById(R.id.police_date_tv);

            this.mImageView = itemView.findViewById(R.id.police_mapIV);
        }

        @Override
        public void onClick(View view) {

           /* int position = getAdapterPosition();
            Intent intent = new Intent(view.getContext(), ItemFiveActivity.class);

            c.startActivity(intent);*/
        }
    }
}

