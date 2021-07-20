package com.example.muonsach.cart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.RadioButton;

import com.example.muonsach.R;
import com.example.muonsach.databinding.ActivityYourBillBinding;
import com.example.muonsach.login.User;
import com.example.muonsach.obj.ItemCart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class YourBillActivity extends AppCompatActivity {
    ActivityYourBillBinding binding;
    ItemBillAdapter itemBillAdapter;
    ArrayList<ItemCart> itemCartArrayList;
    List<String> name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_your_bill);


        binding.nameUser.setText(getIntent().getStringExtra("nameUser"));
        binding.nameUser2.setText(getIntent().getStringExtra("nameUser"));
        binding.nameUser1.setText(getIntent().getStringExtra("nameUser"));

        binding.emailUser.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        binding.emailUser2.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        binding.addressUser.setText(getIntent().getStringExtra("addressUser"));
        binding.phoneUser.setText(getIntent().getStringExtra("phoneUser"));
        binding.totalCart.setText(getIntent().getStringExtra("totalCart"));
        binding.totalBill.setText(getIntent().getStringExtra("totalBill"));


        name = ItemCartAdapter.getItemBuy();
        itemCartArrayList = new ArrayList<>();
        itemBillAdapter = new ItemBillAdapter(this,itemCartArrayList);
        runningItemBillAdapter();
    }
    private void runningItemBillAdapter(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        binding.rcvBill.setLayoutManager(linearLayoutManager);
        binding.rcvBill.setFocusable(false);
        binding.rcvBill.setNestedScrollingEnabled(false);
        binding.rcvBill.setAdapter(itemBillAdapter);



        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users")
                .child(User.formatEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail()))
                .child("Cart");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ItemCart itemCart = snapshot.getValue(ItemCart.class);
                for (String book : name){
                    if (book.equals(itemCart.getNameBookCart() + "-"+itemCart.getNameUserBorrowCart())){
                        itemCartArrayList.add(itemCart);
                    }
                }
                itemBillAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ItemCart itemCart = snapshot.getValue(ItemCart.class);
                for (String book : name){
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
}