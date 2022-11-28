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
        //carga de la categoria
        numNiveles=20;
        numUnlock=3;
    }

    @Override
    public boolean init() {
        _f = _myEngine.getGraphics().newFont("JosefinSans-Bold.ttf", 20, false);
        _volverImage = _myEngine.getGraphics().newImage("Arrow.png");
        _candadoImage = _myEngine.getGraphics().newImage("lock.png");
        _newImage = _myEngine.getGraphics().newImage("new.png");
        if(_f == null || _volverImage == null|| _candadoImage == null)
            return false;

        _h = getGameHeight();
        _w = getGameWidth();

        for(int i = 0; i< _botones.length; i++){

            Scene s = new GameScene(getGameWidth(), getGameHeight(), _size, i);
            _botones[i] = new ChangeSceneButton(_w/4*(i%4), _h * (1 + i / 4) / 6, _w / 6, _w / 6,"", _myEngine, s, null, 0.8);
        }

        _botonVolver = new ChangeSceneButton( _w / 10, _h / 20 , _w * 2 / 7, _h / 15, "Volver", _myEngine, _volverImage,0.04);

        return true;
    }

    @Override
    public void render() {
        _myEngine.getGraphics().setActualFont(_f);
        _myEngine.getGraphics().setColor(0xFF000000);

        _botonVolver.render(_myEngine.getGraphics());
        for (int i = 0; i < _botones.length; i++) {
            if (i >= numUnlock) {
                _botones[i]._image = _candadoImage;
            }
            else if(i<numUnlock-1){
                String path = _size+ "/" + i + ".png";
                Image _image = _myEngine.getGraphics().newImage(path);
                _botones[i]._image =_myEngine.getGraphics().newImage(path);
            }
            else{
                _botones[i]._image = _newImage;
            }
            _botones[i].render(_myEngine.getGraphics());
        }
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void processInput(Input.TouchEvent input) {
        switch (input.get_type()){
            case CLICK_CORTO:
                if(_botonVolver._rect.contains(input.get_posX(), input.get_posY())) _botonVolver.handleEvent(input);

                for(int i = 0; i < _botones.length; i++)
                {
                    if(_botones[i]._rect.contains(input.get_posX(), input.get_posY()) && i<numUnlock) _botones[i].handleEvent(input);
                }
                break;
            case CLICK_LARGO:
                break;
        }
    }

    @Override
    public boolean release() {
        return true;
    }

    int _h;
    int _w;
    Font _f;
    Image _volverImage;
    Image _candadoImage;
    Image _newImage;
    int _size;
    ChangeSceneButton _botonVolver;

    int numNiveles =20;
    int numUnlock =3;

    ChangeSceneButton[] _botones = new ChangeSceneButton[numNiveles];
}
