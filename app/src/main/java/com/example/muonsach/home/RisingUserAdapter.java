package com.example.muonsach.home;

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
import com.example.muonsach.obj.RisingUser;

import java.util.List;

public class RisingUserAdapter extends RecyclerView.Adapter<RisingUserAdapter.RisingUserViewHolder> {

    Context context;
    List<RisingUser> risingUserList;
    ItemClickListener listener;

    public RisingUserAdapter(Context context, List<RisingUser> risingUserList, ItemClickListener listener) {
        this.context = context;
        this.risingUserList = risingUserList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RisingUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_rising_user,parent,false);
        return new RisingUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RisingUserViewHolder holder, int position) {
        if (risingUserList == null){
            return;
        }
        RisingUser risingUser = risingUserList.get(position);

        Glide.with(holder.imgRisingUser.getContext()).load(risingUser.getImgRisingUser()).into(holder.imgRisingUser);
        holder.emailRisingUser.setText(risingUser.getEmailRisingUser());
        holder.nameRisingUser.setText(risingUser.getNameRisingUser());
    }

    @Override
    public int getItemCount() {
        if (risingUserList != null){
            return risingUserList.size();
        }
        return 0;
    }

    class RisingUserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imgRisingUser;
        private TextView nameRisingUser, emailRisingUser;
        public RisingUserViewHolder(@NonNull View itemView) {
            super(itemView);
            imgRisingUser = itemView.findViewById(R.id.imgRisingUser);
            nameRisingUser = itemView.findViewById(R.id.nameRisingUser);
            emailRisingUser = itemView.findViewById(R.id.emailRisingUser);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v,getAdapterPosition());
        }
    }
}
