package src;

import java.io.IOException;

public class ServerMonitorSystem {
    public static void main(String[] args) {
        HeartbeatManager heartbeatManager = new HeartbeatManager();

        // Start the HeartbeatManager thread
        heartbeatManager.start();

        int serverPort = 8080; // Change to your desired port
        try {
            CriticalServerProcess serverProcess = new CriticalServerProcess(heartbeatManager, serverPort);
            serverProcess.start();

            // Create and start the SecondaryServer with a reference to the primary server
            SecondaryServer secondaryServer = new SecondaryServer(serverProcess);
            secondaryServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
