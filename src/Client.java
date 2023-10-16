package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.ConnectException;

public class Client {
    public static void main(String[] args) {
        String serverAddress = "192.168.1.126";
        int serverPort = 8080;

        try {
            Socket socket = new Socket(serverAddress, serverPort);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Server says: " + message);
            }

            System.out.println("Server has closed the connection.");
            socket.close();
        } catch (ConnectException e) {
            System.out.println("Failed to connect to the server. Server might be down or unreachable.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
