public class HeartbeatManager extends Thread {

    private long lastHeartbeat = System.currentTimeMillis();
    private static final long THRESHOLD = 3000;

    public void run() {
        while (true) {
            if ((System.currentTimeMillis() - lastHeartbeat) > THRESHOLD) {
                System.out.println("Server might have crashed!");
                lastHeartbeat = System.currentTimeMillis();
            }
        }
    }

    public void receiveHeartbeat() {
        lastHeartbeat = System.currentTimeMillis();
    }
}
