// @author - GS Sasank
// @roll_no - 1910110152
// @email - gs132@snu.edu.in

import java.io.*;
import java.net.*;


public class server {
    public static void main(String[] args) throws IOException {


        try {
            ServerSocket serverSocket = new ServerSocket (1234);
            System.out.println ("Server Started");

            // Create an input and output stream to send data to the server
            while (true) {
                Socket socket = serverSocket.accept(); //wait for the client request (Listen)
                DataInputStream in = new DataInputStream(socket.getInputStream()); //create I/O streams for communicating to the client (Connect)
                DataOutputStream out = new DataOutputStream(socket.getOutputStream()); //create I/O streams for communicating to the client (Connect)

                int n = in.readInt();

                int [][] adj = new int[n][n];
                // Read the message from the client
                // Read adjacency matrix from the client
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        adj[i][j] = in.readInt();
                    }
                }

                int d = in.readInt();
                char s = in.readChar();
                char ds = in.readChar();

                // Print the adjacency matrix
                System.out.println("Adjacency Matrix");

                for (int[] ints : adj) {
                    for (int anInt : ints) {
                        System.out.print(anInt + " ");
                    }
                    System.out.println();
                }

                System.out.println("Distance: " + d);


                // convert Character to capital
                s = Character.toUpperCase(s);
                ds = Character.toUpperCase(ds);

                System.out.println("Source: " + s);
                System.out.println("Destination: " + ds);




            }



        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }
}
