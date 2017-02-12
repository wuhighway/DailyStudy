package com.highway.study.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by highway
 * on 2017/2/12.
 */

public class MyDatebaseHelper extends SQLiteOpenHelper {
    private static final int VERSION_CODE = 1;
    private static final String CREATE_BOOK_DB = "create table Book ("
            + "id integer primary key autoincrement, "
            + "author text, "
            + "price real, "
            + "pages integer, "
            + "name text)";
    private static final String CREATE_CATEGORY = "create table Category ("
            + "id integer primary key autoincrement, "
            + "category_name text, "
            + "category_code integer)";
    private Context mContext;
    public MyDatebaseHelper(Context context, String name) {
        super(context, name, null, VERSION_CODE);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK_DB);
        Toast.makeText(mContext, "create success", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
