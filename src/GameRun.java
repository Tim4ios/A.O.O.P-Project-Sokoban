import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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
    private BufferedImage boxPlaced;
    private PlayField pf;
    public char[][] charArray;
    public PlayerCoordinates pc;
    public int counter=0;

    public GameRun(String title, int width, int height) {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    private void initialize(){
        wall = ImageLoad.image("Textures/sokoban_icons/wall.png");
        blank = ImageLoad.image("Textures/sokoban_icons/blank.png");
        player = ImageLoad.image("Textures/sokoban_icons/player.png");
        box = ImageLoad.image("Textures/sokoban_icons/crate.png");
        target = ImageLoad.image("/Textures/sokoban_icons/blankmarked.png");
        boxPlaced = ImageLoad.image("/Textures/sokoban_icons/cratemarked.png");
        display = new Display(height,width,title);
        display.getCanvas().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int direction = e.getKeyCode();
                try {
                    if (direction == KeyEvent.VK_LEFT) {
                        System.out.println(counter++);
                        //if there is a box to the left of the player
                        if((charArray[pc.getPosX()-1][pc.getPosY()] == 'B'||charArray[pc.getPosX()-1][pc.getPosY()] == 'W')) {
                            //if there isn't a wall after the box
                            if (!(charArray[pc.getPosX()-2][pc.getPosY()] == '*')) {
                                if((charArray[pc.getPosX()-2][pc.getPosY()] == 'T')){
                                    charArray[pc.getPosX()-2][pc.getPosY()]='W';
                                }else{
                                    charArray[pc.getPosX()-2][pc.getPosY()]='B';
                                }
                                charArray[pc.getPosX()][pc.getPosY()]=' '; //The spot the player was in is turning blanc
                                charArray[pc.getPosX()-1][pc.getPosY()]='P';
                                pc.setPosX(pc.getPosX()-1);
                            }
                        }else if(!(charArray[pc.getPosX()-1][pc.getPosY()] == '*')){
                            charArray[pc.getPosX()][pc.getPosY()]=' '; //The spot the player was in is turning blanc
                            charArray[pc.getPosX()-1][pc.getPosY()]='P';
                            pc.setPosX(pc.getPosX()-1);

                        }

                    }
                    if (direction == KeyEvent.VK_RIGHT) {
                        //if there is a box right of the player
                        if((charArray[pc.getPosX()+1][pc.getPosY()] == 'B'||charArray[pc.getPosX()+1][pc.getPosY()] == 'W')) {
                            //if there isn't a wall after the box
                            if (!(charArray[pc.getPosX()+2][pc.getPosY()] == '*')) {
                                if((charArray[pc.getPosX()+2][pc.getPosY()] == 'T')){
                                    charArray[pc.getPosX()+2][pc.getPosY()]='W';
                                }else{
                                    charArray[pc.getPosX()+2][pc.getPosY()]='B';
                                }
                                charArray[pc.getPosX()][pc.getPosY()]=' '; //The spot the player was in is turning blanc
                                charArray[pc.getPosX()+1][pc.getPosY()]='P';
                                pc.setPosX(pc.getPosX()+1); //Change xVal since we are moving on the x-axis
                            }
                            //if there isn't a wall right of the player
                        }else if(!(charArray[pc.getPosX()+1][pc.getPosY()] == '*')){
                            charArray[pc.getPosX()][pc.getPosY()]=' '; //The spot the player was in is turning blanc
                            charArray[pc.getPosX()+1][pc.getPosY()]='P';
                            pc.setPosX(pc.getPosX()+1);
                        }
                    }
                    if (direction == KeyEvent.VK_UP) {
                        System.out.println(counter++);
                        //if there is a box to the left of the player
                        if((charArray[pc.getPosX()][pc.getPosY()-1] == 'B'||charArray[pc.getPosX()][pc.getPosY()-1] == 'W')) {
                            //if there isn't a wall after the box
                            if (!(charArray[pc.getPosX()][pc.getPosY()-2] == '*')) {
                                if((charArray[pc.getPosX()][pc.getPosY()-2] == 'T')){
                                    charArray[pc.getPosX()][pc.getPosY()-2]='W';
                                }else{
                                    charArray[pc.getPosX()][pc.getPosY()-2]='B';
                                }
                                charArray[pc.getPosX()][pc.getPosY()]=' '; //The spot the player was in is turning blanc
                                charArray[pc.getPosX()][pc.getPosY()-1]='P';
                                pc.setPosY(pc.getPosY()-1);
                            }
                        }else if(!(charArray[pc.getPosX()][pc.getPosY()-1] == '*')){
                            charArray[pc.getPosX()][pc.getPosY()]=' '; //The spot the player was in is turning blanc
                            charArray[pc.getPosX()][pc.getPosY()-1]='P';
                            pc.setPosY(pc.getPosY()-1);

                        }

                    }
                    if (direction == KeyEvent.VK_DOWN) {
                        if((charArray[pc.getPosX()][pc.getPosY()+1] == 'B'||charArray[pc.getPosX()][pc.getPosY()+1] == 'W')) {
                            //if there isn't a wall after the box
                            if (!(charArray[pc.getPosX()][pc.getPosY()+2] == '*')) {
                                if((charArray[pc.getPosX()][pc.getPosY()+2] == 'T')){
                                    charArray[pc.getPosX()][pc.getPosY()+2]='W';
                                }else{
                                    charArray[pc.getPosX()][pc.getPosY()+2]='B';
                                }
                                charArray[pc.getPosX()][pc.getPosY()]=' '; //The spot the player was in is turning blanc
                                charArray[pc.getPosX()][pc.getPosY()+1]='P';

                                pc.setPosY(pc.getPosY()+1);
                            }
                        }else if(!(charArray[pc.getPosX()][pc.getPosY()+1] == '*')){
                            charArray[pc.getPosX()][pc.getPosY()]=' '; //The spot the player was in is turning blanc
                            charArray[pc.getPosX()][pc.getPosY()+1]='P';
                            pc.setPosY(pc.getPosY()+1);
                        }

                    }
                }catch (IndexOutOfBoundsException a){
                    a.printStackTrace();
                }
            }
        });
        pf = new PlayField();
        charArray = pf.setUpLvlOne();
        pc = pf.player;
    }

    private void update(){
        //Make sure to keep the target painted even if a player or block passes it
        for (PlayerCoordinates p :pf.targets) {
            if(charArray[p.getPosX()][p.getPosY()]=='T')continue;
            if(!(charArray[p.getPosX()][p.getPosY()]=='P')&&!(charArray[p.getPosX()][p.getPosY()]=='B')&&!(charArray[p.getPosX()][p.getPosY()]=='W')){
                charArray[p.getPosX()][p.getPosY()] ='T';
            }
        }

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

        //Beginning to draw
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
                    case 'W':
                        //Print BoxPlaced
                        graphics.drawImage(boxPlaced,i*width/10,j*height/10,null);
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
        int fps = 60; //Setting up frame rate
        double timePerUpdate = 1000000000 / fps; //1 billion nanoseconds in a second
        double d = 0;
        long current;
        long lastTime = System.nanoTime(); //Like a clock in nanoseconds
        initialize();

        while(isRunning){
            current = System.nanoTime();
            d += (current-lastTime)/timePerUpdate;
            lastTime = current;

            if(d >= 1) {
                update();
                render();
                d--;
            }
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
