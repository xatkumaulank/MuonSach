package com.example.muonsach.search;

import android.content.Context;
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
import com.example.muonsach.obj.BookShare;

import java.util.ArrayList;
import java.util.List;

public class BookShareAdapter extends RecyclerView.Adapter<BookShareAdapter.BookShareViewHolder> implements Filterable {
    List<BookShare> bookShareList;
    Context context;
    List<BookShare> bookShareListOld;
    ItemClickListener listener;
    public BookShareAdapter(List<BookShare> data, Context context, ItemClickListener listener) {
        this.bookShareList = data;
        this.bookShareListOld = data;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookShareViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_book_share,parent,false);
        return new BookShareViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookShareViewHolder holder, int position) {
        BookShare bookShare = bookShareList.get(position);
        if (bookShare == null){
            return;
        }
        Glide.with(holder.imgvBookshare.getContext()).load(bookShare.getImgBookShare()).into(holder.imgvBookshare);
        holder.rabBookshare.setRating(bookShare.getNumStars());
        holder.tvBookshareName.setText(bookShare.getImgBookShareName());
    }

    @Override
    public int getItemCount() {
        if (bookShareList != null){
            return bookShareList.size();
        }
        return 0;
    }



    class BookShareViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgvBookshare;
        TextView tvBookshareName;
        RatingBar rabBookshare;
        public BookShareViewHolder(@NonNull View itemView) {
            super(itemView);
            imgvBookshare = itemView.findViewById(R.id.imgv_bookshare);
            tvBookshareName = itemView.findViewById(R.id.tv_bookshare_name);
            rabBookshare = itemView.findViewById(R.id.rab_bookshare);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v,getAdapterPosition());
        }
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()){
                    bookShareList = bookShareListOld;
                }else {
                    List<BookShare> list = new ArrayList<>();
                    for (BookShare bookShare: bookShareListOld) {
                        if (bookShare.getImgBookShareName().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(bookShare);
                        }
                    }
                    bookShareList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = bookShareList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                bookShareList = (List<BookShare>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
