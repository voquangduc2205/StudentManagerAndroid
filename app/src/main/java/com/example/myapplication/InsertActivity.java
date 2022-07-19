package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InsertActivity extends AppCompatActivity {
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        EditText name = findViewById(R.id.hoten);
        EditText email = findViewById(R.id.email);
        EditText date = findViewById(R.id.ngaysinh);
        Button add = findViewById(R.id.btn_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String path = getFilesDir() + "/mydb";
                try {
                    db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.CREATE_IF_NECESSARY);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                db.beginTransaction();
                try {
                    ContentValues cv = new ContentValues();


                    cv.put("name", name.getText().toString());
                    cv.put("email", email.getText().toString());
                    cv.put("date", date.getText().toString());

                    long ret = db.insert("student", null, cv);
                    Log.v("TAG", "ret = " + ret);

                    cv.clear();
                    db.setTransactionSuccessful();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    db.endTransaction();
                }
                finish();
            }
        });
    }

    public void add(View view){


    }
}