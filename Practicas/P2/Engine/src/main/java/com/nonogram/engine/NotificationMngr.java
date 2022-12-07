package com.nonogram.engine;

public interface NotificationMngr {
    void sendNotification(String title, String contentText, boolean autoCancel, int daysToNotify);
}
