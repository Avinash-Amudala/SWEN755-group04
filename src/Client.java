package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.ConnectException;

public class Client {
    public static void main(String[] args) {
        String serverAddress = "127.0.0.1";
        int primaryServerPort = 8080;
        int secondaryServerPort = 8081;

        // Try connecting to primary server first
        if (!tryConnect(serverAddress, primaryServerPort)) {
            // If failed, try connecting to secondary server
            tryConnect(serverAddress, secondaryServerPort);
        }
    }

    private static boolean tryConnect(String serverAddress, int serverPort) {
        try {
            Socket socket = new Socket(serverAddress, serverPort);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String message = in.readLine();
            if (message != null) {
                System.out.println("Server says: " + message);
            }

            in.close();
            socket.close();
            return true;
        } catch (ConnectException e) {
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
