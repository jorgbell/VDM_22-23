package com.nonogram.androidengine;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.drawable.Icon;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.graphics.drawable.IconCompat;

import com.nonogram.engine.NotificationMngr;

public class AndroidNotificationManager implements NotificationMngr {
    public AndroidNotificationManager(Context c) {
        _context = c;
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

    public void setIcon(int icon){
        _smallIcon = icon;
    }

    @Override
    public void sendNotification(String title, String contentText, boolean autoCancel, int daysToNotify) {
          AndroidNotification aN = new AndroidNotification(_context, CHANNEL_ID,_smallIcon, daysToNotify);
          aN.send(title, contentText, autoCancel);
    }

    Context _context;
    String CHANNEL_ID = "My Notification";
    int _smallIcon;

}
