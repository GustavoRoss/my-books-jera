package com.example.gustavo.books.models;

import com.example.gustavo.books.database.DBContract;
import org.chalup.microorm.annotations.Column;

import java.io.Serializable;

/**
 * Created by gustavo on 17/11/17.
 */
public class Book implements Serializable{

    @Column(DBContract.Books._ID)
    private long id;
    //variables
    @Column(DBContract.Books.BOOK_NAME)
    private String name;
    @Column(DBContract.Books.BOOK_PAGES)
    private int pages;

    private byte[] image;

    //constructors
    public Book(String name, int pages, byte[] image) {
        this.name = name;
        this.pages = pages;
        this.image = image;
    }

    public Book() {

    }

    //getters and setters methods
    public int getPages() {
        return pages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return " Nome: " + name + "\n Qtd. de páginas: " + pages;
    }
}