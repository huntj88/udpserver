import java.util.LinkedList;

/**
 * Created by James on 10/12/2015.
 */
public class PacketList {
    private final LinkedList<Packet> packetList = new LinkedList<>();

    public synchronized void add(Packet packet)
    {
        //System.out.println(packet.getUsername());

        /*if(packet instanceof TextPacket)
        {
            System.out.println("text: " + ((TextPacket) packet).getText());
        }*/

        packetList.add(packet);
    }

    public synchronized void remove()
    {
        //System.out.print(packetList.size()+" ");
        if(packetList.size()>0)
        packetList.removeFirst();
        //System.out.println(packetList.size());
    }

    public synchronized Packet get()
    {
        return packetList.get(0);
    }

    public synchronized int size()
    {
        return packetList.size();
    }
}
