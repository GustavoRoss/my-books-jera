package com.example.gustavo.books.activities.Book;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.Manifest;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gustavo.books.R;
import com.example.gustavo.books.activities.Reminder.RegisterReminderActivity;
import com.example.gustavo.books.models.Book;
import com.example.gustavo.books.repository.BooksRepository;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

public class RegisterBookActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int REQUEST_CODE_GALLERY = 12;
    private EditText bookName, qtdPages;
    private ImageView image;
    private BooksRepository booksRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        bookName = findViewById(R.id.book_name);
        qtdPages = findViewById(R.id.qtd_pages);
        image = findViewById(R.id.book_image);

        findViewById(R.id.register_book_button).setOnClickListener(this);
        findViewById(R.id.list_books_activity).setOnClickListener(this);
        findViewById(R.id.register_reminder_activity_button).setOnClickListener(this);
        findViewById(R.id.choose_image).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        booksRepository = new BooksRepository(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            //função do botão de registro
            case R.id.register_book_button:
                String name = bookName.getText().toString();
                int pages = Integer.valueOf(qtdPages.getText().toString());
                byte[] bookImage = imageViewToByte(image);

                Book book = new Book(name, pages, bookImage);
                booksRepository.save(book);

                bookName.setText("");
                qtdPages.setText("");
                image.setImageResource(R.drawable.books);

                break;

            //função do botão de listar livros
            case R.id.list_books_activity:
                List<Book> books = booksRepository.findAll();

                Intent gotoListBooksActivity = new Intent(this, BooksActivity.class);
                gotoListBooksActivity.putExtra("books", (Serializable) books);

                startActivity(gotoListBooksActivity);
                break;

            //função do botão de cadastrar lembretes
            case R.id.register_reminder_activity_button:
                List<Book> booksSpinner = booksRepository.findAll();

                Intent gotoRegisterReminderActivity = new Intent(this, RegisterReminderActivity.class);
                gotoRegisterReminderActivity.putExtra("books", (Serializable) booksSpinner);

                startActivity(gotoRegisterReminderActivity);
                break;

            case R.id.choose_image:
                ActivityCompat.requestPermissions(this,
                        new String[] { Manifest.permission.READ_EXTERNAL_STORAGE }, REQUEST_CODE_GALLERY);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            } else {
                Toast.makeText(this, R.string.permission_danied, Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                image.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}
