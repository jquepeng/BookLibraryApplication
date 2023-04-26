package com.example.booklibraryapplication.provider;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "books")
public class Book {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "bookNo")
    private int bookNo;

    @ColumnInfo(name = "bookID")
    private String id;

    @ColumnInfo(name = "bookTitle")
    private String title;

    @ColumnInfo(name = "bookAuthor")
    private String author;

    @ColumnInfo(name = "bookIsbn")
    private String isbn;

    @ColumnInfo(name = "bookDescription")
    private String description;

    @ColumnInfo(name = "bookPrice")
    private String price;

    public Book() {}

    public Book(String id, String title, String author, String isbn, String description, String price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.description = description;
        this.price = price;
    }

    public int getBookNo() {
        return bookNo;
    }

    public void setBookNo(int bookNo) {
        this.bookNo = bookNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}