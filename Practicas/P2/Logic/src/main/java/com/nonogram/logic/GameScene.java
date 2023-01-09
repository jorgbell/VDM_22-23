package com.nonogram.logic;

import com.nonogram.engine.AbstractScene;
import com.nonogram.engine.Font;
import com.nonogram.engine.Image;
import com.nonogram.engine.Input;

public class GameScene extends AbstractScene {

    public GameScene(int rows, int columns, int solvablePercentage, LogicJSON.PreferencesData pref) {
        super();
        _rows = rows;
        _columns = columns;
        _solvablePercentage = solvablePercentage;
        _generado = true;
        _preferences = pref;

    }

    // Constructora para cargado
    public GameScene(int size, int level, CatScene catScene) {
        super();
        _rows = size;
        _columns = size;
        _level = level;
        _path = size + "x" + size + "/" + _level + ".json";
        _generado = false;
        _preferences = MenuScene._preferences;
        _catScene = catScene;
    }


    @Override
    public boolean init() {
        super.init();

        _currentLifes = _preferences.currentLifes;
        if (_generado) {
            _t = new TableroGenerado(_rows, _columns, _solvablePercentage);
            _t.init();
        } else {
            _t = new TableroCargado(LogicJSON.readBoardFromJSON(_path));
            _t.init();
            //si existe un estado guardado para este nivel, lo carga
            if (encuentraEstado()) {
                TableroCargado tc = (TableroCargado) _t;
                tc.cargaEstadoTablero(_preferences.estado.Estado);
            }
        }

        _gameHeight = getGameHeight();
        _gameWidth = getGameWidth();
        _casillas = new CasillaButton[_columns][_rows];

        _maxDimension = Math.max(_columns, _rows);

        _volverImage = _myEngine.getGraphics().newImage("Arrow.png");
        _f = _myEngine.getGraphics().newFont("JosefinSans-Bold.ttf", 15, false);
        //init tamaño del tablero
        ResizeElements();

        if (_f == null || _messageFont == null || _volverImage == null)
            return false;

        return true;
    }

    private boolean encuentraEstado() {
        if (_preferences.estado.size == _catScene._size && _preferences.estado.level == _level)
            return true;
        return false;
    }

    @Override
    public void rotate() {
        super.rotate();
        _gameWidth = super.getGameWidth();
        _gameHeight = super.getGameHeight();

        ResizeElements();
    }

