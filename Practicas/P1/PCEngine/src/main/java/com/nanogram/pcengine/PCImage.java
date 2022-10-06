package com.nanogram.pcengine;

import com.nanogram.engine.Image;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PCImage implements Image {
    PCImage(String path){
        try {
            _baseImage = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getWidth() {
        return _baseImage.getWidth(null);
    }

    @Override
    public int getHeight() {
        return _baseImage.getHeight(null);
    }


    public java.awt.Image _baseImage;
}
