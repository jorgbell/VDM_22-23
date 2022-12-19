package com.nonogram.engine;

import java.util.List;
import java.util.Stack;

//Clase que controla las escenas que hay en partida, tiene los mismos métodos que las escenas individuales para así llamar a la escena que toque en el momento.
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

    public void handelClosingNotifications(){
        if(sceneStack.size()>0)
            sceneStack.peek().handleClosingNotifications();
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
            return _myEngine.getGraphics().getWindowHeight();
    }

    public boolean push(Scene s) {
        if(s==null){
            System.err.println("Error al pushear una nueva escena");
            return false;
        }
        sceneStack.push(s);
        s.setEngine(_myEngine);
        //inicializamos la escena siempre que la añadamos
        if(!s.init()){
            System.err.println("Error al inicializar la escena");
            return false;
        }

        return true;
    }

    public boolean pop() {
        //liberamos la escena siempre que la quitemos
        if(!sceneStack.peek().release()){
            System.err.println("Error al liberar la escena");
            return false;
        }
        handelClosingNotifications();
        sceneStack.pop();
        return true;
    }

    public boolean release(){
        while(!sceneStack.empty()){
            if(!pop())
                return false;
        }
        return true;
    }

    public boolean persist(){
        if(sceneStack.size()>0){
            sceneStack.peek().persist();
            return true;
        }
        return false;
    }

    Stack<Scene> sceneStack;
    Engine _myEngine;
}
