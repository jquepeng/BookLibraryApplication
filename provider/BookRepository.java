package com.example.booklibraryapplication.provider;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

public class BookRepository {
    private final BookDao myBookDao;
    private static final String TAG = "SuperTag";
    //private final LiveData<List<Book>> myAllBooks;

    BookRepository(Application application) {
        BookDatabase db = BookDatabase.getDatabase(application);
        myBookDao = db.bookDao();
        //myAllBooks = myBookDao.getAllBooks();
    }

    LiveData<List<Book>> getAllBooks() {
        Log.d("BookViewModel", "All books retrieved");
        return myBookDao.getAllBooks();
    }

    Book getBook(String id) {
        Log.d(TAG,"bRepo: Calling bookID:" + id);
        Book book = myBookDao.getBook(id).get(0);
        Log.d(TAG, "bRepo: Book returning: " + book.getTitle());
        return book;
    }

    Book getLastBook() { return myBookDao.getLastBook().get(0);}

    long maxBookNo() {
        Log.d(TAG, "maxBookNo: Repository called");
        long maxNo = myBookDao.maxBookNo();
        Log.d(TAG, "Max book number is " + maxNo);
        return maxNo;
    }

    void insert(Book book) {
        BookDatabase.databaseWriteExecutor.execute(() -> {myBookDao.addBook(book);});
        Log.d(TAG, "Book added");
    }

    void deleteMaxPrice() {
        BookDatabase.databaseWriteExecutor.execute(() -> {
            myBookDao.deleteMaxPriceBook();
        });
    }

    void deleteLastBook() {
        BookDatabase.databaseWriteExecutor.execute(() -> {
            myBookDao.deleteLastBook();
        });
        Log.d(TAG, "Book deleted");
    }

    void deleteAll() {
        BookDatabase.databaseWriteExecutor.execute(() -> {
            myBookDao.deleteAllBooks();
        });
        Log.d(TAG, "All books deleted");
    }

}
