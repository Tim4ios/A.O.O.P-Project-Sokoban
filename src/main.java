import java.awt.image.BufferedImage;

public class main {
     public static void main(String[] args) {
        GameRun game = new GameRun("SOKOBAN!!!",300,300);
        game.start();

        PlayField f = new PlayField();
       char[][] pf = f.setUpLvlOne();
         for (int i = 0; i <pf.length ; i++) {
             for (int j = 0; j <pf.length ; j++) {
                 System.out.print(pf[i][j]+" ");
             }
             System.out.println();
         }

    }
}

