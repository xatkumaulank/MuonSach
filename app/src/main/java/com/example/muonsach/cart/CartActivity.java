package com.example.muonsach.cart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.muonsach.R;
import com.example.muonsach.databinding.ActivityCartBinding;
import com.example.muonsach.home.CategoryAdapter;
import com.example.muonsach.login.User;
import com.example.muonsach.obj.Category;
import com.example.muonsach.obj.ItemCart;
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
import java.util.Arrays;
import java.util.Objects;

public class CartActivity extends AppCompatActivity {
    ActivityCartBinding binding;
    ItemCartAdapter itemCartAdapter;
    String email;
    ArrayList<ItemCart> itemCartArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart);

        email = getIntent().getStringExtra("email");
        setUpCart(email);

        itemCartArrayList = new ArrayList<>();
        itemCartAdapter = new ItemCartAdapter(this, itemCartArrayList);
        binding.selectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    itemCartAdapter.setCheckAll(false);
                } else {
                    itemCartAdapter.setCheckAll(true);
                }
                itemCartAdapter.notifyDataSetChanged();
            }
        });
        binding.deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemCartAdapter.setDeleteAll(true);
                itemCartAdapter.notifyDataSetChanged();
            }
        });
        binding.btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemCartAdapter.isCheckAll == 0) {
                    Toast.makeText(CartActivity.this, "Bạn chưa chọn cuốn sách nào!", Toast.LENGTH_LONG).show();
                    return;
                }
                //Toast.makeText(CartActivity.this,ItemCartAdapter.getItemBuy().toString(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(CartActivity.this, ConfirmOrderActivity.class);
                intent.putExtra("nameCustomer", binding.cartUserName.getText().toString().trim());
                intent.putExtra("phoneCustomer", binding.cartUserPhone.getText().toString().trim());
                intent.putExtra("addressCustomer", binding.cartUserAddress.getText().toString().trim());
                intent.putExtra("priceOrder", binding.cartTotal.getText().toString().trim());
                startActivity(intent);
            }
        });
        setUpCart(email);

        runningItemCartAdapter();

    }

    private void runningItemCartAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CartActivity.this, RecyclerView.VERTICAL, false);
        binding.rcvCart.setLayoutManager(linearLayoutManager);
        binding.rcvCart.setFocusable(false);
        binding.rcvCart.setNestedScrollingEnabled(false);
        binding.rcvCart.setAdapter(itemCartAdapter);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users")
                .child(User.formatEmail(email)).child("Cart");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                ItemCart itemCart = snapshot.getValue(ItemCart.class);
                itemCartArrayList.add(itemCart);

                itemCartAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                ItemCart itemCart = snapshot.getValue(ItemCart.class);
                itemCartArrayList.add(itemCart);

                itemCartAdapter.notifyDataSetChanged();
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

    private void setUpCart(String email) {
        FirebaseDatabase.getInstance().getReference().child("users")
                .child(User.formatEmail(email))
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        //binding.cartTotal.setText(String.valueOf(task.getResult().getValue()));
                        if (Objects.equals(snapshot.getKey(), "totalCart")) {
                            binding.cartTotal.setText(String.valueOf(snapshot.getValue()));
                        }

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
        FirebaseDatabase.getInstance().getReference().child("users").child(User.formatEmail(email))
                .child("fullname").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    binding.cartUserName.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });
        FirebaseDatabase.getInstance().getReference().child("users").child(User.formatEmail(email))
                .child("phonenum").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    binding.cartUserPhone.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });
        FirebaseDatabase.getInstance().getReference().child("users").child(User.formatEmail(email))
                .child("address").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    binding.cartUserAddress.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });
    }

}
