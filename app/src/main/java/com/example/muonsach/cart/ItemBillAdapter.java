package com.example.muonsach.cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.muonsach.R;
import com.example.muonsach.obj.ItemCart;

import java.util.List;

public class ItemBillAdapter extends RecyclerView.Adapter<ItemBillAdapter.ItemBillViewHolder> {

    Context context;
    List<ItemCart> itemCartList;

    public ItemBillAdapter(Context context, List<ItemCart> itemCartList) {
        this.context = context;
        this.itemCartList = itemCartList;
    }

    @NonNull
    @Override
    public ItemBillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_bill,parent,false);
        return new ItemBillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemBillViewHolder holder, int position) {
        if (itemCartList == null){
            return;
        }
        ItemCart itemCart = itemCartList.get(position);

        Glide.with(holder.imgBookCart.getContext()).load(itemCart.getImgBookCart()).into(holder.imgBookCart);
        Glide.with(holder.imgUserBorrowCart.getContext()).load(itemCart.getImgUserBorrowCart()).into(holder.imgUserBorrowCart);

        holder.addressUserBorrowCart.setText(itemCart.getAddressUserBorrowCart());
        holder.nameBookCart.setText(itemCart.getNameBookCart());
        holder.priceBookCart.setText(itemCart.getPriceBookCart());
        holder.nameUserBorrowCart.setText(itemCart.getNameUserBorrowCart());
    }

    @Override
    public int getItemCount() {
        if (itemCartList != null){
            return itemCartList.size();
        }
        return 0;
    }

    class ItemBillViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgBookCart, imgUserBorrowCart;
        private TextView nameBookCart, priceBookCart, nameUserBorrowCart, addressUserBorrowCart;
        public ItemBillViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBookCart = itemView.findViewById(R.id.imgBookBill);
            imgUserBorrowCart = itemView.findViewById(R.id.imgUserBorrowBill);
            nameBookCart = itemView.findViewById(R.id.nameBookBill);
            priceBookCart = itemView.findViewById(R.id.priceBookBill);
            nameUserBorrowCart = itemView.findViewById(R.id.nameUserBorrowBill);
            addressUserBorrowCart = itemView.findViewById(R.id.addressUserBorrowBill);
        }

    }
}
