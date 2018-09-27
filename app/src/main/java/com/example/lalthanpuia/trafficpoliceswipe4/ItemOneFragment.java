package com.example.lalthanpuia.trafficpoliceswipe4;

import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.srx.widget.PullToLoadView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */

public class ItemOneFragment extends Fragment {

    FirebaseDatabase database;
    DatabaseReference myRef;
    public static ItemOneFragment newInstance() {
        ItemOneFragment fragment = new ItemOneFragment();
        return fragment;
    }

    public ItemOneFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_item_one, container, false);


        PullToLoadView pullToLoadView = view.findViewById(R.id.pullToLoadView);

        new Paginator(getContext(),pullToLoadView).initializePaginator();

        return view;
    }
}