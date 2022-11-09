package com.nonogram.engine;

public interface Audio {
    public Sound newSound(String fileName);
    public Sound playSound(String id);
    public void pauseSound(String id);
    public void stopAll();
    Sound getSound(String id);
    public void setPath(String audioPath);
}
