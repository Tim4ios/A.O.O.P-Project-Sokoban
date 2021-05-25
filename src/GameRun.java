import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

/*
Ideas:
1. Add a counter that if the player does above a certain number of moves game asks if u want to retry
2. Player presses R to restart (more logical)
 */

public class GameRun implements Runnable {

    private Display display;
    public int width, height;
    private Thread t;
    public String title;
    public Graphics graphics;
    public int tick;

    boolean firstRun = true;
    private boolean isRunning = false;
    private boolean win = false;
    private BufferedImage wall, blank, box, player, target, boxPlaced;

    public PlayField pf;
    public char[][] charArray;
    public int currentLvl;
    //private PlayerCoordinates pc;


    public GameRun(String title, int width, int height) {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    private void initialize(){
        //Loading in all of the images
        wall = ImageLoad.image("Textures/sokoban_icons/wall.png");
        blank = ImageLoad.image("Textures/sokoban_icons/blank.png");
        player = ImageLoad.image("Textures/sokoban_icons/player.png");
        box = ImageLoad.image("Textures/sokoban_icons/crate.png");
        target = ImageLoad.image("/Textures/sokoban_icons/blankmarked.png");
        boxPlaced = ImageLoad.image("/Textures/sokoban_icons/cratemarked.png");

        //Display the game is to be on
        display = new Display(height,width,title);
        pf = new PlayField();
        charArray = pf.setUpLvlOne();

        display.getCanvas().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                firstRun = false;
                PlayerCoordinates pc = pf.player;
                int direction = e.getKeyCode();
                try {
                    if (direction == KeyEvent.VK_LEFT) {
                        //if there is a box to the left of the player
                        if((charArray[pc.getPosX()-1][pc.getPosY()] == 'B'||charArray[pc.getPosX()-1][pc.getPosY()] == 'W')) {
                            //if there isn't a wall or another box after the box (our boy isn't that strong)
                            if (!(charArray[pc.getPosX()-2][pc.getPosY()] == '*')&&!(charArray[pc.getPosX()-2][pc.getPosY()] == 'B')&&!(charArray[pc.getPosX()-2][pc.getPosY()] == 'W')) {
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
                            //if there isn't a wall or another box after the box (our boy isn't that strong)
                            if (!(charArray[pc.getPosX()+2][pc.getPosY()] == '*')&&!(charArray[pc.getPosX()+2][pc.getPosY()] == 'B')&&!(charArray[pc.getPosX()+2][pc.getPosY()] == 'W')) {
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

                        //if there is a box to the left of the player
                        if((charArray[pc.getPosX()][pc.getPosY()-1] == 'B'||charArray[pc.getPosX()][pc.getPosY()-1] == 'W')) {
                            //if there isn't a wall or another box after the box (our boy isn't that strong)
                            if (!(charArray[pc.getPosX()][pc.getPosY()-2] == '*')&&!(charArray[pc.getPosX()][pc.getPosY()-2] == 'B')&&!(charArray[pc.getPosX()][pc.getPosY()-2] == 'W')) {
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
                            //if there isn't a wall or another box after the box (our boy isn't that strong)
                            if (!(charArray[pc.getPosX()][pc.getPosY()+2] == '*')&&!(charArray[pc.getPosX()][pc.getPosY()+2] == 'B')&&!(charArray[pc.getPosX()][pc.getPosY()+2] == 'W')) {
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
                        //User press R to restart lvl
                    } if (direction == KeyEvent.VK_R) {
                        pf = new PlayField();

                        switch (currentLvl) {
                            case 0 -> charArray = pf.setUpLvlOne();
                            case 1 -> charArray = pf.setUpLvlTwo();
                            case 2 -> charArray = pf.setUpLvlThree();
                            case 3 -> charArray = pf.setUpLvlFour();
                            default -> charArray = pf.setUpLvlThree();
                        }

                        pc = pf.player;
                    }
                    //User press enter to move to next lvl if win condition is true
                    if (direction == KeyEvent.VK_ENTER && win) {
                        pf = new PlayField();
                        currentLvl++;
                        switch (currentLvl) {
                            case 1 -> charArray = pf.setUpLvlTwo();
                            case 2 -> charArray = pf.setUpLvlThree();
                            case 3 -> charArray = pf.setUpLvlFour();
                            default -> {
                                charArray = pf.setUpLvlOne();
                                currentLvl = 0;
                            }
                        }
                        pc = pf.player;

                    }

                }catch (IndexOutOfBoundsException a){
                    a.printStackTrace();
                }
            }
        });

    }

    private void update(){

        win = true;
        //Make sure to keep the target painted even if a player or block passes it
        for (PlayerCoordinates p : PlayField.targets) {

            if(!(charArray[p.getPosX()][p.getPosY()]=='P')&&!(charArray[p.getPosX()][p.getPosY()]=='B')&&!(charArray[p.getPosX()][p.getPosY()]=='W')){
                charArray[p.getPosX()][p.getPosY()] ='T';
            }
            //If all the target squares have filled in box on them the player have won
            if(!(charArray[p.getPosX()][p.getPosY()]=='W')){
                win = false;
            }
        }

    }


    private void render(){
        BufferStrategy bufferStrategy = display.getCanvas().getBufferStrategy(); //Hidden screen to prevent flickering
        //First time running the game
        if (bufferStrategy ==null){
            display.getCanvas().createBufferStrategy(3); //Don't need more than 3
            return;
        }
        Graphics graphics = bufferStrategy.getDrawGraphics();  //Like a paintbrush to draw on the canvas
        //Clear window
        graphics.clearRect(0,0,width,height);

        //Beginning to draw

        //10x10 board was the goal so each picture is drawn as a tenth of width and height for a generic implementation
        for (int i = 0; i < charArray.length; i++) {
            for (int j = 0; j < charArray[0].length; j++) {
                switch (charArray[i][j]) {
                    case '*' ->
                            //Print wall
                            graphics.drawImage(wall, i * width / 10, j * height / 10, null);
                    case 'P' ->
                            //Print Player

                            graphics.drawImage(player, i * width / 10, j * height / 10, null);
                    case 'T' ->
                            //Print Target
                            graphics.drawImage(target, i * width / 10, j * height / 10, null);
                    case 'B' ->
                            //Print Box
                            graphics.drawImage(box, i * width / 10, j * height / 10, null);
                    case 'W' ->
                            //Print BoxPlaced
                            graphics.drawImage(boxPlaced, i * width / 10, j * height / 10, null);
                    default ->
                            //Pint empty space
                            graphics.drawImage(blank, i * width / 10, j * height / 10, null);
                }

            }

        }

        //Explain game premiss to user
        graphics.setColor(Color.BLUE);
        graphics.setFont(new Font("Bold", 1, 15));
        if(firstRun) {
            graphics.clearRect(20,120,280,80);
            graphics.drawString("Move the boxes to their targets", 35, 160);
            graphics.drawString("press R to restart", 84, 175);
        }

        //If all the boxes have reached their targets
        if(win){
            graphics.clearRect(20,120,280,80);
            if(currentLvl==3){
                graphics.drawString("You beat the final level!!!", 64, 160);
                graphics.drawString("Press enter to start over", 64, 175);
            }else
                graphics.drawString("You win, press enter for next lvl", 32, 160);
        }

        //Banner saying which level the user currently is on moving across the screen
        graphics.setColor(Color.WHITE);
        graphics.drawString("Level: "+(currentLvl+1), tick++, 20);
        if(tick==320)
            tick=0;

        //Ending drawing
        bufferStrategy.show();
        graphics.dispose();

    }

    @Override
    public void run() {
        int fps = 30; //Setting up frame rate
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
