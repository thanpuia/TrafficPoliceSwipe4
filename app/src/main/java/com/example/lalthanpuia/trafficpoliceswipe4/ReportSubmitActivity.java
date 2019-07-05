package com.example.lalthanpuia.trafficpoliceswipe4;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class ReportSubmitActivity extends AppCompatActivity  implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ActivityCompat.OnRequestPermissionsResultCallback {

    private static final String TAG = "MyApps-Location2";

    GoogleApiClient mGoogleApiClient;

    private LocationRequest locationRequest;

    EditText et_admin, et_date, et_message;
    Button  chooseImg, upload;
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
    private Toolbar toolbar;

    String shared_userUniqueKey;
    String shared_fullName;
    String shared_email;
    String shared_phone;
    String shared_Uid;
    boolean imageSelect = false;
    FrameLayout button ;

    // public static ArrayList<String> userPostUniqueIdLists;

    String newKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_submit);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setNavigationIcon(R.drawable.back_arrow);
        toolbar.setLogoDescription("sdf");

        // Setting toolbar as the ActionBar with setSupportActionBar() call
        setSupportActionBar(toolbar);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        pictureUrl = "images/" + UUID.randomUUID().toString();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        //GETTING THE SHARED PREFERENCES:
        shared_Uid = sharedPreferences.getString("Uid","");

        Log.d("TAG","ItemTwoFragment/mAuth:"+ sharedPreferences.getString("fullName", null));

/*
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) getApplicationContext())
                .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) getApplicationContext())
                .addApi(LocationServices.API).build();
*/

        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();



        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi
                .checkLocationSettings(mGoogleApiClient, builder.build());
        mGoogleApiClient.connect();

        et_message =findViewById(R.id.et_message);
        button = findViewById(R.id.button);
        chooseImg = findViewById(R.id.chooseImage);
        //upload = view.findViewById(R.id.upload);
        imageView = findViewById(R.id.imgView);
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

//        chooseImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                chooseImage();
//            }
//        });


    }

    private void updateIntoAdminNotification() {
        String currentDateandTime = sdf.format(new Date());

        newKey = database.child("notifications").push().getKey();

        String message = String.valueOf(et_message.getText());

        if (message.equals("")) { Toast.makeText(getApplicationContext(), "Enter something", Toast.LENGTH_SHORT).show();
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

            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), filePath);
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
            final ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            //pictureUrl = "images" + UUID.randomUUID().toString();

            StorageReference ref = storageReference.child(pictureUrl);

            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();

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


    public void onConnected(@Nullable Bundle bundle) { }

    public void onConnectionSuspended(int i) { }

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) { }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.report_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.profileEdit) {


            return true;

        }else if (id == R.id.logout){

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
