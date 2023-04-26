package com.example.booklibraryapplication.provider;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class BookViewModel extends AndroidViewModel {
    private static final String TAG = "SuperTag";
    private final BookRepository mRepository;
    private final LiveData<List<Book>> mAllBooks;

    public BookViewModel(@NonNull Application application) {
        super(application);
        mRepository = new BookRepository(application);
        mAllBooks = mRepository.getAllBooks();
    }

    public LiveData<List<Book>> getAllBooks() {
        return mAllBooks;
    }

    public Book getBook(String id) {
        Log.d(TAG,"bvm: Calling bookID:" + id);
        return mRepository.getBook(id);
    }

    public Book getLastBook() {
        return mRepository.getLastBook();
    }

    public long maxBookNo() {
        Log.d(TAG, "maxBookNo: ViewModel called");
        return mRepository.maxBookNo();
    }
    public void insert(Book book) {
        mRepository.insert(book);
    }

    public void deleteMaxPrice() {
        mRepository.deleteMaxPrice();
    }

    public void deleteLastBook() {
        mRepository.deleteLastBook();
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }

}
