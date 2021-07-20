package com.example.muonsach.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.muonsach.R;
import com.example.muonsach.login.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    private Context mContext;
    private List<Chat> mChatList;


    FirebaseUser fuser;
    public MessageAdapter(Context mContext, List<Chat> mChats) {
        this.mContext = mContext;
        this.mChatList = mChats;

    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == MSG_TYPE_RIGHT){
            view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);
        }
        else {
            view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false);
        }
        return new MessageViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Chat chat = mChatList.get(position);
        holder.show_message.setText(chat.getMessage());
        holder.sender.setText(chat.getSender());
        holder.receiver.setText(chat.getReceiver());
        Glide.with(holder.imgsender.getContext()).load(chat.getImgsender()).into(holder.imgsender);

    }

    @Override
    public int getItemCount() {
        if (mChatList != null){
            return mChatList.size();
        }
        return 0;
    }

    class MessageViewHolder extends RecyclerView.ViewHolder{
        private TextView show_message, sender, receiver;
        private ImageView imgsender;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            show_message = itemView.findViewById(R.id.show_message);
            imgsender = itemView.findViewById(R.id.imgsender);
            sender = itemView.findViewById(R.id.sender);
            receiver = itemView.findViewById(R.id.receiver);
        }
    }

    @Override
    public int getItemViewType(int position) {
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        if (mChatList.get(position).getSender().equals(User.formatEmail(fuser.getEmail()))){
            return MSG_TYPE_RIGHT;
        }else {
            return MSG_TYPE_LEFT;
        }
    }
}
