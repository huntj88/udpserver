import gui.GUI;

import javax.swing.*;
import java.awt.image.BufferedImage;

/**
 * Created by James on 10/12/2015.
 */
public class Start{

    public static final int WIDTH = 500;
    public static final int HEIGHT = 300;

    public static void main(String[] cows)
    {
        IncomingPackets incomingPackets = new IncomingPackets();
    }

    public void setUpGui()
    {
        JFrame frame = new JFrame("Server");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GUI gui = new GUI();
        frame.getContentPane().add(gui);
        frame.setSize(WIDTH,HEIGHT);
        frame.setVisible(true);
    }
}
