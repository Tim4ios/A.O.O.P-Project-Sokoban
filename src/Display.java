import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Display {
	private JFrame frame;
	private Canvas canvas;
	private int height, width;
	private String title;
	
	
	public Display(String t, int w, int h) {
		height = h;
		width = w;
		t = title;
		initDisplay();
	}
	
	private void initDisplay() {
        frame = new JFrame(title);
        frame.setSize(width,height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        //Canvas set up
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width,height)); //Same as the Frame
        canvas.setMinimumSize(new Dimension(width,height));  //Make sure it stays that size
        canvas.setMaximumSize(new Dimension(width,height)); // -||-

        frame.add(canvas);
        frame.pack();
	}
	
	public Canvas getCanvas() {
		return canvas;
	}
}
