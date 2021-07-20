package com.example.muonsach.cart;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.muonsach.R;
import com.example.muonsach.clickRcv.ItemClickListener;
import com.example.muonsach.login.User;
import com.example.muonsach.obj.ItemCart;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class ItemCartAdapter extends RecyclerView.Adapter<ItemCartAdapter.ItemCartViewHolder> {

    Context context;
    List<ItemCart> itemCartList;
    int totalCart = 0;
    public boolean checkAll = true;
    public boolean deleteAll = false;
    public int isCheckAll = 0;
    public static List<String> itemBuy = new ArrayList<>();
    public static List<String> itemUserName = new ArrayList<>();

    public ItemCartAdapter(Context context, List<ItemCart> itemCartList) {
        this.context = context;
        this.itemCartList = itemCartList;
    }

    public static List<String> getItemUserName() {
        return itemUserName;
    }

    public static List<String> getItemBuy() {
        return itemBuy;
    }

    public void setCheckAll(boolean checkAll) {
        this.checkAll = checkAll;
    }

    public void setDeleteAll(boolean deleteAll) {
        this.deleteAll = deleteAll;
    }

    @NonNull
    @Override
    public ItemCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_cart, parent, false);
        return new ItemCartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemCartViewHolder holder, int position) {
        if (itemCartList == null) {
            return;
        }
        ItemCart itemCart = itemCartList.get(position);

        Glide.with(holder.imgBookCart.getContext()).load(itemCart.getImgBookCart()).into(holder.imgBookCart);
        Glide.with(holder.imgUserBorrowCart.getContext()).load(itemCart.getImgUserBorrowCart()).into(holder.imgUserBorrowCart);

        holder.addressUserBorrowCart.setText(itemCart.getAddressUserBorrowCart());
        holder.nameBookCart.setText(itemCart.getNameBookCart());
        holder.priceBookCart.setText(itemCart.getPriceBookCart());
        holder.nameUserBorrowCart.setText(itemCart.getNameUserBorrowCart());
        holder.emailUserBorrowCart.setText(itemCart.getEmailUserBorrowCart());
        if (checkAll) {
            holder.checkBox.setChecked(false);
        } else {
            holder.checkBox.setChecked(true);
        }
        if (deleteAll) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users")
                    .child(User.formatEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail()))
                    .child("Cart");
            AlertDialog.Builder builder = new AlertDialog.Builder(holder.delete.getContext());
            builder.setTitle("Delete all of this?");
            builder.setMessage("Delete ...");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    reference
                            .removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        itemCartList.clear();
                                        notifyDataSetChanged();
                                        Toast.makeText(context, "Deleted All Item", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(context, task.getException().toString(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            });
            builder.setNegativeButton("No", null);
            builder.show();
            this.deleteAll = false;
        }
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    totalCart += Integer.parseInt(itemCartList.get(position).getPriceBookCart());
                    isCheckAll++;
                    itemBuy.add(itemCartList.get(position).getNameBookCart() + "-"
                            + itemCartList.get(position).getNameUserBorrowCart());
                    itemUserName.add(itemCartList.get(position).getEmailUserBorrowCart() + "-"
                            + itemCartList.get(position).getNameBookCart());
                } else {
                    isCheckAll--;
                    totalCart -= Integer.parseInt(itemCartList.get(position).getPriceBookCart());
                    itemBuy.remove(itemCartList.get(position).getNameBookCart() + "-"
                            + itemCartList.get(position).getNameUserBorrowCart());
                    itemUserName.remove(itemCartList.get(position).getEmailUserBorrowCart() + "-"
                            + itemCartList.get(position).getNameBookCart());
                }
                FirebaseDatabase.getInstance().getReference().child("users").
                        child(User.formatEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail()))
                        .child("totalCart").setValue(totalCart);

            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users")
                        .child(User.formatEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail()))
                        .child("Cart");
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.delete.getContext());
                builder.setTitle("Delete this?");
                builder.setMessage("Delete ...");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reference.child(itemCartList.get(position).getNameBookCart() + "-"
                                + itemCartList.get(position).getNameUserBorrowCart())
                                .removeValue()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            itemCartList.remove(itemCartList.get(position));
                                            notifyDataSetChanged();
                                            Toast.makeText(context, "Deleted Item", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(context, task.getException().toString(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                    }
                });
                builder.setNegativeButton("No", null);
                builder.show();
            }
        });
    }


    @Override
    public int getItemCount() {
        if (itemCartList != null) {
            return itemCartList.size();
        }
        return 0;
    }

    class ItemCartViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgBookCart, imgUserBorrowCart;
        private TextView nameBookCart, priceBookCart, nameUserBorrowCart, addressUserBorrowCart, emailUserBorrowCart;
        private CheckBox checkBox;
        private LottieAnimationView delete;

        public ItemCartViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBookCart = itemView.findViewById(R.id.imgBookCart);
            imgUserBorrowCart = itemView.findViewById(R.id.imgUserBorrowCart);
            nameBookCart = itemView.findViewById(R.id.nameBookCart);
            priceBookCart = itemView.findViewById(R.id.priceBookCart);
            nameUserBorrowCart = itemView.findViewById(R.id.nameUserBorrowCart);
            addressUserBorrowCart = itemView.findViewById(R.id.addressUserBorrowCart);
            checkBox = itemView.findViewById(R.id.checkboxCart);
            delete = itemView.findViewById(R.id.delete_item_cart);
            emailUserBorrowCart = itemView.findViewById(R.id.emailUserBorrowCart);
        }

    }
}
