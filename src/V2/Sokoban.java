package V2;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Sokoban extends Engine {
    private char[][] level = new char[10][10];
    public int currentLvl;
    public PlayField pf;
    private Sprite box, wall, player, blank, target, boxPlaced;
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
    
    
    private void drawLevel() {
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
    }

    @Override
    public void onUserUpdate() {
        win = true;
        //Make sure to keep the target painted even if a player or block passes it
        for (PlayerCoordinates p : PlayField.targets) {

            if(!(getPositionInField(p.getPosX(), p.getPosY())== 'P') && !(getPositionInField(p.getPosX(), p.getPosY()) =='B') && !(getPositionInField(p.getPosX(), p.getPosY()) == 'W')){
                level[p.getPosX()][p.getPosY()] ='T';
            }
            //If all the target squares have filled in box on them the player has won
            if(!(getPositionInField(p.getPosX(), p.getPosY()) =='W')){
                win = false;
            }
        }
        
        drawLevel();

        //Explain game premiss to use
        g.setColor(Color.BLUE);
        g.setFont(new Font("Bold", 1, 15));
        if(firstRun) {
            draw("Move the boxes to their targets", 35, 160);
            draw("press R to restart", 84, 175);
        }

        //If all the boxes have reached their targets
        if(win){
            if(currentLvl==3){
                draw("You beat the final level!!!", 64, 160);
                draw("Press enter to start over", 64, 175);
            }else
                draw("You win, press enter for next lvl", 32, 160);
        }

        //Banner saying which level the user currently is on moving across the screen
        g.setColor(Color.WHITE);
        draw("Level: "+(currentLvl+1), tick++, 20);
        if(tick==320)
            tick=0;
    }
    
    private char getPositionInField(int x, int y) {
    	return level[x][y];
    }
    
    private char setPositionInField(char c, int x, int y) {
    	level[x][y] = c;
    }

    @Override
    public void onKeyPressed(KeyEvent e) {
        firstRun = false;
        PlayerCoordinates pc = pf.player;
        int direction = e.getKeyCode();
        try {
            if (direction == KeyEvent.VK_LEFT) {
                //if there is a box to the left of the player
                if((getPositionInField(pc.getPosX()-1,pc.getPosY()) == 'B'|| getPositionInField(pc.getPosX()-1,pc.getPosY()) == 'W')) {
                    //if there isn't a wall or another box after the box
                    if (!(getPositionInField(pc.getPosX()-2,pc.getPosY()) == '*')
                    && !(getPositionInField(pc.getPosX()-2,pc.getPosY()) == 'B')
                    && !(getPositionInField(pc.getPosX()-2,pc.getPosY()) == 'W')) {
                        if (( getPositionInField(level[pc.getPosX()-2, pc.getPosY()) == 'T')){
                        	setPositionInField('W', pc.getPosX()-2, pc.getPosY());
                        } else {
                        	setPositionInField('B', pc.getPosX()-2, pc.getPosY());
                        }
                        setPositionInField(' ', pc.getPosX(), pc.getPosY()); //The spot the player was in is turning blank
                        setPositionInField('P', pc.getPosX()-1, pc.getPosY());
                        pc.setPosX(pc.getPosX()-1);
                    }
                } else if (!(getPositionInField(pc.getPosX()-1, pc.getPosY()) == '*')) {
                    setPositionInField(' ', pc.getPosX(), pc.getPosY()); //The spot the player was in is turning blank
                    setPositionInField('P', pc.getPosX()-1, pc.getPosY());
                    pc.setPosX(pc.getPosX()-1);

                }

            }
            if (direction == KeyEvent.VK_RIGHT) {
                //if there is a box right of the player
                if ((getPositionInField(pc.getPosX()+1, pc.getPosY()) == 'B'|| getPositionInField(pc.getPosX()+1, pc.getPosY()) == 'W')) {
                    //if there isn't a wall or another box after the box (our boy isn't that strong)
                    if (!(getPositionInField(pc.getPosX()+2, pc.getPosY()) == '*')
                    && !(getPositionInField(pc.getPosX()+2, pc.getPosY()) == 'B')
                    && !(getPositionInField(pc.getPosX()+2, pc.getPosY()) == 'W')) {
                        if((getPositionInField(pc.getPosX()+2, pc.getPosY()) == 'T')){
                            setPositionInField('W', pc.getPosX()+2, pc.getPosY());
                        } else {
                        	setPositionInField('B', pc.getPosX() + 2, pc.getPosY());
                        }
                        setPositionInField(' ', pc.getPosX(), pc.getPosY()); //The spot the player was in is turning blank
                        setPositionInField('P', pc.getPosX(), pc.getPosY());
                        pc.setPosX(pc.getPosX()+1); //Change xVal since we are moving on the x-axis
                    }
                    //if there isn't a wall right of the player
                }else if (!(getPositionInField(pc.getPosX()+1, pc.getPosY()) == '*')) {
                	setPositionInField(' ', pc.getPosX(), pc.getPosY()); //The spot the player was in is turning blank
                	setPositionInField('P', pc.getPosX() + 1 , pc.getPosY());
                    pc.setPosX(pc.getPosX()+1);
                }
            }
            if (direction == KeyEvent.VK_UP) {

                //if there is a box to the left of the player
                if(( getPositionInField(pc.getPosX(), pc.getPosY()) == 'B'|| getPositionInField(pc.getPosX(), pc.getPosY()-1) == 'W')) {
                    //if there isn't a wall or another box after the box (our boy isn't that strong)
                    if (!(getPositionInField(pc.getPosX(), pc.getPosY()-2) == '*')
                    && !(getPositionInField(pc.getPosX(), pc.getPosY()-2) == 'B')
                    && !(getPositionInField(pc.getPosX(), pc.getPosY()-2) == 'W')) {
                        if((getPositionInField(pc.getPosX(), pc.getPosY()-2) == 'T')) {
                            setPositionInField('W', pc.getPosX(), pc.getPosY()-2);
                        } else {
                            setPositionInField('B', pc.getPosX(), pc.getPosY()-2);
                        }
                        setPositionInField(' ', pc.getPosX(), pc.getPosY()); //The spot the player was in is turning blank
                        setPositionInField('P', pc.getPosX(), pc.getPosY()-1);
                        pc.setPosY(pc.getPosY()-1);
                    }
                } else if (!(getPositionInField(pc.getPosX(), pc.getPosY()-1) == '*')) {
                	setPositionInField(' ', pc.getPosX(), pc.getPosY());
                	setPositionInField('P', pc.getPosX(), pc.getPosY()-1);
                    pc.setPosY(pc.getPosY()-1);

                }

            }
            if (direction == KeyEvent.VK_DOWN) {
                if((getPositionInField(pc.getPosX(), pc.getPosY()+1) == 'B'|| getPositionInField(pc.getPosX(), pc.getPosY()+1) == 'W')) {
                    //if there isn't a wall or another box after the box (our boy isn't that strong)
                    if (!(getPositionInField(pc.getPosX(), pc.getPosY()+2) == '*')
                    && !(getPositionInField(pc.getPosX(), pc.getPosY()+2) == 'B')
                    && !(getPositionInField(pc.getPosX(), pc.getPosY()+2) == 'W')) {
                        if((getPositionInField(pc.getPosX(), pc.getPosY()+2) == 'T')) {
                            setPositionInField('W', pc.getPosX(), pc.getPosY()+2);
                        } else {
                        	setPositionInField('B', pc.getPosX(), pc.getPosY()+2);
                        }
                        setPositionInField(' ', pc.getPosX(), pc.getPosY());
                        setPositionInField('P', pc.getPosX(), pc.getPosY()+1);
                        pc.setPosY(pc.getPosY()+1);
                    }
                }else if (!(getPositionInField(pc.getPosX(), pc.getPosY()+1) == '*')) {
                	setPositionInField(' ', pc.getPosX(), pc.getPosY());
                	setPositionInField('P', pc.getPosX(), pc.getPosY()+1);
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