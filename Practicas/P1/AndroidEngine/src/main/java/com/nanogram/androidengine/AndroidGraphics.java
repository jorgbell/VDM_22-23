package com.nanogram.androidengine;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

import com.nanogram.engine.AbstractGraphics;
import com.nanogram.engine.Font;
import com.nanogram.engine.Graphics;
import com.nanogram.engine.Image;
import com.nanogram.engine.Scene;

import java.io.IOException;

public class AndroidGraphics extends AbstractGraphics {
    public AndroidGraphics(AppCompatActivity c, int w, int h) {
        super(w, h);
        _context = c;
        _renderView = new SurfaceView(_context);
        _context.setContentView(_renderView);
        _holder = _renderView.getHolder();
        _paint = new Paint();

    }


    @Override
    public Image newImage(String name) {
        return null;
    }

    @Override
    public Font newFont(String filename, int size, boolean isBold) {
        return null;
    }

    @Override
    public void clear(int color) {
        _canvas.drawColor(color);
    }

    @Override
    public void translate(int x, int y) {
        _canvas.translate(x, y);
    }

    @Override
    public void scale(int x, int y) {
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
    public void drawImage(Image image, int x, int y) {

    }

    @Override
    public void setColor(int color) {
        super.setColor(color);
        _paint.setColor(color);
    }

    @Override
    public void setActualFont(Font font) {

    }

    @Override
    public void fillSquare(int cx, int cy, int side) {
        _paint.setStyle(Paint.Style.FILL);
        Rect rectangle = new Rect(cx, cy, side, side);
        _canvas.drawRect(rectangle, _paint);
    }

    @Override
    public void drawSquare(int cx, int cy, int side) {
        _paint.setStyle(Paint.Style.STROKE);
        Rect rectangle = new Rect(cx, cy, side, side);
        _canvas.drawRect(rectangle, _paint);
    }

    @Override
    public void drawLine(float initX, float initY, float endX, float endY) {
        _canvas.drawLine(initX, initY, endX, endY, _paint);
    }

    @Override
    public void drawText(String text, int x, int y) {

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
    public void setResolution() {

    }

    @Override
    public void render() {
        // Pintamos el frame
        while (!_holder.getSurface().isValid())
            ;
        _canvas = _holder.lockCanvas();
        _canvas.drawColor(0xFF0000FF); // ARGB
        _myScene.render();
        _holder.unlockCanvasAndPost(_canvas);
    }


    //VARIABLES
    private SurfaceView _renderView;
    private SurfaceHolder _holder;
    private Canvas _canvas;
    private Paint _paint;
    private AppCompatActivity _context;

}
