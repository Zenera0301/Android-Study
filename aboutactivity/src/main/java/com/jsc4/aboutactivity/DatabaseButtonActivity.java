package com.jsc4.aboutactivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jsc4.aboutactivity.database.DatabaseHelper;
import com.jsc4.aboutactivity.provider.URIList;

public class DatabaseButtonActivity extends AppCompatActivity {

    SQLiteDatabase mSqLiteDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        mSqLiteDatabase = databaseHelper.getWritableDatabase();

        // Insert
        findViewById(R.id.database_add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // IO操作，建议后台操作
                // 拼装成contentValues
                ContentValues contentValues = new ContentValues();
                contentValues.put(DatabaseHelper.USERNAME, "miamia");
                contentValues.put(DatabaseHelper.AGE, "13");

                // 判断插入是否成功
                long row =  mSqLiteDatabase.insert(DatabaseHelper.USER_TABLE_NAME, null, contentValues);
                Log.i("djtest", "onClick: row="+row);
                if(row != -1) {
                    Toast.makeText(DatabaseButtonActivity.this, "插入成功！", Toast.LENGTH_SHORT).show();
                }

                // query
                Cursor cursor = mSqLiteDatabase.query(DatabaseHelper.USER_TABLE_NAME, null, null, null, null, null, null);
                if(cursor.moveToFirst()) {
                    int count = cursor.getCount();
                    for (int i = 0; i < count; i++) {
                        String userName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.USERNAME));
                        String age = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.AGE));
                        Log.i("djtest",i + " : " + userName + " | " + age + ".");
                    }
                }
            }
        });

        findViewById(R.id.database_delete_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // delete
                String whereClauseString = "username=?";
                String[] whereArgs = {"miamia"};
                mSqLiteDatabase.delete(DatabaseHelper.USER_TABLE_NAME, whereClauseString, whereArgs);
            }
        });

        findViewById(R.id.database_update_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // update
                // 当字段username=miamia时，将age更新为18
                ContentValues contentValues = new ContentValues();
                contentValues.put(DatabaseHelper.AGE, "18");
                String whereClauseString = "username=?";
                String[] whereArgs = {"miamia"};
                mSqLiteDatabase.update(DatabaseHelper.USER_TABLE_NAME, contentValues, whereClauseString, whereArgs);
            }
        });

        findViewById(R.id.database_transactions_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSqLiteDatabase.beginTransaction();//开始事务，此时数据库会被锁定
                try {
                    // 做你的操作
                    for (int i = 0; i < 1000; i++) {
                        mSqLiteDatabase.execSQL("insert into user(username, age) values ('miamia', '5岁')");
                    }
                    mSqLiteDatabase.setTransactionSuccessful();//设置事务已经成功，否则自动回滚不提交
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    mSqLiteDatabase.endTransaction();//提交并关闭事务
                }

                ContentResolver contentResolver = getContentResolver();

                // 这里拿着uri去找
                contentResolver.query(Uri.parse(URIList.USER_URI), null, null, null, null);//content://com.jsc4.aboutactivity/user
            }
        });


    }
}
