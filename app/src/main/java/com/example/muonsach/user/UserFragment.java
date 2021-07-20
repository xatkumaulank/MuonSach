package com.example.muonsach.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.muonsach.R;
import com.example.muonsach.databinding.FragmentUserBinding;
import com.example.muonsach.home.HomeActivity;
import com.example.muonsach.login.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class UserFragment extends Fragment {
    FragmentUserBinding binding;
    private DatabaseReference databaseReference;
    BookcaseAdapter bookcaseAdapter;

    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_user,container,false);


        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

//        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
//        binding.userViewpager.setAdapter(viewPagerAdapter);
//        binding.tablayout.setupWithViewPager(binding.userViewpager);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        getDataFromFireBase(email);
        runningBookcaseAdapter(email);

        binding.tvEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserFragment.this.getContext(),UpdateInfoUserActivity.class);

                startActivity(intent);
            }
        });
        return binding.getRoot();
    }
    private void getDataFromFireBase(String email){

        databaseReference.child("users").child(User.formatEmail(email)).child("emailId").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    binding.tvAcountName.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });
        databaseReference.child("users").child(User.formatEmail(email)).child("fullname").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    binding.tvUser.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });
        databaseReference.child("users").child(User.formatEmail(email)).child("address").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    binding.tvAddress.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });
        databaseReference.child("users").child(User.formatEmail(email)).child("image").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Glide.with(binding.imgvAvatUser.getContext()).load(task.getResult().getValue()).into(binding.imgvAvatUser);
                }
            }
        });

    }
    private void runningBookcaseAdapter(String email){
        List<BookCase> list = new ArrayList<>();
        bookcaseAdapter = new BookcaseAdapter(list,UserFragment.this.getContext());
        LinearLayoutManager functionLinearLayoutManager = new LinearLayoutManager(UserFragment.this.getContext(), RecyclerView.VERTICAL,false);
        binding.rcvBookcase.setLayoutManager(functionLinearLayoutManager);
        binding.rcvBookcase.setAdapter(bookcaseAdapter);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(User.formatEmail(email)).child("bookcase");

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                BookCase bookCase = snapshot.getValue(BookCase.class);
                list.add(bookCase);

                bookcaseAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                BookCase bookCase = snapshot.getValue(BookCase.class);
                list.add(bookCase);

                bookcaseAdapter.notifyDataSetChanged();
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