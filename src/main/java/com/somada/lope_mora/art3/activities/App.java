package com.somada.lope_mora.art3.activities;

import android.app.Application;

import com.estimote.proximity_sdk.api.EstimoteCloudCredentials;
import com.somada.lope_mora.art3.estimote.NotificationsManager;

public class App extends Application {

    public EstimoteCloudCredentials cloudCredentials =
            new EstimoteCloudCredentials("teste-9uh", "8eb34dacdcbb41e9d8f481be7f1aee0b");
    private NotificationsManager notificationsManager;

    public void enableBeaconNotifications(){
        notificationsManager = new NotificationsManager(this);
        notificationsManager.startMonitoring();
    }
}
