package ChatApp;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {

    public static void main(String[] args) {

        String serverAddress = "localhost";
        int port = 5000;

        try {
            Socket socket = new Socket(serverAddress, port);
            System.out.println("Connected to chat server");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            Scanner sc = new Scanner(System.in);

            // Thread to receive messages
            new Thread(() -> {
                try {
                    String msg;
                    while ((msg = in.readLine()) != null) {
                        System.out.println("Message: " + msg);
                    }
                } catch (IOException e) {
                }
            }).start();

            // Sending messages
            while (true) {
                String message = sc.nextLine();
                out.println(message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
