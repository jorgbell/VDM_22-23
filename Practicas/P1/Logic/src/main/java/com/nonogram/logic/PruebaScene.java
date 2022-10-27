package com.nonogram.logic;

import com.nonogram.engine.Engine;
import com.nonogram.engine.Scene;

public class PruebaScene implements Scene{

    Tablero t;
    int h;
    int w;
    boolean initialized = false;
    boolean show = false;
    float time = 10;
    int size = 15;

    public PruebaScene()
    {
    }

    @Override
    public void render() {
       // _myEngine.getGraphics().clear(255, );

        w = _myEngine.getGraphics().getWindowWidth();
        int width = w - 300;
        int leftmargin = 20;
        int downmargin = 50;


        _myEngine.getGraphics().setColor(0);

       // _myEngine.getGraphics().drawLine(35, 40, w, w);

        for(int i = 0; i <= size; i++) _myEngine.getGraphics().drawLine(leftmargin + ((leftmargin +width) / size) * i, downmargin, leftmargin + ((leftmargin +width) / size) * i, (downmargin * 4) / 5 + downmargin + width);
        for(int i = 0; i <= size; i++) _myEngine.getGraphics().drawLine(leftmargin,downmargin + ((downmargin + width) / size) * i, leftmargin / 2 + leftmargin + width, downmargin + ((downmargin + width) / size) * i);

        for(int i = 0; i < size; i++)
        {
            if(show)
            {
                for(int j = 0; j < size; j++)
                {
                   // if(t.solucion[i][j]) _myEngine.getGraphics().fillSquare(leftmargin + ((leftmargin + width) / size) * j, downmargin + ((downmargin + width) / size) * i, ((width + downmargin)  / size));
                }
            }

            _myEngine.getGraphics().drawText(t.filas[i].numbers, leftmargin + leftmargin + width + 5, 25 + downmargin + ((downmargin + width) / size) * i);
        }

        for(int i = 0; i < size; i++)
        {
            String[] s = t.columnas[i].numbers.split("\\.");

            for(int j = 0; j < s.length; j++) _myEngine.getGraphics().drawText(s[j],15 + leftmargin + ((leftmargin + width) / size) * i, 20 + downmargin + downmargin + width + j * 20);
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

    //TODO: AbstractScene
    @Override
    public void setEngine(Engine e) {
        _myEngine = e;
    }
    Engine _myEngine;

    void init()
    {
        t = new Tablero();
        t.init(size);

        h =_myEngine.getGraphics().getWindowHeight();
        w =_myEngine.getGraphics().getWindowWidth();
    }
}
