package com.nonogram.logic;

import com.nonogram.engine.AbstractScene;
import com.nonogram.engine.Audio;
import com.nonogram.engine.Font;
import com.nonogram.engine.Graphics;
import com.nonogram.engine.Image;
import com.nonogram.engine.Input;
import com.nonogram.engine.Scene;

import java.util.Stack;


public class SizeScene extends AbstractScene {

    PopSceneButton botonVolver;
    PushSceneButton[] botonesSizes = new PushSceneButton[4];

    public SizeScene(int gameWidth, int gameHeight) { super(gameWidth,gameHeight); }

    @Override
    public boolean init() {
        _h = getGameHeight();
        _w = getGameWidth();

        _f = _myEngine.getGraphics().newFont("JosefinSans-Bold.ttf", 20, false);
        _myEngine.getGraphics().setActualFont(_f);

        for (int i = 0; i < botonesSizes.length; i++)
        {
            int size = 5 * (i + 1);
            Scene s = new GameScene(getGameWidth(), getGameHeight(), size);
            botonesSizes[i] = new PushSceneButton((_w / 20 + 150) * (1 + i % 2) - 100, _h * (1 + i / 2) / 4, _w / 4, _w / 4, size + "x" + size, _myEngine, s);
        }

        botonVolver = new PopSceneButton( _w / 10, _h / 20 , _w * 2 / 7, _h / 15, "Volver", _myEngine);
        return true;
    }

    @Override
    public void render() {
        _myEngine.getGraphics().setColor(0xFF000000);
        botonVolver.render(_myEngine.getGraphics());
        for(int i = 0; i < botonesSizes.length; i++) botonesSizes[i].render(_myEngine.getGraphics());

    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void processInput(Input.TouchEvent input) {
        switch (input.get_type()){
            case PULSAR:
                if(botonVolver.rect.contains(input.get_posX(), input.get_posY())) botonVolver.handleEvent(input);

                for(int i = 0; i < botonesSizes.length; i++)
                {
                    if(botonesSizes[i].rect.contains(input.get_posX(), input.get_posY())) botonesSizes[i].handleEvent(input);
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
}