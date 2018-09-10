package com.example.lalthanpuia.trafficpoliceswipe4;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemTwoFragment extends Fragment {

    EditText et_admin,et_date,et_message;
    Button button;
    private int admin=0;
    private int date = 100;
    private int message = 500;
    public static ItemTwoFragment newInstance() {
        ItemTwoFragment fragment = new ItemTwoFragment();
        return fragment;
    }


    public ItemTwoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view =inflater.inflate(R.layout.fragment_item_two, container, false);

        et_admin = view.findViewById(R.id.et_admin);
        et_date = view.findViewById(R.id.et_date);
        et_message = view.findViewById(R.id.et_message);
        button = view.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference database = FirebaseDatabase.getInstance().getReference();


//                for(int i=0; i<10;i++){
//                    admin++;
//                    date++;
//                    message++;
//
//                    String s_admin = String.valueOf("admin- "+admin);
//                    String s_date = String.valueOf("date: "+date);
//                    String s_message = String.valueOf("message: "+message);
//
//                    String newKey = database.child("notifications").push().getKey();
//                    database.child("notifications/" + newKey).child("admin").setValue(s_admin);
//                    database.child("notifications/" + newKey).child("date").setValue(s_date);
//                    database.child("notifications/" + newKey).child("message").setValue(s_message);
//                }




                                    String admin = String.valueOf(et_admin.getText());
                String date = String.valueOf(et_date.getText());
                String message = String.valueOf(et_message.getText());

                if(admin!=null && date!=null &&message !=null){

                    String newKey = database.child("notifications").push().getKey();
                    database.child("notifications/" + newKey).child("admin").setValue(admin);
                    database.child("notifications/" + newKey).child("date").setValue(date);
                    database.child("notifications/" + newKey).child("message").setValue(message);
                }else{
                    Toast.makeText(getContext(), "Enter all field", Toast.LENGTH_SHORT).show();
                }
            }
        });

      // myRef.push().setValue("33");
        return view;
    }

}
