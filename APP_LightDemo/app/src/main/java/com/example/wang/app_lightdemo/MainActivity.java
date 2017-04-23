package com.example.wang.app_lightdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    private Button mLightButton = null;
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


        mLightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashing = !flashing;

                if (flashing) {
                    mLightButton.setText("Stop Flashing the Light");
                } else {
                    mLightButton.setText("Flashing Light at 20S");
                }

                mLightHander.postDelayed(mLightRunnable, 20000);
            }
        });
    }
}
