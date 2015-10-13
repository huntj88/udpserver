import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by James on 10/12/2015.
 */
public class OutgoingPackets implements Runnable {

    private boolean running = true;
    DatagramSocket serverSocket = null;
    ConnectionMap connections;
    PacketList packets;

    public OutgoingPackets(ConnectionMap connections, PacketList packets, DatagramSocket serverSocket) {
        this.connections = connections;
        this.packets = packets;
        this.serverSocket = serverSocket;
        new Thread(this).start();
    }

    public void stopThread() {
        running = false;
        serverSocket.close();
    }

    public void send() {

        System.out.println("send attempt "+packets.size());
        byte[] sendData = serializeManagerPacket(packets.get());

        Iterator it = connections.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            //System.out.println(pair.getKey() + " = " + pair.getValue());
            Connection temp = (Connection)pair.getValue();
            System.out.println(temp.getIP()+" "+temp.getPort());
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, temp.getIP(), temp.getPort());
            try {
                serverSocket.send(sendPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("sent");
            //it.remove(); // avoids a ConcurrentModificationException
        }
        System.out.println("packet removed "+packets.size());
        packets.remove();
    }

    public byte[] serializeManagerPacket(Packet mp) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(2048);
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(mp);
            oos.close();
            // get the byte array of the object
            byte[] obj = baos.toByteArray();
            baos.close();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    public void run() {
        while (running) {
            if(packets.size()>0)
                send();
            else
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }
}
