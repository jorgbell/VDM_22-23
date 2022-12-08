package com.nonogram.androidengine;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.nonogram.engine.Engine;
import com.nonogram.engine.MyNotification;
import com.nonogram.engine.NotificationMngr;

public class AndroidNotificationManager implements NotificationMngr{
    public AndroidNotificationManager(Context context) {
        super();
        _context = context;
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "My Notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            String description = "My Channel Description";
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notmngr = _context.getSystemService(NotificationManager.class);
            notmngr.createNotificationChannel(channel);
        }
    }
    public AndroidNotificationManager(){};

    @Override
    public MyNotification createNotification(String title, String contentText, boolean autoCancel) {
        aN = new AndroidNotification(_context, CHANNEL_ID, _smallIcon);
        aN.opensApp =  openAppIntent;
        aN.create(title, contentText, autoCancel);
        return aN;
    }

    @Override
    public void send(int id) {
        //busca en la lista de notificaciones creadas y hace el send de la que tiene el id indicado
    }

    @Override
    public void setEngine(Engine e) {
        _myEngine = e;
    }

    public void setOpenAppIntent(PendingIntent intent) {
        openAppIntent = intent;
    }



    AndroidNotification aN;
    Context _context;
    String CHANNEL_ID = "My Notification";
    int _smallIcon;
    PendingIntent openAppIntent;
    Engine _myEngine;


}
