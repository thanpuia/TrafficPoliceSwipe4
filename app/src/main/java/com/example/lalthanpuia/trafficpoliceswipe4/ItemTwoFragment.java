package com.example.lalthanpuia.trafficpoliceswipe4;


import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemTwoFragment extends Fragment {

    EditText et_admin,et_date,et_message;
    Button button,chooseImg,upload;
    private int admin=0;
    private int date = 100;
    private int message = 500;
    private ImageView imageView;
    private Uri filePath;
    FirebaseStorage storage;
    StorageReference storageReference;

    private final int PICK_IMAGE_REQUEST = 71;
    private static final int CAMERA_REQUEST = 123;

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
         View view =inflater.inflate(R.layout.fragment_item_two, container, false);

        et_admin = view.findViewById(R.id.et_admin);
        et_date = view.findViewById(R.id.et_date);
        et_message = view.findViewById(R.id.et_message);
        button = view.findViewById(R.id.button);
        chooseImg = view.findViewById(R.id.chooseImage);
        upload = view.findViewById(R.id.upload);
        imageView = view.findViewById(R.id.imgView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference database = FirebaseDatabase.getInstance().getReference();


                //   for(int i=0; i<10;i++){
                    admin++;
                    date++;
                    message++;

                    String s_admin = String.valueOf("admin- "+admin);
                    String s_date = String.valueOf("date: "+date);
                    String s_message = String.valueOf("message: "+message);

                    String newKey = database.child("notifications").push().getKey();
                    database.child("notifications/" + newKey).child("admin").setValue(s_admin);
                    database.child("notifications/" + newKey).child("date").setValue(s_date);
                    database.child("notifications/" + newKey).child("message").setValue(s_message);
           //     }




                String admin = String.valueOf(et_admin.getText());
                String date = String.valueOf(et_date.getText());
                String message = String.valueOf(et_message.getText());

                if(admin!=null && date!=null &&message !=null){

                   // String newKey = database.child("notifications").push().getKey();
                    database.child("notifications/" + newKey).child("admin").setValue(admin);
                    database.child("notifications/" + newKey).child("date").setValue(date);
                    database.child("notifications/" + newKey).child("message").setValue(message);
                }else{
                    Toast.makeText(getContext(), "Enter all field", Toast.LENGTH_SHORT).show();
                }

                et_admin.setText("");
                et_date.setText("");
                et_message.setText("");
            }
        });
        
        chooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();

            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

      // myRef.push().setValue("33");
        return view;
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

            StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
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
}
