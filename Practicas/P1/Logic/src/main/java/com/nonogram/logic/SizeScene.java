package com.nonogram.logic;

import com.nonogram.engine.AbstractScene;
import com.nonogram.engine.Font;
import com.nonogram.engine.Image;
import com.nonogram.engine.Input;
import com.nonogram.engine.Scene;


public class SizeScene extends AbstractScene {

    public SizeScene(int gameWidth, int gameHeight, int solvablePercentage)
    {
        super(gameWidth,gameHeight);
        _solvablePercentage = solvablePercentage;
    }

    @Override
    public boolean init() {
        _f = _myEngine.getGraphics().newFont("JosefinSans-Bold.ttf", 20, false);
        _volverImage = _myEngine.getGraphics().newImage("Arrow.png");
        if(_f == null || _volverImage == null)
            return false;


        _h = getGameHeight();
        _w = getGameWidth();
        //Crea los botones de los diferentes tableros
        for (int i = 0; i < _botonesSizes.length; i++)
        {
            //todo: igual meter que dependiendo del numero de sizes se dispongan mas o menos en la misma fila?
            int rowNumber = _sizes[i][0];
            int columnNumber = _sizes[i][1];
            //int size = 5 * (i + 1);
            Scene s = new GameScene(getGameWidth(), getGameHeight(), rowNumber, columnNumber, _solvablePercentage);
            _botonesSizes[i] = new ChangeSceneButton((_w / 20 + 150) * (1 + i % 2) - 100, _h * (1 + i / 2) / 4, _w / 4, _w / 4, rowNumber + "x" + columnNumber, _myEngine, s, null);
        }

        _botonVolver = new ChangeSceneButton( _w / 10, _h / 20 , _w * 2 / 7, _h / 15, "Volver", _myEngine, _volverImage);
        return true;
    }

    @Override
    public void render() {
        _myEngine.getGraphics().setActualFont(_f);
        _myEngine.getGraphics().setColor(0xFF000000);
        _myEngine.getGraphics().drawText("Selecciona el tamaño del puzzle", _w /5, _h /5);

        _botonVolver.render(_myEngine.getGraphics());
        for(int i = 0; i < _botonesSizes.length; i++) _botonesSizes[i].render(_myEngine.getGraphics());
    }

    @Override
    public void update(double deltaTime) {}

    @Override
    public void processInput(Input.TouchEvent input) {
        switch (input.get_type()){
            case PULSAR:
                if(_botonVolver._rect.contains(input.get_posX(), input.get_posY())) _botonVolver.handleEvent(input);

                for(int i = 0; i < _botonesSizes.length; i++)
                {
                    if(_botonesSizes[i]._rect.contains(input.get_posX(), input.get_posY())) _botonesSizes[i].handleEvent(input);
                }
                break;
            case SOLTAR:
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
    int[][] _sizes = {{5, 5}, {8, 8}, {10, 5}, {10, 8}, {10, 10}, {15, 15}}; //el minimo es 5x5
    int _solvablePercentage;
    ChangeSceneButton _botonVolver;
    ChangeSceneButton[] _botonesSizes = new ChangeSceneButton[_sizes.length];
}