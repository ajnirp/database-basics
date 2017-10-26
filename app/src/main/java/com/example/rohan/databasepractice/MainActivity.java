package com.example.rohan.databasepractice;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase db = openOrCreateDatabase("MyDatabase", Context.MODE_PRIVATE, null);

        db.execSQL("DROP TABLE IF EXISTS Photos");
        db.execSQL("CREATE TABLE Photos (ID INTEGER, location TEXT, size INTEGER)");
        db.execSQL("INSERT INTO Photos VALUES (1, 'sdcard/p1.jpg', 100)");
        db.execSQL("INSERT INTO Photos VALUES (2, 'sdcard/p2.jpg', 200)");
        db.execSQL("INSERT INTO Photos VALUES (3, 'sdcard/p3.jpg', 300)");
        db.execSQL("INSERT INTO Photos VALUES (4, 'sdcard/p4.jpg', 200)");

        Cursor c = db.rawQuery("SELECT * FROM Photos", null);
        c.moveToFirst();

        for (int i = 0; i < c.getCount(); i++) {
            String str = "";
            for (int j = 0; j < c.getColumnCount(); j++) {
                str = str + c.getString(j) + " ";
            }
            Log.v("tag", str);
            c.moveToNext();
        }

        db.execSQL("DROP TABLE IF EXISTS Tags");
        db.execSQL("CREATE TABLE Tags (ID INTEGER, tag TEXT)");
        db.execSQL("INSERT INTO Tags VALUES (1, 'Old Well')");
        db.execSQL("INSERT INTO Tags VALUES (1, 'UNC')");
        db.execSQL("INSERT INTO Tags VALUES (2, 'Sitterson')");
        db.execSQL("INSERT INTO Tags VALUES (2, 'Building')");
        db.execSQL("INSERT INTO Tags VALUES (3, 'Sky')");
        db.execSQL("INSERT INTO Tags VALUES (4, 'Dining')");
        db.execSQL("INSERT INTO Tags VALUES (4, 'Building')");


    }

    public void find(View v) {
        SQLiteDatabase db = openOrCreateDatabase("MyDatabase", Context.MODE_PRIVATE, null);

        Log.v("tag", "Find button clicked");
        String tag_query = ((EditText) findViewById(R.id.search_tag)).getText().toString();
        String size_query = ((EditText) findViewById(R.id.search_size)).getText().toString();
        String query = "SELECT Photos.ID, location, size, tag FROM Photos, Tags WHERE Photos.ID = Tags.ID";

        if (tag_query.length() > 0) {
            query += " AND tag = '" + tag_query + "'";
        }

        if (size_query.length() > 0) {
            query += " AND size = " + size_query;
        }

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        String str = "";

        for (int i = 0; i < c.getCount(); i++) {
            for (int j = 0; j < c.getColumnCount(); j++) {
                str = str + c.getString(j) + "\n";
            }
            Log.v("tag", str);
            c.moveToNext();
        }

        ((TextView) findViewById(R.id.result_view)).setText(str);
    }
}
