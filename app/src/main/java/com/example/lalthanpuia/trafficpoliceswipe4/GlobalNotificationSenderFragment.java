package com.example.lalthanpuia.trafficpoliceswipe4;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.lalthanpuia.trafficpoliceswipe4.entity.GlobalNotifyEntity;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import es.dmoral.toasty.Toasty;


/**
 * A simple {@link Fragment} subclass.
 */
public class GlobalNotificationSenderFragment extends Fragment {

    Button globalSenderButton;
    SimpleDateFormat sdf;
    GlobalNotifyEntity globalNotifyEntity;
    EditText messageET;
    DatabaseReference firebaseDatabase;

    public GlobalNotificationSenderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_global_notification_sender, container,false);
        firebaseDatabase = FirebaseDatabase.getInstance().getReference();

        messageET = view.findViewById(R.id.globalMessage);
        globalSenderButton = view.findViewById(R.id.globalSenderButton);

        sdf = new SimpleDateFormat("HH:mm dd,MMMM", Locale.ENGLISH);

        globalSenderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageET.getText().toString();
                String currentTime = sdf.format(new Date());

                globalNotifyEntity = new GlobalNotifyEntity();
                globalNotifyEntity.setTimeStamp(currentTime);
                globalNotifyEntity.setMessage(message);

                //upload the data
                String newKey = firebaseDatabase.child("global_notifications").push().getKey();
                firebaseDatabase.child("global_notifications/"+newKey).setValue(globalNotifyEntity, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        messageET.setText("");
                        Toasty.success(getContext(),"sent successfully",Toasty.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return view;
    }


}
