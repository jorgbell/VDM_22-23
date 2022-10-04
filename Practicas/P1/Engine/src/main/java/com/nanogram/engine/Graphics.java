package com.nanogram.engine;

import java.awt.FontFormatException;
import java.io.IOException;

public interface Graphics {
    //carga una imagen almacenada en el contenedor de recursos de la aplicación a partir de su nombre.
    Image newImage(String name) throws IOException;
    //crea una nueva fuente del tamaño especificado a partir de un fichero .ttf. Se indica si se desea o no fuente en negrita.
    Font newFont(String filename, int size, boolean isBold) throws IOException, FontFormatException;
    //borra el contenido completo de la ventana, rellenándolo con un color recibido como parámetro.
    void clear(int color);
    //Métodos de control de la transformación sobre el canvas
    void translate(int x, int y);
    void scale(int x, int y);
    void save();
    void restore();
    //recibe una imagen y la muestra en la pantalla. Se pueden necesitar diferentes versiones de este método dependiendo de si se
    // permite o no escalar la imagen, si se permite elegir qué porción de la imagen original se muestra, etc.
    void drawImage(Image image, int x, int y);
    //establece el color a utilizar en las operaciones de dibujado posteriores.
    void setColor(int color);
    //cambia la fuente actual
    void setActualFont(Font font);
    //dibuja un cuadrado relleno del color activo.
    void fillSquare(int cx, int cy, int side);
    //dibuja un cuadrado sin relleno y con el borde del color activo.
    void drawSquare(int cx, int cy, int side);
    //dibuja una línea recta del color activo.
    void drawLine(float initX, float initY, float endX, float endY);
    //escribe el texto con la fuente y color activos.
    void drawText(String text, int x, int y);
    //tamaños de la ventana
    int getWindowWidth();
    int getWindowHeight();
    void setResolution();
    //añadir una ventana
    public void setScene(Scene s);
}
