package com.nonogram.engine;

import java.util.Vector;

public abstract class AbstractGraphics implements Graphics {
    public AbstractGraphics() {
        scaleFactor = 1;
        translateX = 0;
        translateY = 0;
        palettes = new Vector<Palette>();
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
        clearGame((int)palettes.get(_actualPalette).bgColor);
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

        float aspectH = windowHeight / gameFHeight;
        float aspectW = windowWidth / gameFWidth;

        //En caso contrario
        scaleFactor = Math.min(aspectH, aspectW);

        translateX = (int) (windowWidth - gameFWidth * scaleFactor) / 2;
        translateY = (int) (windowHeight - gameFHeight * scaleFactor) / 2;

        translate(translateX + getLeftBorder(), translateY + getTopBorder());
        scale(scaleFactor, scaleFactor);
    }

    @Override
    public float worldToGameX(float x) {
        float nx = (x - translateX - getLeftBorder()) / scaleFactor;
        return nx;
    }

    @Override
    public float worldToGameY(float y) {
        float ny = (y - translateY - getTopBorder()) / scaleFactor;
        return ny;
    }


    @Override
    public void setFullScreen(boolean fullScreen) {
        ;
    }

    @Override
    public void addPalette(String b, String  t, String  h, String p, String e){
        palettes.add(new Palette(b,t,h,p,e));
    }
    class Palette{
        public long bgColor;
        public long textColor;
        public long hlColor;
        public long pickColor;
        public long emptyColor;

        public Palette(String b, String  t, String  h, String p, String e){
            bgColor = Long.decode(b);
            textColor=Long.decode(t);
            hlColor = Long.decode(h);
            pickColor = Long.decode(p);
            emptyColor = Long.decode(e);
        }
    }

    @Override
    public void setPalette(int i){
        _actualPalette=i;
    }
    @Override
    public int getBgColor(){return (int)palettes.get(_actualPalette).bgColor;};
    @Override
    public int getTextColor(){return (int)palettes.get(_actualPalette).textColor;};
    @Override
    public int getHLColor(){return (int)palettes.get(_actualPalette).hlColor;};
    @Override
    public int getPickColor(){return (int)palettes.get(_actualPalette).pickColor;};
    @Override
    public int getEmptyColor(){return (int)palettes.get(_actualPalette).emptyColor;};

    protected Vector<Palette> palettes;
    //VARIABLES
    protected int _actualColor;
    protected int _actualPalette;
    protected Font _actualFont;
    protected AbstractEngine.EnginePaths _myPaths;
    int translateX, translateY;
    float scaleFactor;
    protected SceneManager sceneManager;
    protected Engine _myEngine;


}
