package com.example.lalthanpuia.trafficpoliceswipe4;


import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lalthanpuia.trafficpoliceswipe4.signing.GoogleSignIn;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import es.dmoral.toasty.Toasty;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemTwoFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ActivityCompat.OnRequestPermissionsResultCallback {

    private static final String TAG = "MyApps-Location2";

    GoogleApiClient mGoogleApiClient;

    private LocationRequest locationRequest;

    EditText et_admin, et_date, et_message;
    Button button, chooseImg, upload;
    private ImageView imageView;
    private Uri filePath;
    FirebaseStorage storage;
    StorageReference storageReference;
    public static String pictureUrl;
    long timestamp;
    DatabaseReference database;
    public static LocationManager locationManager;
    public static LocationListener locationListener;
    SimpleDateFormat sdf;
    private final int PICK_IMAGE_REQUEST = 71;
    private static final int CAMERA_REQUEST = 123;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    String shared_userUniqueKey;
    String shared_fullName;
    String shared_email;
    String shared_phone;
    String shared_Uid;
    boolean imageSelect = false;

   // public static ArrayList<String> userPostUniqueIdLists;

    String newKey;
    public static ItemTwoFragment newInstance() {
        ItemTwoFragment fragment = new ItemTwoFragment();
        return fragment;
    }

    public ItemTwoFragment() {
        // Required empty public constructor
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item_two, container, false);
        pictureUrl = "images/" + UUID.randomUUID().toString();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        //GETTING THE SHARED PREFERENCES:
        //shared_userUniqueKey = sharedPreferences.getString("userUniqueKey","");
        //shared_fullName = sharedPreferences.getString("fullName","");
        //shared_email = sharedPreferences.getString("email","");
        //shared_phone = sharedPreferences.getString("phone","");
        shared_Uid = sharedPreferences.getString("Uid","");

        Log.d("TAG","ItemTwoFragment/mAuth:"+ sharedPreferences.getString("fullName", null));

        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi
                .checkLocationSettings(mGoogleApiClient, builder.build());
        mGoogleApiClient.connect();

        et_message = view.findViewById(R.id.et_message);
        button = view.findViewById(R.id.button);
        chooseImg = view.findViewById(R.id.chooseImage);
        //upload = view.findViewById(R.id.upload);
        imageView = view.findViewById(R.id.imgView);
        database = FirebaseDatabase.getInstance().getReference();

        // get the current date and time in human readable format
        sdf = new SimpleDateFormat("HH:mm dd,MMMM", Locale.ENGLISH);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               updateIntoAdminNotification();
               if(imageSelect) {//CHOOSE PICTURE IS TRUE
                    uploadImage();
               }
               updateIntoUserAccount();

            }
        });
        
        chooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

      /*  upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });*/

      //  getUserPostIds();//FOR FURTHER USE
      // myRef.push().setValue("33");
        return view;
    }

    private void updateIntoAdminNotification() {
        String currentDateandTime = sdf.format(new Date());

        newKey = database.child("notifications").push().getKey();

        String message = String.valueOf(et_message.getText());

        if (message.equals("")) { Toast.makeText(getContext(), "Enter something", Toast.LENGTH_SHORT).show();
        } else {
            database.child("notifications/" + newKey).child("admin").setValue("admin");
            database.child("notifications/" + newKey).child("date").setValue(currentDateandTime);
            database.child("notifications/" + newKey).child("message").setValue(message);

            timestamp = System.currentTimeMillis() / -1000;
            database.child("notifications/" + newKey).child("sortkey").setValue(timestamp);

            //WITH PIC OR WITHOUT PIC CHECKER
            //Pic Embedded
            if (filePath != null) {
                database.child("notifications/" + newKey).child("downloadURL").setValue(pictureUrl);
            }

            //pic not Embedded
            if (filePath == null) {
                database.child("notifications/" + newKey).child("downloadURL").setValue("0");
            }

            //GETTING THE LOCATION

            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            database.child("notifications/" + newKey).child("latitude").setValue(String.valueOf(lastLocation.getLatitude()));
            database.child("notifications/" + newKey).child("longitude").setValue(String.valueOf(lastLocation.getLongitude()));
            database.child("notifications/" + newKey).child("altitude").setValue(String.valueOf(lastLocation.getAltitude()));
            database.child("notifications/" + newKey).child("accuracy").setValue(String.valueOf(lastLocation.getAccuracy()));
            database.child("notifications/" + newKey).child("police_incharge").setValue("");
            database.child("notifications/" + newKey).child("username").setValue(shared_fullName);
            database.child("notifications/" + newKey).child("user_phone").setValue(shared_phone);



        }
        // CLEAR THE FIELD
        et_message.setText("");
    }

    private void updateIntoUserAccount() {

        String newPostUniqueKey = database.child("user_details/post_id").push().getKey();
        database.child("user_details/"+shared_userUniqueKey+"/post_id").child(newPostUniqueKey).setValue(newKey);
    }


    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 71);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
                imageSelect = true;
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    private void uploadImage() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            //pictureUrl = "images" + UUID.randomUUID().toString();

            StorageReference ref = storageReference.child(pictureUrl);

            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }
    @Override
    public void onConnected(@Nullable Bundle bundle) { }
    @Override
    public void onConnectionSuspended(int i) { }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) { }



}