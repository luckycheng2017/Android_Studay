package com.example.wang.pricecode.zxing.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.example.wang.pricecode.EditActivity;
import com.example.wang.pricecode.MyDatabaseHelper;
import com.example.wang.pricecode.R;
import com.example.wang.pricecode.zxing.decode.DecodeThread;

public class ResultActivity extends Activity {

	private ImageView mResultImage;
	private TextView mResultText;

	private Button mAddButton;
    private Button mModifyButton;
	private Button mDeleteButton;
    private TextView mPriceResult;

    private MyDatabaseHelper dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		final Bundle extras = getIntent().getExtras();

		mResultImage = (ImageView) findViewById(R.id.result_image);
		mResultText = (TextView) findViewById(R.id.result_text);

        mPriceResult = (TextView) findViewById(R.id.result_price);

        dbHelper = new MyDatabaseHelper(this, "priceCode.db", null, 1);

		mAddButton = (Button) findViewById(R.id.button_add);
		mAddButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
                if (extras != null) {
                    String result = extras.getString("result");
                    Bundle bundle = new Bundle();
                    bundle.putString("result", result);
                    bundle.putBoolean("update", false);
                    startActivity(new Intent(ResultActivity.this, EditActivity.class).putExtras(bundle));
                }
			}
		});

        mModifyButton = (Button) findViewById(R.id.button_modify);
        mModifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (extras != null) {
                    String result = extras.getString("result");
                    Bundle bundle = new Bundle();
                    bundle.putString("result", result);
                    bundle.putBoolean("update", true);
                    startActivity(new Intent(ResultActivity.this, EditActivity.class).putExtras(bundle));
                }
            }
        });

		mDeleteButton = (Button) findViewById(R.id.button_delete);

		if (null != extras) {
			int width = extras.getInt("width");
			int height = extras.getInt("height");

			LayoutParams lps = new LayoutParams(width, height);
			lps.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
			lps.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
			lps.rightMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
			
			mResultImage.setLayoutParams(lps);

			String result = extras.getString("result");
			mResultText.setText("序列码：" + result);

			mPriceResult.setText("价格：¥");

            SQLiteDatabase db = dbHelper.getWritableDatabase();
			Cursor cursor = db.query("priceCode", null, null, null, null, null, null);
			if (cursor.getCount() != 0) {
				if (cursor.moveToFirst()) {
					do {
						String sequenceCode = cursor.getString(cursor.getColumnIndex("SequenceCode"));
						float price = cursor.getFloat(cursor.getColumnIndex("price"));
						if (result.equals(sequenceCode)) {
							mPriceResult.setText("价格：¥" + String.valueOf(price));
							mAddButton.setEnabled(false);
							mModifyButton.setEnabled(true);
                            break;
						} else {
							mAddButton.setEnabled(true);
							mModifyButton.setEnabled(false);
						}
					} while (cursor.moveToNext());
				}
			} else {
				mModifyButton.setEnabled(false);
			}

			Bitmap barcode = null;
			byte[] compressedBitmap = extras.getByteArray(DecodeThread.BARCODE_BITMAP);
			if (compressedBitmap != null) {
				barcode = BitmapFactory.decodeByteArray(compressedBitmap, 0, compressedBitmap.length, null);
				// Mutable copy:
				barcode = barcode.copy(Bitmap.Config.RGB_565, true);
			}

			mResultImage.setImageBitmap(barcode);
		}
	}
}
