package com.nonogram.logic;

import com.nonogram.engine.AbstractScene;
import com.nonogram.engine.Engine;
import com.nonogram.engine.Input;

public class GameScene extends AbstractScene {

    Tablero t;
    int h;
    int w;
    boolean initialized = false;
    boolean show = false;
    float time = 5;
    int size = 15;

    public GameScene()
    {
    }

    @Override
    public void init() {
        t = new Tablero();
        t.init(size);

        h =_myEngine.getGraphics().getWindowHeight();
        w =_myEngine.getGraphics().getWindowWidth();
    }

    @Override
    public void render() {
       // _myEngine.getGraphics().clear(255, );

        w = _myEngine.getGraphics().getWindowWidth();
        int width = w - 500;
        int leftmargin = 20;
        int downmargin = 50;


        _myEngine.getGraphics().setColor(0XFF000000);

       // _myEngine.getGraphics().drawLine(35, 40, w, w);

        for(int i = 0; i <= size; i++) _myEngine.getGraphics().drawLine(leftmargin + ((leftmargin +width) / size) * i, downmargin, leftmargin + ((leftmargin +width) / size) * i, (downmargin * 4) / 5 + downmargin + width);
        for(int i = 0; i <= size; i++) _myEngine.getGraphics().drawLine(leftmargin,downmargin + ((downmargin + width) / size) * i, leftmargin / 2 + leftmargin + width, downmargin + ((downmargin + width) / size) * i);

        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                Tablero.State state =  t.getCasilla(i, j).getState();

                switch(state)
                {
                    case EMPTY:
                        _myEngine.getGraphics().setColor(0XFFFFFFFF);
                        break;
                    case CROSS:
                        _myEngine.getGraphics().setColor(0XFFa4a4a4);
                        break;
                    case PICK:
                        _myEngine.getGraphics().setColor(0XFF2a35cc);
                        break;
                    case WRONG:
                        _myEngine.getGraphics().setColor(0XFFF03434);
                        break;
                    default:
                        _myEngine.getGraphics().setColor(0XFFFFFFFF);
                        break;
                }

                _myEngine.getGraphics().fillSquare(leftmargin + ((leftmargin + width) / size) * j + 1, downmargin + ((downmargin + width) / size) * i + 2, ((width + downmargin)  / size) - 4);
            }

            if(show)
            {
                t.ComprobarTablero();
            }

           // _myEngine.getGraphics().drawText(t.filas[i].numbers, leftmargin + leftmargin + width + 5, 25 + downmargin + ((downmargin + width) / size) * i);
        }

        for(int i = 0; i < size; i++)
        {
            String[] s = t.columnas[i].numbers.split("\\.");

           // for(int j = 0; j < s.length; j++) _myEngine.getGraphics().drawText(s[j],15 + leftmargin + ((leftmargin + width) / size) * i, 20 + downmargin + downmargin + width + j * 20);
        }

        _myEngine.getGraphics().drawText("Show result in: " + (int)time, 650, 300);
    }

    @Override
    public void update(double deltaTime)
    {
        if(!initialized)
        {
            init();
            initialized = true;
        }

        else
        {
            if(time <= 0) show = true;
            else  time -= deltaTime;
        }
    }

    @Override
    public void processInput(Input.TouchEvent input) {

        switch (input.get_type()){
            case PULSAR:
                int j = ((int)input.get_posX() / ((w - 450) / size)) % size;
                int i = ((int)input.get_posY() / ((w - 350) / size)) % size;
                int s = t.getCasilla(i, j).getState().getValue();
                t.getCasilla(i, j).setState(Tablero.State.values()[(s + 1) % 3]);
                break;
            case SOLTAR:
                break;
        }

    }

}
