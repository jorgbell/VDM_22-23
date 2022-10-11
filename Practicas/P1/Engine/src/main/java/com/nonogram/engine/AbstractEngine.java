package com.nonogram.engine;

public abstract class AbstractEngine implements Engine{

    protected AbstractEngine(){

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
        double elapsedTime = (double)(currentTime - _lastFrameTime) / 1.0E9;
        _lastFrameTime = currentTime;
        return elapsedTime;    }

    @Override
    public boolean stop() {
        _running = false;
        return true;
    }


    //VARIABLES
    protected Graphics _myGraphics;
    protected Input _myInput;
    protected Audio _myAudio;
    protected long _lastFrameTime;
    protected volatile boolean _running = false;
}
