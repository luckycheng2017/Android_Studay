package com.example.wang.pricecode;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.wang.pricecode.zxing.activity.CaptureActivity;

public class MainActivity extends AppCompatActivity {

    private Button MyScanButton = null;

    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new MyDatabaseHelper(this, "priceCode.db", null, 1);
        dbHelper.getWritableDatabase();

        MyScanButton = (Button) findViewById(R.id.scan_button);

        MyScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) !=
                        PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                } else {
                    Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
