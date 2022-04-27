// @author - GS Sasank
// @roll_no - 1910110152
// @email - gs132@snu.edu.in

import java.util.Scanner;

public class client {
    public static void main(String[] args) {

        try { // Take input for adjacency matrix
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
        System.out.println("Destination: " + ds);}
        catch (Exception e) {   //catch the exception
            System.out.println("Invalid input, re-run the program");
        }
    }
}
