package com.youtube.sorcjc.tabdeportes;

import android.app.Application;

import com.onesignal.OneSignal;

public class Global extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize OneSignal
        OneSignal.startInit(this).init();
    }
}