package com.example.wang.app_lightdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.os.Handler;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private Button mLightButton = null;
    private SeekBar mBacklightSeekBar = null;
    private boolean flashing = false;
    final private int LED_NOTIFICATION_ID = 123;

    private Handler mLightHander = new Handler();
    private LightRunnable mLightRunnable = new LightRunnable();

    class LightRunnable implements Runnable {
        @Override
        public void run() {
            if (flashing) {
                FlashLight();
            } else {
                ClearLed();
            }
        }
    }

    private void FlashLight()
    {
        NotificationManager nm = ( NotificationManager ) getSystemService( NOTIFICATION_SERVICE );
        Notification notif = new Notification();
        notif.ledARGB = 0xFF0000ff;
        notif.flags = Notification.FLAG_SHOW_LIGHTS;
        notif.ledOnMS = 200;
        notif.ledOffMS = 200;
        nm.notify(LED_NOTIFICATION_ID, notif);
    }

    private void ClearLed() {
        NotificationManager nm = ( NotificationManager ) getSystemService( NOTIFICATION_SERVICE );
        nm.cancel(LED_NOTIFICATION_ID);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLightButton = (Button) findViewById(R.id.button);
        mBacklightSeekBar = (SeekBar) findViewById(R.id.seekbar);

        try {
            Settings.System.putInt(getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE,
                    Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);

            int brightness = android.provider.Settings.System.getInt(getContentResolver(),
                    android.provider.Settings.System.SCREEN_BRIGHTNESS);
            mBacklightSeekBar.setProgress(brightness*100/255);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        mLightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashing = !flashing;

                if (flashing) {
                    mLightButton.setText("Stop Flashing the Light");
                } else {
                    mLightButton.setText("Flashing Light at 65S");
                }

                mLightHander.postDelayed(mLightRunnable, 65000);
            }
        });

        mBacklightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int brightness = mBacklightSeekBar.getProgress();
                brightness = brightness * 255 / 100;

                android.provider.Settings.System.putInt(getContentResolver(),
                        android.provider.Settings.System.SCREEN_BRIGHTNESS,
                        brightness);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar){

            }
        });
    }
}
