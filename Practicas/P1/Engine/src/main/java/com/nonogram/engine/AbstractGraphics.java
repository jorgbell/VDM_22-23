package com.nonogram.engine;

public abstract class AbstractGraphics implements Graphics {
    public AbstractGraphics(){
    }

    @Override
    public void setPaths(AbstractEngine.EnginePaths p){
        _myPaths = p;
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

    @Override
    public void paintFrame() {
        clearWindow();
        //reescalamos
        reScale();
        // Pintamos la escena
        _myScene.render();
    }

    void reScale(){
        //tamaño de juego de la escena actual. Si la escena cambia, podria cambiar este valor.
        _gameHeight = _myScene.getGameHeight();
        _gameWidth = _myScene.getGameWidth();

        //sacamos la relacion de aspecto actual, segun el tamaño de la ventana.
        float aspectH = (float) getWindowHeight() / _gameHeight;
        float aspectW = (float) getWindowWidth() / _gameWidth;

        //si ventana y juego tienen el mismo tamaño, el factor de reescalado es 1.
        if(aspectH == 1 && aspectW == 1){
            translate(0,0);
            scale(1,1);
            return;
        }

        //En caso contrario
        float scaleFactor;
        int translateX, translateY;
        scaleFactor = Math.min(aspectH, aspectW);

        translateX = (int)(getWindowWidth() - _gameWidth * scaleFactor) /2;
        translateY = (int)(getWindowHeight() - _gameHeight * scaleFactor) /2;

        translate(translateX, translateY);
        scale(scaleFactor, scaleFactor);
    }

    //VARIABLES
    protected Scene _myScene;
    protected int _actualColor;
    protected Font _actualFont;
    protected AbstractEngine.EnginePaths _myPaths;
    protected int _gameHeight, _gameWidth;
}
