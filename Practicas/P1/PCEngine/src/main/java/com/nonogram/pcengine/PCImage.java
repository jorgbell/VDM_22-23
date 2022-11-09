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
        if(_baseImage != null)
            return _baseImage.getWidth(null);
        else{
            System.err.println("no se ha creado bien la imagen, error al coger su ancho");
            return -1;
        }
    }

    @Override
    public int getHeight() {
        if(_baseImage != null)
            return _baseImage.getHeight(null);
        else{
            System.err.println("no se ha creado bien la imagen, error al coger su alto");
            return -1;
        }
    }


    public java.awt.Image _baseImage = null;
}
