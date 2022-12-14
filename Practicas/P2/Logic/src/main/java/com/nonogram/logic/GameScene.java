package com.nonogram.logic;

import com.nonogram.engine.AbstractScene;
import com.nonogram.engine.Font;
import com.nonogram.engine.Image;
import com.nonogram.engine.Input;

public class GameScene extends AbstractScene {

    public GameScene(int gameWidth, int gameHeight, int rows, int columns, int solvablePercentage, LogicJSON.PreferencesData pref) {
        super(gameWidth, gameHeight);
        _rows = rows;
        _columns = columns;
        _solvablePercentage = solvablePercentage;
        _generado = true;
        _preferences = pref;

    }

    // Constructora para cargado
    public GameScene(int gameWidth, int gameHeight, int size, int level, CatScene catScene) {
        super(gameWidth, gameHeight);
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

        _f = _myEngine.getGraphics().newFont("JosefinSans-Bold.ttf", 20, false);
        _volverImage = _myEngine.getGraphics().newImage("Arrow.png");
        if (_f == null || _volverImage == null)
            return false;


        _gameHeight = getGameHeight();
        _gameWidth = getGameWidth();
        _casillas = new CasillaButton[_columns][_rows];

        _maxDimension = Math.max(_columns, _rows);

        //init tamaño del tablero
        _tableroSize = (_gameWidth / 20) * 14;
        _tileSize = _tableroSize / _maxDimension;
        _tableroX = (_gameWidth / 20) * 5;
        _tableroY = (_gameHeight / 20) * 8;
        _numberFontSize = 45 - (int) (5.7 * (Math.log(_maxDimension) / Math.log(1.595)));
        _myEngine.getGraphics().setActualFont(_f);

        for (int i = 0; i < _columns; i++) //se crean las casillas "fisicas"
        {
            for (int j = 0; j < _rows; j++) {
                _casillas[i][j] = new CasillaButton(_tableroX + _tileSize * i, _tableroY + _tileSize * j, _tileSize - 2, _tileSize - 2, _t.getCasilla(j, i));
            }
        }

        //botones de ui
        _botonRendirse = new ChangeSceneButton(_gameWidth / 10, _gameHeight / 20, _gameWidth * 2 / 5, _gameHeight / 15, null);
        _botonRendirse.addImage(_volverImage, 0.04, Button.ImagePos.LEFT);
        _botonRendirse.addText("Volver");


        _botonVictoria = new ChangeSceneButton(_gameWidth * 2 / 5, _gameHeight * 8 / 10, _gameWidth * 2 / 7, _gameHeight / 15, null);
        _botonVictoria.addImage(_volverImage, 0.04, Button.ImagePos.LEFT);
        _botonVictoria.addText("Volver");

        if (!_generado) {
            _botonCompartir = new ShareImageButton(_gameWidth * 4 / 5, _gameHeight * 8 / 10, _gameWidth * 1 / 7, _gameHeight / 15, _myEngine.getIntentManager(), _rows + "x" + _columns + "/" + _level + ".png");
            _botonCompartir.addText("Compartir");
        }

        return true;
    }

    private boolean encuentraEstado() {
        if (_preferences.estado.size == _catScene._size && _preferences.estado.level == _level)
            return true;
        return false;
    }

    @Override
    public void render() {

        _myEngine.getGraphics().setColor(LogicJSON.Palette.toInt(_preferences.unlockedPalettes.get(_preferences.actualPalette).textColor));

        if (!_end) //durante la partida
        {
            //Rectagulos para poner los numeros y el tablero
            _myEngine.getGraphics().drawRect((_tableroX - _gameWidth / 5) + 5, _tableroY - 3, (_tileSize * _columns) + 5 + _tableroX - (_gameWidth / 16), _tableroSize);
            _myEngine.getGraphics().drawRect(_tableroX - 3, _tableroY - _gameHeight / 10, (_tileSize * _columns) + 5, _tableroY + _tableroSize - (_gameHeight * 76 / 250));

            //UI
            _f.setSize(20);
            _botonRendirse.render(_myEngine.getGraphics());

            _f.setSize(_numberFontSize);

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

            if (_showErrors) //texto al pulsar comprobar
            {
                _f.setSize(20);
                _myEngine.getGraphics().setColor(LogicJSON.Palette.toInt(_preferences.unlockedPalettes.get(_preferences.actualPalette).hlColor));
                _myEngine.getGraphics().drawText("Te faltan " + _remaining + " casillas", _gameWidth / 2, _gameHeight / 5);
                _myEngine.getGraphics().drawText("Te quedan " + Integer.toString(_currentLifes - 1) + " vidas", _gameWidth / 2, _gameHeight / 4);
            }
        } else //fin de la partida
        {
            String finalText;
            if (!_won) finalText = "HAS PERDIDO";
            else finalText = "ENHORABUENA";
            _f.setSize(40);
            _myEngine.getGraphics().setColor(LogicJSON.Palette.toInt(_preferences.unlockedPalettes.get(_preferences.actualPalette).textColor));
            _myEngine.getGraphics().drawText(finalText, _gameWidth / 2, _gameHeight / 4);
            _f.setSize(20);
            if (paletaDesbloqueada) {
                _myEngine.getGraphics().drawText("¡Desbloqueaste una nueva paleta!", _gameWidth / 2, _gameHeight / 8);
                _myEngine.getGraphics().drawText("Cámbiala en el menú principal.", _gameWidth / 2, _gameHeight / 6);
            }
            _botonVictoria.render(_myEngine.getGraphics());
            if (!_generado) _botonCompartir.render(_myEngine.getGraphics());
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
            if (!_generado && _botonCompartir._rect.contains(input.get_posX(), input.get_posY()))
                _botonCompartir.handleEvent(input);
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
            //marca una espera de 2 segundos en la que se muestra la casilla mala
            _remaining = _t.getRemaining();
            _showErrors = true;
            _showTime = 2;
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

    String _file;
    Font _f;
    Image _volverImage;
    int _numberFontSize;

    int _remaining = 0;

    CasillaButton[][] _casillas;
    ChangeSceneButton _botonRendirse;
    ChangeSceneButton _botonVictoria;
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
