package com.example.wang.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.wang.hardlibrary.HardControl;

public class MainActivity extends AppCompatActivity {

    private boolean ledon = false;
    private Button button = null;
    private CheckBox checkBoxLed1 = null;
    private CheckBox checkBoxLed2 = null;
    private CheckBox checkBoxLed3 = null;
    private CheckBox checkBoxLed4 = null;

    class MyButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            HardControl hardControl = new HardControl();

            ledon = !ledon;
            if (ledon) {
                button.setText("ALL OFF");
                checkBoxLed1.setChecked(true);
                checkBoxLed2.setChecked(true);
                checkBoxLed3.setChecked(true);
                checkBoxLed4.setChecked(true);

                for (int i = 0; i < 4; i++) {
                    hardControl.ledCtrl(i, 1);
                }
            }
            else {
                button.setText("ALL ON");
                checkBoxLed1.setChecked(false);
                checkBoxLed2.setChecked(false);
                checkBoxLed3.setChecked(false);
                checkBoxLed4.setChecked(false);

                for (int i = 0; i < 4; i++) {
                    hardControl.ledCtrl(i, 0);
                }
            }
        }
    }

    public void onCheckboxClicked(View view) {
        HardControl.getInstance().ledOpen();

        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.LED1:
                if (checked) {
                    HardControl.getInstance().ledCtrl(0, 1);
                    // Put some meat on the sandwich
                    Toast.makeText(getApplicationContext(), "LED1 on", Toast.LENGTH_SHORT).show();
                }
                else {
                    HardControl.getInstance().ledCtrl(0, 0);
                    // Remove the meat
                    Toast.makeText(getApplicationContext(), "LED1 off", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.LED2:
                if (checked) {
                    HardControl.getInstance().ledCtrl(1, 1);
                    // Put some meat on the sandwich
                    Toast.makeText(getApplicationContext(), "LED2 on", Toast.LENGTH_SHORT).show();
                }
                else {
                    HardControl.getInstance().ledCtrl(1, 0);
                    // Remove the meat
                    Toast.makeText(getApplicationContext(), "LED2 off", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.LED3:
                if (checked) {
                    HardControl.getInstance().ledCtrl(2, 1);
                    // Put some meat on the sandwich
                    Toast.makeText(getApplicationContext(), "LED3 on", Toast.LENGTH_SHORT).show();
                }
                else {
                    HardControl.getInstance().ledCtrl(2, 0);
                    // Remove the meat
                    Toast.makeText(getApplicationContext(), "LED3 off", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.LED4:
                if (checked) {
                    HardControl.getInstance().ledCtrl(3, 1);
                    // Put some meat on the sandwich
                    Toast.makeText(getApplicationContext(), "LED4 on", Toast.LENGTH_SHORT).show();
                }
                else {
                    HardControl.getInstance().ledCtrl(3, 0);
                    // Remove the meat
                    Toast.makeText(getApplicationContext(), "LED4 off", Toast.LENGTH_SHORT).show();
                }
                break;
            // TODO: Veggie sandwich
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HardControl.ledOpen();

        button = (Button) findViewById(R.id.BUTTON);

        checkBoxLed1 = (CheckBox) findViewById(R.id.LED1);
        checkBoxLed2 = (CheckBox) findViewById(R.id.LED2);
        checkBoxLed3 = (CheckBox) findViewById(R.id.LED3);
        checkBoxLed4 = (CheckBox) findViewById(R.id.LED4);

        button.setOnClickListener(new MyButtonListener());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HardControl.getInstance().ledClose();
    }
}
