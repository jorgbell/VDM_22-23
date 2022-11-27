package com.nonogram.logic;

import com.nonogram.engine.AbstractScene;
import com.nonogram.engine.Font;
import com.nonogram.engine.Image;
import com.nonogram.engine.Input;

public class GameScene extends AbstractScene {

    public GameScene(int gameWidth, int gameHeight, int rows, int columns, int solvablePercentage)
    {
        super(gameWidth,gameHeight);
        _rows = rows;
        _columns = columns;
        _solvablePercentage = solvablePercentage;
        //_t = new TableroGenerado(_rows, _columns, _solvablePercentage);

    }
    // Constructora para cargado
    public GameScene(int gameWidth, int gameHeight, int size, int level)
    {
        super(gameWidth,gameHeight);
        _rows = size;
        _columns = size;
        _solvablePercentage = 1;
        _level = level; //nivel a cargar en la categoria
        //_t = new TableroCargado(_myEngine.getGraphics().newBoard("10x10/302.json"));

    }

    public GameScene(int gameWidth, int gameHeight, String file)
    {
        super(gameWidth,gameHeight);
        _file = file;
    }


    @Override
    public boolean init() {
        //_t = new TableroGenerado(_rows, _columns, _solvablePercentage);


        _t = new TableroCargado(_myEngine.getGraphics().newBoard("10x10/302.json"));
        _t.init();

        _f = _myEngine.getGraphics().newFont("JosefinSans-Bold.ttf", 20, false);
        _volverImage = _myEngine.getGraphics().newImage("Arrow.png");
        _resolverImage = _myEngine.getGraphics().newImage("Lupa.png");
        if(_f == null || _volverImage == null || _resolverImage == null)
            return false;


        _gameHeight = getGameHeight();
        _gameWidth = getGameWidth();
        _casillas = new CasillaButton[_columns][_rows];

        _maxDimension = Math.max(_columns, _rows);

        //init tamaño del tablero
        _tableroSize = (_gameWidth / 20) * 14;
        _tileSize =  _tableroSize / _maxDimension;
        _tableroX = (_gameWidth / 20) * 5;
        _tableroY = (_gameHeight / 20) * 8;
        _numberFontSize = 45 - (int)(5.7 * (Math.log(_maxDimension) / Math.log(1.595)));
        _myEngine.getGraphics().setActualFont(_f);

        for (int i = 0; i < _columns; i++) //se crean las casillas "fisicas"
        {
            for (int j = 0; j < _rows; j++)
            {
                _casillas[i][j] = new CasillaButton(_tableroX + _tileSize * i, _tableroY + _tileSize * j, _tileSize - 2, _tileSize - 2, _t.getCasilla(j, i));
            }
        }

        //botones de ui
        _botonResolver = new ResuelveButton( _gameWidth * 3 / 5, _gameHeight / 20 , _gameWidth * 2 / 5, _gameHeight / 15, this, _resolverImage);
        _botonFF = new ChangeSceneButton( _gameWidth / 10, _gameHeight / 20 , _gameWidth * 2 / 5, _gameHeight / 15, "Rendirse", _myEngine, _volverImage);
        _botonVictoria = new ChangeSceneButton(_gameWidth * 2 / 5, _gameHeight * 8 / 10, _gameWidth * 2 / 7, _gameHeight / 15, "Volver", _myEngine, _volverImage);
        return true;
    }

    @Override
    public void render() {

        _myEngine.getGraphics().setColor(0XFF000000);

        if(!_won) //durante la partida
        {
            //Rectagulos para poner los numeros y el tablero
            _myEngine.getGraphics().drawRect((_tableroX - _gameWidth / 5) + 5, _tableroY - 3, (_tileSize * _columns) + 5 + _tableroX - (_gameWidth / 16), _tableroSize);
            _myEngine.getGraphics().drawRect(_tableroX - 3, _tableroY - _gameHeight / 10, (_tileSize * _columns) + 5, _tableroY + _tableroSize - (_gameHeight * 76 / 250) );

            //UI
            _f.setSize(20);
            _botonResolver.render(_myEngine.getGraphics());
            _botonFF.render(_myEngine.getGraphics());

            _f.setSize(_numberFontSize);

            //Numeros
            int columnaSpace = _tileSize;
            int columnaXMargin =  _tileSize / 5;
            int columnaYMargin =  _tableroSize / 30;
            int columnaInterSpace = _numberFontSize * 10 / 9;

            int filaSpace = _numberFontSize * 10 / 9;
            int filaXMargin = _tableroSize / 30;
            int filaYMargin = _tileSize / 5;
            int filaInterSpace =_tileSize;
            _myEngine.getGraphics().setColor(0XFF000000);

            for(int i = 0; i < _columns; i++)
            {
                String[] sc = _t._columnas[i].numbers.split("\\.");
                for(int j = sc.length - 1; j >= 0; j--) _myEngine.getGraphics().drawText(sc[(sc.length - 1) - j], _tableroX - columnaXMargin + _tileSize / 2 + columnaSpace * i, (_tableroY - columnaYMargin) - columnaInterSpace * j);
            }

            for(int i = 0; i < _rows; i++)
            {
                String[] sf = _t._filas[i].numbers.split("\\.");
                for(int j = sf.length - 1; j >= 0; j--) _myEngine.getGraphics().drawText(sf[(sf.length - 1) - j],(_tableroX - filaXMargin) - filaSpace * (j + 1), _tableroY + filaYMargin + _tileSize / 2 + filaInterSpace * i);
            }

            if(_showErrors) //texto al pulsar comprobar
            {
                _f.setSize(20);
                _myEngine.getGraphics().setColor(0XFFFF0000);
                _myEngine.getGraphics().drawText("Te faltan " + _remaining + " casillas", _gameWidth / 2, _gameHeight/5);
                _myEngine.getGraphics().drawText("Tienes mal " + _wrongs + " casillas", _gameWidth / 2, _gameHeight/4);
            }
        }
        else //fin de la partida
        {
            _f.setSize(40);
            _myEngine.getGraphics().setColor(0XFF000000);
            _myEngine.getGraphics().drawText("ENHORABUENA", _gameWidth / 2, _gameHeight/4);
            _f.setSize(20);
            _botonVictoria.render(_myEngine.getGraphics());
        }

        for (int i = 0; i < _columns; i++) //renderizado de las casillas fisicas
        {
            for (int j = 0; j < _rows; j++)
            {
                Tablero.State s = _t.getCasilla(j, i).getState();
                if(!_won || s == Tablero.State.PICK) _casillas[i][j].render(_myEngine.getGraphics());
            }
        }

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
        _showTime = 0;
        _won = false;
        return true;
    }

    Tablero _t;
    int _gameHeight;
    int _gameWidth;
    boolean _showErrors = false;
    boolean _won = false;
    float _showTime = 0;
    int _rows;
    int _columns;
    int _maxDimension;

    int _solvablePercentage;

    int _tableroSize;
    int _tileSize;
    int _tableroX;
    int _tableroY;

    String _file;
    Font _f;
    Image _volverImage;
    Image _resolverImage;
    int _numberFontSize;

    int _wrongs = 0;
    int _remaining = 0;

    CasillaButton[][] _casillas;
    ResuelveButton _botonResolver;
    ChangeSceneButton _botonFF;
    ChangeSceneButton _botonVictoria;

    int _level;
}
