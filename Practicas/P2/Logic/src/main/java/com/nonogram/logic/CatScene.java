package com.nonogram.logic;

import com.nonogram.engine.AbstractScene;
import com.nonogram.engine.Font;
import com.nonogram.engine.Image;
import com.nonogram.engine.Input;
import com.nonogram.engine.Scene;

public class CatScene extends AbstractScene {

    public CatScene(int gameWidth, int gameHeight, int size) {
        super(gameWidth, gameHeight);
        _size=size;
        numNiveles=20;
    }

    @Override
    public boolean init() {
        _f = _myEngine.getGraphics().newFont("JosefinSans-Bold.ttf", 20, false);
        _volverImage = _myEngine.getGraphics().newImage("Arrow.png");
        if(_f == null || _volverImage == null)
            return false;


        _h = getGameHeight();
        _w = getGameWidth();

        for(int i =0; i<numNiveles; i++){

            Scene s = new GameScene(getGameWidth(), getGameHeight(), _size, _size, 0);
            _botonesSizes[i] = new ChangeSceneButton((_w / 20 + 150) * (1 + i % 4) - 100, _h * (1 + i / 2) / 4, _w / 4, _w / 4, Integer.toString(i+1), _myEngine, s, null);

        }

        _botonVolver = new ChangeSceneButton( _w / 10, _h / 20 , _w * 2 / 7, _h / 15, "Volver", _myEngine, _volverImage);

        return true;
    }

    @Override
    public void render() {

    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void processInput(Input.TouchEvent input) {

    }

    @Override
    public boolean release() {
        return false;
    }

    int _h;
    int _w;
    Font _f;
    Image _volverImage;
    int _size;
    ChangeSceneButton _botonVolver;

    int numNiveles;
    int rows;
    int cols;
    ChangeSceneButton[] _botonesSizes = new ChangeSceneButton[_size];
}
