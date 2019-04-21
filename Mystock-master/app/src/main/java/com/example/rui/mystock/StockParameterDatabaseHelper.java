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
            + "StockID text primary key, "
            + "Rise real, "
            + "RiseSwitch integer, "
            + "Fall real, "
            + "FallSwitch integer, "
            + "RiseAmount real, "
            + "RiseAmountSwitch integer, "
            + "FallAmount real, "
            + "FallAmountSwitch integer, "
            + "Buy1Value integer, "
            + "Buy1ValueSwitch integer, "
            + "AverageDiffRise real, "
            + "ADRiseSwitch integer, "
            + "AverageDiffFall real, "
            + "ADFallWwitch integer)";

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
