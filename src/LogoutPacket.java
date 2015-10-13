/**
 * Created by James on 10/13/2015.
 */
public class LogoutPacket extends Packet {

    private static final long serialVersionUID = 2142633872L;

    public LogoutPacket(String username) {
        super(username);
        packetID=2;
    }
}
