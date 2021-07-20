package com.example.muonsach.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.muonsach.R;
import com.example.muonsach.databinding.ActivityChatBinding;
import com.example.muonsach.login.User;
import com.example.muonsach.obj.Notification;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class ChatActivity extends AppCompatActivity{
    ActivityChatBinding binding;
    FirebaseUser firebaseUser;
    MessageAdapter messageAdapter;
    List<Chat> chatList;
    String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_chat);


        binding.rcvChat.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        binding.rcvChat.setLayoutManager(linearLayoutManager);

        binding.chatUserName.setText(getIntent().getStringExtra("fullname"));
        Glide.with(binding.chatUserImg.getContext()).load(getIntent().getStringExtra("imageurl")).into(binding.chatUserImg);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseDatabase.getInstance().getReference().child("users").child(User.formatEmail(firebaseUser.getEmail())).child("image").get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        image = task.getResult().getValue().toString();
                    }
                });
        chatList = new ArrayList<>();
        binding.chatBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textSend = binding.chatTextSend.getText().toString().trim();
                if(!textSend.equals("")){
                    sendMessage(User.formatEmail(firebaseUser.getEmail()), User.formatEmail(getIntent().getStringExtra("emailId")),textSend,
                            image);

                }
                else {
                    Toast.makeText(ChatActivity.this,"Empty Text!",Toast.LENGTH_LONG).show();
                }
                binding.chatTextSend.setText("");
            }
        });
        readMessage();

    }
    private void sendMessage(String sender, String receiver, String message, String imgsender){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver",receiver);
        hashMap.put("message",message);
        hashMap.put("imgsender",imgsender);
        reference.child("users").child(sender).child("Chats").push().setValue(hashMap);
        reference.child("users").child(receiver).child("Chats").push().setValue(hashMap);

        Notification notification = new Notification(imgsender,sender,"Đã gửi tin nhắn cho bạn",
                Calendar.getInstance().getTime().toString());
        reference.child("users")
                .child(receiver).child("notification").push().setValue(notification);
    }
    private void readMessage(){

        getData(User.formatEmail(firebaseUser.getEmail()));

    }
    private void getData(String sender){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(sender).child("Chats");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatList.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Chat chat = dataSnapshot.getValue(Chat.class);
                    if (chat.getSender().equals(User.formatEmail(firebaseUser.getEmail()))
                            && chat.getReceiver().equals(User.formatEmail(getIntent().getStringExtra("emailId")))
                            || chat.getSender().equals(User.formatEmail(getIntent().getStringExtra("emailId"))) &&
                            chat.getReceiver().equals(User.formatEmail(firebaseUser.getEmail()))){
                        chatList.add(chat);
                    }
                    messageAdapter = new MessageAdapter(ChatActivity.this,chatList);
                    LinearLayoutManager messageLinearLayoutManager = new LinearLayoutManager(ChatActivity.this,RecyclerView.VERTICAL,false);
                    binding.rcvChat.setLayoutManager(messageLinearLayoutManager);
                    binding.rcvChat.setFocusable(false);
                    binding.rcvChat.setNestedScrollingEnabled(false);
                    binding.rcvChat.setAdapter(messageAdapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}