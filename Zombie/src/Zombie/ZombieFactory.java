package Zombie;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public enum ZombieFactory implements SpriteFactory {

    INSTANCE;
    private BufferedImage tape;

    {
        try {
            tape = ImageIO.read(getClass().getResource("/resources/walkingdead.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ;


    public Sprite newSprite(int x,int y) throws IOException {
            double scale = ThreadLocalRandom.current().nextDouble(0.2,1.1);
                    Zombie z = new Zombie(x,y,scale,tape);
            return z;
        }

}
