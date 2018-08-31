package com.example.wang.pricecode;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by wang on 18-8-28.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_PRICECODE = "create table priceCode (" +
            "SequenceCode varchar(50) primary key," +
            "price real)";

    private Context mContext;

    public MyDatabaseHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_PRICECODE);
        Toast.makeText(mContext, "Create CREATE_PRICECODE succeeded", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
