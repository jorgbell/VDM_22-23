package com.nonogram.logic;

import com.nonogram.engine.AbstractScene;
import com.nonogram.engine.Input;

public class GameScene extends AbstractScene {

    Tablero t;
    int h;
    int w;
    boolean initialized = false;
    boolean show = false;
    float showTime = 0;
    int size = 15;
    CasillaButton[][] botones;
    RendirseButton botonFF;

    public GameScene(int gameWidth, int gameHeight)
    {
        super(gameWidth,gameHeight);
        botones = new CasillaButton[size][size];
    }

    @Override
    public void init() {
        h =_myEngine.getGraphics().getWindowHeight();
        w = _myEngine.getGraphics().getWindowWidth();

        t = new Tablero();
        t.init(size);

        int x = 150;
        int y = 300;
        int s = 500 / size;

        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                botones[i][j] = new CasillaButton(x + s * i, y + s * j, s - 1, s - 1, t.getCasilla(j, i));
            }
        }

        botonFF = new RendirseButton( w - 200, h - 1000, 300, 100, this);
    }

    @Override
    public void render() {

        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                botones[i][j].render(_myEngine.getGraphics());
            }

            _myEngine.getGraphics().setColor(0XFF000000);
            _myEngine.getGraphics().drawText(t.filas[i].numbers, 675, 320 + ((500 / size) * i));
        }

        botonFF.render(_myEngine.getGraphics());

        for(int i = 0; i < size; i++)
        {
            String[] s = t.columnas[i].numbers.split("\\.");

            for(int j = 0; j < s.length; j++) _myEngine.getGraphics().drawText(s[j],165 + ((500 / size) * i), 825 + (20 * j));
        }

        _myEngine.getGraphics().setColor(0XFF000000);

        //_myEngine.getGraphics().drawText("Show result in: " + (int) showTime, 650, 300);
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
            if (show)
            {
                showTime -= deltaTime;
                if(showTime <= 0)
                {
                    t.LimpiarErrores();
                    show = false;
                }
            }
        }
    }

    @Override
    public void processInput(Input.TouchEvent input) {

        if(!show)
        {
            switch (input.get_type()){
                case PULSAR:

                    for (int i = 0; i < botones.length; i++)
                    {
                        for (int j = 0; j < botones[0].length; j++)
                        {
                            if (botones[i][j].rect.contains(input.get_posX(), input.get_posY()))
                            {
                                botones[i][j].handleEvent(input);
                            }
                        }
                    }

                    if(botonFF.rect.contains(input.get_posX(), input.get_posY())) botonFF.handleEvent(input);

                    break;
                case SOLTAR:
                    break;
            }
        }
    }

    public void showSolution()
    {
        t.ComprobarTablero();
        show = true;
        showTime = 3;
    }

}
