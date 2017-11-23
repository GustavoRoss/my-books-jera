package com.example.gustavo.books.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.gustavo.books.R;
import com.example.gustavo.books.database.DBContract;
import com.example.gustavo.books.database.DBSQLiteHelper;
import com.example.gustavo.books.interfaces.IBaseRepository;
import com.example.gustavo.books.models.Book;

import org.chalup.microorm.MicroOrm;

import java.util.List;

/**
 * Created by gustavo on 19/11/17.
 */

public class BooksRepository implements IBaseRepository<Book> {

    private SQLiteDatabase db;
    private SQLiteDatabase readableDb;
    private Context context;

    public BooksRepository(Context context) {
        this.db = new DBSQLiteHelper(context).getWritableDatabase();
        this.readableDb = new DBSQLiteHelper(context).getReadableDatabase();
        this.context = context;
    }

    @Override
    public void save(Book book) {

        ContentValues values = new ContentValues();
        values.put(DBContract.Books.BOOK_NAME, book.getName());
        values.put(DBContract.Books.BOOK_PAGES, book.getPages());
        values.put(DBContract.Books.BOOK_IMAGE, book.getImage());

        long newRowId = db.insert(DBContract.Books.TABLE_NAME, null, values);

        Toast.makeText(context, R.string.book_saved, Toast.LENGTH_LONG).show();
    }

    @Override
    public void update(Book book) {

        ContentValues values = new ContentValues();
        values.put(DBContract.Books.BOOK_NAME, book.getName());
        values.put(DBContract.Books.BOOK_PAGES, book.getPages());

        String selection = DBContract.Books._ID + " = ?";
        String[] parameters = { String.valueOf(book.getId()) };

        db.update(DBContract.Books.TABLE_NAME, values, selection, parameters);

        Toast.makeText(context, R.string.book_updated, Toast.LENGTH_LONG).show();

    }

    @Override
    public void delete(int id) {

        String selection = DBContract.Books._ID  + " = ?";
        String[] parameters = { String.valueOf(id) };
        db.delete(DBContract.Books.TABLE_NAME, selection, parameters);

        Toast.makeText(context, R.string.book_deleted, Toast.LENGTH_LONG).show();
    }

    @Override
    public Book findOne(int id) {
        String [] projection = {
                DBContract.Books._ID,
                DBContract.Books.BOOK_NAME,
                DBContract.Books.BOOK_PAGES
        };

        String selection = DBContract.Books._ID + " = ?";
        String [] parameters = { String.valueOf(id) };

        Cursor cursor = readableDb.query(DBContract.Books.TABLE_NAME, projection, selection, parameters,null,null,null);

        MicroOrm uOrm = new MicroOrm();
        Book book = uOrm.fromCursor(cursor, Book.class);

        return book;
    }

    @Override
    public List<Book> findAll() {

        String [] columns = {
                DBContract.Books._ID,
                DBContract.Books.BOOK_NAME,
                DBContract.Books.BOOK_PAGES
        };

        Cursor cursor = readableDb.query(DBContract.Books.TABLE_NAME, columns,null,null,null,null,null);

        MicroOrm uOrm = new MicroOrm();
        List<Book> books = uOrm.listFromCursor(cursor, Book.class);

        cursor.close();

        return books;
    }
}
