package com.example.muonsach.search;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.muonsach.R;
import com.example.muonsach.databinding.ActivityResultBookBinding;
import com.example.muonsach.login.User;
import com.example.muonsach.obj.ItemCart;
import com.example.muonsach.obj.Owner;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ResultBookActivity extends AppCompatActivity {

    ActivityResultBookBinding binding;
    OwnerAdapter ownerAdapter;
    List<Owner> ownerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_result_book);

        ownerList = new ArrayList<>();
        ownerAdapter = new OwnerAdapter(ResultBookActivity.this, ownerList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        binding.rcvOwner.setLayoutManager(linearLayoutManager);
        binding.rcvOwner.setAdapter(ownerAdapter);
        runningOwnerAdapter();

    }

    private void runningOwnerAdapter() {
        FirebaseDatabase.getInstance().getReference().child("Owner")
                .child("books").child(getIntent().getStringExtra("nameBook"))
                .child("owner").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Owner owner = snapshot.getValue(Owner.class);
                if (!User.formatEmail(owner.getEmailOwner()).equals(User.formatEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail()))){
                    ownerList.add(owner);
                }
                binding.noInfo.setVisibility(View.GONE);
                ownerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Owner owner = snapshot.getValue(Owner.class);
                if (!User.formatEmail(owner.getEmailOwner()).equals(User.formatEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail()))){
                    ownerList.add(owner);
                }
                binding.noInfo.setVisibility(View.GONE);
                ownerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}