package com.example.wang.aidl_service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Wang on 2017/6/15.
 */

public class RemoteService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("TAG", "onBind: ");
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    private final IRemoteService.Stub mBinder = new IRemoteService.Stub() {
        @Override
        public int add(int num1, int num2) throws RemoteException {
            Log.d("TAG", "add: " + num1 + " " + num2);
            return num1 + num2;
        }
    };
}
