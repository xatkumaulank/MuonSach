package com.example.muonsach.search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.muonsach.R;
import com.example.muonsach.chat.ChatActivity;
import com.example.muonsach.clickRcv.ItemClickListener;
import com.example.muonsach.databinding.ActivityDetailUserBinding;

import com.example.muonsach.login.User;
import com.example.muonsach.obj.ItemCart;
import com.example.muonsach.user.BookCase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DetailUserActivity extends AppCompatActivity {
    ActivityDetailUserBinding binding;
    DatabaseReference databaseReference;
    String imageUrl, fullname, emailId, email;
    BookcaseDetalAdapter bookcaseDetalAdapter;
    ArrayList<BookCase> bookCaseList;
    ItemClickListener listenerCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_detail_user);
        databaseReference = FirebaseDatabase.getInstance().getReference();


        email = getIntent().getStringExtra("email");
        //binding.tvAcountNameDetail.setText(email);
        loadDataFromFB(email);
        bookCaseList = new ArrayList<>();
        runningBookcaseAdapter();

        databaseReference.child("users").child(email).child("image").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    imageUrl = task.getResult().getValue().toString();
                }
            }
        });
        databaseReference.child("users").child(email).child("fullname").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    fullname = task.getResult().getValue().toString();
                }
            }
        });
        databaseReference.child("users").child(email).child("emailId").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    emailId = task.getResult().getValue().toString();
                }
            }
        });

        binding.tvAddFriendDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailUserActivity.this, ChatActivity.class);
                intent.putExtra("imageurl",imageUrl);
                intent.putExtra("fullname",fullname);
                intent.putExtra("emailId",emailId);
//                SharedPreferences sharedPreferences = getSharedPreferences("key",MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString("emailId",emailId);
//                editor.apply();
                startActivity(intent);
            }
        });


        //binding.tvAddFriendDetail.setText(email);




    }

    private void runningBookcaseAdapter(){
        setOnClickListener();
        bookcaseDetalAdapter = new BookcaseDetalAdapter(DetailUserActivity.this,bookCaseList,listenerCart);
        LinearLayoutManager functionLinearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        binding.rcvBookcaseDetail.setLayoutManager(functionLinearLayoutManager);
        binding.rcvBookcaseDetail.setAdapter(bookcaseDetalAdapter);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(email).child("bookcase");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    BookCase bookCase = dataSnapshot.getValue(BookCase.class);
                    bookCaseList.add(bookCase);
                }
                bookcaseDetalAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void setOnClickListener(){

        listenerCart = new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").
                        child(User.formatEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail())).child("Cart");
                ItemCart itemCart = new ItemCart();

                itemCart.setImgBookCart(bookCaseList.get(position).getImgBookcaseBook());
                itemCart.setAddressUserBorrowCart(binding.tvAddressDetail.getText().toString().trim());
                itemCart.setImgUserBorrowCart(imageUrl);
                itemCart.setNameBookCart(bookCaseList.get(position).getTvBookcaseBookname());
                itemCart.setPriceBookCart(bookCaseList.get(position).getTvBookcasePrice());
                itemCart.setNameUserBorrowCart(binding.tvUserDetail.getText().toString().trim());
                itemCart.setEmailUserBorrowCart(User.formatEmail(binding.tvAcountNameDetail.getText().toString().trim()));

                databaseReference.child(bookCaseList.get(position).getTvBookcaseBookname() + "-"+
                        binding.tvUserDetail.getText().toString().trim()).setValue(itemCart);

                FirebaseDatabase.getInstance().getReference().child("users").
                        child(User.formatEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail())).child("totalCart").setValue(0);
                Toast.makeText(DetailUserActivity.this,"Đã thêm",Toast.LENGTH_LONG).show();
            }
        };
    }
    private void loadDataFromFB(String email){

        databaseReference.child("users").child(email).child("emailId").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    binding.tvAcountNameDetail.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });
        databaseReference.child("users").child(email).child("fullname").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    binding.tvUserDetail.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });
        databaseReference.child("users").child(email).child("address").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    binding.tvAddressDetail.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });
        databaseReference.child("users").child(email).child("image").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Glide.with(binding.imgvAvatUserDetail.getContext()).load(task.getResult().getValue()).into(binding.imgvAvatUserDetail);
                }
            }
        });

    }
}