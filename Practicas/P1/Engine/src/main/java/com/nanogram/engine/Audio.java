package com.nanogram.engine;

public interface Audio {
    Sound newSound(String fileName);
    Sound playSound(String id);
}