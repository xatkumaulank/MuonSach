package com.example.muonsach.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.muonsach.R;
import com.example.muonsach.clickRcv.ItemClickListener;
import com.example.muonsach.login.User;
import com.example.muonsach.obj.ItemCart;
import com.example.muonsach.user.BookCase;
import com.example.muonsach.user.BookcaseAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class BookcaseDetalAdapter extends RecyclerView.Adapter<BookcaseDetalAdapter.BookcaseViewHolder> {

    Context context;
    ArrayList<BookCase> bookCaseList;
    ItemClickListener listener;
    public BookcaseDetalAdapter(Context context, ArrayList<BookCase> bookCaseList, ItemClickListener listener) {
        this.context = context;
        this.bookCaseList = bookCaseList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookcaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_bookcase_detail,parent,false);
        return new BookcaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookcaseViewHolder holder, int position) {
        BookCase bookCase = bookCaseList.get(position);
        if (bookCase == null){
            return;
        }
        Glide.with(holder.imgvBookcaseBook.getContext()).load(bookCase.getImgBookcaseBook()).into(holder.imgvBookcaseBook);
        holder.tvBookcasePrice.setText(bookCase.getTvBookcasePrice());
        holder.tvBookcaseBookname.setText(bookCase.getTvBookcaseBookname());
        holder.tvBookcaseAuthorname.setText(bookCase.getTvBookcaseAuthorname());


    }

    @Override
    public int getItemCount() {
        if (bookCaseList != null){
            return bookCaseList.size();
        }
        return 0;
    }

    class BookcaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgvBookcaseBook;
        TextView tvBookcaseBookname, tvBookcaseAuthorname,tvBookcasePrice;
        public BookcaseViewHolder(@NonNull View itemView) {
            super(itemView);
            imgvBookcaseBook = itemView.findViewById(R.id.imgv_bookcase_book_detail);
            tvBookcaseBookname = itemView.findViewById(R.id.tv_bookcase_bookname_detail);
            tvBookcaseAuthorname = itemView.findViewById(R.id.tv_bookcase_authorname_detail);
            tvBookcasePrice = itemView.findViewById(R.id.tv_bookcase_price_detail);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v,getAdapterPosition());
        }
    }
}
