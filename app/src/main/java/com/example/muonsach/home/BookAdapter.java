package com.example.muonsach.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.muonsach.R;
import com.example.muonsach.clickRcv.ItemClickListener;
import com.example.muonsach.obj.Book;
import com.example.muonsach.obj.BookShare;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> implements Filterable {

    ArrayList<Book> bookArrayList;
    ArrayList<Book> bookArrayListOld;
    Context context;
    ItemClickListener listener;
    public BookAdapter(ArrayList<Book> bookArrayList, Context context, ItemClickListener listener) {
        this.bookArrayList = bookArrayList;
        this.context = context;
        this.listener = listener;
        this.bookArrayListOld = bookArrayList;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_book,parent,false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookArrayList.get(position);
        if (book == null){
            return;
        }
        Glide.with(holder.bookCover.getContext()).load(book.getBookimg()).into(holder.bookCover);
        holder.bookAuthor.setText(book.getBookauthor());
        holder.bookName.setText(book.getBookname());
        holder.ratingBar.setRating(book.getRating());
    }

    @Override
    public int getItemCount() {
        if (bookArrayList != null){
            return bookArrayList.size();
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
                    bookArrayList = bookArrayListOld;
                }else {
                    ArrayList<Book> list = new ArrayList<>();
                    for (Book book: bookArrayListOld) {
                        if (book.getBookname().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(book);
                        }
                    }
                    bookArrayList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = bookArrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                bookArrayList = (ArrayList<Book>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView bookCover;
        private TextView bookName,bookAuthor;
        private RatingBar ratingBar;
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            bookCover = itemView.findViewById(R.id.imgv_book);
            bookName = itemView.findViewById(R.id.tv_book_name);
            bookAuthor = itemView.findViewById(R.id.tv_book_author);
            ratingBar = itemView.findViewById(R.id.rating_bar);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v,getAdapterPosition());

        }
    }
}
