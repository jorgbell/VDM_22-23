package com.nonogram.engine;

public abstract class AbstractGraphics implements Graphics {
    public AbstractGraphics(){
        scaleFactor = 1;
        translateX = 0;
        translateY = 0;
    }

    @Override
    public boolean setInputListener(Input inputListener){
        inputListener.setGraphics(this);
        return true;
    }

    @Override
    public void setPaths(AbstractEngine.EnginePaths p){
        _myPaths = p;
    }
    @Override
    public void setSceneManager(SceneManager smng){sceneManager = smng;}

    @Override
    public void setColor(int color) {
        _actualColor = color;
    }

    @Override
    public void setActualFont(Font font) {
        _actualFont = font;
    }

    @Override
    public void paintFrame() {
        clearWindow();
        //reescalamos
        reScale();
        // Pintamos la escena
        sceneManager.render();
    }

    @Override
    public void reScale(){
        //sacamos la relacion de aspecto actual, segun el tamaño de la ventana.
        float wh = (float) getWindowHeight(); float ww = getWindowWidth();
        float gh = (float) getGameHeight(); float gw = getGameWidth();
        float aspectH = wh/gh;
        float aspectW = ww/gw;

        //si ventana y juego tienen el mismo tamaño, el factor de reescalado es 1.
        if(aspectH == 1 && aspectW == 1){
            translate(getBorderWidth(),getBorderHeight());
            scale(1,1);
            return;
        }

        //En caso contrario
        scaleFactor = Math.min(aspectH, aspectW);

        translateX = (int)(getWindowWidth() - getGameWidth() * scaleFactor) /2;
        translateY = (int)(getWindowHeight()  - getGameHeight() * scaleFactor) /2;

        translate(translateX + getBorderWidth(), translateY + getBorderHeight());
        scale(scaleFactor, scaleFactor);
    }

    @Override
    public float worldToGameX(float x) {
        float nx = (x - translateX - getBorderWidth())/scaleFactor;
        return nx;
    }
    @Override
    public float worldToGameY(float y) {
        float ny =(y - translateY - getBorderHeight())/scaleFactor;
        return ny;
    }

    @Override
    public int getGameWidth() {
        return sceneManager.getGameWidth() - (int)getBorderWidth();
    }

    @Override
    public int getGameHeight() {
        return sceneManager.getGameHeight() - (int)getBorderHeight();
    }

    //VARIABLES
    protected int _actualColor;
    protected Font _actualFont;
    protected AbstractEngine.EnginePaths _myPaths;
    int translateX, translateY;
    float scaleFactor;
    SceneManager sceneManager;
}
