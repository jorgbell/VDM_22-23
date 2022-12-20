package com.nonogram.logic;

import com.nonogram.engine.AbstractScene;
import com.nonogram.engine.Font;
import com.nonogram.engine.Image;
import com.nonogram.engine.Input;
import com.nonogram.engine.Scene;


public class SizeScene extends AbstractScene {

    public SizeScene(int solvablePercentage)
    {
        super();
        _solvablePercentage = solvablePercentage;
        _preferences = MenuScene._preferences;
    }

    @Override
    public boolean init() {
        super.init();

        _f = _myEngine.getGraphics().newFont("JosefinSans-Bold.ttf", 20, false);
        _volverImage = _myEngine.getGraphics().newImage("Arrow.png");
        if(_f == null || _volverImage == null)
            return false;

        _h = getGameHeight();
        _w = getGameWidth();

        _botonVolver = new ChangeSceneButton( _w / 10, _h / 20 , _w * 2 / 7, _h / 15, null);
        _botonVolver.addText("Volver");
        _botonVolver.addImage(_volverImage,0.04, Button.ImagePos.LEFT);

        ResizeElements();

        return true;
    }

    @Override
    public void render() {
        _myEngine.getGraphics().setActualFont(_f);
        _myEngine.getGraphics().setColor(LogicJSON.Palette.toInt(_preferences.unlockedPalettes.get(_preferences.actualPalette).textColor));
        _myEngine.getGraphics().drawText("Selecciona el tamaño del puzzle", _w /2, textY);

        _botonVolver.render(_myEngine.getGraphics());
        for(int i = 0; i < _botonesSizes.length; i++) _botonesSizes[i].render(_myEngine.getGraphics());
    }

    @Override
    public void rotate() {
        super.rotate();
        _w = super.getGameWidth();
        _h = super.getGameHeight();

        ResizeElements();

    }

    @Override
    public void update(double deltaTime) {
        MenuScene.changeToDarkMode();

    }

    @Override
    public void processInput(Input.TouchEvent input) {
        switch (input.get_type()){
            case CLICK_CORTO:
                if(_botonVolver._rect.contains(input.get_posX(), input.get_posY())) _botonVolver.handleEvent(input);

                for(int i = 0; i < _botonesSizes.length; i++)
                {
                    if(_botonesSizes[i]._rect.contains(input.get_posX(), input.get_posY())) _botonesSizes[i].handleEvent(input);
                }
                break;
            case CLICK_LARGO:
                break;
        }
    }

    @Override
    public boolean release() {
        persist();
        return true;
    }

    @Override
    public boolean persist() {
        LogicJSON.writePreferencesToJson("preferences.json", _preferences);

        return false;
    }

    @Override
    public void handleClosingNotifications() {

    }

    @Override
    public void handleOpeningNotifications() {

    }
    @Override
    public void ResizeElements()
    {

        _h = getGameHeight();
        _w = getGameWidth();
        for (int i = 0; i < _botonesSizes.length; i++)
        {
            int x = 0;
            int y = 0;
            int w = 0;
            ChangeSceneButton auxVolver;

            if(!super.landscape) {
                x = (_w / 7) * (1 + i % 2 * 3);
                y = _h * (1 + i / 2) / 4;
                w = _w / 4;
                _f = _myEngine.getGraphics().newFont("JosefinSans-Bold.ttf", _w / 15 , false);
                textY=_h /6;
                auxVolver = new ChangeSceneButton( _w / 10, _h / 20 , _w * 2 / 7, _h / 15, null);
            }

            else{
                x = (_w / 20 ) +(_w*1/5) * (1 + i % 3);
                y = (_h * (1 + i / 3)) / 3;
                w = _h / 4;
                _f = _myEngine.getGraphics().newFont("JosefinSans-Bold.ttf", _w / 20 , false);
                textY=_h /4;
                auxVolver = new ChangeSceneButton( _w / 20, _h / 15, _w / 7, _h / 8, null);
            }

            if(_botonesSizes[i] == null) {
                //todo: igual meter que dependiendo del numero de sizes se dispongan mas o menos en la misma fila?
                int rowNumber = _sizes[i][0];
                int columnNumber = _sizes[i][1];
                //int size = 5 * (i + 1);
                Scene s = new GameScene(rowNumber, columnNumber, _solvablePercentage, _preferences);
                _botonesSizes[i] = new ChangeSceneButton(x, y, w, w, s);
                _botonesSizes[i].addText(rowNumber + "x" + columnNumber);
            }

            else _botonesSizes[i].setDimensions(x, y, w, w);
            if(_botonVolver == null){
                _botonVolver = auxVolver;
                _botonVolver.addText("Volver");
                _botonVolver.addImage(_volverImage,0.04, Button.ImagePos.LEFT);
            }

            else _botonVolver.setDimensions(auxVolver._rect._x, auxVolver._rect._y, auxVolver._rect._w, auxVolver._rect._h);

        }
    }

    @Override
    public void handleAdd() {
        _preferences.currentLifes++;
    }

    int _h;
    int _w;
    Font _f;
    int textY;
    Image _volverImage;
    int[][] _sizes = {{5, 5}, {8, 8}, {10, 5}, {10, 8}, {10, 10}, {15, 15}}; //el minimo es 5x5
    int _solvablePercentage;
    ChangeSceneButton _botonVolver;
    ChangeSceneButton[] _botonesSizes = new ChangeSceneButton[_sizes.length];

    LogicJSON.PreferencesData _preferences;
}