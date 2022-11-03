package com.nonogram.engine;

public abstract class Sound {
    public Sound(String name, String audioPath){
        _id = name;
        _audioPath = audioPath;
    }

    protected String _id;
    protected String _audioPath;
}
