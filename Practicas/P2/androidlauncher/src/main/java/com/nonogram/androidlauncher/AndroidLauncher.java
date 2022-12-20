package com.nonogram.androidlauncher;


import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.nonogram.androidengine.AndroidEngine;
import com.nonogram.engine.NotificationData;
import com.nonogram.logic.MenuScene;

import java.util.Stack;
import java.util.concurrent.TimeUnit;

public class AndroidLauncher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //creamos e inicializamos el motor
        _myEngine = new AndroidEngine(this);
        //manejo de errores: si se crea mal algo, para antes de empezar.
        if(!_myEngine.init()){
            _myEngine.stop();
        }
        //inicializamos el tamaño del scenemanager para darle el tamaño al resto de escenas a partir de ahi
        //valores similares a los que teniamos previamente para poder verlo todo mejor hasta que redimensionemos los objetos bien
        int fullw = (int) (Resources.getSystem().getDisplayMetrics().widthPixels * 0.4);
        int fullh = (int) (Resources.getSystem().getDisplayMetrics().heightPixels * 0.4);

        if(fullw > fullh)  fullw = (int)(fullh / 0.602);
        else if(fullw < fullh)  fullh = (int)(fullw / 0.6);

        _myEngine.getSceneManager().setGameSize(fullw,fullh);
        //inicializamos la primera escena
        MenuScene sceneinicial = new MenuScene(fullw,fullh);
        if(!_myEngine.getSceneManager().push(sceneinicial)){
            _myEngine.stop();
        }

        //detecta si ha entrado mediante una notificacion
        Intent intent = getIntent();
        if(intent.getExtras()!=null && intent.getExtras().containsKey("notification")){
            sceneinicial.handleOpeningNotifications();
        }

        getSupportActionBar().hide();
        View decorView = getWindow().getDecorView();

        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

    }

    @Override
    protected void onPause() {
        super.onPause();
        _myEngine.getSensors().unregisterAll();
        _myEngine.pause();
        handleClosingNotifications();

    }

    @Override
    public void onBackPressed() {
        _myEngine.getSceneManager().pop();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        _myEngine.getSceneManager().rotate();
    }

    @Override
    protected void onResume() {
        // Avisamos a la vista (que es la encargada del active render)
        // de lo que está pasando.
        super.onResume();
        _myEngine.getSensors().registerAll();
        _myEngine.resume();

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

    void handleClosingNotifications(){
        Stack<NotificationData> closingNotis = _myEngine.getClosingNotifications();

        while(!closingNotis.empty()){
            NotificationData notData = closingNotis.peek();
            Data inputData = new Data.Builder()
                    .putString("title", notData._title)
                    .putString("contentText", notData._contentText)
                    .putString("biggerText", notData._biggerText)
                    .putBoolean("autocancel", true)
                    .putString("CHANNEL_ID", _myEngine.getAndroidNotificationManager().get_CHANNEL_ID())
                    .build();
            // we then retrieve it inside the NotifyWorker with:
            // final int DBEventID = getInputData().getInt(DBEventIDTag, ERROR_VALUE);
            OneTimeWorkRequest notificationWork = new OneTimeWorkRequest.Builder(AndroidWorker.class)
                    .setInitialDelay(notData._delaySeconds, TimeUnit.SECONDS)
                    .setInputData(inputData)
                    //.addTag(workTag)
                    .build();

            WorkManager.getInstance(this).enqueue(notificationWork);
            closingNotis.pop();
        }
    }

    private AndroidEngine _myEngine;

}
