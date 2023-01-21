package Zombie;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CrossHair implements MouseMotionListener, MouseListener, CrossHairListener {
    DrawPanel parent;
    List<CrossHairListener> listeners = new ArrayList<CrossHairListener>();

    CrossHair(DrawPanel parent){
        this.parent = parent;
    }


    int x;
    int y;
    boolean activated = false;

    public void draw(Graphics g) {
        if (activated) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.WHITE);
        }
        g.drawLine(x - 20, y, x + 20, y);
        g.drawLine(x, y - 20, x, y + 20);
        g.drawOval(x-15,y-15,30,30);
    }

    void addCrossHairListener(CrossHairListener e){
        listeners.add(e);
    }


    void notifyListeners(){
        for(var e:listeners)
            e.onShotsFired(x,y);
    }


    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        parent.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        //System.out.print("Myszka"+x+","+y);
        onShotsFired(x, y);
        activated = true;
        parent.repaint();
        notifyListeners();
        Timer timer = new Timer("Timer");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                activated = false;
                parent.repaint();
            }
        }, 300);
    }

    public void mouseDragged(MouseEvent e) { }

    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void onShotsFired(int x, int y) {

    }
}

