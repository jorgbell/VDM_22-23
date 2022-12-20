package com.nonogram.engine;

import java.util.Stack;

public abstract class AbstractEngine implements Engine, Runnable {

    //clase estatica que usaremos para almacenar las direcciones de las carpetas de assets
    public static class EnginePaths{
        public EnginePaths(String r, String i, String f, String a, String j){
            _rootPath = r;
            _imagesPath = i;
            _fontsPath = f;
            _audioPath = a;
            _JSONPath = j;
        }
        public String _rootPath;
        public String _imagesPath;
        public String _fontsPath;
        public String _audioPath;
        public String _JSONPath;
    }

    protected AbstractEngine(Graphics g, Input i, Audio a, JSONManager j, AbstractSensors s, NotificationMngr nmng, IntentManager in, EnginePaths paths) {
        _mySceneManager = new SceneManager(this);
        _myPaths = paths;
        _myInput = i;
        _myGraphics = g;
        _myAudio = a;
        _myJSONManager = j;
        _mySensors = s;
        _myNotificationManager = nmng;
        _myIntentManager = in;
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
    public void close(){
        System.exit(0);
    }

    @Override
    public AbstractSensors getSensors() {
        return _mySensors;
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
    public NotificationMngr getNotificationManager() {
        return _myNotificationManager;
    }

    @Override
    public JSONManager getJSONManager(){return _myJSONManager;}

    public IntentManager getIntentManager(){return _myIntentManager;}

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
        _mySceneManager.release();
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
            _myAudio.pauseAll();
            _mySceneManager.persist();
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
    public void addClosingNotification(NotificationData data) {
        closingNotifications.push(data);
    }

    @Override
    public Stack<NotificationData> getClosingNotifications(){ return closingNotifications;}

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
    protected AbstractSensors _mySensors;
    protected SceneManager _mySceneManager;
    protected JSONManager _myJSONManager;
    protected NotificationMngr _myNotificationManager;
    protected IntentManager _myIntentManager;
    protected long _lastFrameTime;
    protected boolean _running = false;
    protected Stack<NotificationData> closingNotifications = new Stack<NotificationData>();
}
