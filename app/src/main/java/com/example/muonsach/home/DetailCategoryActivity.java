package com.example.muonsach.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.muonsach.R;
import com.example.muonsach.clickRcv.ItemClickListener;
import com.example.muonsach.databinding.ActivityDetailCategoryBinding;
import com.example.muonsach.obj.Book;
import com.example.muonsach.search.DetailBookShareActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DetailCategoryActivity extends AppCompatActivity {
    ActivityDetailCategoryBinding binding;
    DetailCategoryAdapter detailCategoryAdapter;
    ArrayList<Book> bookArrayList;
    ItemClickListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_detail_category);

        Glide.with(binding.imgCategory.getContext()).load(getIntent().getStringExtra("imgCategory")).into(binding.imgCategory);
        binding.nameCategory.setText(getIntent().getStringExtra("nameCategory"));

        bookArrayList = new ArrayList<>();
        listener = new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(DetailCategoryActivity.this, DetailBookShareActivity.class);
                intent.putExtra("nameBook",bookArrayList.get(position).getBookname());
                intent.putExtra("imgBook",bookArrayList.get(position).getBookimg());
                intent.putExtra("authorBook",bookArrayList.get(position).getBookauthor());
                intent.putExtra("numstar",bookArrayList.get(position).getRating());
                startActivity(intent);
            }
        };
        detailCategoryAdapter = new DetailCategoryAdapter(this,bookArrayList,listener);
        LinearLayoutManager bookLinearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        binding.rcvDetailCategory.setLayoutManager(bookLinearLayoutManager);
        binding.rcvDetailCategory.setFocusable(false);
        binding.rcvDetailCategory.setNestedScrollingEnabled(false);
        binding.rcvDetailCategory.setAdapter(detailCategoryAdapter);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Category")
                .child(getIntent().getStringExtra("nameCategory")).child("books");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Book book = dataSnapshot.getValue(Book.class);
                    bookArrayList.add(book);
                }
                detailCategoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}