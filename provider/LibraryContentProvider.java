package com.example.booklibraryapplication.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class LibraryContentProvider extends ContentProvider {
    BookDatabase db;
    private static final String TAG = "SuperTag";
    private static final int BOOKS_MULTIPLE = 100;
    private static final int BOOKS_SINGLE = 200;
    private static final int BOOKS_SINGLE_ID = 210;

    UriMatcher uriMatcher = createUriMatcher();

    public LibraryContentProvider() {

    }

    private static UriMatcher createUriMatcher() {
        Log.d(TAG,"URI Matcher Created");
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = "fit2081.app.JEREMY";
        uriMatcher.addURI(authority,"books",BOOKS_MULTIPLE );
        uriMatcher.addURI(authority,"books" + "/book",BOOKS_SINGLE);
        uriMatcher.addURI(authority, "books" + "/#", BOOKS_SINGLE_ID);
        return uriMatcher;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        db = BookDatabase.getDatabase(getContext());
        Log.d(TAG, "LibraryContentProvider onCreate()");

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        int p = uriMatcher.match(uri);

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables("books");
        String query = builder.buildQuery(projection, selection, null, null, sortOrder, null);
        Log.d("LIBRARY_CONTENTPROVIDER", query);

        Cursor cursor = db
                //Retrieves raw database rather than room makes it readable then executes the query
                .getOpenHelper()
                .getReadableDatabase()
                .query(query);


        // TODO: Implement this to handle query requests from clients.
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}