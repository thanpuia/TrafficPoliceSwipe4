package com.example.lalthanpuia.trafficpoliceswipe4;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FragmentHolderActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_holder);

        Intent intent = getIntent();

        String getString = intent.getStringExtra("click");
        ItemOneFragment itemOneFragment = null;
        ItemTwoFragment itemTwoFragment = null;
        ItemThreeFragment itemThreeFragment = null;
        ItemFourFragment itemFourFragment = null;
        SingleUserFeed singleUserFeed = null;
        GlobalNotificationSenderFragment globalNotificationSenderFragment = null;
        GlobalNotificationFragment globalNotificationFragment = null;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (getString) {

            case "1":
                itemOneFragment = ItemOneFragment.newInstance();
                transaction.replace(R.id.frame_layout, itemOneFragment);
                break;

            case "2":
                itemTwoFragment = ItemTwoFragment.newInstance();
                transaction.replace(R.id.frame_layout, itemTwoFragment);
                break;

            case "3":
                              /*  itemThreeFragment = ItemThreeFragment.newInstance();
                                transaction.replace(R.id.frame_layout,itemThreeFragment);*/
                Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent1, CAMERA_REQUEST);
                break;

            case "4":
                itemTwoFragment = ItemTwoFragment.newInstance();
                // itemFourFragment = ItemFourFragment.newInstance();
                // transaction.replace(R.id.frame_layout,itemFourFragment);
                singleUserFeed = new SingleUserFeed();
                transaction.replace(R.id.frame_layout, singleUserFeed);
                break;

            case "5":

                break;

            case "6":

                break;
            case "7":
                globalNotificationSenderFragment = new GlobalNotificationSenderFragment();
                transaction.replace(R.id.frame_layout, globalNotificationSenderFragment);
                break;
            case "8":
                globalNotificationFragment = new GlobalNotificationFragment();
                transaction.replace(R.id.frame_layout,globalNotificationFragment);
                break;
        }

        transaction.commit();
    }
}
