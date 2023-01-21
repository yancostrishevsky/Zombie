package Zombie;

public class Stats {
    int killedZombies = 0;
    int shots = 0;

    public void incrementKilledZombies() {
        killedZombies++;
    }

    public void incrementShots(){shots++;}

    public int getKilledZombies() {
        return killedZombies;
    }

    public int getShots(){return shots;}
}
