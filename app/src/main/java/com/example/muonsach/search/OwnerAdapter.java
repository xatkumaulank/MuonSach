package com.example.muonsach.search;

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
import com.example.muonsach.obj.Owner;

import java.util.List;

public class OwnerAdapter extends RecyclerView.Adapter<OwnerAdapter.OwnerViewHolder> {

    Context context;
    List<Owner> ownerList;

    public OwnerAdapter(Context context, List<Owner> ownerList) {
        this.context = context;
        this.ownerList = ownerList;
    }

    @NonNull
    @Override
    public OwnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_owner,parent,false);
        return new OwnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OwnerViewHolder holder, int position) {
        if (ownerList == null){
            return;
        }
        Owner owner = ownerList.get(position);
        Glide.with(holder.imgOwner.getContext()).load(owner.getImgOwner()).into(holder.imgOwner);
        holder.phoneOwner.setText(owner.getPhoneOwner());
        holder.addressOwner.setText(owner.getAddressOwner());
        holder.nameOwner.setText(owner.getNameOwner());
        holder.emailOwner.setText(owner.getEmailOwner());
    }

    @Override
    public int getItemCount() {
        if (ownerList != null){
            return ownerList.size();
        }
        return 0;
    }

    class OwnerViewHolder extends RecyclerView.ViewHolder{

        ImageView imgOwner;
        TextView nameOwner, addressOwner, phoneOwner, emailOwner;

        public OwnerViewHolder(@NonNull View itemView) {
            super(itemView);
            imgOwner = itemView.findViewById(R.id.imgOwner);
            nameOwner = itemView.findViewById(R.id.nameOwner);
            addressOwner = itemView.findViewById(R.id.addressOwner);
            phoneOwner = itemView.findViewById(R.id.phoneOwner);
            emailOwner = itemView.findViewById(R.id.emailOwner);
        }
    }
}
