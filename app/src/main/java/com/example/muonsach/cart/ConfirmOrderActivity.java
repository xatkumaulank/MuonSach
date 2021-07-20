package com.example.muonsach.cart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.muonsach.R;
import com.example.muonsach.databinding.ActivityConfirmOrderBinding;
import com.example.muonsach.home.HomeActivity;
import com.example.muonsach.home.HomeFragment;
import com.example.muonsach.login.User;
import com.example.muonsach.obj.ItemCart;
import com.example.muonsach.obj.Notification;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ConfirmOrderActivity extends AppCompatActivity {
    ActivityConfirmOrderBinding binding;
    ItemBillAdapter itemBillAdapter;
    ArrayList<ItemCart> itemCartArrayList;
    RadioButton radioButton;
    int totalCart =0;
    List<String> nameBook, nameUserBorrow;
    String img, fullname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_confirm_order);

        binding.addressCustomer.setText(getIntent().getStringExtra("addressCustomer"));
        binding.nameCustomer.setText(getIntent().getStringExtra("nameCustomer"));
        binding.phoneCustomer.setText(getIntent().getStringExtra("phoneCustomer"));
        binding.totalCart.setText(getIntent().getStringExtra("priceOrder"));

        totalCart = Integer.parseInt(binding.totalCart.getText().toString().trim());
        binding.transportFee.setText("15000");
        binding.totalBill.setText(String.valueOf(totalCart + 15000));
        binding.priceOrder.setText(String.valueOf(totalCart + 15000));


        FirebaseDatabase.getInstance().getReference().child("users")
                .child(User.formatEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail()))
                .child("image").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    img = String.valueOf(task.getResult().getValue());
                }
            }
        });
        //Glide.with(binding.imgCustomer.getContext()).load(img).into(binding.imgCustomer);


        nameBook = ItemCartAdapter.getItemBuy();
        nameUserBorrow = ItemCartAdapter.getItemUserName();


        itemCartArrayList = new ArrayList<>();
        itemBillAdapter = new ItemBillAdapter(this,itemCartArrayList);

        binding.btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        //Toast.makeText(ConfirmOrderActivity.this,ItemCartAdapter.getItemBuy().toString(),Toast.LENGTH_LONG).show();
        runningItemBillAdapter();
    }
    private void showDialog(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users")
                .child(User.formatEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail()));
        for (String book : nameBook){
            reference.child("Cart").child(book).removeValue();
        }
        for (String name : nameUserBorrow){
            String [] nameArr = name.split("-");
            FirebaseDatabase.getInstance().getReference().child("users").child(nameArr[0]).child("bookcase")
                    .child(nameArr[1]).removeValue();

            Notification notification = new Notification(img,binding.nameCustomer.getText().toString().trim()
                    ,"Cho mượn thành công", Calendar.getInstance().getTime().toString());
            FirebaseDatabase.getInstance().getReference().child("users")
                    .child(nameArr[0]).child("notification").push().setValue(notification);
        }

        Notification notification = new Notification(img,binding.nameCustomer.getText().toString().trim()
                ,"Mượn thành công", Calendar.getInstance().getTime().toString());
        reference.child("notification").push().setValue(notification);


        AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmOrderActivity.this);
        builder.setTitle("Thông báo");
        builder.setMessage("Mua thành công");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                gotoHome();
            }
        });
        builder.setNegativeButton("Về trang chủ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                gotoHome();
            }
        });

        builder.show();
    }
    private void gotoHome(){
        Intent intent = new Intent(ConfirmOrderActivity.this,HomeActivity.class);
        startActivity(intent);
    }

    private void runningItemBillAdapter(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        binding.rcvItemInBill.setLayoutManager(linearLayoutManager);
        binding.rcvItemInBill.setFocusable(false);
        binding.rcvItemInBill.setNestedScrollingEnabled(false);
        binding.rcvItemInBill.setAdapter(itemBillAdapter);



        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users")
                .child(User.formatEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail()))
                .child("Cart");

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ItemCart itemCart = snapshot.getValue(ItemCart.class);
                for (String book : nameBook){
                    if (book.equals(itemCart.getNameBookCart() + "-"+itemCart.getNameUserBorrowCart())){
                        itemCartArrayList.add(itemCart);
                    }
                }
                itemBillAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ItemCart itemCart = snapshot.getValue(ItemCart.class);
                for (String book : nameBook){
                    if (book.equals(itemCart.getNameBookCart() + "-"+itemCart.getNameUserBorrowCart())){
                        itemCartArrayList.add(itemCart);
                    }
                }
                itemBillAdapter.notifyDataSetChanged();
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
    public void checkButton(View v){
        int radioId = binding.methodPayment.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
    }

    @Override
    protected void onStop() {
        super.onStop();
        nameBook.clear();
        nameUserBorrow.clear();
    }
}