
package com.example.mytext;

import com.example.mytext.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;

public class MainActivity extends Activity {
    
    private boolean ledOn = false;
    private Button button = (Button) findViewById(R.id.BUTTON);
    private CheckBox CheckBoxLED1 = null;
    private CheckBox CheckBoxLED2 = null;
    private CheckBox CheckBoxLED3 = null;
    private CheckBox CheckBoxLED4 = null;
    
    class MyButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            ledOn = !ledOn;
            if (ledOn) {
                button.setText("ALL ON");
                
            }
        }
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
