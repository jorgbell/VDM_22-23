package com.nonogram.engine;

import java.util.List;
import java.util.Stack;

public class SceneManager {
    public SceneManager(Engine e){
        _myEngine = e;
        sceneStack = new Stack<Scene>();
    }

    public void render() {
        if(sceneStack.size()>0)
            sceneStack.peek().render();
    }

    public void update(double deltaTime) {
        if(sceneStack.size() == 0)
            _myEngine.stop();
        else
            sceneStack.peek().update(deltaTime);
    }

    public void getInput() {
        List<Input.TouchEvent> inputList = _myEngine.getInput().getTouchEvents();
        while(inputList.size()>0){//mientras haya input que procesar
            Input.TouchEvent aux = inputList.get(0); //cogemos el primero a procesar
            inputList.remove(0); //lo borramos de la lista
            processInput(aux); //lo procesamos
        }
    }

    public void processInput(Input.TouchEvent input) {
        if(sceneStack.size()>0)
            sceneStack.peek().processInput(input);
    }


    public int getGameWidth() {
        if(sceneStack.size()>0)
            return sceneStack.peek().getGameWidth();
        else
            return _myEngine.getGraphics().getWindowWidth();
    }

    public int getGameHeight() {
        if(sceneStack.size()>0)
            return sceneStack.peek().getGameHeight();
        else
            return _myEngine.getGraphics().getWindowHeight();    }

    public void push(Scene s) {
        sceneStack.push(s);
        s.setEngine(_myEngine);
        s.init();
    }

    public void pop() {
        sceneStack.peek().release();
        sceneStack.pop();
    }

    Stack<Scene> sceneStack;
    Engine _myEngine;
}
