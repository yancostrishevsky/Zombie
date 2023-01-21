package Zombie;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

public class DrawPanel  extends JPanel implements CrossHairListener {
    BufferedImage background;
    SpriteFactory factory;
    CrossHair crossHair = new CrossHair(this);
    Stats stats = new Stats();
    Semaphore mutex = new Semaphore(1);
    SynchronizedSpriteList sprites = new SynchronizedSpriteList();



    DrawPanel(URL backgroundImagageURL, SpriteFactory factory) {
        try {
            background = ImageIO.read(backgroundImagageURL);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        this.factory = factory;
        addMouseMotionListener(crossHair);
        addMouseListener(crossHair);
        crossHair.addCrossHairListener(this);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        for (Sprite s : this.sprites.getSprites()) {
            s.draw(g, this);
            removeInvisibleSprites();
        }

        crossHair.draw(g);
        Font font = new Font("Dialog", Font.PLAIN, 30);
        g.setFont(font);
        g.drawString("Zabite zombi: " + stats.getKilledZombies(), 10, 30);
        g.drawString("Strzały: " + stats.getShots(), 10, 60);

    }

    @Override
    public void onShotsFired(int x, int y) {
        stats.incrementShots();
        try {
            mutex.acquire();
            for (int i = sprites.size() - 1; i >= 0; i--) {
                Sprite z = sprites.getSprites().get(i);
                if (z.isHit(x, y)) {
                    sprites.remove(z);
                    stats.incrementKilledZombies();
                    repaint();
                    System.out.println("usuneity");
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mutex.release();
        }
    }


    private void sortSprites() {
        try {
            mutex.acquire();
            sprites.getSprites().sort((Sprite s1, Sprite s2) -> {
                if (s1 instanceof Zombie && s2 instanceof Zombie) {
                    Zombie z1 = (Zombie) s1;
                    Zombie z2 = (Zombie) s2;
                    return z1.isCloser(z2) ? -1 : 1;
                }
                return 0;
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mutex.release();

        }
    }

    private void removeInvisibleSprites() {
        sprites.getSprites().removeIf(s -> s instanceof Zombie && !((Zombie) s).isVisible());
    }


    class AnimationThread extends Thread {
        private AtomicBoolean stopRequested = new AtomicBoolean();
        int i = 0;


        public void requestStop() {
            stopRequested.set(true);
        }

        @Override
        public void run() {
            while (!stopRequested.get()) {
                for (Sprite s : sprites.getSprites()) {
                    s.next();
                }
                repaint();
                try {
                    sleep(1000 / 30);  // 30 klatek na sekundę
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (i % 30 == 0) {
                    try {
                        sprites.add(factory.newSprite(getWidth(), (int) ((Math.random() * (1.4 - 1) + 0.5) * (getHeight()))));
                        sortSprites();
                        //sprites.add(factory.newSprite(getWidth(), (int) ((Math.random() * (2.0 - 0.2) + 0.2) * (getHeight()))));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                i++;
            }

        }
    }
}



