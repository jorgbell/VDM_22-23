package com.nonogram.androidlauncher;

import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.nonogram.androidengine.AndroidEngine;

public class FirebaseMang extends FirebaseMessagingService {

    public FirebaseMang(AppCompatActivity activity, AndroidEngine e){
        _myEngine = e;
        Intent intent = new Intent(activity.getBaseContext(), AndroidLauncher.class);
        intent.setAction("Action");
        intent.putExtra("key", "Life");
        PendingIntent pendingIntent = PendingIntent.getActivity(activity,0,intent, PendingIntent.FLAG_UPDATE_CURRENT);
        _myEngine.getAndroidNotificationManager().setOpenAppIntent(pendingIntent);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
//        // Check if message contains a notification payload.
//        if (remoteMessage.getNotification() != null) {
//            Log.d("tag", "Message Notification Body: " + remoteMessage.getNotification().getBody());
//            _myEngine.getAndroidNotificationManager().createNotification(remoteMessage.getNotification().getTitle(),
//                    remoteMessage.getNotification().getBody(),
//                    true);
//            _myEngine.getAndroidNotificationManager().send(1);
//        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }


    AndroidEngine _myEngine;
}