    @Override
    public void render() {

        _myEngine.getGraphics().setColor(LogicJSON.Palette.toInt(_preferences.unlockedPalettes.get(_preferences.actualPalette).textColor));

        if (!_end) //durante la partida
        {

            //UI
            _myEngine.getGraphics().setActualFont(_messageFont);

            _botonRendirse.render(_myEngine.getGraphics());
            _f.setSize(_numberFontSize);
            _myEngine.getGraphics().setActualFont(_f);
            
            //Numeros
            int columnaSpace = _tileSize;
            int columnaXMargin = _tileSize / 5;
            int columnaYMargin = _tableroSize / 30;
            int columnaInterSpace = _numberFontSize * 10 / 9;

            int filaSpace = _numberFontSize * 10 / 9;
            int filaXMargin = _tableroSize / 30;
            int filaYMargin = _tileSize / 5;
            int filaInterSpace = _tileSize;
            _myEngine.getGraphics().setColor(LogicJSON.Palette.toInt(_preferences.unlockedPalettes.get(_preferences.actualPalette).textColor));

            for (int i = 0; i < _columns; i++) {
                String[] sc = _t._columnas[i].numbers.split("\\.");
                for (int j = sc.length - 1; j >= 0; j--)
                    _myEngine.getGraphics().drawText(sc[(sc.length - 1) - j], _tableroX - columnaXMargin + _tileSize / 2 + columnaSpace * i, (_tableroY - columnaYMargin) - columnaInterSpace * j);
            }

            for (int i = 0; i < _rows; i++) {
                String[] sf = _t._filas[i].numbers.split("\\.");
                for (int j = sf.length - 1; j >= 0; j--)
                    _myEngine.getGraphics().drawText(sf[(sf.length - 1) - j], (_tableroX - filaXMargin) - filaSpace * (j + 1), _tableroY + filaYMargin + _tileSize / 2 + filaInterSpace * i);
            }

            _myEngine.getGraphics().setActualFont(_messageFont);
            _myEngine.getGraphics().setColor(LogicJSON.Palette.toInt(_preferences.unlockedPalettes.get(_preferences.actualPalette).hlColor));

            String lifeText;
            if (_currentLifes != 1) lifeText = " vidas"; else lifeText = " vida";
            _myEngine.getGraphics().drawText("Te quedan " + Integer.toString(_currentLifes - 1) + lifeText, _messageX , _messageY + _messageFont.getSize() * 2);

            if (_showErrors) //texto al pulsar comprobar
            {
                _myEngine.getGraphics().drawText("Te faltan " + _remaining + " casillas", _messageX, _messageY);
                _myEngine.getGraphics().setActualFont(_f);
            }
        } else //fin de la partida
        {
            String finalText;
            if (!_won){
                finalText = "HAS PERDIDO";
                _botonAd.render(_myEngine.getGraphics());
            }
            else{
                finalText = "ENHORABUENA";
                if (!_generado) _botonCompartir.render(_myEngine.getGraphics());
            }
            _f.setSize(40);
            _myEngine.getGraphics().setColor(LogicJSON.Palette.toInt(_preferences.unlockedPalettes.get(_preferences.actualPalette).textColor));
            _myEngine.getGraphics().drawText(finalText, _finalTextX, _finalTextY);

            _myEngine.getGraphics().setActualFont(_messageFont);
            _messageFont.setSize((int)(_messageFont.getSize() * 0.8));
            if (paletaDesbloqueada) {
                _myEngine.getGraphics().drawText("¡Desbloqueaste una nueva paleta!", _paletaX, _paletaY);
                _myEngine.getGraphics().drawText("Cámbiala en el menú principal.", _paletaX, _paletaY + _messageFont.getSize() * 2);
            }
            _messageFont.setSize((int)(_messageFont.getSize() / 0.8));
            _myEngine.getGraphics().setActualFont(_f);
            _botonVictoria.render(_myEngine.getGraphics());
        }

        for (int i = 0; i < _columns; i++) //renderizado de las casillas fisicas
        {
            for (int j = 0; j < _rows; j++) {
                Tablero.State s = _t.getCasilla(j, i).getState();
                if (!_end || s == Tablero.State.PICK)
                    _casillas[i][j].render(_myEngine.getGraphics());
            }
        }

    }

    @Override
    public void update(double deltaTime) {
        MenuScene.changeToDarkMode();

        if (_showErrors) {
            _showTime -= deltaTime;
            if (_showTime <= 0) {
                _t.LimpiarErrores(_wrong.y, _wrong.x);
                _wrong = null;
                _showErrors = false;
                _currentLifes--;
                if (_currentLifes == 0) {
                    _end = true;
                    _won = false;
                }
            }
        }
    }

    @Override
    public void processInput(Input.TouchEvent input) {
        if (!_end) //si la partida no ha terminado
        {
            if (!_showErrors) { //si no se esta mostrando los errores, te deja hacer pulsaciones a botones
                //o pulsas el de volver, o pulsas una casilla
                if (_botonRendirse._rect.contains(input.get_posX(), input.get_posY()))
                    _botonRendirse.handleEvent(input);
                else {
                    boolean clicked = false;
                    int i = 0;
                    int j;
                    while (!clicked && i < _casillas.length) {
                        j = 0;
                        while (!clicked && j < _casillas[0].length) {
                            if (_casillas[i][j]._rect.contains(input.get_posX(), input.get_posY())) {
                                clicked = true;
                                canPersist = true;
                                _casillas[i][j].handleEvent(input);
                                checkError(i, j);
                            }
                            j++;
                        }
                        i++;
                    }
                }
            }
        } else {
            if (_botonVictoria._rect.contains(input.get_posX(), input.get_posY()))
                _botonVictoria.handleEvent(input);
            if(_won && !_generado && _botonCompartir._rect.contains(input.get_posX(), input.get_posY()))
                _botonCompartir.handleEvent(input);
            if(!_won && _botonAd._rect.contains(input.get_posX(), input.get_posY()))
                _botonAd.handleEvent(input);

        }
    }

