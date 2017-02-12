package com.highway.study.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.highway.study.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SQliteTestActivity extends AppCompatActivity {

    private MyDatebaseHelper mDbHelper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_test);
        mDbHelper = new MyDatebaseHelper(this, "BookStore.db");
        db = mDbHelper.getWritableDatabase();
        ButterKnife.bind(this);

    }

    @OnClick(R.id.bt_add)
    public void addDate() {
        mDbHelper.getWritableDatabase();
    }


    private void insert() {
        ContentValues values = new ContentValues();
        values.put("name", "The Da Vinci Code");
        values.put("author", "Dan Brown");
        values.put("pages", 454);
        values.put("price", 16.69);
        db.insert("Book", null, values);
        values.clear();

        values.put("name", "The Lost Symbol");
        values.put("author", "Dan Brown");
        values.put("pages", 510);
        values.put("price", 19.69);
        db.insert("Book", null, values);
    }

    private void update() {
        ContentValues values = new ContentValues();
        values.put("price", 10.99);
        db.update("Book", values, "name = ?", new String[] {"The Da Vinci Code"});
    }

    private void delete() {
        db.delete("Book", "pages > ?", new String[]{"500"});
    }

    private void query() {
        Cursor cursor = db.query("Book", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do{
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String author = cursor.getString(cursor.getColumnIndex("author"));
                int page = cursor.getInt(cursor.getColumnIndex("pages"));
                double price = cursor.getDouble(cursor.getColumnIndex("price"));
            } while (cursor.moveToNext());
        }
        cursor.close();
    }


}
