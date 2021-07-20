package com.example.muonsach.search;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.muonsach.R;
import com.example.muonsach.clickRcv.ItemClickListener;
import com.example.muonsach.databinding.FragmentSearchBinding;
import com.example.muonsach.home.BookAdapter;
import com.example.muonsach.login.User;
import com.example.muonsach.map.MapActivity;
import com.example.muonsach.obj.Book;
import com.example.muonsach.obj.BookShare;
import com.example.muonsach.obj.UserNear;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class SearchFragment extends Fragment {
    FragmentSearchBinding binding;
    UserNearAdapter userNearAdapter;
    BookAdapter bookShareAdapter;
    ArrayList<UserNear> userNearArrayList;
    ArrayList<Book> bookShareArrayList;
    ItemClickListener listener;
    ItemClickListener listener2;
    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_search,container,false);


        userNearArrayList = new ArrayList<>();

        setOnClickListener();
        userNearAdapter = new UserNearAdapter(userNearArrayList,this.getContext(),listener);
        LinearLayoutManager userNearLinearLayoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.HORIZONTAL,false);
        binding.rcvNearUser.setLayoutManager(userNearLinearLayoutManager);
        binding.rcvNearUser.setAdapter(userNearAdapter);
        DatabaseReference reference =FirebaseDatabase.getInstance().getReference().child("using");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    UserNear userNear = dataSnapshot.getValue(UserNear.class);
                    if (!userNear.getEmail().equals(User.formatEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail()))){
                        userNearArrayList.add(userNear);
                    }
                }
                userNearAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        bookShareArrayList = new ArrayList<>();

        listener2 = new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(SearchFragment.this.getContext(),DetailBookShareActivity.class);
                intent.putExtra("imgBook",bookShareArrayList.get(position).getBookimg());
                intent.putExtra("nameBook",bookShareArrayList.get(position).getBookname());
                intent.putExtra("numstar",bookShareArrayList.get(position).getRating());
                intent.putExtra("authorBook",bookShareArrayList.get(position).getBookauthor());
                startActivity(intent);
            }
        };
        bookShareAdapter = new BookAdapter(bookShareArrayList,this.getContext(),listener2);
        GridLayoutManager bookShareGridLayoutManager = new GridLayoutManager(this.getContext(),3);
        binding.rcvBookshare.setLayoutManager(bookShareGridLayoutManager);
        binding.rcvBookshare.setAdapter(bookShareAdapter);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Category")
                .child("Sách học Tiếng Anh").child("books");
        DatabaseReference referenceKH = FirebaseDatabase.getInstance().getReference().child("Category")
                .child("Sách khoa học").child("books");

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Book book = snapshot.getValue(Book.class);
                bookShareArrayList.add(book);

                bookShareAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

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
        referenceKH.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Book book = snapshot.getValue(Book.class);
                bookShareArrayList.add(book);

                bookShareAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

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
        binding.tvMoreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchFragment.this.getContext(), MapActivity.class);
                startActivity(intent);
            }
        });
        binding.searchBook.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                bookShareAdapter.getFilter().filter(query);
                userNearAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                bookShareAdapter.getFilter().filter(newText);
                userNearAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return binding.getRoot();
    }
    private void setOnClickListener(){
        listener = new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(SearchFragment.this.getContext(),DetailUserActivity.class);
                intent.putExtra("email",userNearArrayList.get(position).getEmail());

                startActivity(intent);
            }
        };
    }
}