import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public abstract class Engine {
	
	
	protected String title;
	protected int height;
	protected int width;
	private int fps = 30;
	private Display display = null;
	private BufferStrategy bufferStrategy = null;
	private Graphics graphics = null;
	private Boolean isRunning = false;
	
	private void createWindow() {
		display = new Display(title, width, height);
		initGraphics();
	}
	
	private void initGraphics() {
        bufferStrategy = display.getCanvas().getBufferStrategy(); //Hidden screen to prevent flickering
        //First time running the game
        if (bufferStrategy==null){
            display.getCanvas().createBufferStrategy(3); //Don't need more than 3
            return;
        }
        graphics = bufferStrategy.getDrawGraphics();  //Like a paintbrush to draw on the canvas
        //Clear window
        clear();
        show();
	}
	
	private void clear() {
        graphics.clearRect(0,0,width,height);
	}
	
	private void show() {
		bufferStrategy.show();
        graphics.dispose();
	}
	public void setFps(int f) {
		fps = f;
	}
	
	public Engine() {
		title = "text";
		height = 300;
		width = 400;
	}
	
	public void start() {
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
		return graphics != null && bufferStrategy != null;
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
 
}
