
// =============================
// SensorServer.java
// =============================
package realtime;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SensorServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(6666)) {
            System.out.println("Server is running...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                new SensorClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
        }
    }
}

class SensorClientHandler extends Thread {
    private final Socket socket;

    public SensorClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (DataInputStream input = new DataInputStream(socket.getInputStream())) {
            while (true) {
                String data = input.readUTF();
                System.out.println("Received from " + socket.getInetAddress() + ": " + data);
            }
        } catch (IOException e) {
            System.out.println("Client disconnected: " + socket.getInetAddress());
        }
    }
}