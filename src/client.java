/**
 * @author - GS Sasank
 * @email - gs132@snu.edu.in
 * @roll_no - 1910110152
 **/

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Scanner;
import java.io.*;
import java.net.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class client {
    public static void main(String[] args) throws IOException {

        // Take input for adjacency matrix
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of vertices:");
        int n = sc.nextInt();
        int[][] adj = new int[n][n];
        System.out.println("Enter the adjacency matrix:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                adj[i][j] = sc.nextInt();
            }
        }

        // Take input for the distance to be verified
        System.out.println("Enter the distance to be verified:");
        int d = sc.nextInt();

        // Take input for source and destination
        System.out.println("Enter the source: (Enter the letter of the source)");
        char s = sc.next().charAt(0);
        System.out.println("Enter the destination: (Enter the letter of the destination)");
        char ds = sc.next().charAt(0);

        //print the adjacency matrix
        System.out.println("Adjacency matrix entered by the user:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(adj[i][j] + " ");

            }
            System.out.println();
        }

        //print the distance to be verified
        System.out.println("\nLength of path: " + d);
        //print the source and destination
        System.out.println("\nSource: " + s);
        System.out.println("Destination: " + ds);


        try {
            // Create a socket to connect to the server
            Socket socket = new Socket("127.0.0.1", 1234);
            System.out.println("\nOpened a socket to the server\n");
            // Create an input and output stream to send data to the server
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            out.writeInt(n);
            // Send the adjacency matrix to the server
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    out.writeInt(adj[i][j]);
                }
            }

            out.writeInt(d);
            out.writeChar(s);
            out.writeChar(ds);
            out.flush();
            System.out.println("Sent the data to the server");

            // Receive the result from the server
            int result = in.readInt();

            if (result == 0) {
                System.out.println("No path exists between the given source and destination of given length");
            } else if (result > 0) {
                System.out.println("Yes! Path exists between the given source and destination of given length.");
                System.out.println("No. of paths of given length: " + result);
            } else {
                System.out.println("Error");
            }

            DataInputStream dIn = new DataInputStream(socket.getInputStream());

            int length = dIn.readInt();
            byte[] message = new byte[length];// read length of incoming message
            if (length > 0) {
                dIn.readFully(message, 0, message.length);
            }

            // convert byte array to image
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(message));
            // save image to file
            ImageIO.write(image, "png", new File("image.png"));
            // display image
            JFrame frame = new JFrame("Image of Directed Graph");
            frame.setBackground(Color.WHITE);
            frame.setLocationRelativeTo(null);
            frame.getContentPane().add(new JLabel(new ImageIcon(image)));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
