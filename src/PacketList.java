import java.util.LinkedList;

/**
 * Created by James on 10/12/2015.
 */
public class PacketList {
    private final LinkedList<Packet> packetList = new LinkedList<>();

    public synchronized void add(Packet packet)
    {
        System.out.println(packet.getUsername());
        packetList.add(packet);
    }

    public synchronized void remove()
    {
        packetList.removeFirst();
    }

    public synchronized Packet get()
    {
        return packetList.get(0);
    }
}
