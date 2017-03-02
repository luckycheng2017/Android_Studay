
package com.example.mytext;

import com.example.mytext.R;

import java.util.TreeMap;

import javax.security.auth.PrivateCredentialPermission;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class MainActivity extends Activity {
    
    private boolean ledOn = false;
    private Button button = null;
    private CheckBox checkBoxLED1 = null;
    private CheckBox checkBoxLED2 = null;
    private CheckBox checkBoxLED3 = null;
    private CheckBox checkBoxLED4 = null;
    
    class MyButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            ledOn = !ledOn;
            if (ledOn) {
                button.setText("ALL OFF");
                checkBoxLED1.setChecked(false);
                checkBoxLED2.setChecked(false);
                checkBoxLED3.setChecked(false);
                checkBoxLED4.setChecked(false);
            } else {
                button.setText("ALL ON");
                checkBoxLED1.setChecked(true);
                checkBoxLED2.setChecked(true);
                checkBoxLED3.setChecked(true);
                checkBoxLED4.setChecked(true);
            }
        }
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.BUTTON);
        
        checkBoxLED1 = (CheckBox) findViewById(R.id.LED1);
        checkBoxLED2 = (CheckBox) findViewById(R.id.LED2);
        checkBoxLED3 = (CheckBox) findViewById(R.id.LED3);
        checkBoxLED4 = (CheckBox) findViewById(R.id.LED4);
        
        button.setOnClickListener(new MyButtonClickListener());
    }
    
    private void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        
        switch (view.getId()) {
            case R.id.LED1:
                if (checked) {
                    Toast.makeText(getApplicationContext(), "LED1 ON", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "LED1 OFF", Toast.LENGTH_SHORT).show();
                }
                break;
                
            case R.id.LED2:
                if (checked) {
                    Toast.makeText(getApplicationContext(), "LED2 ON", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "LED2 OFF", Toast.LENGTH_SHORT).show();
                }
                break;
                
            case R.id.LED3:
                if (checked) {
                    Toast.makeText(getApplicationContext(), "LED3 ON", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "LED3 OFF", Toast.LENGTH_SHORT).show();
                }
                break;
                
            case R.id.LED4:
                if (checked) {
                    Toast.makeText(getApplicationContext(), "LED4 ON", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "LED4 OFF", Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    private int ledCtrl(int which, boolean flag) {
        int ret = 0;
        return ret;
    }
    
    private int ledOpen() {
        int ret = 0;
        return ret;
    }
    
    private void ledClose() {
        
    }
}
