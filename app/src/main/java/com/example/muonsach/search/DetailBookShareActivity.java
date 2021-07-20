package com.example.muonsach.search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.muonsach.R;
import com.example.muonsach.databinding.ActivityDetailBookShareBinding;
import com.example.muonsach.login.User;
import com.example.muonsach.obj.Owner;
import com.example.muonsach.user.BookCase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class DetailBookShareActivity extends AppCompatActivity {
    ActivityDetailBookShareBinding binding;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_book_share);
        Intent intent = getIntent();
        Glide.with(binding.imgDetailBookShare.getContext()).load(intent.getStringExtra("imgBook")).into(binding.imgDetailBookShare);
        binding.rabDetailBookShare.setRating(intent.getIntExtra("numstar", 0));
        binding.tvDetailBookAuthorShare.setText(intent.getStringExtra("authorBook"));
        binding.tvDetailBookNameShare.setText(intent.getStringExtra("nameBook"));

        databaseReference = FirebaseDatabase.getInstance().getReference();
        String bookname = binding.tvDetailBookNameShare.getText().toString().trim();
        LoadDataFromFB(bookname);
        setUpOwner(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        binding.lnDetailBookAddShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog();
            }
        });
        binding.lnDetailBookBorrowShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailBookShareActivity.this, ResultBookActivity.class);
                intent.putExtra("nameBook", binding.tvDetailBookNameShare.getText().toString().trim());
                startActivity(intent);
            }
        });
    }

    private void showBottomSheetDialog() {
        View dialog = getLayoutInflater().inflate(R.layout.layout_bottom_sheet_book, null);

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);

        bottomSheetDialog.setContentView(dialog);

        ImageView imgBookDialog = dialog.findViewById(R.id.imgBookDialog);
        Glide.with(imgBookDialog.getContext()).load(getIntent().getStringExtra("imgBook")).into(imgBookDialog);

        TextView nameBookDialog = dialog.findViewById(R.id.nameBookDialog);
        nameBookDialog.setText(getIntent().getStringExtra("nameBook"));

        TextView authorBookDialog = dialog.findViewById(R.id.authorBookDialog);
        authorBookDialog.setText(getIntent().getStringExtra("authorBook"));

        EditText price = dialog.findViewById(R.id.priceBookDialog);

        Button okDialog = dialog.findViewById(R.id.okDialog);
        okDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToBookcase(getIntent().getStringExtra("imgBook"), authorBookDialog.getText().toString().trim(),
                        nameBookDialog.getText().toString().trim(), price.getText().toString().trim());
                addOwner(FirebaseAuth.getInstance().getCurrentUser().getEmail(),price.getText().toString().trim());
                //addOwner(FirebaseAuth.getInstance().getCurrentUser().getEmail(),price.getText().toString().trim());
                Toast.makeText(DetailBookShareActivity.this, "Da them", Toast.LENGTH_LONG).show();
                bottomSheetDialog.dismiss();
            }
        });

        Button cancelDialog = dialog.findViewById(R.id.cancelDialog);
        cancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.show();
    }

    private void addOwner(String email, String price){
        Owner owner = new Owner(binding.imgOwner.getText().toString().trim(),
                binding.nameOwner.getText().toString().trim(),
                binding.addressOwner.getText().toString().trim(),
                price,
                binding.emailOwner.getText().toString().trim());
        databaseReference.child("Owner")
                .child("books")
                .child(binding.tvDetailBookNameShare.getText().toString().trim())
                .child("owner").child(User.formatEmail(email)).setValue(owner);

    }
    private void setUpOwner(String email) {
        databaseReference.child("users")
                .child(User.formatEmail(email)).child("address").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    //Toast.makeText(DetailBookShareActivity.this,,Toast.LENGTH_LONG).show();
                    binding.addressOwner.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });
        databaseReference.child("users")
                .child(User.formatEmail(email)).child("image").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    binding.imgOwner.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });



        databaseReference.child("users")
                .child(User.formatEmail(email)).child("fullname").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    binding.nameOwner.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });
        databaseReference.child("users")
                .child(User.formatEmail(email)).child("emailId").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    binding.emailOwner.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });

    }

    private void addToBookcase(String imgUrl, String author, String nameBook, String price) {
        BookCase bookCase = new BookCase(imgUrl, nameBook, author, price);
        FirebaseDatabase.getInstance().getReference()
                .child("users").child(User.formatEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail()))
                .child("bookcase")
                .child(nameBook).setValue(bookCase);
    }

    private void LoadDataFromFB(String bookname) {
        databaseReference.child("bookDetail").child(bookname).child("content").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    binding.tvContentShare.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });
        databaseReference.child("bookDetail").child(bookname).child("bookCover").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    binding.tvBookCoverShare.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });
        databaseReference.child("bookDetail").child(bookname).child("company").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    binding.tvCompanyShare.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });
        databaseReference.child("bookDetail").child(bookname).child("companyPublic").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    binding.tvCompanyPublicShare.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });
        databaseReference.child("bookDetail").child(bookname).child("datePublic").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    binding.tvPublicDateShare.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });
        databaseReference.child("bookDetail").child(bookname).child("pageNum").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    binding.tvPageNumShare.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });
        databaseReference.child("bookDetail").child(bookname).child("size").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    binding.tvSizeShare.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });
    }
}