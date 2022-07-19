package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    public void createTable() {
        db.beginTransaction();
        try {
            db.execSQL("create table student(" +
                    "studentID integer PRIMARY KEY autoincrement," +
                    "name text," +
                    "email text," +
                    "date text)");

            db.execSQL("insert into student(name, email, date) values('vo quang duc', 'duc@gmail.com', '22-05-2001')");
            db.execSQL("insert into student(name, email, date) values('pham thanh ha', 'ha@gmail.com', '21-08-2001')");
            db.execSQL("insert into student(name, email, date) values('nguyen van a', 'a@gmail.com', '19-05-2001')");

            db.setTransactionSuccessful();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    SQLiteDatabase db;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list_view);

        // Open DB

        String path = getFilesDir() + "/mydb";
        try {
            db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        createTable();

        findViewById(R.id.button_insert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InsertActivity.class);
                startActivity(intent);

            }
        });

        findViewById(R.id.button_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.beginTransaction();
                try {
//                    db.execSQL("update tblAMIGO set phone='555-0000' where name='AAA'");

                    ContentValues cv = new ContentValues();
                    cv.put("name", "Maria");

                    long ret = db.update("tblAMIGO", cv, "recID > 9 and recID < 16", null);
                    Log.v("TAG", "ret = " + ret);

                    db.setTransactionSuccessful();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    db.endTransaction();
                }
            }
        });

        findViewById(R.id.button_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.beginTransaction();
                try {
//                    db.execSQL("delete from tblAMIGO where recID<5");

                    long ret = db.delete("tblAMIGO", "recID > 9 and recID < 16", null);
                    Log.v("TAG", "ret = " + ret);

                    db.setTransactionSuccessful();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    db.endTransaction();
                }
            }
        });

        findViewById(R.id.button_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] columns = {"studentID", "name", "email", "date"};
                Cursor cs = db.query("student", columns,
                        null, null, null, null ,null);

                Log.v("TAG", "# records: " + cs.getCount());

                cs.moveToPosition(-1);
                while (cs.moveToNext()) {
                    int recID = cs.getInt(0);
                    String name = cs.getString(1);
                    String phone = cs.getString(2);

                    Log.v("TAG", recID + " --- " +name + " --- " + phone);
                }

                ItemAdapter adapter = new ItemAdapter(cs);
                listView.setAdapter(adapter);
            }
        });
    }


    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }
}