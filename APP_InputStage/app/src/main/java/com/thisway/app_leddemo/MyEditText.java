package com.thisway.app_leddemo;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;

/**
 * Created by wang on 17-9-20.
 */

public class MyEditText extends EditText {
    private static final String TAG = MyEditText.class.getSimpleName();

    public MyEditText(Context context) {
        super(context);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public String getKeyStatus(KeyEvent keyEvent) {
        if (keyEvent.getAction() == KeyEvent.ACTION_DOWN)
            return "down";
        else
            return "up";
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent keyEvent) {
        Log.d(TAG, "onKeyPreIme " + keyCode + " " + getKeyStatus(keyEvent));
        return super.onKeyPreIme(keyCode, keyEvent);
    }

    public KeyEvent createAnotherKeyEvent(KeyEvent keyEvent) {
        return new KeyEvent(keyEvent.getDownTime(), keyEvent.getEventTime(), keyEvent.getAction(), keyEvent.getKeyCode() + 1, keyEvent.getRepeatCount(), keyEvent.getMetaState(), keyEvent.getDeviceId(), keyEvent.getScanCode());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
        Log.d(TAG, "MyEditText onKeyDown " + keyCode + " " + getKeyStatus(keyEvent));
        return super.onKeyDown(keyCode, keyEvent);
//        return super.onKeyDown(keyCode+1, createAnotherKeyEvent(event));
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent keyEvent) {
        Log.d(TAG, "MyEditText onKeyUp " + keyCode + " " + getKeyStatus(keyEvent));
        return super.onKeyUp(keyCode, keyEvent);
    }
}
