package com.nonogram.engine;

public interface MyNotification {
    public void create(String title, String contentText, boolean autoCancel);
    public void send();
}
