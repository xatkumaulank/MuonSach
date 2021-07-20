package com.example.muonsach.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.muonsach.R;
import com.example.muonsach.clickRcv.ItemClickListener;
import com.example.muonsach.databinding.ActivityBookBorrowBinding;
import com.example.muonsach.obj.BookBorrow;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BookBorrowActivity extends AppCompatActivity {
    ActivityBookBorrowBinding binding;
    BookBorrowAdapter bookBorrowAdapter;
    ItemClickListener listener;
    ArrayList<BookBorrow> borrowArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_book_borrow);
        setOnclickListener();
        borrowArrayList = new ArrayList<>();
        bookBorrowAdapter = new BookBorrowAdapter(borrowArrayList,this,listener);
        LinearLayoutManager bookBorrowLinearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        binding.rcvBorrowBook.setLayoutManager(bookBorrowLinearLayoutManager);
        binding.rcvBorrowBook.setAdapter(bookBorrowAdapter);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("bookBorrow");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    BookBorrow bookBorrow = dataSnapshot.getValue(BookBorrow.class);
                    borrowArrayList.add(bookBorrow);
                }
                bookBorrowAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void setOnclickListener() {
        listener = new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(BookBorrowActivity.this,DetailBookBorrowActivity.class);
                intent.putExtra("bookname",borrowArrayList.get(position).getTvBookBorrowName());
                intent.putExtra("bookurl",borrowArrayList.get(position).getImgBookBorrow());
                intent.putExtra("bookauthor",borrowArrayList.get(position).getTvBookBorrowAuthorName());
                intent.putExtra("numstar",borrowArrayList.get(position).getNumStar());
                startActivity(intent);
            }
        };
    }
}