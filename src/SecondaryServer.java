package src;

public class SecondaryServer extends Thread {
    private volatile boolean running = true;
    private int checkpointInterval = 2000;  // Set the interval for checking checkpoints in milliseconds
    private CriticalServerProcess primaryServer;

    public SecondaryServer(CriticalServerProcess primaryServer) {
        this.primaryServer = primaryServer;
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
        System.out.println("Restored from checkpoint");
    }

    private void takeOverAsActive() {
        // Start accepting client requests as the new active server.
        // In a real system, you would handle client connections and requests here.
        // Here, we'll just simulate it with a log message.
        System.out.println("Taking over as the active server");
    }
}
