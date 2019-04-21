package com.example.rui.mystock;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    private StockParameterDatabaseHelper stockParaDbaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);

        stockParaDbaseHelper = new StockParameterDatabaseHelper(this, "StockParameter.db", null, 1);
        SQLiteDatabase stockParameterDB = stockParaDbaseHelper.getWritableDatabase();

        Intent intent = getIntent();

        TextView nowPrice = (TextView) findViewById(R.id.stockNowPrice);
        nowPrice.setText(intent.getStringExtra("nowPrice"));

        TextView riseAmount = (TextView) findViewById(R.id.riseAmount);
        riseAmount.setText(intent.getStringExtra("amount"));

        TextView buy1Num = (TextView) findViewById(R.id.buy1Num);
        buy1Num.setText(intent.getStringExtra("buy1"));

        EditText priceRise = (EditText) findViewById(R.id.price_rise);
        EditText priceFall = (EditText) findViewById(R.id.price_fall);
        EditText priceRiseAmount = (EditText) findViewById(R.id.price_rise_amount);
        EditText priceFallAmount = (EditText) findViewById(R.id.price_fall_amount);
        EditText buy1Set = (EditText) findViewById(R.id.buy1_set);

        Cursor cursor =stockParameterDB.query("StockParameter", null, "StockID = ?",
                new String[]{intent.getStringExtra("stockID")}, null, null, null);
        if (cursor.moveToFirst()) {
            priceRise.setText(String.valueOf(cursor.getDouble(cursor.getColumnIndex("Rise"))));
            priceFall.setText(String.valueOf(cursor.getDouble(cursor.getColumnIndex("Fall"))));
            priceRiseAmount.setText(String.valueOf(cursor.getDouble(cursor.getColumnIndex("RiseAmount"))));
            priceFallAmount.setText(String.valueOf(cursor.getDouble(cursor.getColumnIndex("FallAmount"))));
            buy1Set.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex("Buy1Value"))));
        }
        stockParameterDB.close();
    }

    public void saveParameter(View view) {
        SQLiteDatabase stockParameterDB = stockParaDbaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        Intent intent = getIntent();

        EditText priceRise = (EditText) findViewById(R.id.price_rise);
        EditText priceFall = (EditText) findViewById(R.id.price_fall);
        EditText priceRiseAmount = (EditText) findViewById(R.id.price_rise_amount);
        EditText priceFallAmount = (EditText) findViewById(R.id.price_fall_amount);
        EditText buy1Set = (EditText) findViewById(R.id.buy1_set);

        values.put("Rise", Double.valueOf(priceRise.getText().toString()));
        values.put("Fall", Double.valueOf(priceFall.getText().toString()));
        values.put("RiseAmount", Double.valueOf(priceRiseAmount.getText().toString()));
        values.put("FallAmount", Double.valueOf(priceFallAmount.getText().toString()));
        values.put("Buy1Value", Integer.valueOf(buy1Set.getText().toString()));

        stockParameterDB.update("StockParameter", values, "StockID = ?",
                new String[]{intent.getStringExtra("stockID")});
        stockParameterDB.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
