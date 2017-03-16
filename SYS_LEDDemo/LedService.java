package com.android.server;

import android.os.ILedService;

public class LedService extends ILedService.Stub {

	private static final String TAG = "LedService";
	
	public int ledCtrl(int which, int status) throw  android.os.RemoteException {
		return native_ledCtrl(which, status);
	}

	public LedService() {
		native_ledOpen();
	}
	
	public static native int native_ledOpen();
	public static native void native_ledClose();
	public static native int native_ledCtrl(int which, int status);
}

