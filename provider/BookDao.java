package com.example.booklibraryapplication.provider;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BookDao {
    @Query("select * from books")
    LiveData<List<Book>> getAllBooks();

    @Query("SELECT * FROM books WHERE bookID = :id")
    List<Book> getBook(String id);

    @Query("SELECT * FROM books WHERE bookNo = (select MAX(bookNo) from books)")
    List<Book> getLastBook();

    @Query("select MAX(bookNo) from books")
    Long maxBookNo();

    @Insert
    void addBook(Book book);

    @Query("delete from books where bookId= :id")
    void deleteBook(int id);

    @Query("delete from books where bookNo = (select MAX(bookNo) from books)")
    void deleteLastBook();

    @Query("delete from books where bookPrice = (select max(bookPrice) from books)")
    void deleteMaxPriceBook();

    @Query("delete FROM books")
    void deleteAllBooks();
}
