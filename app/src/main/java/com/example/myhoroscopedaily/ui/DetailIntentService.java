package com.example.myhoroscopedaily.ui;

import android.app.IntentService;
import android.content.Intent;
import com.example.myhoroscopedaily.ui.Detail;


public class DetailIntentService extends IntentService {

    public DetailIntentService() {
        super("DetailIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // COMPLETED (12) Get the action from the Intent that started this Service
        String action = intent.getAction();

//  TODO (11) Override onHandleIntent
//      TODO (12) Get the action from the Intent that started this Service
//      TODO (13) Call ReminderTasks.executeTask and pass in the action to be performed
        // COMPLETED (13) Call ReminderTasks.executeTask and pass in the action to be performed
        Detail.executeTask(this, action);
    }
}
