import java.io.IOException;
public class ServerMonitorSystem {

    public static void main(String[] args) {
        HeartbeatManager hm = new HeartbeatManager();
        try {
            CriticalServerProcess csp = new CriticalServerProcess(hm, 8080);
            hm.start();
            csp.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
