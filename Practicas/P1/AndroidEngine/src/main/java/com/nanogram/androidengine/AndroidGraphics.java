package com.nanogram.androidengine;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

import com.nanogram.engine.AbstractGraphics;

public class AndroidGraphics extends AbstractGraphics {
    public AndroidGraphics(AppCompatActivity c, int w, int h){
        super(w,h);
        _context = c;
        _renderView = new SurfaceView(_context);
        _context.setContentView(_renderView);
        _holder = _renderView.getHolder();

    }

    //VARIABLES
    private SurfaceView _renderView;
    private SurfaceHolder _holder;
    private Canvas _canvas;
    private Paint paint;
    private AppCompatActivity _context;

}
