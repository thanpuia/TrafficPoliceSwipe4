package com.example.lalthanpuia.trafficpoliceswipe4;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import java.util.function.Consumer;

import es.dmoral.toasty.Toasty;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;

import static android.support.constraint.Constraints.TAG;
import static com.example.lalthanpuia.trafficpoliceswipe4.ItemOneFragment.MY_PERMISSIONS_REQUEST_LOCATION;

public class ReportSubmitActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private static final String TAG = "MyApps-Location2";

    GoogleApiClient mGoogleApiClient;

    private LocationRequest locationRequest;

    EditText et_admin, et_date, et_message;
    Button chooseImg, upload;
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

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    String shared_userUniqueKey;
    String shared_fullName;
    String shared_email;
    String shared_phone;
    String shared_Uid;
    String shared_role;

    String lat,lng;

    boolean imageSelect = false;
    FrameLayout button;
    static String reportTitle = "";
    EditText reportTitleEdittext;
    public static final int MY_PERMISSION_REQUEST_READ_EXTERNAL_STORAGE = 1;


    // public static ArrayList<String> userPostUniqueIdLists;

    String newKey;

    // location last updated time
    private String mLastUpdateTime;

    // location updates interval - 10sec
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    // fastest updates interval - 5 sec
    // location updates will be received if another app is requesting the locations
    // than your app can handle
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 5000;

    private static final int REQUEST_CHECK_SETTINGS = 100;


    // bunch of location related apis
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_submit);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setNavigationIcon(R.drawable.back_arrow);

        toolbar.setTitleTextColor(Color.rgb(205, 163, 128));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Submit Report");
        // Setting toolbar as the ActionBar with setSupportActionBar() call

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        pictureUrl = "images/" + UUID.randomUUID().toString();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        //GETTING THE SHARED PREFERENCES:
        shared_Uid = sharedPreferences.getString("uid", "");
        shared_email = sharedPreferences.getString("email", "");
        shared_fullName = sharedPreferences.getString("fullName", "");
        Log.i("TAG", "Shared FullName:" + shared_fullName);
        shared_phone = sharedPreferences.getString("phoneNumber", "");
        shared_role = sharedPreferences.getString("role", "");
        Log.i("TAG", "Shared Role:" + shared_role);

        Log.d("TAG", "ItemTwoFragment/mAuth:" + sharedPreferences.getString("fullName", null));




        // CALL THE INIT
        init();




        et_message = findViewById(R.id.et_message);
        button = findViewById(R.id.button);
        //chooseImg = findViewById(R.id.chooseImage);
        //upload = view.findViewById(R.id.upload);
        //imageView = findViewById(R.id.imgView);
        reportTitleEdittext = findViewById(R.id.reportTitle);

        database = FirebaseDatabase.getInstance().getReference();

        // get the current date and time in human readable format
        sdf = new SimpleDateFormat("HH:mm dd,MMMM", Locale.ENGLISH);

        //Put the title
        if (!reportTitle.equals(""))
            reportTitleEdittext.setText(reportTitle);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateIntoAdminNotification();
                if (imageSelect) {//CHOOSE PICTURE IS TRUE
                    uploadImage();
                }
                updateIntoUserAccount();
            }
        });

        startLocationButtonClick();

    }

    private void updateIntoAdminNotification() {

        //**GET LOCATION START

       // showLastKnownLocation();
        newKey = database.child("notifications").push().getKey();

        SmartLocation.with(this).location()
                .oneFix()
                .start(new OnLocationUpdatedListener() {


                    @Override
                    public void onLocationUpdated(Location location) {

                        Log.i("TAG/Smart Location","Location:"+location);
                        lat = String.valueOf(location.getLatitude());
                        lng = String.valueOf(location.getLongitude());
                        database.child("notifications/" + newKey).child("latitude").setValue(String.valueOf(lat));
                        database.child("notifications/" + newKey).child("longitude").setValue(String.valueOf(lng));
                    }
                });

        //** GET LOCATION ENDS


        String currentDateandTime = sdf.format(new Date());


        String message = String.valueOf(et_message.getText());

        if (message.equals("")) {
            Toast.makeText(getApplicationContext(), "Enter something", Toast.LENGTH_SHORT).show();
        } else {
            // database.child("notifications/" + newKey).child("admin").setValue(shared_role);
            database.child("notifications/" + newKey).child("date").setValue(currentDateandTime);
            database.child("notifications/" + newKey).child("message").setValue(message);

            timestamp = System.currentTimeMillis() / -1000;

            database.child("notifications/" + newKey).child("sortkey").setValue(timestamp);
            database.child("notifications/" + newKey).child("sender_name").setValue(shared_fullName);
            database.child("notifications/" + newKey).child("sender_phone").setValue(shared_phone);
            database.child("notifications/" + newKey).child("sender_role").setValue(shared_role);
            database.child("notifications/" + newKey).child("status").setValue(shared_role);


            database.child("notifications/" + newKey).child("title").setValue(reportTitleEdittext.getText().toString());


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
           // Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            // database.child("notifications/" + newKey).child("latitude").setValue(String.valueOf(lat));
          //  database.child("notifications/" + newKey).child("longitude").setValue(String.valueOf(lng));
          /*  database.child("notifications/" + newKey).child("altitude").setValue(String.valueOf(lastLocation.getAltitude()));
            database.child("notifications/" + newKey).child("accuracy").setValue(String.valueOf(lastLocation.getAccuracy()))*/
            database.child("notifications/" + newKey).child("police_incharge").setValue("").addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    et_message.setText("");
                    reportTitleEdittext.setText("");
                    Toasty.success(getApplicationContext(), "Report sent success!", Toasty.LENGTH_SHORT).show();
                }
            });
            //  database.child("notifications/" + newKey).child("username").setValue(shared_fullName);
            //database.child("notifications/" + newKey).child("user_phone").setValue(shared_phone);


        }
        // CLEAR THE FIELD

    }

    private void updateIntoUserAccount() {

        String newPostUniqueKey = database.child("user_details/post_id").push().getKey();
        database.child("user_details/" + shared_Uid + "/post_id").child(newPostUniqueKey).setValue(newKey);
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
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
                imageSelect = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {

        if (filePath != null) {
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
                            Toast.makeText(getApplicationContext(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }
    }


    public void onConnected(@Nullable Bundle bundle) {
    }

    public void onConnectionSuspended(int i) {
    }

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.report_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent(ReportSubmitActivity.this, MainActivity.class);
             //   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                //finish();
                return true;

            case R.id.profileEdit:
                ;
                return true;

            case R.id.logout:
                ;
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }


    }

    public void reportlistClick(View view) {
    }

    public void listbuttonClick(View view) {
        startActivity(new Intent(this, ListOfReportTitlesActivity.class));

    }
    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    public void showLastKnownLocation() {
        if (mCurrentLocation != null) {
            Log.i("TAG/report submit",mCurrentLocation.getLatitude()+"location");
            Toast.makeText(getApplicationContext(), "Lat: " + mCurrentLocation.getLatitude()
                    + ", Lng: " + mCurrentLocation.getLongitude(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Last known location is not available!", Toast.LENGTH_SHORT).show();
            Log.i("TAG/report submit","not available!");

        }
    }


    //@OnClick(R.id.btn_start_location_updates)
    public void startLocationButtonClick() {
        // Requesting ACCESS_FINE_LOCATION using Dexter library
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                     //   mRequestingLocationUpdates = true;
                       // startLocationUpdates();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            // open device settings when the permission is
                            // denied permanently
                          //  openSettings();
                        }
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }

                }).check();
    }

    //API INIT FOR THE LIBRARY

    private void init() {
        Log.i("TAG/report submit/init","here");
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // location is received
                mCurrentLocation = locationResult.getLastLocation();
                Log.i("TAG/report submit/init","here:"+mCurrentLocation);

                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());

               // updateLocationUI();
            }
        };

       // mRequestingLocationUpdates = false;

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }
}

