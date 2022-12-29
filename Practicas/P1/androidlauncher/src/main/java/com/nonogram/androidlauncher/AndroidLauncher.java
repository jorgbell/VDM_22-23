package com.nonogram.androidlauncher;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.nonogram.androidengine.AndroidEngine;
import com.nonogram.logic.MenuScene;

public class AndroidLauncher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _myEngine = new AndroidEngine(this);
        //manejo de errores: si se crea mal algo, para antes de empezar.
        if(!_myEngine.init()){
            _myEngine.close();
        }
        _myEngine.getSceneManager().setGameSize(450,800);
        MenuScene sceneinicial = new MenuScene();
        if(!_myEngine.getSceneManager().push(sceneinicial)){
            _myEngine.close();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        _myEngine.pause();
    }

    @Override
    protected void onResume() {
        // Avisamos a la vista (que es la encargada del active render)
        // de lo que está pasando.
        super.onResume();
        _myEngine.resume();
    }


    @Override
    public void onBackPressed() {
        _myEngine.getSceneManager().pop();
        if(_myEngine.getSceneManager().empty()){
            _myEngine.stop();
        }
    }

    @Override
    protected void onDestroy() {
        _myEngine.stop();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        _myEngine.pause();
        super.onStop();
    }

    MenuScene sceneinicial;
    private AndroidEngine _myEngine;

}