    public void checkError(int fila, int columna) {
        if (_t.checkCasilla(columna, fila)) {
            //es correcta la pulsacion
            _remaining = _t.getRemaining();
            if (_remaining == 0) {
                _end = true;
                _won = true;
                if (!_generado) {
                    //si devuelve true, es que ha desbloqueado una paleta. Asi mostramos el texto en pantalla.
                    //Si devuelve false es en cualquier otro caso
                    if (_catScene.increaseLevel(_level)) {
                        paletaDesbloqueada = true;
                    }
                }
            }
        } else {
            //es incorrecta la pulsacion
            //marca una espera en la que se muestra la casilla mala
            _remaining = _t.getRemaining();
            _showErrors = true;
            _showTime = 1f;
            _wrong = _t.getCasilla(columna, fila);
            _wrong.estado = Tablero.State.WRONG;
        }
    }

    @Override
    public boolean release() {
        persist();
        _showTime = 0;
        _end = false;
        canPersist = false;
        return true;
    }

    @Override
    public boolean persist() {
        if (!_generado) {
            //si puedes realizar la persistencia del estado del tablero (ha colocado al menos un cuadrado), la realiza.
            if(canPersist){
                LogicJSON.EstadoData e = new LogicJSON.EstadoData();
                //si no ha terminado la partida, debe guardar el estado. Si la termina, lo debe sustituir por uno vacio
                if (!_end) {
                    //crea el estado actual
                    TableroCargado tc = (TableroCargado) _t;
                    e.level = _level;
                    e.size = _catScene._size;
                    e.Estado = tc.guardaEstadoTablero();
                }
                _preferences.estado = e;
            }
        }
        _preferences.currentLifes = _currentLifes;
        LogicJSON.writePreferencesToJson("preferences.json", _preferences);
        return false;
    }

    @Override
    public void handleClosingNotifications() {

    }

    @Override
    public void handleOpeningNotifications() {

    }

    @Override
    public void handleAdd() {
        _preferences.currentLifes++;

    }

