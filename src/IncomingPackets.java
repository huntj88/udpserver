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
    DatagramSocket serverSocket=null;
    ConnectionMap connections;
    PacketList packets;

    public IncomingPackets(ConnectionMap connections,PacketList packets,DatagramSocket serverSocket)
    {
        this.connections=connections;
        this.packets=packets;
        this.serverSocket=serverSocket;
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

        if(!connections.contains(obj.getUsername()))
        {
            System.out.println("new connection "+obj.getUsername());
            connections.add(obj.getUsername(),new Connection(recvPacket.getAddress(),recvPacket.getPort(),obj.getUsername()));
        }
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
        byte[] receiveData = new byte[1024];

        while(running)
        {
            System.out.println("waiting for packet");
            packets.add(receive(receiveData));

        }
    }
}
