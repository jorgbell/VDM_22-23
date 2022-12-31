package com.nonogram.logic;

import com.nonogram.engine.AbstractScene;
import com.nonogram.engine.Font;
import com.nonogram.engine.Image;
import com.nonogram.engine.Input;
import com.nonogram.engine.Scene;


public class DifficultyScene extends AbstractScene {

    public DifficultyScene() { super(); }

    @Override
    public boolean init() {
        super.init();

        _h = getGameHeight();
        _w = getGameWidth();

        _f = _myEngine.getGraphics().newFont("JosefinSans-Bold.ttf", _w / 21, false);
        _volverImage = _myEngine.getGraphics().newImage("Arrow.png");
        if(_f == null || _volverImage == null)
            return false;

        //Crea los botones de los diferentes tableros
        for (int i = 0; i < _botonesSizes.length; i++)
        {
            //todo: igual meter que dependiendo del numero de sizes se dispongan mas o menos en la misma fila?
            int percentage = 60 - 20 * i;
            Scene s = new SizeScene(percentage);
            _botonesSizes[i] = new ChangeSceneButton((_w / 20 + 120) * (1 + i) - 115, _h / 3, _w / 4, _w / 4, _difficulties[i], _myEngine, s, null);
        }

        _botonVolver = new ChangeSceneButton( _w / 10, _h / 20 , _w * 2 / 7, _h / 15, "Volver", _myEngine, _volverImage);
        return true;
    }

    @Override
    public void render() {
        _myEngine.getGraphics().setActualFont(_f);
        _myEngine.getGraphics().setColor(0xFF000000);
        _myEngine.getGraphics().drawText("Selecciona la dificultad del puzzle", _w /5, _h /5);

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

    private int _h;
    private int _w;
    private Font _f;
    private Image _volverImage;
    private String[] _difficulties ={"Facil", "Normal", "Dificil"}; //el minimo es 5x5
    private ChangeSceneButton _botonVolver;
    private ChangeSceneButton[] _botonesSizes = new ChangeSceneButton[_difficulties.length];
}