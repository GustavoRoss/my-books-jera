package com.example.gustavo.books.activities.Book;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.gustavo.books.R;
import com.example.gustavo.books.models.Book;

import java.util.List;

/**
 * Created by gustavo on 17/11/17.
 */

public class BooksActivity extends AppCompatActivity  {

    private ListView booksList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_books);

        booksList = findViewById(R.id.books_list);

        List<Book> books = (List<Book>) getIntent().getSerializableExtra("books");
        ArrayAdapter<Book> arrayAdapter = new ArrayAdapter<Book>(this, android.R.layout.simple_list_item_1, books);

        booksList.setAdapter(arrayAdapter);

    }

}
