package com.example.muonsach.user;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.muonsach.R;
import com.example.muonsach.clickRcv.ItemClickListener;
import com.example.muonsach.login.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class BookcaseAdapter extends RecyclerView.Adapter<BookcaseAdapter.BookcaseViewHolder> {
    List<BookCase> bookCaseList;
    Context context;


    public BookcaseAdapter(List<BookCase> bookCaseList, Context context) {
        this.bookCaseList = bookCaseList;
        this.context = context;

    }

    @NonNull
    @Override
    public BookcaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_bookcase,parent,false);
        return new BookcaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookcaseViewHolder holder, int position) {
        BookCase bookCase = bookCaseList.get(position);
        if (bookCase == null){
            return;
        }
        Glide.with(holder.itemView).load(bookCase.getImgBookcaseBook()).into(holder.imgvBookcaseBook);
        holder.tvBookcasePrice.setText(bookCase.getTvBookcasePrice());
        holder.tvBookcaseBookname.setText(bookCase.getTvBookcaseBookname());
        holder.tvBookcaseAuthorname.setText(bookCase.getTvBookcaseAuthorname());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.delete.getContext());
                builder.setTitle("Delete this?");
                builder.setMessage("Delete ...");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("users").child(User.formatEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail()))
                                .child("bookcase").child(bookCaseList.get(position).getTvBookcaseBookname())
                                .removeValue()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            bookCaseList.remove(bookCaseList.get(position));
                                            notifyDataSetChanged();
                                            Toast.makeText(context,"Deleted Item",Toast.LENGTH_LONG).show();
                                        }
                                        else {
                                            Toast.makeText(context,task.getException().toString(),Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                    }
                });
                builder.setNegativeButton("No",null);
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (bookCaseList != null){
            return bookCaseList.size();
        }
        return 0;
    }

    class BookcaseViewHolder extends RecyclerView.ViewHolder {
        ImageView imgvBookcaseBook;
        TextView tvBookcaseBookname, tvBookcaseAuthorname,tvBookcasePrice;
        LottieAnimationView delete;
        public BookcaseViewHolder(@NonNull View itemView) {
            super(itemView);
            imgvBookcaseBook = itemView.findViewById(R.id.imgv_bookcase_book);
            tvBookcaseBookname = itemView.findViewById(R.id.tv_bookcase_bookname);
            tvBookcaseAuthorname = itemView.findViewById(R.id.tv_bookcase_authorname);
            tvBookcasePrice = itemView.findViewById(R.id.tv_bookcase_price);
            delete = itemView.findViewById(R.id.delete_bookcase);
        }
    }
}
