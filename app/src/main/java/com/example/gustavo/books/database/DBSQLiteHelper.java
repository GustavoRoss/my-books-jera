package com.example.gustavo.books.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gustavo on 19/11/17.
 */

public class DBSQLiteHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "jera_books_database";

    public DBSQLiteHelper(Context context) {
        super(context, DATABASE_NAME,  null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DBContract.Books.SQL_CREATE_ENTRIES);
        sqLiteDatabase.execSQL(DBContract.Reminders.SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DBContract.Books.SQL_DELETE_ENTRIES);
        sqLiteDatabase.execSQL(DBContract.Reminders.SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

}
