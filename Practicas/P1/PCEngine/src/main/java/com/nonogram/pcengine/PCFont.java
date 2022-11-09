package com.nonogram.pcengine;

import com.nonogram.engine.AbstractFont;

import java.io.FileInputStream;
import java.io.InputStream;

public class PCFont extends AbstractFont {

    public PCFont(String p, int s, boolean b) {
        super(p,s,b);
        try (InputStream is = new FileInputStream(_filePath)) {
            _baseFont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, is);
            setSize(s);
            setBold(b);
        }
        catch (Exception e) {
            // Ouch. No está.
            System.err.println("Error cargando la fuente: " + e);
        }
    }

    @Override
    public int getSize() {
        return _baseFont.getSize();
    }

    @Override
    public boolean isBold() {
        return _baseFont.isBold();
    }

    @Override
    public void setSize(int size) {
        super.setSize(size);
        _baseFont = _baseFont.deriveFont(java.awt.Font.BOLD, _size);
    }

    @Override
    public void setBold(boolean bold) {
        super.setBold(bold);
        if(_bold)
            _baseFont = _baseFont.deriveFont(java.awt.Font.BOLD, _size);
        else
            _baseFont = _baseFont.deriveFont(java.awt.Font.PLAIN, _size);
    }

    //VARIABLES
    public java.awt.Font _baseFont;

}
