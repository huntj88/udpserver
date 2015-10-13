import gui.GUI;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by James on 10/12/2015.
 */
public class Start{

    public static final int WIDTH = 500;
    public static final int HEIGHT = 300;

    public static void main(String[] cows)
    {
        ConnectionMap connections = new ConnectionMap();
        PacketList packets = new PacketList();
        DatagramSocket serverSocket = null;
        try {
            serverSocket = new DatagramSocket(9876);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        IncomingPackets incomingPackets = new IncomingPackets(connections,packets,serverSocket);
        OutgoingPackets outgoingPackets = new OutgoingPackets(connections,packets,serverSocket);

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
