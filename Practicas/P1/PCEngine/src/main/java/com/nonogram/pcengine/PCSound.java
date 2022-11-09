package com.nonogram.pcengine;

import com.nonogram.engine.Sound;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class PCSound extends Sound {

    public PCSound(String name, String audioPath) {
        super(name, audioPath);
        try {
            _clip = AudioSystem.getClip();
            _clip.open(AudioSystem.getAudioInputStream(new File(_fileName +_id)));
            _clip.loop(Clip.LOOP_CONTINUOUSLY);
            _clip.setFramePosition(0);
            if(_clip.isRunning())
                _clip.stop();

        } catch (Exception e){
            System.err.println("Couldn't load audio file");
            e.printStackTrace();
        }
    }

    Clip _clip;
}
