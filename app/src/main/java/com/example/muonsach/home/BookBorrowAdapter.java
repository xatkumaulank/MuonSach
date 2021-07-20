package com.example.muonsach.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.muonsach.R;
import com.example.muonsach.clickRcv.ItemClickListener;
import com.example.muonsach.obj.BookBorrow;

import java.util.List;

public class BookBorrowAdapter extends RecyclerView.Adapter<BookBorrowAdapter.BookBorrowViewHolder> {
    List<BookBorrow> bookBorrowList;
    Context context;
    ItemClickListener listener;
    public BookBorrowAdapter(List<BookBorrow> bookBorrowList, Context context, ItemClickListener listener) {
        this.bookBorrowList = bookBorrowList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookBorrowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.iten_borrow_book,parent,false);
        return new BookBorrowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookBorrowViewHolder holder, int position) {
        BookBorrow bookBorrow = bookBorrowList.get(position);
        if (bookBorrow == null){
            return;
        }
        Glide.with(holder.book.getContext()).load(bookBorrow.getImgBookBorrow()).into(holder.book);
        holder.ratingBar.setRating(bookBorrow.getNumStar());
        holder.amountNote.setText(bookBorrow.getAmountNote());
        holder.amountReader.setText(bookBorrow.getAmountReader());
        holder.bookAuthor.setText(bookBorrow.getTvBookBorrowAuthorName());
        holder.bookName.setText(bookBorrow.getTvBookBorrowName());
    }

    @Override
    public int getItemCount() {
        if (bookBorrowList != null){
            return bookBorrowList.size();
        }
        return 0;
    }

    class BookBorrowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView book;
        TextView bookName,bookAuthor,amountReader,amountNote;
        RatingBar ratingBar;
        public BookBorrowViewHolder(@NonNull View itemView) {
            super(itemView);
            book = itemView.findViewById(R.id.img_borrow_book);
            bookName = itemView.findViewById(R.id.tv_borrow_book_name);
            bookAuthor = itemView.findViewById(R.id.tv_borrow_book_author_name);
            amountReader = itemView.findViewById(R.id.tv_borrow_book_amount_reading);
            amountNote = itemView.findViewById(R.id.tv_borrow_book_amount_note);
            ratingBar = itemView.findViewById(R.id.rab_borrow_book);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            listener.onClick(v,getAdapterPosition());
        }
    }
}
