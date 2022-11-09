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
        // Pintamos la escena
        sceneManager.render();
    }

    @Override
    public void reScale(){

        //sacamos la relacion de aspecto actual, segun el tamaño de la ventana.
        float windowHeight = (float) getWindowHeight(); float windowWidth = getWindowWidth();
        float gameFHeight = sceneManager.getGameHeight(); float gameFWidth = sceneManager.getGameWidth();

        float aspectH = windowHeight/gameFHeight;
        float aspectW = windowWidth/gameFWidth;

        //En caso contrario
        scaleFactor = Math.min(aspectH, aspectW);

        translateX = (int)(windowWidth - gameFWidth* scaleFactor) /2;
        translateY = (int)(windowHeight  - gameFHeight* scaleFactor) /2;

        translate(translateX + getLeftBorder(), translateY + getTopBorder());
        scale(scaleFactor, scaleFactor);
    }

    @Override
    public float worldToGameX(float x) {
        float nx = (x - translateX - getLeftBorder())/scaleFactor;
        return nx;
    }
    @Override
    public float worldToGameY(float y) {
        float ny =(y - translateY - getTopBorder())/scaleFactor;
        return ny;
    }


    //VARIABLES
    protected int _actualColor;
    protected Font _actualFont;
    protected AbstractEngine.EnginePaths _myPaths;
    int translateX, translateY;
    float scaleFactor;
    protected SceneManager sceneManager;
}
