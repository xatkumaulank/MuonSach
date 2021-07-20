package com.example.muonsach.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.muonsach.R;
import com.example.muonsach.clickRcv.ItemClickListener;
import com.example.muonsach.obj.BookShare;
import com.example.muonsach.obj.UserNear;

import java.util.ArrayList;
import java.util.List;

public class UserNearAdapter extends RecyclerView.Adapter<UserNearAdapter.UserNearViewHolder> implements Filterable {
    List<UserNear> userNearList;
    Context context;
    ItemClickListener listener;
    List<UserNear> userNearListOld;
    public UserNearAdapter(List<UserNear> userNearList, Context context, ItemClickListener listener) {
        this.userNearList = userNearList;
        this.userNearListOld = userNearList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserNearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_user_near,parent,false);
        return new UserNearViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserNearViewHolder holder, int position) {
        UserNear userNear = userNearList.get(position);
        if (userNear == null){
            return;
        }
        Glide.with(holder.itemView).load(userNear.getImage()).into(holder.imgNearUserAvt);
        holder.tvNearUserName.setText(userNear.getFullname());
        holder.tvNearUserEmail.setText(userNear.getEmail());
    }

    @Override
    public int getItemCount() {
        if (userNearList != null){
            return userNearList.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()){
                    userNearList = userNearListOld;
                }else {
                    List<UserNear> list = new ArrayList<>();
                    for (UserNear userNear: userNearListOld) {
                        if (userNear.getFullname().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(userNear);
                        }
                    }
                    userNearList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = userNearList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                userNearList = (List<UserNear>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    class UserNearViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgNearUserAvt;
        TextView tvNearUserName,tvNearUserEmail;
        public UserNearViewHolder(@NonNull View itemView) {
            super(itemView);
            imgNearUserAvt = itemView.findViewById(R.id.img_usernear_avt);
            tvNearUserName = itemView.findViewById(R.id.tv_usernear_name);
            tvNearUserEmail = itemView.findViewById(R.id.tv_usernear_email);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v,getAdapterPosition());
        }
    }
}
