import sun.rmi.runtime.Log;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by James on 10/12/2015.
 */
public class IncomingPackets implements Runnable {

    private boolean running = true;
    private DatagramSocket serverSocket=null;
    ConnectionMap connections = new ConnectionMap();
    PacketList packets = new PacketList();

    public IncomingPackets()
    {
        new Thread(this).start();
    }

    public void stopThread()
    {
        running=false;
        serverSocket.close();
    }

    public Packet receive(byte[] receiveData)
    {

        //receive a packet
        int receivedBytes = 0;

        DatagramPacket recvPacket = new DatagramPacket(receiveData, receiveData.length);
        try{
            serverSocket.receive(recvPacket);
            receivedBytes = recvPacket.getLength();
        }catch(IOException e){
            System.err.println("IOException in UdpReceiver.receive");
            return null;
        }
        byte[] myObject = new byte[receivedBytes];

        for(int i = 0; i < receivedBytes; i++)
        {
            myObject[i] = receiveData[i];
        }

        Packet obj = deserializeManagerPacket(receiveData);

        return obj;
    }


    public Packet deserializeManagerPacket(byte[] data)
    {
        try
        {
            ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(data));
            Packet obj = (Packet) iStream.readObject();
            iStream.close();
            return obj;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void run(){
        try {
            serverSocket = new DatagramSocket(9876);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        byte[] receiveData = new byte[1024];

        while(running)
        {
            System.out.println("waiting for packet");
            packets.add(receive(receiveData));

        }
    }
}
