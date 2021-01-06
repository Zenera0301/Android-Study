package com.jsc4.aboutactivity.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jsc4.aboutactivity.database.DatabaseHelper;

public class TestContentProvider extends ContentProvider {

    private static UriMatcher sUriMatcher;
    public static final int URI_MATCH_USER = 1;
    public static final int URI_MATCH_BOOK = 2;

    static {
        sUriMatcher  = new UriMatcher(UriMatcher.NO_MATCH);

        // content://com.jsc4.aboutactivity/user
        // content://com.jsc4.aboutactivity/book
        sUriMatcher.addURI(URIList.AUTHORITY, DatabaseHelper.USER_TABLE_NAME, URI_MATCH_USER);
        sUriMatcher.addURI(URIList.AUTHORITY, DatabaseHelper.USER_TABLE_NAME, URI_MATCH_BOOK);
    }

    DatabaseHelper mDatabaseHelper;

    @Override
    public boolean onCreate() {
        mDatabaseHelper = new DatabaseHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        // 知道用户在调用哪张表
        String tableName = getTabName(uri);
        if(TextUtils.isEmpty(tableName)){
            return null;
        }

        Cursor cursor = mDatabaseHelper.getReadableDatabase()
                .query(tableName, projection, selection, selectionArgs, null, null, sortOrder);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        String tableName = getTabName(uri);
        if(TextUtils.isEmpty(tableName)){
            return null;
        }
        long id = mDatabaseHelper.getWritableDatabase().insert(tableName, null, values);

        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        String tableName = getTabName(uri);
        if(TextUtils.isEmpty(tableName)){
            return -1;
        }
        int count = mDatabaseHelper.getWritableDatabase().delete(tableName, selection, selectionArgs);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    private String getTabName(Uri uri){
        int type = sUriMatcher.match(uri);
        String tableName = null;
        switch (type){
            case URI_MATCH_USER:
                tableName = DatabaseHelper.USER_TABLE_NAME;
                break;
            case URI_MATCH_BOOK:
                tableName = DatabaseHelper.BOOK_TABLE_NAME;
                break;
        }
        return tableName;
    }
}
