package com.nonogram.engine;

import java.util.Vector;

public abstract class AbstractGraphics implements Graphics {
    public AbstractGraphics() {
        scaleFactor = 1;
        translateX = 0;
        translateY = 0;
    }

    @Override
    public boolean setInputListener(Input inputListener) {
        inputListener.setGraphics(this);
        return true;
    }

    @Override
    public void setEngine(Engine e) {
        _myEngine = e;
        sceneManager = _myEngine.getSceneManager();
        AbstractEngine ae = (AbstractEngine)_myEngine;
        _myPaths = ae._myPaths;
    }

    @Override
    public void setColor(int color) {
        _actualColor = color;
    }

    @Override
    public void setActualFont(Font font) {
        if (font == null) {
            System.err.println("Error al setear la fuente actual");
            return;
        }
        _actualFont = font;
    }

    @Override
    public void paintFrame() {
        clearWindow();
        //reescalamos
        reScale();
        clearGame(_bgColor);
        // Pintamos la escena
        sceneManager.render();
    }

    @Override
    public void reScale() {

        //sacamos la relacion de aspecto actual, segun el tamaño de la ventana.
        float windowHeight = (float) getWindowHeight();
        float windowWidth = getWindowWidth();
        float gameFHeight = sceneManager.getGameHeight();
        float gameFWidth = sceneManager.getGameWidth();

        scaleH = windowHeight / gameFHeight;
        scaleW = windowWidth / gameFWidth;

        if(scaleH > scaleW) horizontal = true;
        else horizontal = false;

        //En caso contrario
        scaleFactor = Math.min(scaleH, scaleW);

        translateX = (int) (windowWidth - gameFWidth * scaleFactor) / 2;
        translateY = (int) (windowHeight - gameFHeight * scaleFactor) / 2;

        translate(translateX + getLeftBorder(), translateY + getTopBorder());
        scale(scaleFactor, scaleFactor);
    }

    @Override
    public float worldToGameX(float x) {
        float nx = (x - translateX - getLeftBorder()) / scaleW;
        return nx;
    }

    @Override
    public float worldToGameY(float y) {
        float ny = (y - translateY - getTopBorder()) / scaleH;
        return ny;
    }


    @Override
    public void setFullScreen(boolean fullScreen) {
        ;
    }

    @Override
    public void setBGColor(int color) {
        _bgColor = color;
    }

    @Override
    public boolean isWindowHorizontal() {
        return horizontal;
    }

    //VARIABLES
    protected int _actualColor;
    protected int _bgColor = 0xFFFFFFFF;
    protected int _actualPalette;
    protected Font _actualFont;
    protected AbstractEngine.EnginePaths _myPaths;
    protected int translateX, translateY;
    protected float scaleW, scaleH;
    float scaleFactor;
    protected SceneManager sceneManager;
    protected Engine _myEngine;
    protected boolean horizontal = false;


}
