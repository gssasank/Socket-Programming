/**
 * @author - GS Sasank
 * @email - gs132@snu.edu.in
 * @roll_no - 1910110152
 **/


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.visualization.VisualizationImageServer;
import org.apache.commons.collections15.Transformer;

import javax.imageio.ImageIO;
import javax.swing.*;

public class server {

    static int checkPath(int[][] adj, int u, int v, int k) {

        int counter = 0;
        if ((k == 0 && u == v) || (k == 1 && adj[u][v] == 1))
            return 1;
        if (k <= 0)
            return 0;

        for (int i = 0; i < v; i++) {
            if (adj[u][i] == 1) {
                counter += checkPath(adj, i, v, k - 1);
            }
        }

        return counter;
    }

    public static void main(String[] args) throws IOException {


        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Server Started");

            // Create an input and output stream to send data to the server
            while (true) {
                Socket socket = serverSocket.accept(); //wait for the client request (Listen)
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                int n = in.readInt();

                int[][] adj = new int[n][n];

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

                int u = s - 65;
                int v = ds - 65;
                int paths = checkPath(adj, u, v, d);

                if (paths == 0) {
                    System.out.println("No path found");
                    out.writeInt(0);
                } else if (paths > 0) {
                    System.out.println("Path found");
                    out.writeInt(paths);
                } else {
                    System.out.println("Error");
                    out.writeInt(paths);
                }

                // Using JUNG library to create a Directed Sparse Graph object, which will then be sent to the server
                DirectedSparseGraph<String, String> directedGraph = new DirectedSparseGraph<>();

                for (int i = 0; i < n; i++) {
                    directedGraph.addVertex(Character.toString((char) (i + 65)));
                }

                //add edges to the graph
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (adj[i][j] == 1) {
                            directedGraph.addEdge((char) (i + 65) + Character.toString((char) (j + 65)), Character.toString((char) (i + 65)), Character.toString((char) (j + 65)));
                        }
                    }
                }

                VisualizationImageServer<String, String> imageVisualizer = new VisualizationImageServer<>(new CircleLayout<>(directedGraph),
                        new Dimension(500, 500));

                Transformer<String, String> transformer = arg0 -> arg0;
                imageVisualizer.getRenderContext().setVertexLabelTransformer(transformer);

                JFrame frame = new JFrame("Image of Directed Graph rendered from the server");
                frame.setLocationRelativeTo(null);
                frame.getContentPane().add(imageVisualizer);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.dispose();

                BufferedImage image = new BufferedImage(imageVisualizer.getWidth(), imageVisualizer.getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics = image.createGraphics();
                imageVisualizer.print(graphics);
                graphics.dispose();

                frame.paint(image.getGraphics());

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(image, "png", baos);
                baos.flush();
                byte[] imageInByte = baos.toByteArray();
                baos.close();

                DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
                dOut.writeInt(imageInByte.length);
                dOut.write(imageInByte);
                dOut.flush();


            }


        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }
}
