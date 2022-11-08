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
    int tileNumber = 10;

    int tableroSize;
    int tileSize;
    int tableroX;
    int tableroY;

    CasillaButton[][] botones;
    RendirseButton botonFF;

    public GameScene(int gameWidth, int gameHeight)
    {
        super(gameWidth,gameHeight);
        botones = new CasillaButton[tileNumber][tileNumber];
    }

    @Override
    public boolean init() {
        h = getGameHeight();
        w = getGameWidth();

        tableroSize = (w / 20) * 14;
        tileSize =  tableroSize / tileNumber;
        tableroX = (w / 20) * 5;
        tableroY = (h / 20) * 10;
        t = new Tablero();
        t.init(tileNumber);


        for (int i = 0; i < tileNumber; i++)
        {
            for (int j = 0; j < tileNumber; j++)
            {
                botones[i][j] = new CasillaButton(tableroX + tileSize * i, tableroY + tileSize * j, tileSize - 1, tileSize - 1, t.getCasilla(j, i));
            }
        }

        botonFF = new RendirseButton( w - 200, h - 1000, 300, 100, this);

        return true;
    }

    @Override
    public void render() {

        _myEngine.getGraphics().clearGame(0XFF225500);

        _myEngine.getGraphics().setColor(0XFF000000);
        _myEngine.getGraphics().drawRect(tableroX - w / 5, tableroY - 3, tableroSize + tableroX - (w / 16), tableroSize - 2);
        _myEngine.getGraphics().drawRect(tableroX - 3, tableroY - h / 10, tableroSize - 5, tableroSize + tableroY - (h * 6 / 15) );

        for (int i = 0; i < tileNumber; i++)
        {
            for (int j = 0; j < tileNumber; j++)
            {
                botones[i][j].render(_myEngine.getGraphics());
            }

            _myEngine.getGraphics().setColor(0XFF000000);
            _myEngine.getGraphics().drawText(t.filas[i].numbers, tableroX - tileSize, tableroY + (tileSize / 2) * i);
        }

        botonFF.render(_myEngine.getGraphics());

        for(int i = 0; i < tileNumber; i++)
        {
            String[] s = t.columnas[i].numbers.split("\\.");

            for(int j = s.length - 1; j >= 0; j--) _myEngine.getGraphics().drawText(s[j],tableroX + (tileSize / 2) * i, tableroY + (tileSize / 2) * j);
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
                    System.out.println("FIXED X: " + input.get_posX() + "//FIXED Y: " + input.get_posY());

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

    @Override
    public boolean release() {
        return true;
    }

}
