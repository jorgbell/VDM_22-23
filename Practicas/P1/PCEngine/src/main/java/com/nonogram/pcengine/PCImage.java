package com.nonogram.pcengine;

import com.nonogram.engine.AbstractImage;
import com.nonogram.engine.Image;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PCImage extends AbstractImage {
    PCImage(String path){
        super(path);
        try {
            _baseImage = ImageIO.read(new File(_path));
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
