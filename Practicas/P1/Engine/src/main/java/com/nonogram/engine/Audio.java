package com.nonogram.engine;

public interface Audio {
    public Sound newSound(String fileName);
    public Sound playSound(String id);
    Sound getSound(String id);
    public void setPath(String audioPath);
}
