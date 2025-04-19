package project;

import java.io.*;
import java.net.*;

public class ServerMain {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8888)) {
            System.out.println("Server dang cho ket noi...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client da ket noi!");

                // Mỗi client xử lý ở một thread riêng
                new Thread(() -> {
                    try (DataInputStream input = new DataInputStream(socket.getInputStream())) {
                        while (true) {
                            String data = input.readUTF(); // Blocking
                            System.out.println(data);
                        }
                    } catch (IOException e) {
                        System.out.println("Client ngat ket noi.");
                    }
                }).start();
            }
        } catch (IOException e) {
            System.err.println("Loi server:");
            e.printStackTrace();
        }
    }
}
