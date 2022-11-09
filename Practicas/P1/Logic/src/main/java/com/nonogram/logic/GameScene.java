package com.nonogram.logic;

import com.nonogram.engine.AbstractScene;
import com.nonogram.engine.Font;
import com.nonogram.engine.Input;

import java.util.Vector;

public class GameScene extends AbstractScene {

    public GameScene(int gameWidth, int gameHeight, int size)
    {
        super(gameWidth,gameHeight);
        _tileNumber = size;
    }

    @Override
    public boolean init() {
        _gameHeight = getGameHeight();
        _gameWidth = getGameWidth();

        _casillas = new CasillaButton[_tileNumber][_tileNumber];

        //init tamaño del tablero
        _tableroSize = (_gameWidth / 20) * 14;
        _tileSize =  _tableroSize / _tileNumber;
        _tableroX = (_gameWidth / 20) * 5;
        _tableroY = (_gameHeight / 20) * 8;
        _t = new Tablero();
        _t.init(_tileNumber);

        _f = _myEngine.getGraphics().newFont("JosefinSans-Bold.ttf", 20, false);
        _numberFontSize = 45 - (int)(5.7 * (Math.log(_tileNumber) / Math.log(1.595)));
        _myEngine.getGraphics().setActualFont(_f);

        for (int i = 0; i < _tileNumber; i++) //se crean las casillas "fisicas"
        {
            for (int j = 0; j < _tileNumber; j++)
            {
                _casillas[i][j] = new CasillaButton(_tableroX + _tileSize * i, _tableroY + _tileSize * j, _tileSize - 1, _tileSize - 1, _t.getCasilla(j, i));
            }
        }

        //botones de ui
        _botonResolver = new ResuelveButton( _gameWidth * 3 / 5, _gameHeight / 20 , _gameWidth * 2 / 7, _gameHeight / 15, this);
        _botonFF = new ChangeSceneButton( _gameWidth / 5, _gameHeight / 20 , _gameWidth * 2 / 7, _gameHeight / 15, "Rendirse", _myEngine);
        _botonVictoria = new ChangeSceneButton(_gameWidth * 2 / 5, _gameHeight * 8 / 10, _gameWidth * 2 / 7, _gameHeight / 15, "Volver", _myEngine);
        return true;
    }

