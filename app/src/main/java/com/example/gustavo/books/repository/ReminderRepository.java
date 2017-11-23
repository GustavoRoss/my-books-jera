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
import com.example.gustavo.books.models.Reminder;

import org.chalup.microorm.MicroOrm;

import java.util.List;

/**
 * Created by gustavo on 20/11/17.
 */

public class ReminderRepository implements IBaseRepository<Reminder> {

    private SQLiteDatabase db;
    private SQLiteDatabase readableDb;
    private Context context;

    public ReminderRepository(Context context) {
        this.context = context;
        this.db = new DBSQLiteHelper(context).getWritableDatabase();
        this.readableDb = new DBSQLiteHelper(context).getReadableDatabase();
    }

    @Override
    public void save(Reminder reminder) {
        ContentValues values = new ContentValues();
        values.put(DBContract.Reminders.REMINDER_NAME, reminder.getName());
        values.put(DBContract.Reminders.REMINDER_DATE, reminder.getDate().toString());
        values.put(DBContract.Reminders.REMINDER_TIME, reminder.getTime());
        values.put(DBContract.Reminders.REMINDER_COLUMN_BOOK_NAME, reminder.getBook());
        values.put(DBContract.Reminders.STATUS, reminder.getStatus());

        long newRowId = db.insert(DBContract.Reminders.TABLE_NAME, null, values);

        Toast.makeText(context, R.string.reminder_saved, Toast.LENGTH_LONG).show();
    }

    @Override
    public void update(Reminder reminder) {
        ContentValues values = new ContentValues();
        values.put(DBContract.Reminders.REMINDER_NAME, reminder.getName());
        values.put(DBContract.Reminders.REMINDER_DATE, reminder.getDate().toString());
        values.put(DBContract.Reminders.REMINDER_TIME, reminder.getTime());
        values.put(DBContract.Reminders.REMINDER_COLUMN_BOOK_NAME, reminder.getBook());
        values.put(DBContract.Reminders.STATUS, reminder.getStatus());

        String selection = DBContract.Reminders._ID + " = ?";
        String[] parameters = { String.valueOf(reminder.getId()) };

        db.update(DBContract.Books.TABLE_NAME, values, selection, parameters);

        Toast.makeText(context, R.string.reminder_updated, Toast.LENGTH_LONG).show();
    }

    @Override
    public void delete(int id) {
        String selection = DBContract.Reminders._ID  + " = ?";
        String[] parameters = { String.valueOf(id) };
        db.delete(DBContract.Reminders.TABLE_NAME, selection, parameters);

        Toast.makeText(context, R.string.reminder_deleted, Toast.LENGTH_LONG).show();
    }

    @Override
    public Reminder findOne(int id) {
        return null;
    }

    @Override
    public List<Reminder> findAll() {
        String [] columns = {
                DBContract.Reminders._ID,
                DBContract.Reminders.REMINDER_NAME,
                DBContract.Reminders.REMINDER_DATE,
                DBContract.Reminders.REMINDER_TIME,
                DBContract.Reminders.REMINDER_COLUMN_BOOK_NAME,
                DBContract.Reminders.STATUS
        };

        Cursor cursor = readableDb.query(DBContract.Reminders.TABLE_NAME, columns,null,null,null,null,null);

        MicroOrm uOrm = new MicroOrm();
        List<Reminder> reminders = uOrm.listFromCursor(cursor, Reminder.class);

        cursor.close();

        return reminders;
    }

    public void updateStatus(Reminder reminder) {
        ContentValues values = new ContentValues();

        if(reminder.getStatus() == 0)
            values.put(DBContract.Reminders.STATUS,  1);
        else
            values.put(DBContract.Reminders.STATUS,  0);

        String selection = DBContract.Reminders._ID + " = ?";
        String[] parameters = { String.valueOf(reminder.getId()) };

        db.update(DBContract.Reminders.TABLE_NAME, values, selection, parameters);
    }
}
