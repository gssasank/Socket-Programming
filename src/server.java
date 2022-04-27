/** @author - GS Sasank
 *  @email - gs132@snu.edu.in
 *  @roll_no - 1910110152
**/


import java.io.*;
import java.net.*;
import edu.uci.ics.jung.graph.DirectedSparseGraph;

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
                DataInputStream in = new DataInputStream(socket.getInputStream()); //create I/O streams for communicating to the client (Connect)
                DataOutputStream out = new DataOutputStream(socket.getOutputStream()); //create I/O streams for communicating to the client (Connect)

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

                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

                oos.writeObject(directedGraph);

            }



        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }
}
