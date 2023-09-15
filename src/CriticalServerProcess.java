package src;

import java.net.*;
import java.io.*;

public class CriticalServerProcess extends Thread {

    private HeartbeatManager hm;
    private ServerSocket serverSocket;

    public CriticalServerProcess(HeartbeatManager hm, int port) throws IOException {
        this.hm = hm;
        serverSocket = new ServerSocket(port);
    }

    public void run() {
        while (true) {
            acceptClient();

            // Sending heartbeat
            hm.receiveHeartbeat();

            // Random failure
            if (Math.random() > 0.95) {
                throw new RuntimeException("Random failure in Server!");
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void acceptClient() {
        try {
            Socket socket = serverSocket.accept();
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("Connected to server!");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
