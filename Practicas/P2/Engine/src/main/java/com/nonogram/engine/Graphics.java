package com.nonogram.engine;

public interface Graphics {
    boolean init();

    //setters
    boolean setInputListener(Input listener);
    //establece el color a utilizar en las operaciones de dibujado posteriores.
    void setColor(int color);
    void setBGColor(int color);
    //cambia la fuente actual
    void setActualFont(Font font);
    void setEngine(Engine e);
    boolean isWindowInitialized();
//------------------------------------------------//
    //getters
    //tamaños de la ventana
    int getWindowWidth();
    int getWindowHeight();
    //pilla los tamaños de los bordes de la ventana
    public float getBottomBorder();
    public float getLeftBorder();
    public float getRightBorder();
    public float getTopBorder();
    public void setFullScreen(boolean fullScreen);

    //--------------------------------------------//
    //métodos para el renderizado
    void render();
    void paintFrame();
    void reScale();
    //carga una imagen almacenada en el contenedor de recursos de la aplicación a partir de su nombre.
    Image newImage(String name);
    //crea una nueva fuente del tamaño especificado a partir de un fichero .ttf. Se indica si se desea o no fuente en negrita.
    Font newFont(String filename, int size, boolean isBold);
    //borra el contenido completo de la ventana, rellenándolo con un color recibido como parámetro, o el contenido de la pantalla de juego para cambiar el color de fondo
    void clearGame(int color);
    void clearWindow();
    //recibe una imagen y la muestra en la pantalla. Se pueden necesitar diferentes versiones de este método dependiendo de si se
    // permite o no escalar la imagen, si se permite elegir qué porción de la imagen original se muestra, etc.
    void drawImage(Image image, int x, int y, double scaleX, double scaleY);
    //dibuja un cuadrado relleno del color activo.
    void fillRect(int cx, int cy, int w, int h);
    //dibuja un cuadrado sin relleno y con el borde del color activo.
    void drawRect(int cx, int cy, int w, int h);
    //dibuja una línea recta del color activo.
    void drawLine(float initX, float initY, float endX, float endY);
    //escribe el texto con la fuente y color activos.
    void drawText(String text, int x, int y);
//--------------------------------------------------//
    //Métodos de control de la transformación sobre el canvas
    void translate(float x, float y);
    void scale(float x, float y);
    void save();
    void restore();
//----------------------------------------------------//
    //métodos para transformar una posición de pantalla a posición lógica (reescalada y trasladada)
    public float worldToGameX(float x);
    public float worldToGameY(float y);

}
