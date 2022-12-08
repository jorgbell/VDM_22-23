package com.nonogram.engine;

public interface NotificationMngr {
    MyNotification createNotification(String title, String contentText,boolean autoCancel);
    void send(int id);
    void setEngine(Engine e);
}
