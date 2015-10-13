import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by James on 10/12/2015.
 */

//thread safe
public class ConnectionMap {

    //guarded by (this)
    private final HashMap<String,Connection> connected = new HashMap<>();

    public synchronized void add(String username,Connection connection)
    {
        connected.put(username,connection);
    }

    public synchronized Connection get(String username)
    {
        return connected.get(username);
    }

    public synchronized void remove(String username)
    {
        connected.remove(username);
    }

    public synchronized boolean contains(String username)
    {
        return connected.containsKey(username);
    }

    public synchronized Set<Map.Entry<String,Connection>> entrySet()
    {
        return connected.entrySet();
    }
}
