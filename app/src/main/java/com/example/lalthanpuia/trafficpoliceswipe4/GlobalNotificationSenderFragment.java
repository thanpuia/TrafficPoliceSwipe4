package com.example.lalthanpuia.trafficpoliceswipe4;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class GlobalNotificationSenderFragment extends Fragment {

    DatabaseReference myref;
    FirebaseDatabase globalDatabase;
    long timeStamp;

    public GlobalNotificationSenderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_global_notification_sender, container,false);
        globalDatabase = FirebaseDatabase.getInstance();


        myref = globalDatabase.getReference("global_notification/");


        return view;
    }

}
