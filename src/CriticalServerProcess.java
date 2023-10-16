package src;

import java.net.*;
import java.io.*;

public class CriticalServerProcess extends Thread {
    private HeartbeatManager hm;
    private ServerSocket serverSocket;
    private volatile boolean running = true;  // to manage the server's running state

    public CriticalServerProcess(HeartbeatManager hm, int port) throws IOException {
        this.hm = hm;
        serverSocket = new ServerSocket(port);
    }

    public void run() {
        // Start a separate thread for sending heartbeats and checking for random failures
        new Thread(this::sendHeartbeatsAndRandomFailures).start();

        while (true) {
            acceptClient();
        }
    }

    private void acceptClient() {
        try {
            Socket socket = serverSocket.accept();
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            if(running){
            out.println("Connected to server!");
            System.out.println("Client connected from IP address: " + socket.getInetAddress().getHostAddress());
            }
            else{
                System.out.println("Random failure in Server!");
                out.println("Server has experienced a failure.");
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendHeartbeatsAndRandomFailures() {
        while (running) {
            // Send heartbeat
            hm.receiveHeartbeat();

            // Random failure
            if (Math.random() > 0.9) {
                System.out.println("Random failure in Server!");
                running = false;  // Stop the server gracefully
                break;
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
