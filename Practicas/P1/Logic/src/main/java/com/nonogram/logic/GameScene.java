package com.nonogram.logic;

import com.nonogram.engine.AbstractScene;
import com.nonogram.engine.Font;
import com.nonogram.engine.Image;
import com.nonogram.engine.Input;

public class GameScene extends AbstractScene {

    public GameScene(int rows, int columns, int solvablePercentage)
    {
        super();
        _rows = rows;
        _columns = columns;
        _solvablePercentage = solvablePercentage;
    }

    @Override
    public boolean init() {
        super.init();

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
        _tileSize = _tableroSize / _maxDimension;
        if(_rows != _columns) _tileSize =  (int)((_tableroSize / _maxDimension) * 1.2);
        _tableroX = _gameWidth / 2 - (int)((float)_tileSize * ((float)_columns / 2));
        _tableroY = _gameHeight / 2 - (int)((float)_tileSize * ((float)_rows / 2));
        _t = new Tablero();
        _t.init(_rows, _columns, _solvablePercentage);

        _numberFontSize = (int)(_gameWidth / 10.4) - (int)((_gameWidth / 90) * (Math.log(_maxDimension) / Math.log(1.595)));
        _myEngine.getGraphics().setActualFont(_f);

        for (int i = 0; i < _columns; i++) //se crean las casillas "fisicas"
        {
            for (int j = 0; j < _rows; j++)
            {
                _casillas[i][j] = new CasillaButton(_tableroX + _tileSize * i, _tableroY + _tileSize * j, _tileSize - 2, _tileSize - 2, _t.getCasilla(j, i));
            }
        }

        //botones de ui
        _botonResolver = new ResuelveButton( _gameWidth * 3 / 5, _gameHeight / 20 , _gameWidth * 2 / 7, _gameHeight / 15, this, _resolverImage);
        _botonFF = new ChangeSceneButton( _gameWidth / 10, _gameHeight / 20 , _gameWidth * 2 / 7, _gameHeight / 15, "Rendirse", _myEngine, _volverImage);
        _botonVictoria = new ChangeSceneButton(_gameWidth * 2 / 5, _gameHeight * 8 / 10, _gameWidth * 2 / 7, _gameHeight / 15, "Volver", _myEngine, _volverImage);
        return true;
    }

    @Override
    public void render() {

        _myEngine.getGraphics().setColor(0XFF000000);

        if(!_won) //durante la partida
        {
            //UI
            _f.setSize(_gameWidth / 22);
            _botonResolver.render(_myEngine.getGraphics());
            _botonFF.render(_myEngine.getGraphics());

            _f.setSize(_numberFontSize);

            //Numeros
            int columnaSpace = _tileSize;
            int columnaXMargin =  _tileSize / 5;
            int columnaYMargin =  _tableroSize / 30;
            int columnaInterSpace = _numberFontSize * 10 / 9;

            int filaSpace = _numberFontSize * 8 / 9;
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
                _f.setSize(_gameWidth / 22);
                _myEngine.getGraphics().setColor(0XFFFF0000);
                _myEngine.getGraphics().drawText("Te faltan " + _remaining + " casillas", _gameWidth / 3, _gameHeight * 16 / 20);
                _myEngine.getGraphics().drawText("Tienes mal " + _wrongs + " casillas", _gameWidth / 3, _gameHeight * 17 / 20);
            }
        }
        else //fin de la partida
        {
            _f.setSize(_gameWidth / 11);
            _myEngine.getGraphics().setColor(0XFF000000);
            _myEngine.getGraphics().drawText("ENHORABUENA", _gameWidth / 6, _gameHeight * 4 / 20);
            _f.setSize(_gameWidth / 22);
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

    private Tablero _t;
    private int _gameHeight;
    private int _gameWidth;
    private boolean _showErrors = false;
    private boolean _won = false;
    private float _showTime = 0;
    private int _rows;
    private int _columns;
    private int _maxDimension;

    private int _solvablePercentage;

    private int _tableroSize;
    private int _tileSize;
    private int _tableroX;
    private int _tableroY;

    private Font _f;
    private Image _volverImage;
    private Image _resolverImage;
    private int _numberFontSize;

    private int _wrongs = 0;
    private int _remaining = 0;

    private CasillaButton[][] _casillas;
    private ResuelveButton _botonResolver;
    private ChangeSceneButton _botonFF;
    private ChangeSceneButton _botonVictoria;
}
