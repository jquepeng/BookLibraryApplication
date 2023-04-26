package com.example.booklibraryapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ListBooksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_books);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ListBooksFragment()).addToBackStack("1").commit();
    }
}