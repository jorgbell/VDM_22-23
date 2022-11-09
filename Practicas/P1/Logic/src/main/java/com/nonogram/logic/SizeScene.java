package com.nonogram.logic;

import com.nonogram.engine.AbstractScene;
import com.nonogram.engine.Font;
import com.nonogram.engine.Input;
import com.nonogram.engine.Scene;


public class SizeScene extends AbstractScene {



    public SizeScene(int gameWidth, int gameHeight) { super(gameWidth,gameHeight); }

    @Override
    public boolean init() {
        _h = getGameHeight();
        _w = getGameWidth();

        _f = _myEngine.getGraphics().newFont("JosefinSans-Bold.ttf", 20, false);
        _myEngine.getGraphics().setActualFont(_f);

        //Crea los botones de los diferentes tableros
        for (int i = 0; i < _botonesSizes.length; i++)
        {
            //todo: igual meter que dependiendo del numero de sizes se dispongan mas o menos en la misma fila?
            int size = _sizes[i];
            //int size = 5 * (i + 1);
            Scene s = new GameScene(getGameWidth(), getGameHeight(), size);
            _botonesSizes[i] = new ChangeSceneButton((_w / 20 + 150) * (1 + i % 2) - 100, _h * (1 + i / 2) / 4, _w / 4, _w / 4, size + "x" + size, _myEngine, s);
        }

        _botonVolver = new ChangeSceneButton( _w / 10, _h / 20 , _w * 2 / 7, _h / 15, "Volver", _myEngine);
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
    int[] _sizes ={5,8,10,15}; //el minimo es 5x5
    ChangeSceneButton _botonVolver;
    ChangeSceneButton[] _botonesSizes = new ChangeSceneButton[_sizes.length];
}