package com.example.lalthanpuia.trafficpoliceswipe4;


import android.app.Notification;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */

public class ItemOneFragment extends Fragment {

    private EndlessRecyclerViewScrollListener scrollListener;

    RecyclerView mRecyclerView ;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    private String[] admin;
    private String[] date;
    private String[] message;

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

        admin = new String[]{"apple","brilliant","curious","dare","enlightenment","hope","faith","humility"};
        date = new String[]{"Dpple","Dbrilliant","Dcurious","Ddare","enlightenment","hope","faith","humility"};
        message = new String[]{"Mapple","Mbrilliant","Mcurious","Mdare","enlightenment","hope","faith","humility"};

        mRecyclerView = view.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                mAdapter = new ItemOneFragmentAdapter(admin,date,message);
                mRecyclerView.setAdapter(mAdapter);

            }
        };

        //mAdapter = new ItemOneFragmentAdapter(admin,date,message);
        //mRecyclerView.setAdapter(mAdapter);
        return view;
    }
}