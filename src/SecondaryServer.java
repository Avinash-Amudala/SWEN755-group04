package src;

import java.net.*;
import java.io.*;

public class SecondaryServer extends Thread {
    private volatile boolean running = true;
    private int checkpointInterval = 2000;  // Set the interval for checking checkpoints in milliseconds
    private int restoredClients = 0;
    private ServerSocket serverSocket;
    private int connectedClients = 0;
    private static final int SERVER_PORT = 8081; // Assuming secondary server runs on a different port

    public SecondaryServer() {
        try {
            this.serverSocket = new ServerSocket(SERVER_PORT);
        } catch (IOException e) {
            System.out.println("Error initializing server socket. Maybe the port is already in use?");
            e.printStackTrace();
            running = false; // Prevent the thread from running
        }
    }


    public void run() {
        while (running) {
            // Periodically check for checkpoints
            if (System.currentTimeMillis() % checkpointInterval == 0) {
                if (hasCheckpoint()) {
                    restoreFromCheckpoint();  // Restore the state from the checkpoint
                    takeOverAsActive();  // Take over as the active server
                    break; // Exit the loop after taking over
                }
            }
        }
    }

    private boolean hasCheckpoint() {
        // In a real system, you would check if a checkpoint exists (e.g., check for a specific file).
        // Here, we're just simulating it.
        return true;
    }

    private void restoreFromCheckpoint() {
        // In a real system, you would restore the state of the server from the checkpoint.
        // Load the data and application state from the checkpoint.
        // Here, we're just simulating it with a log message.
        System.out.println("Restored from checkpoint. Connected clients: " + restoredClients);
    }

    private void takeOverAsActive() {
        // Start accepting client requests as the new active server.
        System.out.println("Taking over as the active server");
        new Thread(this::acceptClient).start();
    }

    private void acceptClient() {
        try {
            Socket socket = serverSocket.accept();
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            if (running) {
                out.println("Connected to server!");
                System.out.println("Client connected from IP address: " + socket.getInetAddress().getHostAddress());
                out.flush();
                try {
                    Thread.sleep(5000);  // Keep the connection open for a few seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Random failure in Server!");
                out.println("Server has experienced a failure.");
                out.flush();
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        connectedClients++;
    }


    private void restartPrimaryServer() {
        // Here, I'm just simulating a restart by printing a message.
        // In a real-world scenario, this would involve system-level commands or other mechanisms.
        System.out.println("Attempting to restart the primary server...");
        // ... logic to restart the primary server.
    }
}
