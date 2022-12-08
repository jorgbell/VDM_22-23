package com.nonogram.androidengine;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.nonogram.engine.MyNotification;

public class AndroidNotification implements MyNotification {
    public AndroidNotification(Context context, String channel, int icon){
        _context = context;
        CHANNEL_ID = channel;
        _smallIcon = icon;
    }

    @Override
    public void create(String title, String contentText, boolean autoCancel){
        builder = new NotificationCompat.Builder(_context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stat_onesignal_default)
                .setContentTitle(title)
                .setContentText(contentText)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Bigger text"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(autoCancel);
        if(opensApp!=null)builder.setContentIntent(opensApp);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(_context);
        managerCompat.notify(1, builder.build());

    }

    @Override
    public void send(){

    }

    Context _context;
    String CHANNEL_ID;
    int _smallIcon;
    NotificationCompat.Builder builder;
    PendingIntent opensApp = null;

}
