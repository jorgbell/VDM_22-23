package com.nonogram.androidengine;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;

import com.nonogram.engine.Engine;
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

    @Override
    public void setEngine(Engine e) {
        _myEngine = e;
    }
    public String get_CHANNEL_ID(){return CHANNEL_ID;}

    Context _context;
    String CHANNEL_ID = "My Notification";
    Engine _myEngine;


}
