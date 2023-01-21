package Zombie;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Zombie implements Sprite{
    BufferedImage tape;
    int x = 500;
    int y = 500;
    double scale = 1;

    int index = 0;  // numer wyświetlanego obrazka
    int HEIGHT = 312; // z rysunku;
    int WIDTH = 200; // z rysunku;

    Zombie(int x, int y, double scale) throws IOException {
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.tape = ImageIO.read(getClass().getResource("/resources/walkingdead.png"));
    }

    public Zombie(int x, int y, double scale, BufferedImage tape) {
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.tape = tape;
    }


    /**
     * Pobierz odpowiedni podobraz klatki (odpowiadającej wartości zmiennej index)
     * i wyświetl go w miejscu o współrzędnych (x,y)
     *
     * @param g
     * @param parent
     */

    public void draw(Graphics g, JPanel parent) {
        Image img = tape.getSubimage(this.index*200, 0, 200, 312); // pobierz klatkę
        g.drawImage(img, x, y - (int) (HEIGHT * scale) / 2, (int) (WIDTH * scale), (int) (HEIGHT * scale), parent);
    }

    /**
     * Zmień stan - przejdź do kolejnej klatki
     */

    public void next() {
        //if( this.x < 0)
           // x = 1000;
        x -= 20 * scale;
        index = (index + 1) % 10;
    }
    boolean isVisible() {
        return (x + WIDTH)*scale > 0;
    }

    public boolean isHit(int _x, int _y) {
        System.out.println("MyszkaZzomb: "+_x+", "+_y+"X: "+(x+", "+x+WIDTH)+"Y: "+(y+", "+y+HEIGHT));
        return _x >= x && _x <= x + WIDTH && _y-150*scale<= y && _y-120*scale >= y - HEIGHT*scale;
    }

    public boolean isCloser(Sprite other) {
        if (other instanceof Zombie) {
            Zombie z = (Zombie) other;
            return scale > z.scale;
        }
        return false;
    }
}