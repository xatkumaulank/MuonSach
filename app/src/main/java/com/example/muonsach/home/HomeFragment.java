package com.example.muonsach.home;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.muonsach.R;
import com.example.muonsach.cart.CartActivity;
import com.example.muonsach.clickRcv.ItemClickListener;
import com.example.muonsach.data.Data;
import com.example.muonsach.databinding.FragmentHomeBinding;
import com.example.muonsach.login.User;
import com.example.muonsach.obj.Book;
import com.example.muonsach.obj.Category;
import com.example.muonsach.obj.Photo;
import com.example.muonsach.obj.PhotoAdapter;
import com.example.muonsach.obj.RisingUser;
import com.example.muonsach.search.DetailBookShareActivity;
import com.example.muonsach.search.SearchFragment;
import com.example.muonsach.user.UpdateInfoUserActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    BookAdapter bookAdapter;
    CategoryAdapter categoryAdapter;
    RisingUserAdapter risingUserAdapter;
    ItemClickListener listener, listenerCategory, listenerRisingUser;
    private PhotoAdapter photoAdapter;
    private List<Photo> photoList;
    private Timer timer;
    private ArrayList<Book> bookArrayList;
    private ArrayList<Category> categoryArrayList;
    private ArrayList<RisingUser> risingUserArrayList;
    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false);
        photoList = Data.getListPhotoHome();
        photoAdapter = new PhotoAdapter(this.getContext(),photoList);
        binding.viewpagerHome.setAdapter(photoAdapter);
        binding.circleindicatorHome.setViewPager(binding.viewpagerHome);
        photoAdapter.registerDataSetObserver(binding.circleindicatorHome.getDataSetObserver());


        autoRunPhoto();

        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        openDialog(Gravity.CENTER);

        FirebaseDatabase.getInstance().getReference().child("users").child(User.formatEmail(email))
                .child("image").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Glide.with(binding.userImg.getContext()).load(task.getResult().getValue()).into(binding.userImg);
                }
            }
        });
        binding.cartActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeFragment.this.getContext(), CartActivity.class);

                intent.putExtra("email",email);
                startActivity(intent);
            }
        });
        binding.tvBookMoreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeFragment.this.getContext(),BookBorrowActivity.class);
                startActivity(intent);
            }
        });
        bookArrayList = new ArrayList<>();
        categoryArrayList = new ArrayList<>();
        risingUserArrayList = new ArrayList<>();
        


        return binding.getRoot();
    }


    @Override
    public void onStart() {
        super.onStart();
        runningBookAdapter();
        runningCategoryAdapter();
        runningRisingUserAdapter();
    }

    private void runningCategoryAdapter(){
        setOnClickListenerCategory();
        categoryAdapter = new CategoryAdapter(this.getContext(),categoryArrayList,listenerCategory);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(),2,RecyclerView.HORIZONTAL,false);
        binding.rcvCategory.setLayoutManager(gridLayoutManager);
        binding.rcvCategory.setFocusable(false);
        binding.rcvCategory.setNestedScrollingEnabled(false);
        binding.rcvCategory.setAdapter(categoryAdapter);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Category");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Category category = dataSnapshot.getValue(Category.class);
                    categoryArrayList.add(category);
                }
                categoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void runningRisingUserAdapter(){
        setOnClickListenerRisingUser();
        risingUserAdapter = new RisingUserAdapter(this.getContext(),risingUserArrayList,listenerRisingUser);
        LinearLayoutManager risingLinearLayoutManager = new LinearLayoutManager(this.getContext(),RecyclerView.HORIZONTAL,false);
        binding.rcvRisingUser.setLayoutManager(risingLinearLayoutManager);
        binding.rcvRisingUser.setFocusable(false);
        binding.rcvRisingUser.setNestedScrollingEnabled(false);
        binding.rcvRisingUser.setAdapter(risingUserAdapter);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("RisingUser");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    RisingUser risingUser = dataSnapshot.getValue(RisingUser.class);
                    risingUserArrayList.add(risingUser);
                }
                risingUserAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void runningBookAdapter(){
        setOnclickListener();
        bookAdapter = new BookAdapter(bookArrayList,this.getContext(),listener);
        LinearLayoutManager bookLinearLayoutManager = new LinearLayoutManager(this.getContext(),RecyclerView.HORIZONTAL,false);
        binding.rcvBook.setLayoutManager(bookLinearLayoutManager);
        binding.rcvBook.setFocusable(false);
        binding.rcvBook.setNestedScrollingEnabled(false);
        binding.rcvBook.setAdapter(bookAdapter);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("book");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Book book = dataSnapshot.getValue(Book.class);
                    bookArrayList.add(book);
                }
                bookAdapter.notifyDataSetChanged();
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
                Intent intent = new Intent(HomeFragment.this.getContext(), DetailBookShareActivity.class);
                intent.putExtra("nameBook",bookArrayList.get(position).getBookname());
                intent.putExtra("imgBook",bookArrayList.get(position).getBookimg());
                intent.putExtra("authorBook",bookArrayList.get(position).getBookauthor());
                intent.putExtra("numstar",bookArrayList.get(position).getRating());
                startActivity(intent);
            }
        };
    }
    private void setOnClickListenerCategory(){
        listenerCategory = new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(HomeFragment.this.getContext(),DetailCategoryActivity.class);

                intent.putExtra("imgCategory",categoryArrayList.get(position).getImgCategory());
                intent.putExtra("nameCategory",categoryArrayList.get(position).getNameCategory());
                startActivity(intent);
            }
        };
    }
    private void setOnClickListenerRisingUser(){
        listenerRisingUser = new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(HomeFragment.this.getContext(),risingUserArrayList.get(position).getNameRisingUser(),Toast.LENGTH_LONG).show();
            }
        };
    }
    private void autoRunPhoto(){
        if(photoList == null || photoList.isEmpty()){
            return;
        }
        if(timer == null){
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currentItem = binding.viewpagerHome.getCurrentItem();
                        int totalItem = photoList.size() - 1;
                        if (currentItem < totalItem){
                            currentItem++;
                            binding.viewpagerHome.setCurrentItem(currentItem);
                        }else {
                            binding.viewpagerHome.setCurrentItem(0);
                        }
                    }
                });
            }
        },500,3000);
    }
    private void openDialog(int bottom) {
        final Dialog dialog = new Dialog(this.getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog);
        Window window = dialog.getWindow();

        if (window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = bottom;
        window.setAttributes(windowAttributes);



        Button update = dialog.findViewById(R.id.btn_update_info);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeFragment.this.getContext(), UpdateInfoUserActivity.class);

                startActivity(intent);
            }
        });
        dialog.show();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null){
            timer.cancel();
            timer = null;
        }
    }

}