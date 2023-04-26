package com.example.booklibraryapplication;

import com.example.booklibraryapplication.provider.Book;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {
    ArrayList<Book> bookList = new ArrayList<>();


    public void setBookList(ArrayList<Book> bookList) {
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_layout, parent, false); //CardView inflated as RecyclerView list item
        ViewHolder viewHolder = new ViewHolder(v);
        Log.d("RecycleAdapter", "onCreateViewHolder");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bookCard_bookID.setText("ID: " + bookList.get(position).getId());
        holder.bookCard_title.setText("TITLE: " + bookList.get(position).getTitle());
        holder.bookCard_author.setText("AUTHOR: " + bookList.get(position).getAuthor());
        holder.bookCard_isbn.setText("ISBN: " + bookList.get(position).getIsbn());
        holder.bookCard_desc.setText("DESC: " + bookList.get(position).getDescription());
        holder.bookCard_price.setText("PRICE: " + bookList.get(position).getPrice());
        Log.d("week7App", "onBindViewHolder");
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView bookCard_bookID, bookCard_author, bookCard_title, bookCard_isbn, bookCard_desc, bookCard_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookCard_bookID = itemView.findViewById(R.id.card_tV_bookID);
            bookCard_author = itemView.findViewById(R.id.card_tV_author);
            bookCard_title = itemView.findViewById(R.id.card_tV_title);
            bookCard_isbn = itemView.findViewById(R.id.card_tV_isbn);
            bookCard_desc = itemView.findViewById(R.id.card_tV_description);
            bookCard_price = itemView.findViewById(R.id.card_tV_price);
        }
    }
}