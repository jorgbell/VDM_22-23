package com.nonogram.androidengine;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.graphics.drawable.IconCompat;

public class AndroidNotification {
    public AndroidNotification(Context context, String channel, int icon, int daysToNotify){
        _context = context;
        CHANNEL_ID = channel;
        _smallIcon = icon;
        _daysToNotify = daysToNotify;
    }

    public void send(String title, String contentText, boolean autoCancel){

        NotificationCompat.Builder builder = new NotificationCompat.Builder(_context, CHANNEL_ID);
        builder.setContentText(title);
        builder.setContentText(contentText);

        builder.setSmallIcon(R.drawable.ic_stat_onesignal_default);
        builder.setAutoCancel(autoCancel);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if(_daysToNotify >0){
            Intent intent = new Intent(_context, this.getClass());
            PendingIntent contentIntent = PendingIntent.getActivity(_context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

            builder.setContentIntent(contentIntent);
        }

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(_context);
        managerCompat.notify(1, builder.build());
    }

    Context _context;
    String CHANNEL_ID;
    int _smallIcon;
    int _daysToNotify = -1;
}
