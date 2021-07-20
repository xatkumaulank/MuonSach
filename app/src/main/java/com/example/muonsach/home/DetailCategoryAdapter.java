package com.example.muonsach.home;

import android.content.Context;
import android.media.Image;
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
import com.example.muonsach.obj.Book;

import java.util.List;

public class DetailCategoryAdapter extends RecyclerView.Adapter<DetailCategoryAdapter.DetailCategoryViewHolder> {

    Context context;
    List<Book> bookList;
    ItemClickListener listener;

    public DetailCategoryAdapter(Context context, List<Book> bookList, ItemClickListener listener) {
        this.context = context;
        this.bookList = bookList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DetailCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_book_in_category,parent,false);
        return new DetailCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailCategoryViewHolder holder, int position) {
        if (bookList == null){
            return;
        }
        Book book = bookList.get(position);
        Glide.with(holder.imgBook.getContext()).load(book.getBookimg()).into(holder.imgBook);
        holder.authorBook.setText(book.getBookauthor());
        holder.nameBook.setText(book.getBookname());
        holder.ratingBook.setRating(book.getRating());
    }

    @Override
    public int getItemCount() {
        if (bookList != null){
            return bookList.size();
        }
        return 0;
    }

    class DetailCategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imgBook;
        private TextView nameBook, authorBook;
        private RatingBar ratingBook;
        public DetailCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBook = itemView.findViewById(R.id.imgBook);
            nameBook = itemView.findViewById(R.id.nameBook);
            authorBook = itemView.findViewById(R.id.authorBook);
            ratingBook = itemView.findViewById(R.id.ratingBook);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v,getAdapterPosition());
        }
    }
}
