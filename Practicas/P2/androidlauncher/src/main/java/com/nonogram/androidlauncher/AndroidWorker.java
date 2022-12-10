package com.nonogram.androidlauncher;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class AndroidWorker extends Worker {

    public AndroidWorker(@NonNull Context context, @NonNull WorkerParameters params){
        super(context,params);
    }

    @NonNull
    @Override
    public Result doWork() {

        final String title = getInputData().getString("title");
        final String contentText = getInputData().getString("contentText");
        final String biggerText = getInputData().getString("biggerText");
        final String CHANNEL_ID = getInputData().getString("CHANNEL_ID");
        final boolean autoCancel  = getInputData().getBoolean("autocancel", true);

        createAndSendNotification(title, contentText, biggerText, CHANNEL_ID, autoCancel);
        return Result.success();
    }

    public void createAndSendNotification(String title, String contentText, String biggerText, String CHANNEL_ID, boolean autoCancel){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(com.nonogram.androidengine.R.drawable.ic_stat_onesignal_default)
                .setContentTitle(title)
                .setContentText(contentText)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(biggerText))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(autoCancel);

        Intent intent = new Intent(getApplicationContext(), AndroidLauncher.class);
        intent.putExtra("notification", true);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getApplicationContext());
        managerCompat.notify(1, builder.build());
    }

}
