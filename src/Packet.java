import java.io.Serializable;

/**
 * Created by James on 10/12/2015.
 */
public class Packet implements Serializable{

    private static final long serialVersionUID = 2142633872L;
    protected String username;
    protected int packetID;

    public Packet(String username)
    {
        this.username=username;
        packetID=0;
    }

    public String getUsername()
    {
        return username;
    }

    public int getPacketID()
    {
        return packetID;
    }
}