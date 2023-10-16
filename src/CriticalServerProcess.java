package src;

import java.net.*;
import java.io.*;
import java.util.Date;

public class CriticalServerProcess extends Thread {
    private HeartbeatManager hm;
    private ServerSocket serverSocket;
    private volatile boolean running = true;  // to manage the server's running state
    private int checkpointInterval = 60000;  // Set the interval for checkpointing in milliseconds

    public CriticalServerProcess(HeartbeatManager hm, int port) throws IOException {
        this.hm = hm;
        serverSocket = new ServerSocket(port);
    }

    public void run() {
        // Start a separate thread for sending heartbeats and checking for random failures
        new Thread(this::sendHeartbeatsAndRandomFailures).start();

        while (running) {
            acceptClient();
        }
    }

    private void acceptClient() {
        try {
            Socket socket = serverSocket.accept();
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            if (running) {
                out.println("Connected to server!");
                System.out.println("Client connected from IP address: " + socket.getInetAddress().getHostAddress());
            } else {
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
                createCheckpoint();  // Capture a checkpoint before stopping
                // Start the SecondaryServer when a failure occurs
                new SecondaryServer(this).start();
                hm.stopServer();
                break;
            }

            try {
                Thread.sleep(1000);

                // Periodically create checkpoints
                if (System.currentTimeMillis() % checkpointInterval == 0) {
                    createCheckpoint();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void createCheckpoint() {
        // In a real system, you would capture and store the state of the server,
        // e.g., data and application state, in a reliable manner.
        // Here, we're just simulating it with a simple log message.
        System.out.println("Checkpoint created at " + new Date());
    }
}
