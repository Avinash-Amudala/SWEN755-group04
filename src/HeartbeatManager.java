package src;

public class HeartbeatManager extends Thread {
    private long lastHeartbeat = System.currentTimeMillis();
    private static final long THRESHOLD = 3000;
    private volatile boolean serverAlive = true;

    public void run() {
        while (serverAlive) {
            if ((System.currentTimeMillis() - getLastHeartbeat()) > THRESHOLD) {
                if (serverAlive) {
                    System.out.println("Server might have crashed!");
                }
                resetHeartbeat();
            }
        }
    }

    public synchronized void receiveHeartbeat() {
        lastHeartbeat = System.currentTimeMillis();
    }

    private synchronized long getLastHeartbeat() {
        return lastHeartbeat;
    }

    private synchronized void resetHeartbeat() {
        lastHeartbeat = System.currentTimeMillis();
    }

    public void stopServer() {
        serverAlive = false;
    }
}
