package com.nonogram.logic;

import com.nonogram.engine.AbstractScene;
import com.nonogram.engine.Font;
import com.nonogram.engine.Input;

public class GameScene extends AbstractScene {

    Tablero t;
    int h;
    int w;
    boolean initialized = false;
    boolean show = false;
    boolean won = false;
    float showTime = 0;
    int tileNumber;

    int tableroSize;
    int tileSize;
    int tableroX;
    int tableroY;

    int numberFontSize;

    int wrongs = 0;
    int remaining = 0;

    CasillaButton[][] botones;
    ResuelveButton botonResolver;
    PopSceneButton botonFF;
    PopSceneButton botonVictoria;

    public GameScene(int gameWidth, int gameHeight, int size)
    {
        super(gameWidth,gameHeight);
        tileNumber = size;
        botones = new CasillaButton[tileNumber][tileNumber];
    }

    @Override
    public boolean init() {
        h = getGameHeight();
        w = getGameWidth();

        tableroSize = (w / 20) * 14;
        tileSize =  tableroSize / tileNumber;
        tableroX = (w / 20) * 5;
        tableroY = (h / 20) * 8;
        t = new Tablero();
        t.init(tileNumber);

        f = _myEngine.getGraphics().newFont("JosefinSans-Bold.ttf", 20, false);

        numberFontSize = 45 - (int)(5.7 * (Math.log(tileNumber) / Math.log(1.595)));
        numbersf = _myEngine.getGraphics().newFont("JosefinSans-Bold.ttf", numberFontSize, false);

        _myEngine.getGraphics().setActualFont(f);

        for (int i = 0; i < tileNumber; i++)
        {
            for (int j = 0; j < tileNumber; j++)
            {
                botones[i][j] = new CasillaButton(tableroX + tileSize * i, tableroY + tileSize * j, tileSize - 1, tileSize - 1, t.getCasilla(j, i));
            }
        }

        botonResolver = new ResuelveButton( w * 3 / 5, h / 20 , w * 2 / 7, h / 15, this);
        botonFF = new PopSceneButton( w / 5, h / 20 , w * 2 / 7, h / 15, "Rendirse", _myEngine);
        botonVictoria = new PopSceneButton(w * 2 / 5, h * 8 / 10, w * 2 / 7, h / 15, "Volver", _myEngine);

        return true;
    }

    @Override
    public void render() {

        _myEngine.getGraphics().clearGame(0XFFFFFFFF);

        _myEngine.getGraphics().setColor(0XFF000000);

        if(!won)
        {
            _myEngine.getGraphics().drawRect((tableroX - w / 5) + 5, tableroY - 3, tableroSize + tableroX - (w / 16), tableroSize);
            _myEngine.getGraphics().drawRect(tableroX - 3, tableroY - h / 10, tableroSize, tableroY + tableroSize - (h * 76 / 250) );

            botonResolver.render(_myEngine.getGraphics());
            botonFF.render(_myEngine.getGraphics());

            _myEngine.getGraphics().setActualFont(numbersf);
            for(int i = 0; i < tileNumber; i++)
            {
                _myEngine.getGraphics().setColor(0XFF000000);
                String[] sf = t.filas[i].numbers.split("\\.");
                String[] sc = t.columnas[i].numbers.split("\\.");

                int columnaSpace = tileSize;
                int columnaXMargin =  tileSize / 5;
                int columnaYMargin =  tableroSize / 30;
                int columnaInterSpace = numberFontSize * 10 / 9;

                int filaSpace = numberFontSize * 10 / 9;
                int filaXMargin = tableroSize / 30;
                int filaYMargin = tileSize / 5;
                int filaInterSpace = tableroSize / 20;

                for(int j = sf.length - 1; j >= 0; j--) _myEngine.getGraphics().drawText(sf[(sf.length - 1) - j],(tableroX - filaXMargin) - filaSpace * (j + 1), tableroY + filaYMargin + tileSize / 2 + (tileSize) * i);
                for(int j = sc.length - 1; j >= 0; j--) _myEngine.getGraphics().drawText(sc[(sc.length - 1) - j],tableroX - columnaXMargin + tileSize / 2 + columnaSpace * i, (tableroY - columnaYMargin) - columnaInterSpace * j);
            }

            _myEngine.getGraphics().setActualFont(f);

            if(show)
            {
                _myEngine.getGraphics().setColor(0XFFFF0000);
                _myEngine.getGraphics().drawText("Te faltan " + remaining + " casillas", w / 3, h * 4 / 20);
                _myEngine.getGraphics().drawText("Tienes mal " + wrongs + " casillas", w / 3, h * 5 / 20);
            }
        }

        if (won)
        {
            botonVictoria.render(_myEngine.getGraphics());
        }

        for (int i = 0; i < tileNumber; i++)
        {
            for (int j = 0; j < tileNumber; j++)
            {
                Tablero.State s = t.getCasilla(j, i).getState();
                if(!won || s == Tablero.State.PICK) botones[i][j].render(_myEngine.getGraphics());
            }
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
        switch (input.get_type()){
            case PULSAR:
                System.out.println("FIXED X: " + input.get_posX() + "//FIXED Y: " + input.get_posY());

                if(!won)
                {
                    if(!show || wrongs <= 0)
                    {
                        for (int i = 0; i < botones.length; i++) for (int j = 0; j < botones[0].length; j++)
                        {
                            if (botones[i][j].rect.contains(input.get_posX(), input.get_posY()))
                            {
                                botones[i][j].handleEvent(input);
                            }
                        }
                    }

                    if(botonResolver.rect.contains(input.get_posX(), input.get_posY())) botonResolver.handleEvent(input);
                    if(botonFF.rect.contains(input.get_posX(), input.get_posY())) botonFF.handleEvent(input);
                }

                else if(botonVictoria.rect.contains(input.get_posX(), input.get_posY())) botonVictoria.handleEvent(input);
                break;
            case SOLTAR:
                break;
        }
    }

    public void showSolution()
    {
        wrongs = t.ComprobarTablero();
        remaining = t.getRemaining();
        if(wrongs == 0 && remaining == 0) won = true;
        else {
            show = true;
            showTime = 3;
        }
    }

    @Override
    public boolean release() {
        return true;
    }

    Font f;
    Font numbersf;
}
