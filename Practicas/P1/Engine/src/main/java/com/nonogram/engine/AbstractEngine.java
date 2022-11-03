package com.nonogram.engine;

public abstract class AbstractEngine implements Engine, Runnable /*Arreglar el tema del run. En el metodo run() de Runnable deberia lanzar el run() de PCEngine y AndroidEngine.*/ {

    public static class EnginePaths{
        public EnginePaths(String r, String i, String f){
            _rootPath = r;
            _imagesPath = i;
            _fontsPath = f;
        }
        public String _rootPath;
        public String _imagesPath;
        public String _fontsPath;
    }

    //TODO: meter input etcetc
    protected AbstractEngine(Graphics g, Input i, Scene inicial, EnginePaths paths) {
        _myPaths = paths;
        _myInput = i;
        _myGraphics = g;
        _myGraphics.setPaths(_myPaths);
        _myGraphics.setInputListener(_myInput);
        setScene(inicial);
        inicial.setEngine(this);
        _myScene.init();

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
            _myScene.update(getDeltaTime());
            _myScene.getInput();
            _myGraphics.render();
        }
    }

    @Override
    public void resume() {
        if (!_running) {
            // Solo hacemos algo si no nos estábamos ejecutando ya
            // (programación defensiva)
            _running = true;
            // Lanzamos la ejecución de nuestro método run() en un nuevo Thread.
            _myThread = new Thread(this);
            _myThread.start();
        }
    }

    @Override
    public void pause() {
        if (_running) {
            _running = false;
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
    public void setScene(Scene s) {
        _myScene = s;
        _myGraphics.setScene(s);
    }


    //VARIABLES
    protected EnginePaths _myPaths;
    private Thread _myThread;
    protected Graphics _myGraphics;
    protected Input _myInput;
    protected Audio _myAudio;
    protected Scene _myScene;
    protected long _lastFrameTime;
    protected volatile boolean _running = false;
}
