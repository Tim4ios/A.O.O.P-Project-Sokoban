package V2;

import java.util.ArrayList;

public class PlayField {
    //Our playfield is 10x10 which will contain walls, player, boxes and target
    private final char[][] field = new char[10][10];
    public PlayerCoordinates player;
    public static ArrayList<PlayerCoordinates> targets = new ArrayList<PlayerCoordinates>();

    public PlayField() {
        player = new PlayerCoordinates(1,1);
    }
//Make the field an Observable? Each time there is a change call gui observer to update

    //Important to note that X and Y on the levels you see below is interchanged
    //so it is the opposite from what a normal X/Y coordinate system is
    public char[][] setUpLvlOne(){
        targets.removeAll(targets);
        //Map inverted X is Y and opposite
        field[0] = new char[]{'*', '*', '*', '*', '*', '*', '*', '*', '*', '*'};
        field[1] = new char[]{'*', 'P', '-', '-', '-', '-', '-', '-', '-', '*'};
        field[2] = new char[]{'*', '-', '-', '-', '-', '-', '-', '-', '-', '*'};
        field[3] = new char[]{'*', '-', '-', '-', '-', '-', '-', '-', '-', '*'};
        field[4] = new char[]{'*', '-', '-', '-', '-', '-', '-', '-', '-', '*'};
        field[5] = new char[]{'*', '-', 'B', '-', '-', '-', '-', '-', '-', '*'};
        field[6] = new char[]{'*', '-', '-', '-', '-', '-', '-', '-', '-', '*'};
        field[7] = new char[]{'*', '-', '-', '-', '-', '-', '-', '-', '-', '*'};
        field[8] = new char[]{'*', '-', '-', '-', '-', '-', '-', '-', 'T', '*'};
        field[9] = new char[]{'*', '*', '*', '*', '*', '*', '*', '*', '*', '*'};

        player = new PlayerCoordinates(1,1);
        targets.add(new PlayerCoordinates(8,8));

        return field;
    }

    public char[][] setUpLvlTwo(){
       targets.removeAll(targets);

        field[0] = new char[]{'*', '*', '*', '*', '*', '*', '*', '*', '*', '*'};
        field[1] = new char[]{'*', '-', 'T', '-', '*', '*', '-', '-', '-', '*'};
        field[2] = new char[]{'*', 'P', '-', '-', '*', '*', '-', '-', '-', '*'};
        field[3] = new char[]{'*', '-', '-', '-', '*', '*', '-', '-', '-', '*'};
        field[4] = new char[]{'*', '-', '-', '-', '*', '*', '-', '-', '-', '*'};
        field[5] = new char[]{'*', '-', '-', 'B', 'B', '-', '-', '-', '-', '*'};
        field[6] = new char[]{'*', '-', '-', '-', '-', '-', '-', '*', '-', '*'};
        field[7] = new char[]{'*', '-', '-', '-', '-', '-', '-', '*', '-', '*'};
        field[8] = new char[]{'*', '-', '-', '-', '-', '-', '-', '*', 'T', '*'};
        field[9] = new char[]{'*', '*', '*', '*', '*', '*', '*', '*', '*', '*'};

        player.setPosX(2);
        player.setPosY(1);
        targets.add(new PlayerCoordinates(8,8));
        targets.add(new PlayerCoordinates(2,1));

        return field;
    }

    public char[][] setUpLvlThree(){
        targets.removeAll(targets);

        field[0] = new char[]{'*', '*', '*', '*', '*', '*', '*', '*', '*', '*'};
        field[1] = new char[]{'*', '-', '-', '-', '*', '-', '-', '-', '-', '*'};
        field[2] = new char[]{'*', '-', 'P', 'B', '*', '-', '-', '-', '-', '*'};
        field[3] = new char[]{'*', '-', '-', '-', '*', 'B', '-', '*', '-', '*'};
        field[4] = new char[]{'*', '-', '-', '-', '-', '-', '-', '*', '-', '*'};
        field[5] = new char[]{'*', '-', '-', '-', '-', '-', '-', '*', '-', '*'};
        field[6] = new char[]{'*', '-', '-', '-', '-', '-', '*', '-', '-', '*'};
        field[7] = new char[]{'*', '-', '-', '-', '-', '-', '*', '-', '-', '*'};
        field[8] = new char[]{'*', 'T', '-', '-', '-', '-', '*', '-', 'T', '*'};
        field[9] = new char[]{'*', '*', '*', '*', '*', '*', '*', '*', '*', '*'};
        player.setPosX(2);
        player.setPosY(2);
        targets.add(new PlayerCoordinates(8,8));
        targets.add(new PlayerCoordinates(1,8));

        return field;
    }

    public char[][] setUpLvlFour(){
        targets.removeAll(targets);

        field[0] = new char[]{'*', '*', '*', '*', '*', '*', '*', '*', '*', '*'};
        field[1] = new char[]{'*', '*', '*', '*', '-', '-', '-', '-', '*', '*'};
        field[2] = new char[]{'*', '*', 'T', 'P', 'B', '-', '-', '*', '*', '*'};
        field[3] = new char[]{'*', '*', '*', '*', '-', 'B', 'T', '*', '*', '*'};
        field[4] = new char[]{'*', '*', 'T', '*', '*', 'B', '_', '*', '*', '*'};
        field[5] = new char[]{'*', '*', '_', '*', '_', 'T', '-', '*', '*', '*'};
        field[6] = new char[]{'*', '*', 'B', 'B', 'T', 'B', 'B', 'T', '*', '*'};
        field[7] = new char[]{'*', '*', '-', '-', '-', 'T', '-', '-', '*', '*'};
        field[8] = new char[]{'*', '*', '*', '*', '*', '*', '*', '*', '*', '*'};
        field[9] = new char[]{'*', '*', '*', '*', '*', '*', '*', '*', '*', '*'};
        player.setPosX(2);
        player.setPosY(3);
        targets.add(new PlayerCoordinates(2,2));
        targets.add(new PlayerCoordinates(2,4));
        targets.add(new PlayerCoordinates(4,6));
        targets.add(new PlayerCoordinates(5,5));
        targets.add(new PlayerCoordinates(5,7));
        targets.add(new PlayerCoordinates(6,3));
        targets.add(new PlayerCoordinates(7,6));

        return field;
    }

}
