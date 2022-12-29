package com.nonogram.engine;

import java.util.List;
import java.util.Stack;

//Clase que controla las escenas que hay en partida, tiene los mismos métodos que las escenas individuales para así llamar a la escena que toque en el momento.
public class SceneManager {
    public SceneManager(Engine e) {
        sceneStack = new Stack<Scene>();
    }

    public void render() {
        if (sceneStack.size() > 0)
            sceneStack.peek().render();
    }

    public void update(double deltaTime) {
        if (sceneStack.size() != 0)
            sceneStack.peek().update(deltaTime);
    }

    public void getInput(List<Input.TouchEvent> inputList) {
        while (inputList.size() > 0) {//mientras haya input que procesar
            Input.TouchEvent aux = inputList.get(0); //cogemos el primero a procesar
            inputList.remove(0); //lo borramos de la lista
            processInput(aux); //lo procesamos
        }
    }

    public void processInput(Input.TouchEvent input) {
        if (sceneStack.size() > 0)
            sceneStack.peek().processInput(input);
    }

    public int getGameWidth() {
        return _gameWidth;
    }

    public int getGameHeight() {
        return _gameHeight;
    }

    public void setGameSize(int w, int h) {
        _gameWidth = w;
        _gameHeight = h;
    }

    public boolean push(Scene s, Engine e) {
        if (s == null) {
            System.err.println("Error al pushear una nueva escena");
            return false;
        }
        sceneStack.push(s);
        s.setEngine(e);
        //inicializamos la escena siempre que la añadamos
        if (!s.init()) {
            System.err.println("Error al inicializar la escena");
            return false;
        }

        return true;
    }

    public boolean pop() {
        //liberamos la escena siempre que la quitemos
        if (!sceneStack.peek().release()) {
            System.err.println("Error al liberar la escena");
            return false;
        }
        sceneStack.pop();
        return true;
    }

    public boolean release() {
        while (!sceneStack.empty()) {
            if (!pop())
                return false;
        }
        return true;
    }

    public boolean empty(){
        return sceneStack.empty();
    }

    Stack<Scene> sceneStack;
    static int _gameWidth = 0;
    static int _gameHeight = 0;
}
