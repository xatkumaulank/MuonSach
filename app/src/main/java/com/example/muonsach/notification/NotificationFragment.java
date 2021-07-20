package com.example.muonsach.notification;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.muonsach.R;
import com.example.muonsach.clickRcv.ItemClickListener;
import com.example.muonsach.data.Data;
import com.example.muonsach.databinding.FragmentNotificationBinding;
import com.example.muonsach.home.BookAdapter;
import com.example.muonsach.home.HomeActivity;
import com.example.muonsach.login.User;
import com.example.muonsach.obj.Book;
import com.example.muonsach.obj.Notification;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class NotificationFragment extends Fragment {
    FragmentNotificationBinding binding;
    NotifiAdapter notifiAdapter;
    ArrayList<Notification> notificationArrayList;
    public NotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_notification,container,false);

        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        notificationArrayList = new ArrayList<>();
        notifiAdapter = new NotifiAdapter(notificationArrayList,this.getContext());
        LinearLayoutManager notifiLinearLayoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL,false);
        binding.rcvNotifi.setLayoutManager(notifiLinearLayoutManager);
        binding.rcvNotifi.setAdapter(notifiAdapter);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users")
                .child(User.formatEmail(email)).child("notification");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Notification notification = dataSnapshot.getValue(Notification.class);
                    notificationArrayList.add(notification);
                }
                notifiAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return binding.getRoot();
    }

}