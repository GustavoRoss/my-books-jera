package com.example.gustavo.books.activities.Reminder;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.gustavo.books.R;
import com.example.gustavo.books.models.Reminder;
import com.example.gustavo.books.repository.ReminderRepository;

import java.util.List;

/**
 * Created by gustavo on 21/11/17.
 */

public class RemindersActivity  extends AppCompatActivity {

    private ListView remindersList;
    private ReminderRepository reminderRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_reminders);

        remindersList = findViewById(R.id.reminders_list);

        List<Reminder> reminders = (List<Reminder>) getIntent().getSerializableExtra("reminders");
        final ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, reminders);

        remindersList.setAdapter(arrayAdapter);
        remindersList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                showAlert((Reminder) arrayAdapter.getItem(i));
                return true;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        reminderRepository = new ReminderRepository(this);

    }

    public void showAlert(final Reminder reminder) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(R.string.update_status);
        dialog.setMessage(R.string.sure_to_update);

        dialog.setNegativeButton(R.string.cancel, null);
        dialog.setPositiveButton(R.string.ok, new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                reminderRepository.updateStatus(reminder);
            }});

        dialog.show();

    }

}
