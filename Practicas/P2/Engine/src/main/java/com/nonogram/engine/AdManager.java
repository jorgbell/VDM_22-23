package com.nonogram.engine;

public interface AdManager {
    boolean init();
    void createBanner();
    void deleteBanner();
    public boolean isBannerOn();
    public void setSceneManager(SceneManager s);
    public void showRewarder();
    void loadAds();
}
