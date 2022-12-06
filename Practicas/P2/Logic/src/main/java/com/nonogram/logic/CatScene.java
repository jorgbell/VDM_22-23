package com.nonogram.logic;

import com.nonogram.engine.AbstractScene;
import com.nonogram.engine.Font;
import com.nonogram.engine.Image;
import com.nonogram.engine.Input;
import com.nonogram.engine.JSONManager;
import com.nonogram.engine.Scene;

public class CatScene extends AbstractScene {

    public CatScene(int gameWidth, int gameHeight, int size, HistoriaScene h,LogicJSON.Category thisc, LogicJSON.PreferencesData pref) {
        super(gameWidth, gameHeight);
        _size=size;
        thiscat = thisc;
        _preferences = pref;
        _historiaScene = h;
    }

    @Override
    public boolean init() {
        _botones = new ChangeSceneButton[thiscat.numLevels];
        _boardsImages = new Image[thiscat.numLevels];
        _f = _myEngine.getGraphics().newFont("JosefinSans-Bold.ttf", 20, false);
        _volverImage = _myEngine.getGraphics().newImage("Arrow.png");
        _candadoImage = _myEngine.getGraphics().newImage("lock.png");
        _newImage = _myEngine.getGraphics().newImage("new.png");
        if(_f == null || _volverImage == null|| _candadoImage == null)
            return false;

        _h = getGameHeight();
        _w = getGameWidth();

        for(int i = 0; i< _botones.length; i++){
            String path = _size+ "x"+_size+ "/" + i + ".png";
            _boardsImages[i]=_myEngine.getGraphics().newImage(path);

            Scene s = new GameScene(getGameWidth(), getGameHeight(), _size, i, _preferences, this);
            _botones[i] = new ChangeSceneButton(_w/4*(i%4), _h * (1 + i / 4) / 6, _w / 6, _w / 6, _myEngine, s);

            if (i > thiscat.actualLevel) {
                _botones[i].addImage(_candadoImage,0.8,Button.ImagePos.CENTERED );
            }
            else if(i<thiscat.actualLevel){
                _botones[i].addImage(_boardsImages[i],0.8,Button.ImagePos.CENTERED );
            }
            else{
                _botones[i].addImage(_newImage,0.8,Button.ImagePos.CENTERED );
            }
        }
        if(thiscat.actualLevel >= thiscat.numLevels)
            _botones[_botones.length-1].addImage(_boardsImages[_botones.length-1],0.8,Button.ImagePos.CENTERED );



        _botonVolver = new ChangeSceneButton( _w / 10, _h / 20 , _w * 2 / 7, _h / 15, _myEngine, null);
        _botonVolver.addText("Volver");
        _botonVolver.addImage(_volverImage,0.04, Button.ImagePos.LEFT);


        return true;
    }

    @Override
    public void render() {
        _myEngine.getGraphics().setActualFont(_f);
        _myEngine.getGraphics().setColor(_myEngine.getGraphics().getTextColor());

        _botonVolver.render(_myEngine.getGraphics());
        for (int i = 0; i < _botones.length; i++) {

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
                    if(_botones[i]._rect.contains(input.get_posX(), input.get_posY()) && i<=thiscat.actualLevel)
                        _botones[i].handleEvent(input);
                }
                break;
            case CLICK_LARGO:
                break;
        }
    }

    @Override
    public boolean release() {
        LogicJSON.writePreferencesToJson("preferences.json", _preferences);
        return true;
    }

    public void increaseLevel(int levelPlayed){
        if(thiscat.actualLevel < thiscat.numLevels && thiscat.actualLevel == levelPlayed){
            _botones[thiscat.actualLevel].addImage(_boardsImages[thiscat.actualLevel],0.8,Button.ImagePos.CENTERED );
            thiscat.actualLevel++;
            if(thiscat.numLevels == thiscat.actualLevel){
                _historiaScene.increaseCat();
                return;
            }
            _botones[thiscat.actualLevel].addImage(_newImage,0.8,Button.ImagePos.CENTERED );
        }
    }

    int _h;
    int _w;
    Font _f;
    Image _volverImage;
    Image _candadoImage;
    Image _newImage;
    int _size;
    ChangeSceneButton _botonVolver;


    ChangeSceneButton[] _botones;
    LogicJSON.PreferencesData _preferences;
    LogicJSON.Category thiscat;
    Image[] _boardsImages;
    HistoriaScene _historiaScene;
}
