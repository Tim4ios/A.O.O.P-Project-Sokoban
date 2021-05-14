import javax.swing.*;
import java.awt.*;

public class Display {
    private JFrame jFrame;
    private Canvas canvas;
    private int height, width;
    private String title;

    public Display(int height, int width, String title) {
        this.height = height;
        this.width = width;
        this.title = title;
        initDisplay(); //Avoid cluttering
    }
    private void initDisplay(){
        //Frame set up
         jFrame = new JFrame(title);
         jFrame.setSize(width,height);
         jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         jFrame.setResizable(false);
         jFrame.setLocationRelativeTo(null);
         jFrame.setVisible(true);

         //Canvas set up
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width,height)); //Same as the Frame
        canvas.setMinimumSize(new Dimension(width,height));  //Make sure it stays that size
        canvas.setMaximumSize(new Dimension(width,height)); // -||-

        jFrame.add(canvas);
        jFrame.pack();
    }
    public Canvas getCanvas(){
        return canvas;
    }

}
