package com.nanogram.pcengine;

import com.nanogram.engine.Font;

import java.awt.FontFormatException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class PCFont implements Font {

    public PCFont(String p, int s, boolean b) {
        _filePath = p;
        _size = s;
        _bold = b;
        InputStream is = null;
        try {
            is = new FileInputStream(_filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            _baseFont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(_bold)
            _baseFont = _baseFont.deriveFont(java.awt.Font.BOLD, _size);
        else
            _baseFont = _baseFont.deriveFont(java.awt.Font.PLAIN, _size);
    }

    @Override
    public int getSize() {
        return _baseFont.getSize();
    }

    @Override
    public boolean isBold() {
        return _baseFont.isBold();
    }

    //VARIABLES
    public java.awt.Font _baseFont;
    String _filePath;
    int _size;
    boolean _bold;
}
