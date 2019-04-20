package com.example.rui.mystock;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by wang on 19-4-17.
 */

public class StockParameterDatabaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_STOCK_PAR = "create table StockParameter ("
            + "StockID integer primary key autoincrement, "
            + "Rise real, "
            + "RiseSwitch blob, "
            + "Fall real, "
            + "FallSwitch blob, "
            + "RiseAmount real, "
            + "RiseAmountSwitch blob, "
            + "FallAmount real, "
            + "FallAmountSwitch blob, "
            + "Buy1Value integer, "
            + "Buy1ValueSwitch blob, "
            + "AverageDiffRise real, "
            + "ADRiseSwitch blob, "
            + "AverageDiffFall real, "
            + "ADFallWwitch blob)";

    private Context mContext;

    public StockParameterDatabaseHelper(Context context, String name,
                                        SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STOCK_PAR);
        Toast.makeText(mContext, "Create succeeded", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
