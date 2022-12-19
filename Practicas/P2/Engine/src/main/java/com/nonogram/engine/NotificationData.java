package com.nonogram.engine;

public class NotificationData {
    public NotificationData(String title, String contentText, String biggerText, int delaySeconds){
        _title = title; _contentText = contentText; _biggerText = biggerText; _delaySeconds = delaySeconds;
    }
    public String _title, _contentText, _biggerText;
    public int _delaySeconds;
}
