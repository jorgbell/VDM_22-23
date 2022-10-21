package com.nonogram.engine;

public abstract class AbstractGraphics implements Graphics {
    public AbstractGraphics(int w, int h){
        _windowHeight = h; _windowWidth = w;
    }

    @Override
    public void setColor(int color) {
        _actualColor = color;
    }

    @Override
    public void setActualFont(Font font) {
        _actualFont = font;
    }

    @Override
    public void setScene(Scene s){
        _myScene = s;
    }


    //VARIABLES
    protected int _windowWidth;
    protected int _windowHeight;
    protected Scene _myScene;
    protected int _actualColor;
    protected Font _actualFont;
}
