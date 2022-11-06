package com.nonogram.engine;

public interface Graphics {
    //carga una imagen almacenada en el contenedor de recursos de la aplicación a partir de su nombre.
    Image newImage(String name);
    //crea una nueva fuente del tamaño especificado a partir de un fichero .ttf. Se indica si se desea o no fuente en negrita.
    Font newFont(String filename, int size, boolean isBold);
    //borra el contenido completo de la ventana, rellenándolo con un color recibido como parámetro.
    void clearGame(int color);
    void clearWindow();
    //Métodos de control de la transformación sobre el canvas
    void translate(float x, float y);
    void scale(float x, float y);
    void save();
    void restore();
    //recibe una imagen y la muestra en la pantalla. Se pueden necesitar diferentes versiones de este método dependiendo de si se
    // permite o no escalar la imagen, si se permite elegir qué porción de la imagen original se muestra, etc.
    void drawImage(Image image, int x, int y, double scaleX, double scaleY);
    //establece el color a utilizar en las operaciones de dibujado posteriores.
    void setColor(int color);
    //cambia la fuente actual
    void setActualFont(Font font);
    //dibuja un cuadrado relleno del color activo.
    void fillRect(int cx, int cy, int w, int h);
    //dibuja un cuadrado sin relleno y con el borde del color activo.
    void drawRect(int cx, int cy, int w, int h);
    //dibuja una línea recta del color activo.
    void drawLine(float initX, float initY, float endX, float endY);
    //escribe el texto con la fuente y color activos.
    void drawText(String text, int x, int y);
    //tamaños de la ventana
    int getWindowWidth();
    int getWindowHeight();
    //añadir una ventana
    void setScene(Scene s);
    void render();
    boolean setInputListener(Input listener);
    public void paintFrame();
    void reScale();
    public float worldToGameX(float x);
    public float worldToGameY(float y);
    public float getBorderHeight();
    public float getBorderWidth();
    public int getGameWidth();
    public int getGameHeight();
    void setPaths(AbstractEngine.EnginePaths myPaths);
}
