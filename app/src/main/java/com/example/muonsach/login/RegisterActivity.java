package com.example.muonsach.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.muonsach.R;
import com.example.muonsach.data.UploadData;
import com.example.muonsach.databinding.ActivityRegisterBinding;
import com.example.muonsach.map.MapActivity;
import com.example.muonsach.obj.GeoLocation;
import com.example.muonsach.user.UserFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity{
    ActivityRegisterBinding binding;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseStorage firebaseStorage;
    private static final int Gallery_Code = 1;
    private Uri imageUri = null;
    private ProgressDialog progressDialog;
    double longtitude = 0;
    double latitude = 0;
    private FusedLocationProviderClient fusedLocationProviderClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        firebaseStorage = FirebaseStorage.getInstance();
        progressDialog = new ProgressDialog(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(RegisterActivity.this);

        binding.imgProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,Gallery_Code);
            }
        });

        binding.alreadyRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        binding.yourLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Gallery_Code && resultCode == RESULT_OK){
            imageUri = data.getData();
            binding.imgProfilePic.setImageURI(imageUri);
        }
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setTitle("Waiting...");
                progressDialog.show();
                String fullname = binding.edFullnameRegister.getText().toString().trim();
                String address = binding.edAddreeRegister.getText().toString().trim();
                String emailId = binding.edEmailRegister.getText().toString().trim();
                String password = binding.edPasswordRegister.getText().toString().trim();


                if (!checkUser(fullname,address,emailId,password)){
                    registerUser(fullname,address,emailId,password);
                }
            }
        });
    }
    private boolean checkUser(String fullname, String address, String emailId, String password){
        return (TextUtils.isEmpty(fullname) && TextUtils.isEmpty(address)
                && TextUtils.isEmpty(emailId) && TextUtils.isEmpty(password));
    }
    private void uploadToFB(String fullname, String address, String emailId, String password){
        StorageReference filepath = firebaseStorage.getReference().child("userImg")
                .child(imageUri.getLastPathSegment());
        filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        String t = task.getResult().toString();
                        DatabaseReference newPost = databaseReference.child(User.formatEmail(emailId));
                        newPost.child("fullname").setValue(fullname);
                        newPost.child("address").setValue(address);
                        newPost.child("emailId").setValue(emailId);
                        newPost.child("password").setValue(password);
                        newPost.child("image").setValue(t);

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("using").child(User.formatEmail(emailId));
                        reference.child("fullname").setValue(fullname);
                        reference.child("image").setValue(t);
                        reference.child("email").setValue(User.formatEmail(emailId));

                        GeoLocation geoLocation = new GeoLocation(longtitude,latitude);

                        FirebaseDatabase.getInstance().getReference().child("GeoLocation").child(User.formatEmail(emailId))
                                .setValue(geoLocation);
                        progressDialog.dismiss();
                    }
                });
            }
        });
    }
    private void registerUser(String fullname, String address, String email, String pass) {
        firebaseAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this,"Successfully Registration!",Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(RegisterActivity.this,"Failed Registration!",Toast.LENGTH_LONG).show();
                        }
                    }
                });
        uploadToFB(fullname,address,email,pass);
    }
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null){
                    try {
                        Geocoder geocoder = new Geocoder(RegisterActivity.this, Locale.getDefault());
                        List<Address> list = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                        latitude = list.get(0).getLatitude();
                        longtitude = list.get(0).getLongitude();
                        binding.edAddreeRegister.setText(Html.fromHtml("<font color = '#6200EE'><b></b><br></font>"+list.get(0).getAddressLine(0)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}