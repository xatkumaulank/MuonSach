package com.example.muonsach.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;

import com.example.muonsach.R;
import com.example.muonsach.data.Data;
import com.example.muonsach.databinding.ActivityMessageBinding;
import com.example.muonsach.obj.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {
    ActivityMessageBinding binding;
    MessageAdapter messageAdapter;
    List<Message> messageList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_message);

        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter();
        LinearLayoutManager messageLinearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        binding.rcvMessage.setLayoutManager(messageLinearLayoutManager);
        messageAdapter.setData(messageList);
        binding.rcvMessage.setAdapter(messageAdapter);


        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
        binding.edtMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkKeyBoard();
            }
        });
    }

    private void sendMessage() {
        String message = binding.edtMessage.getText().toString().trim();
        if (TextUtils.isEmpty(message)){
            return;
        }
        messageList.add(new Message(message));
        messageAdapter.notifyDataSetChanged();

        binding.rcvMessage.scrollToPosition(messageList.size() - 1);
        binding.edtMessage.setText("");
    }
    private void checkKeyBoard(){
        binding.activityRoot.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();

                binding.activityRoot.getWindowVisibleDisplayFrame(r);
                int heightDiff = binding.activityRoot.getRootView().getHeight() - r.height();
                if (heightDiff > 0.25 * binding.activityRoot.getRootView().getHeight()){
                    if (messageList.size() > 0){
                        binding.rcvMessage.scrollToPosition(messageList.size() - 1);
                        binding.activityRoot.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            }
        });
    }
}