package com.example.muonsach.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.muonsach.R;
import com.example.muonsach.databinding.ActivityDetailBookBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailBookActivity extends AppCompatActivity {
    ActivityDetailBookBinding binding;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_book);
        Intent intent = getIntent();
        Glide.with(binding.imgDetailBook.getContext()).load(intent.getStringExtra("bookurl")).into(binding.imgDetailBook);
        binding.rabDetailBook.setRating(intent.getIntExtra("numstar", 0));
        binding.tvDetailBookAuthor.setText(intent.getStringExtra("bookauthor"));
        binding.tvDetailBookName.setText(intent.getStringExtra("bookname"));

        databaseReference = FirebaseDatabase.getInstance().getReference();
        String bookname = binding.tvDetailBookName.getText().toString().trim();
        LoadDataFromFB(bookname);


    }

    private void LoadDataFromFB(String bookname) {
        databaseReference.child("bookDetail").child(bookname).child("content").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    binding.tvContent.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });
        databaseReference.child("bookDetail").child(bookname).child("bookCover").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    binding.tvBookCover.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });
        databaseReference.child("bookDetail").child(bookname).child("company").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    binding.tvCompany.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });
        databaseReference.child("bookDetail").child(bookname).child("companyPublic").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    binding.tvCompanyPublic.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });
        databaseReference.child("bookDetail").child(bookname).child("datePublic").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    binding.tvPublicDate.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });
        databaseReference.child("bookDetail").child(bookname).child("pageNum").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    binding.tvPageNum.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });
        databaseReference.child("bookDetail").child(bookname).child("size").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    binding.tvSize.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });
    }
}
