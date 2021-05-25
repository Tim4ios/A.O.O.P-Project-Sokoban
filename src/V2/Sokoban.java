package V2;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Sokoban extends Engine {
    private char[][] level = new char[10][10];
    public int currentLvl;
    public PlayField pf;
    private Sprite box, wall, player, blank, target, boxPlaced;
    public Graphics g;
    public int tick;

    boolean firstRun = true;
    private boolean isRunning = false;
    private boolean win = false;

    public Sokoban() {
        title = "Sokoban";
        width = 320;
        height = 320;

    }

    @Override
    public void onUserStart() {
        pf = new PlayField();
        level = pf.setUpLvlOne();

        box = new Sprite("res/Textures/sokoban_icons/crate.png");
        wall = new Sprite("res/Textures/sokoban_icons/wall.png");
        blank = new Sprite("res/Textures/sokoban_icons/blank.png");
        player = new Sprite("res/Textures/sokoban_icons/player.png");
        boxPlaced = new Sprite("res/Textures/sokoban_icons/cratemarked.png");
        target = new Sprite("res/Textures/sokoban_icons/blankmarked.png");
    }

    @Override
    public void onUserUpdate() {
        win = true;
        //Make sure to keep the target painted even if a player or block passes it
        for (PlayerCoordinates p : PlayField.targets) {

            if(!(level[p.getPosX()][p.getPosY()]=='P')&&!(level[p.getPosX()][p.getPosY()]=='B')&&!(level[p.getPosX()][p.getPosY()]=='W')){
                level[p.getPosX()][p.getPosY()] ='T';
            }
            //If all the target squares have filled in box on them the player have won
            if(!(level[p.getPosX()][p.getPosY()]=='W')){
                win = false;
            }
        }

        for (int i = 0; i < level.length; i++) {
            for (int j = 0; j < level[i].length; j++) {
                switch(level[i][j]) {
                    case '*' ->
                            //Print wall
                            draw(wall, i * width / 10, j * height / 10);
                    case 'P' ->
                            //Print Player

                            draw(player, i * width / 10, j * height / 10);
                    case 'T' ->
                            //Print Target
                            draw(target, i * width / 10, j * height / 10);
                    case 'B' ->
                            //Print Box
                            draw(box, i * width / 10, j * height / 10);
                    case 'W' ->
                            //Print BoxPlaced
                            draw(boxPlaced, i * width / 10, j * height / 10);
                    default ->
                        draw(blank, i*width/10, j*height/10);
                }
            }
        }

       g = box.g;
        //Explain game premiss to use
        g.setColor(Color.BLUE);
        g.setFont(new Font("Bold", 1, 15));
        if(firstRun) {
            g.clearRect(20,120,280,80);
            g.drawString("Move the boxes to their targets", 35, 160);
            g.drawString("press R to restart", 84, 175);
        }

        //If all the boxes have reached their targets
        if(win){
            g.clearRect(20,120,280,80);
            if(currentLvl==3){
                g.drawString("You beat the final level!!!", 64, 160);
                g.drawString("Press enter to start over", 64, 175);
            }else
                g.drawString("You win, press enter for next lvl", 32, 160);
        }

        //Banner saying which level the user currently is on moving across the screen
        g.setColor(Color.WHITE);
        g.drawString("Level: "+(currentLvl+1), tick++, 20);
        if(tick==320)
            tick=0;
    }

    @Override
    public void onKeyPressed(KeyEvent e) {
        firstRun = false;
        PlayerCoordinates pc = pf.player;
        int direction = e.getKeyCode();
        try {
            if (direction == KeyEvent.VK_LEFT) {
                //if there is a box to the left of the player
                if((level[pc.getPosX()-1][pc.getPosY()] == 'B'||level[pc.getPosX()-1][pc.getPosY()] == 'W')) {
                    //if there isn't a wall or another box after the box (our boy isn't that strong)
                    if (!(level[pc.getPosX()-2][pc.getPosY()] == '*')&&!(level[pc.getPosX()-2][pc.getPosY()] == 'B')&&!(level[pc.getPosX()-2][pc.getPosY()] == 'W')) {
                        if((level[pc.getPosX()-2][pc.getPosY()] == 'T')){
                            level[pc.getPosX()-2][pc.getPosY()]='W';
                        }else{
                            level[pc.getPosX()-2][pc.getPosY()]='B';
                        }
                        level[pc.getPosX()][pc.getPosY()]=' '; //The spot the player was in is turning blanc
                        level[pc.getPosX()-1][pc.getPosY()]='P';
                        pc.setPosX(pc.getPosX()-1);
                    }
                }else if(!(level[pc.getPosX()-1][pc.getPosY()] == '*')){
                    level[pc.getPosX()][pc.getPosY()]=' '; //The spot the player was in is turning blanc
                    level[pc.getPosX()-1][pc.getPosY()]='P';
                    pc.setPosX(pc.getPosX()-1);

                }

            }
            if (direction == KeyEvent.VK_RIGHT) {
                //if there is a box right of the player
                if((level[pc.getPosX()+1][pc.getPosY()] == 'B'||level[pc.getPosX()+1][pc.getPosY()] == 'W')) {
                    //if there isn't a wall or another box after the box (our boy isn't that strong)
                    if (!(level[pc.getPosX()+2][pc.getPosY()] == '*')&&!(level[pc.getPosX()+2][pc.getPosY()] == 'B')&&!(level[pc.getPosX()+2][pc.getPosY()] == 'W')) {
                        if((level[pc.getPosX()+2][pc.getPosY()] == 'T')){
                            level[pc.getPosX()+2][pc.getPosY()]='W';
                        }else{
                            level[pc.getPosX()+2][pc.getPosY()]='B';
                        }
                        level[pc.getPosX()][pc.getPosY()]=' '; //The spot the player was in is turning blanc
                        level[pc.getPosX()+1][pc.getPosY()]='P';
                        pc.setPosX(pc.getPosX()+1); //Change xVal since we are moving on the x-axis
                    }
                    //if there isn't a wall right of the player
                }else if(!(level[pc.getPosX()+1][pc.getPosY()] == '*')){
                    level[pc.getPosX()][pc.getPosY()]=' '; //The spot the player was in is turning blanc
                    level[pc.getPosX()+1][pc.getPosY()]='P';
                    pc.setPosX(pc.getPosX()+1);
                }
            }
            if (direction == KeyEvent.VK_UP) {

                //if there is a box to the left of the player
                if((level[pc.getPosX()][pc.getPosY()-1] == 'B'||level[pc.getPosX()][pc.getPosY()-1] == 'W')) {
                    //if there isn't a wall or another box after the box (our boy isn't that strong)
                    if (!(level[pc.getPosX()][pc.getPosY()-2] == '*')&&!(level[pc.getPosX()][pc.getPosY()-2] == 'B')&&!(level[pc.getPosX()][pc.getPosY()-2] == 'W')) {
                        if((level[pc.getPosX()][pc.getPosY()-2] == 'T')){
                            level[pc.getPosX()][pc.getPosY()-2]='W';
                        }else{
                            level[pc.getPosX()][pc.getPosY()-2]='B';
                        }
                        level[pc.getPosX()][pc.getPosY()]=' '; //The spot the player was in is turning blanc
                        level[pc.getPosX()][pc.getPosY()-1]='P';
                        pc.setPosY(pc.getPosY()-1);
                    }
                }else if(!(level[pc.getPosX()][pc.getPosY()-1] == '*')){
                    level[pc.getPosX()][pc.getPosY()]=' '; //The spot the player was in is turning blanc
                    level[pc.getPosX()][pc.getPosY()-1]='P';
                    pc.setPosY(pc.getPosY()-1);

                }

            }
            if (direction == KeyEvent.VK_DOWN) {
                if((level[pc.getPosX()][pc.getPosY()+1] == 'B'||level[pc.getPosX()][pc.getPosY()+1] == 'W')) {
                    //if there isn't a wall or another box after the box (our boy isn't that strong)
                    if (!(level[pc.getPosX()][pc.getPosY()+2] == '*')&&!(level[pc.getPosX()][pc.getPosY()+2] == 'B')&&!(level[pc.getPosX()][pc.getPosY()+2] == 'W')) {
                        if((level[pc.getPosX()][pc.getPosY()+2] == 'T')){
                            level[pc.getPosX()][pc.getPosY()+2]='W';
                        }else{
                            level[pc.getPosX()][pc.getPosY()+2]='B';
                        }
                        level[pc.getPosX()][pc.getPosY()]=' '; //The spot the player was in is turning blanc
                        level[pc.getPosX()][pc.getPosY()+1]='P';

                        pc.setPosY(pc.getPosY()+1);
                    }
                }else if(!(level[pc.getPosX()][pc.getPosY()+1] == '*')){
                    level[pc.getPosX()][pc.getPosY()]=' '; //The spot the player was in is turning blanc
                    level[pc.getPosX()][pc.getPosY()+1]='P';
                    pc.setPosY(pc.getPosY()+1);
                }
                //User press R to restart lvl
            } if (direction == KeyEvent.VK_R) {
                pf = new PlayField();

                switch (currentLvl) {
                    case 0 -> level = pf.setUpLvlOne();
                    case 1 -> level = pf.setUpLvlTwo();
                    case 2 -> level = pf.setUpLvlThree();
                    case 3 -> level = pf.setUpLvlFour();
                    default -> level = pf.setUpLvlThree();
                }

                pc = pf.player;
            }
            //User press enter to move to next lvl if win condition is true
            if (direction == KeyEvent.VK_ENTER && win) {
                pf = new PlayField();
                currentLvl++;
                switch (currentLvl) {
                    case 1 -> level = pf.setUpLvlTwo();
                    case 2 -> level = pf.setUpLvlThree();
                    case 3 -> level = pf.setUpLvlFour();
                    default -> {
                        level = pf.setUpLvlOne();
                        currentLvl = 0;
                    }
                }
                pc = pf.player;

            }

        }catch (IndexOutOfBoundsException a){
            a.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Sokoban s = new Sokoban();
        if (s.init()) {
            s.start();
        } else {
            System.out.println("error during init");
            System.exit(1);
        }
    }
}