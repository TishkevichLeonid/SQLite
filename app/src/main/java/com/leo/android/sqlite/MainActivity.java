package com.leo.android.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mAddBtn, mReadBtn, mClearBtn;
    private EditText mNameText, mEmailText;
    private DBHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDBHelper = new DBHelper(this);

        mNameText = (EditText) findViewById(R.id.Name);
        mEmailText = (EditText) findViewById(R.id.Email);

        mAddBtn = (Button) findViewById(R.id.Add_Button);
        mAddBtn.setOnClickListener(this);

        mReadBtn = (Button) findViewById(R.id.Read_Button);
        mReadBtn.setOnClickListener(this);

        mClearBtn = (Button) findViewById(R.id.Clear_Button);
        mClearBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        SQLiteDatabase database = mDBHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        String name = mNameText.getText().toString();
        String email = mEmailText.getText().toString();

        switch (v.getId()){

            case R.id.Add_Button:
                contentValues.put(DBHelper.KEY_NAME, name);
                contentValues.put(DBHelper.KEY_MAIL, email);

                database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);
                break;
            case R.id.Read_Button:
                Cursor cursor = database.query(DBHelper.TABLE_CONTACTS, null, null, null, null, null, null);

                if (cursor.moveToFirst()){
                    int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                    int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
                    int mailIndex = cursor.getColumnIndex(DBHelper.KEY_MAIL);

                    do {
                        Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                                " name = " + cursor.getString(nameIndex) +
                                ", email = " + cursor.getString(mailIndex));
                    } while (cursor.moveToNext());

                } else Log.d("mLog", "0 rows");

                cursor.close();
                break;
            case R.id.Clear_Button:
                database.delete(DBHelper.TABLE_CONTACTS, null, null);
                break;

        }

        mDBHelper.close();

    }
}
