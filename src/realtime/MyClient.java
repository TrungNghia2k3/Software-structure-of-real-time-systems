package realtime;

import java.io.DataOutputStream;
import java.net.Socket;

public class MyClient {

    // Main driver method
    public static void main(String[] args) {

        // Try block to check if exception occurs
        try {

            // Creating Socket class object and
            // initializing Socket
            Socket s = new Socket("localhost", 6666);

            DataOutputStream d = new DataOutputStream(
                s.getOutputStream());

            // Message to be displayed
            d.writeUTF("Hello GFG Readers!");

            // Flushing out internal buffers,
            // optimizing for better performance
            d.flush();

            // Closing the connections

            // Closing DataOutputStream
            d.close();
            // Closing socket
            s.close();
        }

        // Catch block to handle exceptions
        catch (Exception e) {

            // Print the exception on the console
            System.out.println(e);
        }
    }
}