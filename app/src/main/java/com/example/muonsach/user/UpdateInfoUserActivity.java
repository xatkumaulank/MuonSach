package com.example.muonsach.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.muonsach.R;
import com.example.muonsach.databinding.ActivityUpdateInfoUserBinding;
import com.example.muonsach.home.HomeActivity;
import com.example.muonsach.login.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateInfoUserActivity extends AppCompatActivity {
    ActivityUpdateInfoUserBinding binding;
    DatabaseReference reference;
    String oldpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_info_user);

        reference = FirebaseDatabase.getInstance().getReference().child("users");

        getDataFromFB(User.formatEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail()));

        binding.cbChangePassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    binding.lnChangePass.setVisibility(View.VISIBLE);
                    binding.edtOldPass.setEnabled(true);
                    binding.edtNewPass.setEnabled(true);
                    binding.edtConfirmNewPass.setEnabled(true);
                }
                else {
                    binding.lnChangePass.setVisibility(View.GONE);
                    binding.edtOldPass.setEnabled(false);
                    binding.edtNewPass.setEnabled(false);
                    binding.edtConfirmNewPass.setEnabled(false);
                }

            }
        });
        binding.btnSaveChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData(User.formatEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail()));
            }
        });
    }

    private boolean checkPass(String email){
        reference.child(email).child("password").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    oldpass = String.valueOf(task.getResult().getValue());
                }
            }
        });
        if (!binding.edtOldPass.getText().toString().trim().equals(oldpass)){
            Toast.makeText(UpdateInfoUserActivity.this,"Mật khẩu không khớp",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!binding.edtNewPass.getText().toString().trim().equals(binding.edtConfirmNewPass.getText().toString().trim())){
            Toast.makeText(UpdateInfoUserActivity.this,"Mật khẩu không khớp",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void updateData(String email){
        if (binding.lnChangePass.getVisibility() == View.VISIBLE){
            if (checkPass(email)){
                FirebaseAuth.getInstance().getCurrentUser().updatePassword(binding.edtConfirmNewPass.getText().toString().trim());
                reference.child(email).child("password").setValue(binding.edtConfirmNewPass.getText().toString().trim());
            }else {
                return;
            }
        }
        else {
            reference.child(email).child("phoneNum").setValue(binding.edtPhoneNum.getText().toString().trim());
        }

    }

    private void getDataFromFB(String email) {
        reference.child(email).child("fullname").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    binding.edtName.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });

        reference.child(email).child("emailId").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    binding.edtEmail.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });
        reference.child(email).child("phoneNum").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    binding.edtPhoneNum.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });
    }
}