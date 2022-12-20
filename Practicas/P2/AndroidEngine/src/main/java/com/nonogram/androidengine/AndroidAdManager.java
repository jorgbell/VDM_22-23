package com.nonogram.androidengine;

import android.app.Activity;
import android.content.Context;
import android.opengl.Visibility;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.nonogram.engine.AdManager;
import com.nonogram.engine.Scene;
import com.nonogram.engine.SceneManager;

public class AndroidAdManager implements AdManager {

    public AndroidAdManager(AppCompatActivity c) {
        activity = c;
        _mAdView = null;
        isOn = true;
        adRequest = new AdRequest.Builder().build();

    }

    public void SetContext(AppCompatActivity c) {
        activity = c;
    }

    @Override
    public boolean init() {

        return true;
    }

    @Override
    public void createBanner() {
        if (_mAdView != null) {
            _mAdView.setVisibility(View.VISIBLE);
            isOn = true;
        }
    }

    @Override
    public void deleteBanner() {
        if (_mAdView != null) {
            _mAdView.setVisibility(View.GONE);
            isOn = false;
        }

    }

    @Override
    public boolean isBannerOn() {
        return isOn;
    }

    @Override
    public void setSceneManager(SceneManager s) {
        _mySceneMngr = s;
    }

    public void setAdView(AdView a, ConstraintLayout c) {
        _mAdView = a;
        parent = c;
    }


    @Override
    public void showRewarder(){
        activity.runOnUiThread(new Runnable() {
            @Override public void run() {
                if (_rewardedAd != null) {
                    _rewardedAd.show(activity, new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            // Handle the reward.
                            _mySceneMngr.handleAdd();
                        }
                    });

                } else {
                    System.out.println("The rewarded ad wasn't ready yet.");
                }
            }
        });

    }

    void loadBanner(){
        MobileAds.initialize(activity, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                // cargamos los anuncios
                _mAdView.loadAd(adRequest);
            }
        });
    }
    public void loadRewarded(){

        //cargamos el rewarded
        RewardedAd.load(activity, "ca-app-pub-3940256099942544/5224354917", adRequest, new RewardedAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error.
                String s = loadAdError.toString();
                _rewardedAd = null;
            }

            @Override
            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                _rewardedAd = rewardedAd;
                _rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdClicked() {
                        // Called when a click is recorded for an ad.
                        Log.d("a", "Ad was clicked.");
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        // Called when ad is dismissed.
                        // Set the ad reference to null so you don't show the ad a second time.
                        Log.d("a", "Ad dismissed fullscreen content.");
                        _rewardedAd = null;
                        loadRewarded();
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        // Called when ad fails to show.
                        Log.e("a", "Ad failed to show fullscreen content.");
                        String a = adError.toString();
                        _rewardedAd = null;
                        loadRewarded();
                    }

                    @Override
                    public void onAdImpression() {
                        // Called when an impression is recorded for an ad.
                        Log.d("a", "Ad recorded an impression.");
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        // Called when ad is shown.
                        Log.d("a", "Ad showed fullscreen content.");
                    }
                });

            }
        });


    }
    @Override
    public void loadAds(){
        //cargamos el banner
        loadBanner();
        loadRewarded();
    }


    AppCompatActivity activity;
    SceneManager _mySceneMngr;
    AdView _mAdView = null;
    RewardedAd _rewardedAd = null;
    ViewGroup parent;
    AdRequest adRequest;
    boolean isOn;
}

