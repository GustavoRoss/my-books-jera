package com.example.gustavo.books.database;

import android.provider.BaseColumns;

/**
 * Created by gustavo on 19/11/17.
 */

public final class DBContract {

    //para evitar instâncias acidentais
    private DBContract() {

    }

    public static final class Books implements BaseColumns {
        public static final String TABLE_NAME = "books";
        public static final String BOOK_NAME = "name";
        public static final String BOOK_PAGES = "pages";
        public static final String BOOK_IMAGE = "image";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + Books.TABLE_NAME + " (" +
                        Books._ID + " INTEGER PRIMARY KEY," +
                        Books.BOOK_NAME + " TEXT," +
                        Books.BOOK_PAGES + " TEXT," +
                        Books.BOOK_IMAGE + " BLOB ) ";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + Books.TABLE_NAME;
    }

    public static final class Reminders implements  BaseColumns {
        public static final String TABLE_NAME = "reminders";
        public static final String REMINDER_NAME = "name";
        public static final String REMINDER_TIME = "time";
        public static final String REMINDER_DATE = "date";
        public static final String REMINDER_COLUMN_BOOK_NAME = "book";
        public static final String STATUS = "status";

        //tenho consciência de não usar horas e datas como texto
        //porém preciso estudar mais como trabalhar com datas e hora
        //no SQLite, tendo em vista que ele não possui os tipos boolean e date
        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + Reminders.TABLE_NAME + " (" +
                        Reminders._ID + " INTEGER PRIMARY KEY," +
                        Reminders.REMINDER_NAME + " TEXT, " +
                        Reminders.REMINDER_TIME + " TEXT, " +
                        Reminders.REMINDER_DATE + " TEXT, " +
                        Reminders.STATUS + " INTEGER, " +
                        Reminders.REMINDER_COLUMN_BOOK_NAME + " STRING )";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + Reminders.TABLE_NAME;
    }
}
