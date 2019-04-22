package com.example.rui.mystock;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    private StockParameterDatabaseHelper stockParaDbaseHelper;
    private int priceRiseSwitchStatus;
    private int priceFallSwitchStatus;
    private int priceRiseAmountSwitchStatus;
    private int priceFallAmountSwitchStatus;
    private int buy1SwitchStatus;

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

        Switch priceRiseSwitch = (Switch) findViewById(R.id.price_rise_switch);
        Switch priceFallSwitch = (Switch) findViewById(R.id.price_fall_switch);
        Switch priceRiseAmountSwitch = (Switch) findViewById(R.id.price_rise_amount_switch);
        Switch priceFallAmountSwitch = (Switch) findViewById(R.id.price_fall_amount_switch);
        Switch buy1Switch = (Switch) findViewById(R.id.buy1_switch);

        Cursor cursor =stockParameterDB.query("StockParameter", null, "StockID = ?",
                new String[]{intent.getStringExtra("stockID")}, null, null, null);
        if (cursor.moveToFirst()) {
            priceRise.setText(String.valueOf(cursor.getDouble(cursor.getColumnIndex("Rise"))));
            priceFall.setText(String.valueOf(cursor.getDouble(cursor.getColumnIndex("Fall"))));
            priceRiseAmount.setText(String.valueOf(cursor.getDouble(cursor.getColumnIndex("RiseAmount"))));
            priceFallAmount.setText(String.valueOf(0 - cursor.getDouble(cursor.getColumnIndex("FallAmount"))));
            buy1Set.setText(String.valueOf(cursor.getLong(cursor.getColumnIndex("Buy1Value"))));

            if (cursor.getInt(cursor.getColumnIndex("RiseSwitch")) == 1) {
                priceRiseSwitch.setChecked(true);
                priceRiseSwitchStatus = 1;
            } else {
                priceRiseSwitch.setChecked(false);
                priceRiseSwitchStatus = 0;
            }

            if (cursor.getInt(cursor.getColumnIndex("FallSwitch")) == 1) {
                priceFallSwitch.setChecked(true);
                priceFallSwitchStatus = 1;
            } else {
                priceFallSwitch.setChecked(false);
                priceFallSwitchStatus = 0;
            }

            if (cursor.getInt(cursor.getColumnIndex("RiseAmountSwitch")) == 1) {
                priceRiseAmountSwitch.setChecked(true);
                priceRiseAmountSwitchStatus = 1;
            } else {
                priceRiseAmountSwitch.setChecked(false);
                priceRiseAmountSwitchStatus = 0;
            }

            if (cursor.getInt(cursor.getColumnIndex("FallAmountSwitch")) == 1) {
                priceFallAmountSwitch.setChecked(true);
                priceFallAmountSwitchStatus = 1;
            } else {
                priceFallAmountSwitch.setChecked(false);
                priceFallAmountSwitchStatus = 0;
            }

            if (cursor.getInt(cursor.getColumnIndex("Buy1ValueSwitch")) == 1) {
                buy1Switch.setChecked(true);
                buy1SwitchStatus = 1;
            } else {
                buy1Switch.setChecked(false);
                buy1SwitchStatus = 0;
            }

        }

        priceRiseSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    priceRiseSwitchStatus = 1;
                } else {
                    priceRiseSwitchStatus = 0;
                }
            }
        });

        priceFallSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    priceFallSwitchStatus = 1;
                } else {
                    priceFallSwitchStatus = 0;
                }
            }
        });

        priceRiseAmountSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    priceRiseAmountSwitchStatus = 1;
                } else {
                    priceRiseAmountSwitchStatus = 0;
                }
            }
        });

        priceFallAmountSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    priceFallAmountSwitchStatus = 1;
                } else {
                    priceFallAmountSwitchStatus = 0;
                }
            }
        });

        buy1Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buy1SwitchStatus = 1;
                } else {
                    buy1SwitchStatus = 0;
                }
            }
        });

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

        if (!TextUtils.isEmpty(priceRise.getText())
                && !TextUtils.isEmpty(priceFall.getText())
                && !TextUtils.isEmpty(priceRiseAmount.getText())
                && !TextUtils.isEmpty(priceFallAmount.getText())
                && !TextUtils.isEmpty(buy1Set.getText())) {
            values.put("Rise", Double.valueOf(priceRise.getText().toString()));
            values.put("Fall", Double.valueOf(priceFall.getText().toString()));
            values.put("RiseAmount", Double.valueOf(priceRiseAmount.getText().toString()));
            values.put("FallAmount", 0 - Double.valueOf(priceFallAmount.getText().toString()));
            values.put("Buy1Value", Integer.valueOf(buy1Set.getText().toString()));

            values.put("RiseSwitch", priceRiseSwitchStatus);
            values.put("FallSwitch", priceFallSwitchStatus);
            values.put("RiseAmountSwitch", priceRiseAmountSwitchStatus);
            values.put("FallAmountSwitch", priceFallAmountSwitchStatus);
            values.put("Buy1ValueSwitch", buy1SwitchStatus);

            stockParameterDB.update("StockParameter", values, "StockID = ?",
                    new String[]{intent.getStringExtra("stockID")});
            values.clear();
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "请输入数值", Toast.LENGTH_LONG).show();
        }

        stockParameterDB.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
