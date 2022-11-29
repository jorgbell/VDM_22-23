package com.nonogram.engine;

public abstract class AbstractEngine implements Engine, Runnable {

    //clase estatica que usaremos para almacenar las direcciones de las carpetas de assets
    public static class EnginePaths{
        public EnginePaths(String r, String i, String f, String a, String b){
            _rootPath = r;
            _imagesPath = i;
            _fontsPath = f;
            _audioPath = a;
            _boardsPath = b;
        }
        public String _rootPath;
        public String _imagesPath;
        public String _fontsPath;
        public String _audioPath;
        public String _boardsPath;
    }

    protected AbstractEngine(Graphics g, Input i, Audio a, EnginePaths paths) {
        _mySceneManager = new SceneManager(this);
        _myPaths = paths;
        _myInput = i;
        _myGraphics = g;
        _myAudio = a;
    }

    @Override
    public boolean init(){

        _myGraphics.setEngine(this);
        _myAudio.setPath(_myPaths._audioPath);

        if(!_myGraphics.init() || !_myGraphics.setInputListener(_myInput)){
            return false;
        }
        return true;
    }

    @Override
    public Graphics getGraphics() {
        return _myGraphics;
    }

    @Override
    public Input getInput() {
        return _myInput;
    }

    @Override
    public Audio getAudio() {
        return _myAudio;
    }

    @Override
    public double getDeltaTime() {
        long currentTime = System.nanoTime();
        double elapsedTime = (double) (currentTime - _lastFrameTime) / 1.0E9;
        _lastFrameTime = currentTime;
        return elapsedTime;
    }

    @Override
    public boolean stop() {
        _running = false;
        release();
        System.exit(0);
        return true;
    }

    @Override
    public void run() {
        if (_myThread != Thread.currentThread()) {
            // Evita que cualquiera que no sea esta clase llame a este Runnable en un Thread
            // Programación defensiva
            throw new RuntimeException("run() should not be called directly");
        }
        // Si el Thread se pone en marcha
        // muy rápido, la vista podría todavía no estar inicializada.
        while (_running && _myGraphics.getWindowWidth() == 0) ;
        // Espera activa. Sería más elegante al menos dormir un poco.
        _lastFrameTime = System.nanoTime();

        // Bucle de juego principal.
        while (_running) {
            _mySceneManager.update(getDeltaTime());
            _mySceneManager.getInput();
            _myGraphics.render();
        }


    }

    @Override
    public boolean release() {
        _myAudio.pauseAll();
        return true;
    }

    @Override
    public void resume() {
        if (!_running) {
            // Solo hacemos algo si no nos estábamos ejecutando ya
            // (programación defensiva)
            _running = true;
            _myAudio.resumeAll();
            // Lanzamos la ejecución de nuestro método run() en un nuevo Thread.
            if(_myThread == null)
                _myThread = new Thread(this);
            _myThread.start();
        }
    }

    @Override
    public void pause() {
        if (_running) {
            _running = false;
            release();
            while (true) {
                try {
                    _myThread.join();
                    _myThread = null;
                    break;
                } catch (InterruptedException ie) {
                    // Esto no debería ocurrir nunca...
                }
            }
        }
    }

    @Override
    public SceneManager getSceneManager(){return _mySceneManager;}

    @Override
    public EnginePaths getEnginePaths(){ return _myPaths;}

    //VARIABLES
    public EnginePaths _myPaths;
    private Thread _myThread = null;
    protected Graphics _myGraphics;
    protected Input _myInput;
    protected Audio _myAudio;
    protected SceneManager _mySceneManager;
    protected long _lastFrameTime;
    protected boolean _running = false;
}
