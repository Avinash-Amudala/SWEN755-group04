package src;

public class HeartbeatManager extends Thread {

    private long lastHeartbeat = System.currentTimeMillis();
    private static final long THRESHOLD = 3000;

    public void run() {
        while (true) {
            if ((System.currentTimeMillis() - getLastHeartbeat()) > THRESHOLD) {
                System.out.println("Server might have crashed!");
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
}
