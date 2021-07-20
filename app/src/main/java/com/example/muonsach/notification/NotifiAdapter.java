package com.example.muonsach.notification;

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
import com.example.muonsach.clickRcv.ItemClickListener;
import com.example.muonsach.obj.Notification;

import java.util.List;

public class NotifiAdapter extends RecyclerView.Adapter<NotifiAdapter.NotifiViewHolder> {
    List<Notification> notificationList;
    Context context;
    public NotifiAdapter(List<Notification> notificationList, Context context) {
        this.notificationList = notificationList;
        this.context = context;
    }

    @NonNull
    @Override
    public NotifiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_notifi,parent,false);
        return new NotifiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotifiViewHolder holder, int position) {
        Notification notification = notificationList.get(position);
        if (notification == null){
            return;
        }
        Glide.with(holder.itemView).load(notification.getImgNotifiAvt()).into(holder.avatar);
        holder.name.setText(notification.getTvNotifiName());
        holder.detail.setText(notification.getTvNotifiDetail());
        holder.time.setText(notification.getTvNotifiTime());
    }

    @Override
    public int getItemCount() {
        if (notificationList != null){
            return notificationList.size();
        }
        return 0;
    }

    class NotifiViewHolder extends RecyclerView.ViewHolder{
        ImageView avatar;
        TextView name,detail,time;
        public NotifiViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.imgv_notifi_avt);
            name = itemView.findViewById(R.id.tv_notifi_name);
            detail = itemView.findViewById(R.id.tv_notifi_detail);
            time = itemView.findViewById(R.id.tv_notifi_time);
        }


    }
}
