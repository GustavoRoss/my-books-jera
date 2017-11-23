package com.example.gustavo.books.models;


import com.example.gustavo.books.database.DBContract;

import org.chalup.microorm.annotations.Column;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gustavo on 20/11/17.
 */

public class Reminder implements Serializable {

    @Column(DBContract.Reminders._ID)
    private int id;
    @Column(DBContract.Reminders.REMINDER_NAME)
    private String name;
    @Column(DBContract.Reminders.REMINDER_DATE)
    private String date;
    @Column(DBContract.Reminders.REMINDER_TIME)
    private String time;
    @Column(DBContract.Reminders.REMINDER_COLUMN_BOOK_NAME)
    private String book;
    @Column(DBContract.Reminders.STATUS)
    private int status = 0;

    private List<String> days;

    public Reminder(String name, String date, String time, String book, List<String> days, int status) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.book = book;
        this.days = days;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<String> getDays() {
        return days;
    }

    public void setDays(List<String> days) {
        this.days = days;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" Nome: " + name + "\n");
        stringBuilder.append(" Data: " + date + "\n");
        stringBuilder.append(" Hora: " + time + "\n");
        stringBuilder.append(" Livro: " + book + "\n");
        if(status == 1)
            stringBuilder.append(" STATUS: COMPLETADO");
        else
            stringBuilder.append(" STATUS: NÃO COMPLETADO");

        return stringBuilder.toString();
    }
}
