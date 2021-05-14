import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class GameRun implements Runnable {
    private Display display;
    public int width, height;
    private Thread t;
    public String title;
    private BufferStrategy bufferStrategy;
    private Graphics graphics;
    private boolean isRunning = false;
    private BufferedImage wall;
    private BufferedImage blank;
    private BufferedImage box;
    private BufferedImage player;
    private BufferedImage target;
    private PlayField pf;
    public char[][] charArray;
    public PlayerCoordinates pc;

    public GameRun(String title, int width, int height) {
        this.width = width;
        this.height = height;
        this.title = title;
    }
    private void initialize(){
        pc = new PlayerCoordinates(1,1);
        wall = ImageLoad.image("Textures/sokoban_icons/wall.png");
        blank = ImageLoad.image("Textures/sokoban_icons/blank.png");
        player = ImageLoad.image("Textures/sokoban_icons/player.png");
        box = ImageLoad.image("Textures/sokoban_icons/crate.png");
        target = ImageLoad.image("/Textures/sokoban_icons/blankmarked.png");
        display = new Display(height,width,title);
        pf = new PlayField();
        charArray = pf.setUpLvlOne();
    }
    private void update(){
        int xVal = pc.getPosX();
        int yVal = pc.getPosY();
       // PlayerCoordinates tempPC = new PlayerCoordinates(xVal,yVal);
        display.getCanvas().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int direction = e.getKeyCode();
                try {
                    if (direction == KeyEvent.VK_LEFT) {
                        if((charArray[pc.getPosX()-1][pc.getPosY()] == 'B')) {
                            if (!(charArray[pc.getPosX()-2][pc.getPosY()] == '*')) {
                                charArray[xVal][yVal]=' '; //The spot the player was in is turning blanc
                                charArray[xVal-1][yVal]='P';
                                charArray[xVal-2][yVal]='B';
                                pc.setPosX(pc.getPosX()-1);
                                return;
                            }
                        }else if(!(charArray[pc.getPosX()-1][pc.getPosY()] == '*')){
                            charArray[xVal][yVal]=' '; //The spot the player was in is turning blanc
                            charArray[xVal-1][yVal]='P';
                            pc.setPosX(pc.getPosX()-1);
                            return;
                        }

                    }
                    if (direction == KeyEvent.VK_RIGHT) {
                        if((charArray[pc.getPosX()+1][pc.getPosY()] == 'B')) {
                            if (!(charArray[pc.getPosY()][pc.getPosX() + 2] == '*')) {
                                charArray[yVal][xVal]=' '; //The spot the player was in is turning blanc
                                charArray[yVal][xVal+1]='P';
                                charArray[yVal][xVal+2]='B';
                                pc.setPosX(pc.getPosX()+1);
                                return;
                            }
                        }else if(!(charArray[pc.getPosX()+1][pc.getPosY()] == '*')){
                            charArray[xVal][yVal]=' '; //The spot the player was in is turning blanc
                            charArray[xVal+1][yVal]='P';
                            pc.setPosX(pc.getPosX()+1);
                            return;
                        }
                    }
                    if (direction == KeyEvent.VK_UP) {

                    }
                    if (direction == KeyEvent.VK_DOWN) {

                    }
                }catch (IndexOutOfBoundsException a){
                    a.printStackTrace();
                }
            }
        });

    }
    private void render(){
        bufferStrategy = display.getCanvas().getBufferStrategy(); //Hidden screen to prevent flickering
        //First time running the game
        if (bufferStrategy==null){
            display.getCanvas().createBufferStrategy(3); //Don't need more than 3
            return;
        }
        graphics = bufferStrategy.getDrawGraphics();  //Like a paintbrush to draw on the canvas
        //Clear window
        graphics.clearRect(0,0,width,height);

        //Begining to draw
        //graphics.drawImage(wall,20,20,null);
       for (int i = 0; i < charArray.length; i++) {
            for (int j = 0; j <charArray[0].length ; j++) {
                switch (charArray[i][j]){
                    case '*':
                        //Print wall
                        graphics.drawImage(wall,i*width/10,j*height/10,null);
                        break;
                    case 'P':
                        //Print Player

                        graphics.drawImage(player,i*width/10,j*height/10,null);
                        break;
                    case 'T':
                        //Print Target
                        graphics.drawImage(target,i*width/10,j*height/10,null);
                        break;
                    case 'B':
                        //Print Box
                        graphics.drawImage(box,i*width/10,j*height/10,null);
                        break;
                    default:
                        //Pint empty space
                        graphics.drawImage(blank,i*width/10,j*height/10,null);

                }

            }

        }


        //Ending drawing
        bufferStrategy.show();
        graphics.dispose();

    }

    @Override
    public void run() {
        initialize();
        while(isRunning){
            update();
            render();
        }

        stop();
    }

    public synchronized void stop(){
        if(!isRunning)return;
        isRunning = false;
        try{
            t.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }

    public synchronized void start(){
        if(isRunning) return;

        isRunning = true;
        t = new Thread(this);
        t.start(); //Will call run method
    }
}
