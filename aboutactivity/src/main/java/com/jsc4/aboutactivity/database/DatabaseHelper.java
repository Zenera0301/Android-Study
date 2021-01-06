package com.jsc4.aboutactivity.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String USER_TABLE_NAME = "user";
    public static final String BOOK_TABLE_NAME = "book";
    public static final String USERNAME = "username";
    public static final String AGE = "age";
    public static final String BOOK_NAME = "book_name";
    public static final String BOOK_PRICE = "book_price";
    public static final String DATABASE_NAME = "test.db";
    public static final int VERSION = 1;


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + USER_TABLE_NAME + "(" + USERNAME + " varchar(20) not null, " + AGE + " varchar(10) not null);");
        db.execSQL("create table " + BOOK_TABLE_NAME + "(" + BOOK_NAME + " varchar(20) not null, " + BOOK_PRICE + " varchar(10) not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO on database upgrade operation

    }
}
