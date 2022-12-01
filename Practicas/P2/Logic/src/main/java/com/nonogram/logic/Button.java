package com.nonogram.logic;


import com.nonogram.engine.Graphics;
import com.nonogram.engine.Image;
import com.nonogram.engine.Input;

public abstract class Button {
    public enum ImagePos{CENTERED, UP, DOWN, RIGHT, LEFT}

    public class Rect {
        public Rect(int x, int y, int w, int h) {
            _x = x;
            _y = y;
            _w = w;
            _h = h;
        }

        public boolean contains(double x, double y) {
            return (x > _x && x < _x + _w && y > _y && y < _y + _h);
        }

        int _x, _y, _w, _h;

    }

    public Button(int x, int y, int w, int h) {
        _rect = new Rect(x, y, w, h);
    }

    public void render(Graphics g){

        //fondo
        g.setColor(_bgColor);
        g.fillRect(_rect._x, _rect._y, _rect._w, _rect._h);
        //imagen
        if(_image != null) g.drawImage(_image, (int)_imgX, (int)_imgY, _imageScale, _imageScale);
        //texto
        if(_text!= null) {
            g.setColor(_textColor);
            g.drawText(_text, _rect._x + _rect._w / 2, _rect._y + _rect._h / 2);
        }
        //borde
        g.setColor(_borderColor);
        g.drawRect(_rect._x, _rect._y, _rect._w, _rect._h);
    };

    public abstract void handleEvent(Input.TouchEvent e);
    public abstract void update(double deltaTime);

    public void addImage(Image img , double scale, ImagePos imagePos){

        if(_image.getWidth()*scale > _rect._w || _image.getHeight()*scale > _rect._h){
            System.err.print("ESCALA INCORRECTA");
        }
        _image=img;
        _imageScale = scale;

        switch (imagePos){
            case CENTERED:
                _imgX = _rect._x+(_rect._w - (_image.getWidth()*_imageScale))/2;
                _imgY = _rect._y+(_rect._h-(_image.getHeight()*_imageScale))/2;
                break;
            case UP:
                _imgX = _rect._x+(_rect._w - (_image.getWidth()*_imageScale))/2;
                _imgY= _rect._y;
                break;
            case DOWN:
                _imgX = _rect._x+(_rect._w - (_image.getWidth()*_imageScale))/2;
                _imgY= _rect._y + _rect._h -(_image.getHeight()*_imageScale);
                break;
            case LEFT:
                _imgX = _rect._x;
                _imgY = _rect._y+(_rect._h-(_image.getHeight()*_imageScale))/2;
                break;
            case RIGHT:
                _imgX = _rect._x + _rect._w -(_image.getWidth()*_imageScale);
                _imgY = _rect._y+(_rect._h-(_image.getHeight()*_imageScale))/2;
                break;
        }

    }
    public void addText(String text){ _text = text;}
    public void set_bgColor(int c){_bgColor = c;}
    public void set_textColor(int c){_textColor = c;}
    public void set_borderColor(int c){_borderColor = c;}

    //Action action;
    protected String _text = null;
    protected Rect _rect;
    protected Image _image = null;
    protected double _imageScale;
    protected double _imgX, _imgY;
    protected int _bgColor = 0xFFF0F0F0; //default
    protected int _textColor = 0xFF000000; //default;
    protected int _borderColor = 0xFF000000; //default;
}