    @Override
    public void ResizeElements()
    {
        super.ResizeElements();

        _gameHeight = getGameHeight();
        _gameWidth = getGameWidth();
        ChangeSceneButton auxVictoria;
        ChangeSceneButton auxRendirse;
        RewarderButton auxAd;
        ShareImageButton auxCompartir;

        // Valores a cambiar
        if(!super.landscape) {
            _tableroSize = (_gameWidth / 20) * 14;
            _tableroX = (_gameWidth / 20) * 5;
            _tableroY = (_gameHeight / 20) * 8;
            _finalTextX = _gameWidth / 2;
            _finalTextY = _gameHeight / 6;
            _messageX = _gameWidth * 6 / 11;
            _messageY = _gameHeight / 5;
            _paletaX = _gameWidth * 6 / 11;
            _paletaY = _gameHeight / 4;


            _messageFont = _myEngine.getGraphics().newFont("JosefinSans-Bold.ttf", _gameWidth / 15, false);

            auxVictoria =  new ChangeSceneButton(_gameWidth / 6, _gameHeight * 17 / 20, _gameWidth * 2 / 6, _gameHeight / 10, null);
            auxRendirse =  new ChangeSceneButton(_gameWidth / 10, _gameHeight / 20, _gameWidth * 2 / 7, _gameHeight / 15, null);
            auxAd = new RewarderButton(_gameWidth * 11 / 20, _gameHeight * 17 / 20, _gameWidth * 2 / 5, _gameHeight / 10);
            auxCompartir = new ShareImageButton(_gameWidth * 11 / 20, _gameHeight * 17 / 20, _gameWidth * 2 / 5, _gameHeight / 10, _myEngine.getIntentManager(), _rows + "x" + _columns + "/" + _level + ".png");

        }
        else {
            _tableroSize = (_gameHeight / 20) * 14;
            _tableroX = (_gameWidth / 20) * 8;
            _tableroY = (_gameHeight / 20) * 4;
            _finalTextX = _gameWidth / 5;
            _finalTextY = _gameHeight * 1 / 5;
            _messageX = _gameWidth / 7;
            _messageY = _gameHeight / 3;
            _paletaX = _gameWidth * 4 / 20;
            _paletaY = _gameHeight * 2 / 5;

            _messageFont = _myEngine.getGraphics().newFont("JosefinSans-Bold.ttf", _gameWidth / 30, false);

            auxVictoria =  new ChangeSceneButton(_gameWidth / 15, _gameHeight * 8 / 10, _gameHeight * 3 / 7, _gameWidth / 15, null);
            auxRendirse =  new ChangeSceneButton(_gameWidth / 20, _gameHeight / 15, _gameWidth /7, _gameHeight / 8, null);
            auxAd = new RewarderButton(_gameWidth / 15, _gameHeight * 13 / 20, _gameHeight * 3 / 7, _gameWidth / 15);
            auxCompartir = new ShareImageButton(_gameWidth / 15, _gameHeight * 13 / 20, _gameHeight * 3 / 7, _gameWidth / 15, _myEngine.getIntentManager(), _rows + "x" + _columns + "/" + _level + ".png");

        }

        _tileSize = _tableroSize / _maxDimension;
        _numberFontSize = 45 - (int) (5.7 * (Math.log(_maxDimension) / Math.log(1.595)));

        for (int i = 0; i < _columns; i++) //se crean las casillas "fisicas"
        {
            for (int j = 0; j < _rows; j++) {

                if(_casillas[i][j] == null) _casillas[i][j] = new CasillaButton(_tableroX + _tileSize * i, _tableroY + _tileSize * j, _tileSize - 2, _tileSize - 2, _t.getCasilla(j, i));
                else _casillas[i][j].setDimensions(_tableroX + _tileSize * i, _tableroY + _tileSize * j, _tileSize - 2, _tileSize - 2);
            }
        }

        _myEngine.getGraphics().setActualFont(_f);

        if(_botonRendirse == null) {
            _botonRendirse = auxRendirse;
            _botonRendirse.addImage(_volverImage, 0.04, Button.ImagePos.LEFT);
            _botonRendirse.addText("Volver");
        }
        else _botonRendirse.setDimensions(auxRendirse._rect._x, auxRendirse._rect._y, auxRendirse._rect._w, auxRendirse._rect._h);

        if(_botonVictoria == null) {
            _botonVictoria = auxVictoria;
            _botonVictoria.addImage(_volverImage, 0.04, Button.ImagePos.LEFT);
            _botonVictoria.addText("Volver");
        }
        else _botonVictoria.setDimensions(auxVictoria._rect._x, auxVictoria._rect._y, auxVictoria._rect._w, auxVictoria._rect._h);

        if(_botonAd == null){
            _botonAd = auxAd;
            _botonAd.addText("+1 Vida");
        }
        else _botonAd.setDimensions(auxAd._rect._x, auxAd._rect._y, auxAd._rect._w, auxAd._rect._h);

        if (!_generado) {
            if(_botonCompartir == null){
                _botonCompartir = auxCompartir;
                _botonCompartir.addText("Compartir");
            }
            else _botonCompartir.setDimensions(auxCompartir._rect._x, auxCompartir._rect._y, auxCompartir._rect._w, auxCompartir._rect._h);
        }


    }

    Tablero _t;
    int _gameHeight;
    int _gameWidth;
    boolean _showErrors = false;
    boolean _end = false;
    float _showTime = 0;
    int _rows;
    int _columns;
    int _maxDimension;

    int _solvablePercentage;

    int _tableroSize;
    int _tileSize;
    int _tableroX;
    int _tableroY;

    int _finalTextX;
    int _finalTextY;

    int _messageX;
    int _messageY;

    int _paletaX;
    int _paletaY;

    String _file;
    Font _f;
    Font _messageFont;
    Image _volverImage;
    int _numberFontSize;

    int _remaining = 0;

    CasillaButton[][] _casillas;
    ChangeSceneButton _botonRendirse;
    ChangeSceneButton _botonVictoria;
    RewarderButton _botonAd;
    ShareImageButton _botonCompartir;

    boolean _generado;
    String _path;

    Tablero.Casilla _wrong;

    boolean _won = false;
    int _currentLifes;

    LogicJSON.PreferencesData _preferences;
    CatScene _catScene;
    int _level;

    boolean paletaDesbloqueada = false;
    boolean canPersist = false;
}