    @Override
    public void render() {

        _myEngine.getGraphics().clearGame(0XFFFFFFFF);

        _myEngine.getGraphics().setColor(0XFF000000);

        if(!_won) //durante la partida
        {
            //Rectagulos para poner los numeros y el tablero
            _myEngine.getGraphics().drawRect((_tableroX - _gameWidth / 5) + 5, _tableroY - 3, _tableroSize + _tableroX - (_gameWidth / 16), _tableroSize);
            _myEngine.getGraphics().drawRect(_tableroX - 3, _tableroY - _gameHeight / 10, _tableroSize, _tableroY + _tableroSize - (_gameHeight * 76 / 250) );

            //UI
            _f.setSize(20);
            _botonResolver.render(_myEngine.getGraphics());
            _botonFF.render(_myEngine.getGraphics());

            _f.setSize(_numberFontSize);
            for(int i = 0; i < _tileNumber; i++) //Numeros
            {
                _myEngine.getGraphics().setColor(0XFF000000);
                String[] sf = _t._filas[i].numbers.split("\\.");
                String[] sc = _t._columnas[i].numbers.split("\\.");

                int columnaSpace = _tileSize;
                int columnaXMargin =  _tileSize / 5;
                int columnaYMargin =  _tableroSize / 30;
                int columnaInterSpace = _numberFontSize * 10 / 9;

                int filaSpace = _numberFontSize * 10 / 9;
                int filaXMargin = _tableroSize / 30;
                int filaYMargin = _tileSize / 5;
                int filaInterSpace =_tileSize;

                for(int j = sf.length - 1; j >= 0; j--) _myEngine.getGraphics().drawText(sf[(sf.length - 1) - j],(_tableroX - filaXMargin) - filaSpace * (j + 1), _tableroY + filaYMargin + _tileSize / 2 + filaInterSpace * i);
                for(int j = sc.length - 1; j >= 0; j--) _myEngine.getGraphics().drawText(sc[(sc.length - 1) - j], _tableroX - columnaXMargin + _tileSize / 2 + columnaSpace * i, (_tableroY - columnaYMargin) - columnaInterSpace * j);
            }

            if(_showErrors) //texto al pulsar comprobar
            {
                _f.setSize(20);
                _myEngine.getGraphics().setColor(0XFFFF0000);
                _myEngine.getGraphics().drawText("Te faltan " + _remaining + " casillas", _gameWidth / 3, _gameHeight * 4 / 20);
                _myEngine.getGraphics().drawText("Tienes mal " + _wrongs + " casillas", _gameWidth / 3, _gameHeight * 5 / 20);
            }
        }
        else //fin de la partida
        {
            _f.setSize(20);
            _myEngine.getGraphics().setColor(0XFF000000);
            _myEngine.getGraphics().drawText("ENHORABUENA", _gameWidth / 3, _gameHeight * 4 / 20);
            _botonVictoria.render(_myEngine.getGraphics());
        }

        for (int i = 0; i < _tileNumber; i++) //renderizado de las casillas fisicas
        {
            for (int j = 0; j < _tileNumber; j++)
            {
                Tablero.State s = _t.getCasilla(j, i).getState();
                if(!_won || s == Tablero.State.PICK) _casillas[i][j].render(_myEngine.getGraphics());
            }
        }

        //_myEngine.getGraphics().setColor(0XFF000000);
        //_myEngine.getGraphics().drawText("Show result in: " + (int) showTime, 650, 300);
    }

    @Override
    public void update(double deltaTime)
    {
        if (_showErrors)
        {
            _showTime -= deltaTime;
            if(_showTime <= 0)
            {
                _t.LimpiarErrores();
                _showErrors = false;
            }
        }
    }

    @Override
    public void processInput(Input.TouchEvent input) {
        switch (input.get_type()){
            case PULSAR:
                System.out.println("FIXED X: " + input.get_posX() + "//FIXED Y: " + input.get_posY());

                if(!_won)
                {
                    if(!_showErrors || _wrongs <= 0)
                    {
                        for (int i = 0; i < _casillas.length; i++) for (int j = 0; j < _casillas[0].length; j++)
                        {
                            if (_casillas[i][j]._rect.contains(input.get_posX(), input.get_posY()))
                            {
                                _casillas[i][j].handleEvent(input);
                            }
                        }
                    }

                    if(_botonResolver._rect.contains(input.get_posX(), input.get_posY())) _botonResolver.handleEvent(input);
                    if(_botonFF._rect.contains(input.get_posX(), input.get_posY())) _botonFF.handleEvent(input);
                }

                else if(_botonVictoria._rect.contains(input.get_posX(), input.get_posY())) _botonVictoria.handleEvent(input);
                break;
            case SOLTAR:
                break;
        }
    }

    public void showSolution()
    {
        _wrongs = _t.ComprobarTablero();
        _remaining = _t.getRemaining();
        if(_wrongs == 0 && _remaining == 0) _won = true;
        else {
            _showErrors = true;
            _showTime = 3;
        }
    }

    @Override

    public boolean release() {
        _won = false;
        return true;
    }

    Tablero _t;
    int _gameHeight;
    int _gameWidth;
    boolean _showErrors = false;
    boolean _won = false;
    float _showTime = 0;
    int _tileNumber;

    int _tableroSize;
    int _tileSize;
    int _tableroX;
    int _tableroY;

    Font _f;
    int _numberFontSize;

    int _wrongs = 0;
    int _remaining = 0;

    CasillaButton[][] _casillas;
    ResuelveButton _botonResolver;
    ChangeSceneButton _botonFF;
    ChangeSceneButton _botonVictoria;
}
