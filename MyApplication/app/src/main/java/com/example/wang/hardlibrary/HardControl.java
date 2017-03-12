package com.example.wang.hardlibrary;

import android.content.Context;

public class HardControl {

    private static HardControl hardControl;

    public static native int ledCtrl(int which, int status);
    public static native int ledOpen();
    public static native void ledClose();

    static {
        try {
            System.loadLibrary("hardcontrol");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public synchronized static HardControl getInstance() {
        if (hardControl == null) {
            hardControl = new HardControl();
        }

        return hardControl;
    }
}