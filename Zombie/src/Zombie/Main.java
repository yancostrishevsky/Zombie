package Zombie;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import static Zombie.ZombieFactory.INSTANCE;

public class Main {

    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Zombie");
        URL backgroundImageURL = Main.class.getResource("/resources/mapa2.png"); //MAPY GENEROWAŁEM ZA POMOCĄ AI NA PODSTAWIE ZDJĘCIA GMACHU GŁÓWNEGO AGH
        DrawPanel panel = new DrawPanel(backgroundImageURL, INSTANCE);
        DrawPanel.AnimationThread thread = panel.new AnimationThread();
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                thread.requestStop();
            }
        });

        frame.setContentPane(panel);
        frame.setSize(1024, 1024);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);
        thread.start();
    }
}
