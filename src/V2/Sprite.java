package V2;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {
    private BufferedImage texture;
    private String path;
    public Graphics g;


    public Sprite(String p) {
        path = p;
        try {
            texture = ImageIO.read(new File(p));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void draw(Graphics g, int posX, int posY) {
        this.g = g;
        g.drawImage(texture, posX, posY, null);
    }


    public BufferedImage getTexture() {
        return texture;
    }


    public String getPath() {
        return path;
    }
}
