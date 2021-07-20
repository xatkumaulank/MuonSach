package com.example.muonsach.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.example.muonsach.R;
import com.example.muonsach.data.Data;
import com.example.muonsach.databinding.ActivityDetailBookBorrowBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailBookBorrowActivity extends AppCompatActivity {
    ActivityDetailBookBorrowBinding binding;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_detail_book_borrow);

        Intent intent = getIntent();
        Glide.with(binding.imgDetailBookBorrow.getContext()).load(intent.getStringExtra("bookurl")).into(binding.imgDetailBookBorrow);
        binding.rabDetailBookBorrow.setRating(intent.getIntExtra("numstar",0));
        binding.tvDetailBookAuthorBorrow.setText(intent.getStringExtra("bookauthor"));
        binding.tvDetailBookNameBorrow.setText(intent.getStringExtra("bookname"));

        databaseReference = FirebaseDatabase.getInstance().getReference();
        String bookname = binding.tvDetailBookNameBorrow.getText().toString().trim();
        LoadDataFromFB(bookname);
    }
    private void LoadDataFromFB(String bookname){
        databaseReference.child("bookDetail").child(bookname).child("content").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    binding.tvContentBorrow.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });
        databaseReference.child("bookDetail").child(bookname).child("bookCover").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    binding.tvBookCoverBorrow.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });
        databaseReference.child("bookDetail").child(bookname).child("company").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    binding.tvCompanyBorrow.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });
        databaseReference.child("bookDetail").child(bookname).child("companyPublic").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    binding.tvCompanyPublicBorrow.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });
        databaseReference.child("bookDetail").child(bookname).child("datePublic").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    binding.tvPublicDateBorrow.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });
        databaseReference.child("bookDetail").child(bookname).child("pageNum").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    binding.tvPageNumBorrow.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });
        databaseReference.child("bookDetail").child(bookname).child("size").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    binding.tvSizeBorrow.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });
    }
}