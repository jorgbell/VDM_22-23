package com.nonogram.engine;

public abstract class AbstractGraphics implements Graphics {
    public AbstractGraphics(){
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
    protected Scene _myScene;
    protected int _actualColor;
    protected Font _actualFont;
}
