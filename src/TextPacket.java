/**
 * Created by James on 10/13/2015.
 */
public class TextPacket extends Packet {

    private String text;
    private static final long serialVersionUID = 2142633872L;

    public TextPacket(String username,String text) {
        super(username);
        this.text=text;
        packetID=1;
    }

    public String getText()
    {
        return text;
    }
}
