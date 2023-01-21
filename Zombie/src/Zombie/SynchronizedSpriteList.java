package Zombie;

import java.util.ArrayList;
import java.util.List;

public class SynchronizedSpriteList {
    private List<Sprite> sprites = new ArrayList<>();

    public synchronized void add(Sprite sprite) {
        sprites.add(sprite);
    }

    public synchronized void remove(Sprite sprite) {
        sprites.remove(sprite);
    }

    public synchronized List<Sprite> getSprites() {
        return new ArrayList<>(sprites);
    }
    public synchronized int size(){return sprites.size();}
}
