package com.nonogram.androidengine;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

import com.nonogram.engine.AbstractGraphics;
import com.nonogram.engine.Font;
import com.nonogram.engine.Image;
import com.nonogram.engine.Input;


public class AndroidGraphics extends AbstractGraphics {
    public AndroidGraphics(AppCompatActivity c) {
        super();
        _context = c;
    }
    @Override
    public boolean init() {
        if(_context == null){
            System.err.println("AppCompactActivity null");
            return false;
        }
        _renderView = new SurfaceView(_context);
        _paint = new Paint();
        _context.setContentView(_renderView);
        _holder = _renderView.getHolder();
        return true;
    }

    @Override
    public Image newImage(String name) {
        AndroidImage aimage = null;
        try{
            aimage = new AndroidImage(_myPaths._imagesPath + name, _context.getAssets());
        }catch (Exception e){}

        return aimage;
    }

    @Override
    public Font newFont(String filename, int size, boolean isBold) {
        AndroidFont afont = null;
        try{
            afont = new AndroidFont(_myPaths._fontsPath+ filename, size, isBold, _context.getAssets());
        }catch (Exception e){}

        return afont;
    }


    @Override
    public void clearGame(int color) {
        int c = _actualColor;
        setColor(color);
        fillRect(0,0,sceneManager.getGameWidth(), sceneManager.getGameHeight());
        setColor(c);
    }

    @Override
    public void clearWindow() {
        _canvas.drawColor(0xFFFFFFFF);
    }

    @Override
    public void translate(float x, float y) {
        _canvas.translate(x, y);
    }

    @Override
    public void scale(float x, float y) {
        _canvas.scale(x, y);
    }

    @Override
    public void save() {
        _canvas.save();
    }

    @Override
    public void restore() {
        _canvas.restore();
    }

    @Override
    public void drawImage(Image image, int x, int y,double scaleX, double scaleY) {
        AndroidImage aI = (AndroidImage) image;
        if(aI._getBitmap()== null){
            System.err.println("Error dibujando la imagen");
            return;
        }
        int newW = (int)(aI._bitmap.getWidth() * scaleX);
        int newH = (int)(aI._bitmap.getHeight() * scaleY);
        Bitmap newBitmap = Bitmap.createScaledBitmap(aI._bitmap, newW, newH, true);
        _canvas.drawBitmap(newBitmap,x,y,_paint);
    }

    @Override
    public void setColor(int color) {
        super.setColor(color);
        _paint.setColor(color);
    }

    @Override
    public void setActualFont(Font font) {
        super.setActualFont(font);
        AndroidFont f = (AndroidFont) font;

        if(f._font == null){
            System.err.println("Error seteando el texto de Android");
            return;
        }
        _paint.setTypeface(f._font);
        _paint.setTextSize(_actualFont.getSize());
        _paint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    public void fillRect(int cx, int cy, int w, int h) {
        _paint.setStyle(Paint.Style.FILL);
        Rect rectangle = new Rect(cx, cy, cx+w, cy+h);
        _canvas.drawRect(rectangle, _paint);
    }

    @Override
    public void drawRect(int cx, int cy, int w, int h) {
        _paint.setStyle(Paint.Style.STROKE);
        Rect rectangle = new Rect(cx, cy, cx+w, cy+h);
        _canvas.drawRect(rectangle, _paint);
    }

    @Override
    public void drawLine(float initX, float initY, float endX, float endY) {
        _canvas.drawLine(initX, initY, endX, endY, _paint);
    }

    @Override
    public void drawText(String text, int x, int y) {
        _paint.setStyle(Paint.Style.FILL_AND_STROKE);
        _paint.setTextSize(_actualFont.getSize());
        _paint.setFakeBoldText(_actualFont.isBold());
        _canvas.drawText(text, x, y, _paint);
    }

    @Override
    public int getWindowWidth() {
        return _renderView.getWidth();
    }

    @Override
    public int getWindowHeight() {
        return _renderView.getHeight();
    }


    @Override
    public void render() {
        // Pintamos el frame
        while (!_holder.getSurface().isValid())
            ;
        Canvas c = _holder.lockCanvas();
        _canvas = c;
        paintFrame();
        _holder.unlockCanvasAndPost(_canvas);
    }



    @Override
    public boolean setInputListener(Input listener) {
        super.setInputListener(listener);
        AndroidInput i = (AndroidInput) listener;
        _renderView.setOnTouchListener(i);
        return true;
    }

    @Override
    public float getBottomBorder() {
        return 0;
    }

    @Override
    public float getLeftBorder() {
        return 0;
    }

    @Override
    public float getRightBorder() {
        return 0;
    }

    @Override
    public float getTopBorder() {
        return 0;
    }


    //VARIABLES
    private SurfaceView _renderView;
    private SurfaceHolder _holder;
    private Canvas _canvas;
    private Paint _paint;
    public AppCompatActivity _context;

}
