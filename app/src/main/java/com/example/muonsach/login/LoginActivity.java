package com.example.muonsach.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.muonsach.R;
import com.example.muonsach.data.Data;
import com.example.muonsach.databinding.ActivityLoginBinding;
import com.example.muonsach.home.HomeActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity{
    private ActivityLoginBinding binding;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        //Data.addListCategory();
        //Data.addListRisingUser();
        //Data.addListDetailCategory();
        //Data.addListBookDetail();
        reference = FirebaseDatabase.getInstance().getReference();
        binding.newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        binding.btnDangNhapLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickLogin();
            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    loadDataUser(user);
                }else {
                    loadDataUser(null);
                }
            }
        };

    }



    private void loadDataUser(FirebaseUser user) {
        if (user != null){
            String displayname = user.getDisplayName();
            SharedPreferences sharedPreferences = getSharedPreferences("name",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("displayname",displayname);
            editor.apply();
            if (user.getPhotoUrl() != null){
                String userimg = user.getPhotoUrl().toString();
                userimg = userimg + "?type=large";
                reference.child("users").child(displayname).child("fullname").setValue(displayname);
                reference.child("users").child(displayname).child("image").setValue(userimg);
                reference.child("users").child(displayname).child("emailId").setValue(displayname);
                binding.edPasswordLogin.setText(displayname);
                binding.edEmailLogin.setText(displayname);
            }
        }
    }

    private void clickLogin() {
        String email = binding.edEmailLogin.getText().toString().trim();
        String pass = binding.edPasswordLogin.getText().toString().trim();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)){
            Toast.makeText(LoginActivity.this,"Error",Toast.LENGTH_LONG).show();
        }else {
            firebaseAuth.signInWithEmailAndPassword(email,pass)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(LoginActivity.this,"Login Succesfully!",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                            intent.putExtra("email",email);

                            SharedPreferences sharedPreferences = getSharedPreferences("key",MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("email",email);
                            editor.apply();
                            startActivity(intent);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null){
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }
}