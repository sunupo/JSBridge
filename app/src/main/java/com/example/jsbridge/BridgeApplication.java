package com.example.jsbridge;

import android.app.Application;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class BridgeApplication extends Application {
    @Override
    public void onCreate() {
        JSBridge.init();
        super.onCreate();
    }
}
