package com.nonogram.androidengine;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;

import com.nonogram.engine.Sound;

import java.io.IOException;

public class AndroidSound extends Sound {
    public AndroidSound(String name, String audioPath, AssetManager ass) {
        super(name, audioPath);
        _clip = new MediaPlayer();
        _clip.reset();
        try{
            AssetFileDescriptor afd = ass.openFd(_audioPath + _id);
            _clip.setDataSource(afd.getFileDescriptor(),
                    afd.getStartOffset(), afd.getLength());
            _clip.prepare();
            _clip.setLooping(true);

        }catch(IOException e){
            System.err.println("Couldn't load audio file");
            e.printStackTrace();
        }
    }

    MediaPlayer _clip;
}
