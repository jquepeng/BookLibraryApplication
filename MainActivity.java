package com.example.booklibraryapplication;

import com.example.booklibraryapplication.provider.Book;
import com.example.booklibraryapplication.provider.BookDao;
import com.example.booklibraryapplication.provider.BookDao_Impl;
import com.example.booklibraryapplication.provider.BookDatabase;
import com.example.booklibraryapplication.provider.BookViewModel;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.EditText;
import android.widget.Toast;
import android.content.SharedPreferences;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "SuperTag";
    EditText editText_BookId;
    EditText editText_Title;
    EditText editText_ISBN;
    EditText editText_Author;
    EditText editText_Description;
    EditText editText_Price;

    SharedPreferences sP;

    private BookViewModel mBookViewModel;
    DatabaseReference cloudDB;
    DatabaseReference libraryTable;


    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;


    public static final String BOOKID_KEY = "bookid-key";
    public static final String TITLE_KEY = "title-key";
    public static final String ISBN_KEY = "isbn-key";
    public static final String AUTHOR_KEY = "author-key";
    public static final String DESCRIPTION_KEY = "description-key";
    public static final String PRICE_KEY = "price-key";

    private String bookID, title, isbn, author, description, price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS}, 0);

        MyBroadCastReceiver myBroadCastReceiver = new MyBroadCastReceiver();
        registerReceiver(myBroadCastReceiver, new IntentFilter(com.example.booklibraryapplication.SMSReceiver.SMS_FILTER));
        sP = getSharedPreferences("editTextStrings.ext", Context.MODE_PRIVATE);

        setContentView(R.layout.drawer_layout);
        editText_BookId = findViewById(R.id.editText_BookID_id);
        editText_Title = findViewById(R.id.editText_Title_id);
        editText_ISBN = findViewById(R.id.editText_ISBN_id);
        editText_Author = findViewById(R.id.editText_Author_id);
        editText_Description = findViewById(R.id.editText_Description_id);
        editText_Price = findViewById(R.id.editText_Price_id);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mBookViewModel = new ViewModelProvider(this).get(BookViewModel.class);
        //Model provider (instead of new instance of viewModel) ensures that the viewModel is run across multiple fragments/activities

        navigationView.setNavigationItemSelectedListener(new MyNavigationListener());
        if (savedInstanceState == null || savedInstanceState.isEmpty()) {
            loadBook();
        }

        cloudDB = FirebaseDatabase.getInstance().getReference();
        libraryTable = cloudDB.child("bookLib/library");
        libraryTable.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        //super.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");

        title = editText_Title.getText().toString();
        isbn = editText_ISBN.getText().toString();


        outState.putString(BOOKID_KEY, bookID);
        outState.putString(TITLE_KEY, title);
        outState.putString(ISBN_KEY, isbn);
        outState.putString(AUTHOR_KEY, author);
        outState.putString(DESCRIPTION_KEY, description);
        outState.putString(PRICE_KEY, price);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle inState) {
        Log.d(TAG, "onRestoreInstanceState");
        //bookID = inState.getString(BOOKID_KEY);
        title = inState.getString(TITLE_KEY);
        //Toast.makeText(this, title, Toast.LENGTH_SHORT).show();
        isbn = inState.getString(ISBN_KEY);
        //author = inState.getString(AUTHOR_KEY);
        //description = inState.getString(DESCRIPTION_KEY);
        //price = inState.getString(PRICE_KEY);

        editText_Title.setText(title);
        editText_ISBN.setText(isbn);


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case (R.id.option_menu_clearFields):
                clearFields();
                break;
            case (R.id.option_menu_loadBook):
                loadBook();
                break;
        }
        return true;
    }

    public void onAddBook(View view) {
        addBook();
    }

    public void addBook() {
        String newBookId = editText_BookId.getText().toString();
        String newTitle = editText_Title.getText().toString();
        String newIsbn = editText_ISBN.getText().toString();
        String newAuthor = editText_Author.getText().toString();
        String newDesc = editText_Description.getText().toString();
        String newPrice = editText_Price.getText().toString();

        Book newBook = new Book(newBookId, newTitle, newAuthor, newIsbn, newDesc, newPrice);


        mBookViewModel.insert(newBook);
        //mBookViewModel.getBook(newBookId);
        libraryTable.push().setValue(newBook);

//        try {
//            Toast.makeText(this, mBookViewModel.maxBookId() + " added to DB", Toast.LENGTH_SHORT).show();
//        } catch (Exception e) {
//            Toast.makeText(this, "welp this isn't working", Toast.LENGTH_SHORT).show();
//        }
    }

    public void onLoadBook(View view) {
        loadBook();
    }

    public void loadBook() {
        //Toast.makeText(getBaseContext(), "Loading Book not currently implemented", Toast.LENGTH_SHORT).show();

        try {
            Book loadingBook = mBookViewModel.getBook("000");
            editText_Title.setText(loadingBook.getTitle());
            editText_ISBN.setText(loadingBook.getIsbn());
            editText_Author.setText(loadingBook.getAuthor());
            editText_Description.setText(loadingBook.getDescription());
            editText_Price.setText(loadingBook.getPrice());
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), "getLastBook failed", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClearFields(View view) {
        clearFields();
    }

    public void clearFields() {
        editText_BookId.setText("");
        editText_Title.setText("");
        editText_ISBN.setText("");
        editText_Author.setText("");
        editText_Description.setText("");
        editText_Price.setText("");


        if (mBookViewModel != null) {
            Log.d(TAG,"calling BookDao");
            Toast.makeText(this, mBookViewModel.maxBookNo()+"", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No books", Toast.LENGTH_SHORT).show();
        }

    }

    public void removeLastBook() {
        //mBookViewModel.deleteMaxPrice();
        mBookViewModel.deleteLastBook();
        Toast.makeText(this, "removed", Toast.LENGTH_SHORT).show();
    }

    public void removeAllBooks() {
        SharedPreferences.Editor editor = sP.edit();
        editor.clear().apply();

        mBookViewModel.deleteAll();

        libraryTable.removeValue();

        Toast.makeText(this, "books cleared", Toast.LENGTH_SHORT).show();
    }

    public void listAllBooks() {
        Intent intent = new Intent(this, ListBooksActivity.class);
        startActivity(intent);
    }

    public void onReset(View view) {
        editText_Title.setText("Harry Potter");
        editText_ISBN.setText("54321");
        editText_Author.setText("JK Rowling");
        editText_Description.setText("magic boy");
        editText_Price.setText("20");
    }

    class MyBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String msg = intent.getStringExtra(SMSReceiver.SMS_MSG_KEY);


            StringTokenizer sT = new StringTokenizer(msg, "|");
            String tokenCheck1 = sT.nextToken();

            String title = sT.nextToken();
            String isbn = sT.nextToken();
            String author = sT.nextToken();
            String description = sT.nextToken();
            String price = sT.nextToken();

            String tokenCheck2 = sT.nextToken();

            if (tokenCheck1.equals(tokenCheck2)) {
                Toast.makeText(getBaseContext(), "Updated fields", Toast.LENGTH_SHORT).show();
                editText_Title.setText(title);
                editText_ISBN.setText(isbn);
                editText_Author.setText(author);
                editText_Description.setText(description);
                editText_Price.setText(price);
            } else {
                Toast.makeText(getBaseContext(), "mistmatched tokens " + tokenCheck1 + tokenCheck2, Toast.LENGTH_SHORT).show();
            }
        }
    }

    class MyNavigationListener implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int itemId = item.getItemId();

            switch (itemId) {
                case (R.id.nav_menu_addBook):
                    addBook();
                    break;
                case (R.id.nav_menu_removeLastBook):
                    removeLastBook();
                    break;
                case (R.id.nav_menu_removeAllBooks):
                    removeAllBooks();
                    break;
                case (R.id.nav_menu_listAllBooks):
                    listAllBooks();
                    break;
            }
            drawerLayout.closeDrawers();
            return true;
        }
    }
}
