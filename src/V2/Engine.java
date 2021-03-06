package V2;

import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

public abstract class Engine {


    protected String title;
    protected int height;
    protected int width;
    private int fps = 30;
    private Display display = null;
    private BufferStrategy bufferStrategy = null;
    protected Graphics g = null;
    private Boolean isRunning = false;

    private void createWindow() {
        display = new Display(title, width, height);
    }

    private void initGraphics() {
        System.out.println("Init graphics");
        bufferStrategy = display.getCanvas().getBufferStrategy(); //Hidden screen to prevent flickering
        //First time running the game
        if (bufferStrategy==null){
            display.getCanvas().createBufferStrategy(3); //Don't need more than 3
            bufferStrategy = display.getCanvas().getBufferStrategy();
        }
        g = bufferStrategy.getDrawGraphics();  //Like a paintbrush to draw on the canvas
        //Clear window
        if (g == null) {
            System.out.println("no graphics");
            System.exit(1);
        }
    }

    private void clear() {
        g = bufferStrategy.getDrawGraphics();  //Like a paintbrush to draw on the canvas
        g.clearRect(0,0,width,height);
    }

    private void show() {
        bufferStrategy.show();
        g.dispose();
    }

    public void draw(Sprite s, int posX, int posY) {
        s.draw(g, posX, posY);
    }
    
    public void draw(String s, int posX, int posY) {
    	g.drawString(s, posX, posY);
    }
    
    public void setFps(int f) {
        fps = f;
    }

    public Engine() {
        title = "text";
        height = 320;
        width = 320;
    }

    public void start() {
        display.getCanvas().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                onKeyPressed(e);
            }
        });
        onUserStart();
        double timePerUpdate = 1000000000 / fps; //1 billion nanoseconds in a second
        double d = 0;
        long current;
        long lastTime = System.nanoTime();
        isRunning = true;
        while(isRunning == true) {
            current = System.nanoTime();
            d += (current-lastTime)/timePerUpdate;
            lastTime = current;

            if(d >= 1) {
                clear();
                onUserUpdate();
                show();
                d--;
            }
        }
    }

    public Boolean init() {
        createWindow();
        System.out.println("+ created window");
        initGraphics();
        System.out.println("+ created graphics");

        System.out.println("+ bound key events");
        return g != null && bufferStrategy != null;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public abstract void onUserStart();
    public abstract void onUserUpdate();
    public abstract void onKeyPressed(KeyEvent e);
}