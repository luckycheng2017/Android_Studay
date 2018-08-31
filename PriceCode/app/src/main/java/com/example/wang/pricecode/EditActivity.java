package com.example.wang.pricecode;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by wang on 18-8-29.
 */

public class EditActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;
    private EditText mEditText;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        final Bundle extras = getIntent().getExtras();

        dbHelper = new MyDatabaseHelper(this, "priceCode.db", null, 1);

        mEditText = (EditText) findViewById(R.id.edit_text);

        mButton = (Button) findViewById(R.id.button_save);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (extras != null) {
                    if (extras.getBoolean("update") == false) {
                        String result = extras.getString("result");
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("SequenceCode", Integer.valueOf(result));
                        values.put("price", Float.valueOf(mEditText.getText().toString()));
                        db.insert("priceCode", null, values);
                        values.clear();
                    } else {
                        String result = extras.getString("result");
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("price", Float.valueOf(mEditText.getText().toString()));
                        db.update("priceCode", values, "SequenceCode = ?", new String[]{ result });
                    }
                    Toast.makeText(EditActivity.this, "保存成功", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
