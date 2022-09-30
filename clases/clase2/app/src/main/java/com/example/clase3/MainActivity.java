package com.example.clase3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.lang.reflect.Type;

public class MainActivity extends AppCompatActivity { //actividad principal (extiende de appcompactactivity)

    private SurfaceView renderView;

    private MyRenderClass render; //esta clase render es la que trabajara con el canvas y el surfaceholder

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Creamos el SurfaceView que "contendrá" nuestra escena
        this.renderView = new SurfaceView(this);
        setContentView(this.renderView);
        MyScene scene = new MyScene();

        this.render = new MyRenderClass(this.renderView, this.getAssets());
        scene.init(render);
        render.setScene(scene);

    }

    @Override
    protected void onResume() {
        super.onResume();
        this.render.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.render.pause();
    }

    //Clase interna que representa la escena que queremos pintar
    //ESTO SERIA LOGIC
    class MyScene {
        private int x;
        private int y;
        private int radius;
        private int speed;

        private MyRenderClass renderClass;

        public MyScene(){
            this.x=100;
            this.y=0;
            this.radius = 100;
            this.speed = 150;
        }

        public void init(MyRenderClass renderClass){
            this.renderClass = renderClass;
        }

        public void update(double deltaTime){
            int maxX = this.renderClass.getWidth()-this.radius;

            this.x += this.speed * deltaTime;
            this.y += 1;
            while(this.x < this.radius || this.x > maxX) {
                // Vamos a pintar fuera de la pantalla. Rectificamos.
                if (this.x < this.radius) {
                    // Nos salimos por la izquierda. Rebotamos.
                    this.x = this.radius;
                    this.speed *= -1;
                } else if (this.x > maxX) {
                    // Nos salimos por la derecha. Rebotamos
                    this.x = 2 * maxX - this.x;
                    this.speed *= -1;
                }
            }
        }

        public void render(){ //la renderclass se da la orden a si misma, y despues es la clase escena la que
            renderClass.renderCircle(this.x, this.y, this.radius);
            String filename = " ";//rellenar con el nombre del archivo
            renderClass.createText(filename, 40);
            renderClass.renderText("sample text", this.x+20, this.y+20);
        }
    }

    //Clase interna encargada de obtener el SurfaceHolder y pintar con el canvas
    class MyRenderClass implements Runnable{ //implementa runnable para que el hilo de pintar y el de lo demas sean distintos y no se quede sin memoria para pintar
                                             //esto despues realmente no implementa runnable sino que ira desde el engine
        private SurfaceView myView;
        private SurfaceHolder holder; //lo conseguimos desde el surfaceview
        private Canvas canvas; //los metodos para decir que pintar estan aqui dentro

        private Thread renderThread;

        private boolean running;

        private Paint paint; //aqui le decimos el estilo con el que vamos a pintar
        private Typeface tface;
        private MyScene scene;
        private AssetManager myAss;

        //constructor
        public MyRenderClass(SurfaceView myView, AssetManager am){ //creamos la vista de pintado, con esto pintaremos
            this.myView = myView;
            this.myAss = am;
            this.holder = this.myView.getHolder();
            this.paint = new Paint();
            this.paint.setColor(0xFFFFFFFF); //blanco
        }

        public int getWidth(){
            return this.myView.getWidth();
        }

        //bucle para ejecutar la ventana
        @Override
        public void run() {
            if (renderThread != Thread.currentThread()) {
                // Evita que cualquiera que no sea esta clase llame a este Runnable en un Thread
                // Programación defensiva
                throw new RuntimeException("run() should not be called directly");
            }

            // Si el Thread se pone en marcha
            // muy rápido, la vista podría todavía no estar inicializada.
            while(this.running && this.myView.getWidth() == 0);
            // Espera activa. Sería más elegante al menos dormir un poco.

            long lastFrameTime = System.nanoTime();

            long informePrevio = lastFrameTime; // Informes de FPS
            int frames = 0;

            // Bucle de juego principal.
            while(running) {
                long currentTime = System.nanoTime();
                long nanoElapsedTime = currentTime - lastFrameTime;
                lastFrameTime = currentTime;

                // Informe de FPS
                double elapsedTime = (double) nanoElapsedTime / 1.0E9;
                this.update(elapsedTime);
                if (currentTime - informePrevio > 1000000000l) {
                    long fps = frames * 1000000000l / (currentTime - informePrevio);
                    System.out.println("" + fps + " fps");
                    frames = 0;
                    informePrevio = currentTime;
                }
                ++frames;

                // Pintamos el frame
                while (!this.holder.getSurface().isValid()); //esperamos a que el holder este listo
                this.canvas = this.holder.lockCanvas();
                this.render();
                this.holder.unlockCanvasAndPost(canvas);

                /*
                // Posibilidad: cedemos algo de tiempo. Es una medida conflictiva...
                try { Thread.sleep(1); } catch(Exception e) {}
    			*/
            }
        }

        protected void update(double deltaTime) {
            scene.update(deltaTime);
        }

        public void setScene(MyScene scene) {
            this.scene = scene;
        }

        protected void renderCircle(float x, float y, float r){
            canvas.drawCircle(x, y, r, this.paint);
        }

        protected void renderText(String text, float x, float y){
            canvas.drawText(text, x, y, this.paint);
        }

        protected void render() { //metodo en el que le decimos qué hacer
            // "Borramos" el fondo.
            this.canvas.drawColor(0xFF0000FF); // ARGB
            scene.render();
        }

        public void resume() {
            if (!this.running) {
                // Solo hacemos algo si no nos estábamos ejecutando ya
                // (programación defensiva)
                this.running = true;
                // Lanzamos la ejecución de nuestro método run() en un nuevo Thread.
                this.renderThread = new Thread(this);
                this.renderThread.start();
            }
        }

        public void pause() {
            if (this.running) {
                this.running = false;
                while (true) {
                    try {
                        this.renderThread.join();
                        this.renderThread = null;
                        break;
                    } catch (InterruptedException ie) {
                        // Esto no debería ocurrir nunca...
                    }
                }
            }
        }

        public void createText(String filename, float size) {
            tface = Typeface.createFromAsset(myAss, filename);
            paint.setTypeface(tface);
            paint.setTextSize(size);
        }
    }
}