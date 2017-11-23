package com.example.gustavo.books.activities.Reminder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.gustavo.books.R;
import com.example.gustavo.books.models.Book;
import com.example.gustavo.books.models.Reminder;
import com.example.gustavo.books.repository.ReminderRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gustavo on 11/20/17.
 */

public class RegisterReminderActivity extends AppCompatActivity implements View.OnClickListener{

    //inputs
    Spinner spinnerBooks;
    EditText reminderName, reminderDate, reminderTime;

    //checkboxes
    CheckBox monday, tuesday, wednesday, thursday, friday, saturday, sunday, allDays;

    String bookName;

    ReminderRepository reminderRepository;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remider);

        //capturando views
        spinnerBooks = findViewById(R.id.spinner_books);
        reminderName = findViewById(R.id.reminder_name);
        reminderDate = findViewById(R.id.reminder_date);
        reminderTime = findViewById(R.id.reminder_hour);

        monday = findViewById(R.id.monday);
        tuesday = findViewById(R.id.tuesday);
        wednesday = findViewById(R.id.wednesday);
        thursday = findViewById(R.id.thursday);
        friday = findViewById(R.id.friday);
        saturday = findViewById(R.id.saturday);
        sunday = findViewById(R.id.sunday);
        allDays = findViewById(R.id.all_days);

        //eventos de click de cada botão
        findViewById(R.id.register_reminder_button).setOnClickListener(this);
        findViewById(R.id.list_reminders_activity).setOnClickListener(this);

        //inflando selectbox
        final List<Book> books = (List<Book>) getIntent().getSerializableExtra("books");
        ArrayAdapter<Book> arrayAdapter = new ArrayAdapter<Book>(this, android.R.layout.simple_spinner_item, books);

        //setando adapter e layout
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBooks.setAdapter(arrayAdapter);

        //implementando eventos do selectbox
        spinnerBooks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Book bookSelected = (Book) adapterView.getSelectedItem();
                bookName = bookSelected.getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        reminderRepository = new ReminderRepository(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            //função do botão de salvar lembrete
            case R.id.register_reminder_button:

                String name = reminderName.getText().toString();
                String date = reminderDate.getText().toString();
                String time =  reminderTime.getText().toString();
                List<String> checkBoxList = getCheckBoxCheckeds();

                Reminder reminder = new Reminder(name, date, time, bookName, checkBoxList, 0);
                reminderRepository.save(reminder);
                cleanEntries();

                break;

            //função do botão de listar lembretes
            case R.id.list_reminders_activity:
                Intent gotoListRemindersActivity = new Intent(this, RemindersActivity.class);

                List<Reminder> reminders = reminderRepository.findAll();
                gotoListRemindersActivity.putExtra("reminders", (Serializable) reminders);

                startActivity(gotoListRemindersActivity);

                break;

        }
    }

    private List<String> getCheckBoxCheckeds() {
        List<String> days = new ArrayList<>();
        if(monday.isChecked()) days.add(monday.getText().toString());
        if(tuesday.isChecked()) days.add(tuesday.getText().toString());
        if(wednesday.isChecked()) days.add(wednesday.getText().toString());
        if(thursday.isChecked()) days.add(thursday.getText().toString());
        if(friday.isChecked()) days.add(friday.getText().toString());
        if(saturday.isChecked()) days.add(saturday.getText().toString());
        if(sunday.isChecked()) days.add(sunday.getText().toString());
        if(allDays.isChecked()) days.add(allDays.getText().toString());

        return days;
    }

    private void cleanEntries() {
        reminderName.setText("");
        reminderDate.setText("");
        reminderTime.setText("");
        cleanAllCheckboxes();
    }

    private void cleanAllCheckboxes() {
        monday.setChecked(false); tuesday.setChecked(false);
        wednesday.setChecked(false); thursday.setChecked(false);
        friday.setChecked(false); saturday.setChecked(false);
        sunday.setChecked(false);
    }
}
