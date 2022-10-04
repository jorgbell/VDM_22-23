package com.nanogram.pcengine;

import com.nanogram.engine.Image;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PCImage implements Image {
    PCImage(String path) throws IOException {
        _baseImage = ImageIO.read(new File(path));
    }

    @Override
    public int getWidth() {
        return _baseImage.getWidth(null);
    }

    @Override
    public int getHeight() {
        return _baseImage.getHeight(null);
    }

    //VARIABLES
    int _width;
    int _height;
    public java.awt.Image _baseImage;
}
