package com.nonogram.androidlauncher;


import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.ads.rewarded.ServerSideVerificationOptions;
import com.nonogram.androidengine.AndroidAdManager;
import com.nonogram.androidengine.AndroidEngine;
import com.nonogram.engine.NotificationData;
import com.nonogram.logic.MenuScene;

import java.util.Stack;
import java.util.concurrent.TimeUnit;

public class AndroidLauncher extends AppCompatActivity {

    SurfaceView gameView;
    AdView _mAdView;
    ConstraintLayout c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Accedemos a las dos vistas
        setContentView(R.layout.androidlauncher);
        c = (ConstraintLayout) findViewById(R.id.parent_linear_layout);
        gameView = findViewById(R.id.surfaceView);
        _mAdView = findViewById(R.id.adView);
        //creamos el motor
        _myEngine = new AndroidEngine(this, gameView);
        //manejo de errores: si se crea mal algo, para antes de empezar.
        if(!_myEngine.init()){
            _myEngine.close();
        }
        //cargamos los anuncios
        ((AndroidAdManager)_myEngine.getAdManager()).setAdView(_mAdView, c);
        _myEngine.getAdManager().loadAds();
        //creamos e inicializamos la escena principal
        int orientation = getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_PORTRAIT)
            _myEngine.getSceneManager().setGameSize(450,800);
        else if(orientation == Configuration.ORIENTATION_LANDSCAPE)
            _myEngine.getSceneManager().setGameSize(800,450);

        //inicializamos la primera escena
        MenuScene sceneinicial = new MenuScene();
        if(!_myEngine.getSceneManager().push(sceneinicial)){
            _myEngine.close();
        }
        //detecta si ha entrado mediante una notificacion
        Intent intent = getIntent();
        if(intent.getExtras()!=null && intent.getExtras().containsKey("notification")){
            sceneinicial.handleOpeningNotifications();
        }
        //gestión de la barra de notificaciones
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
    }

    @Override
    public void onBackPressed() {
        _myEngine.getSceneManager().pop();
        if(_myEngine.getSceneManager().empty()){
            handleClosingNotifications();
            _myEngine.stop();
        }
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
        handleClosingNotifications();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        _myEngine.pause();
        handleClosingNotifications();
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
