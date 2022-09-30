package com.nanogram.engine;

public class AbstractGraphics implements Graphics {
    public AbstractGraphics(int w, int h){
        _windowHeight = h; _windowWidth = w;
    }

    @Override
    public Image newImage(String name) {
        return null;
    }

    @Override
    public Font newFont(String filename, int size, boolean isBold) {
        return null;
    }

    @Override
    public void clear(int color) {

    }

    @Override
    public void translate(int x, int y) {

    }

    @Override
    public void scale(int x, int y) {

    }

    @Override
    public void save() {

    }

    @Override
    public void restore() {

    }

    @Override
    public void drawImage(Image image, int x, int y) {

    }

    @Override
    public void setColor(int color) {

    }

    @Override
    public void fillSquare(int cx, int cy, int side) {

    }

    @Override
    public void drawSquare(int cx, int cy, int side) {

    }

    @Override
    public void drawLine(int initX, int initY, int endX, int endY) {

    }

    @Override
    public void drawText(String text, int x, int y) {

    }

    @Override
    public int getWindowWidth() {
        return 0;
    }

    @Override
    public int getWindowHeight() {
        return 0;
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
}
