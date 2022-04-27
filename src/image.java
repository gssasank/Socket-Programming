import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.visualization.VisualizationImageServer;
import org.apache.commons.collections15.Transformer;

import javax.swing.*;
import java.awt.*;

public class image {
    public static void main(String[] args) {
        DirectedSparseGraph<String, String> g = new DirectedSparseGraph<>();
        for (int i = 0; i < 4; i++) {
            g.addVertex(Character.toString((char) (i + 65)));
        }


        g.addEdge("e1", "0", "1");
        g.addEdge("e2", "0", "2");
        g.addEdge("e3", "2", "1");
        g.addEdge("e4", "3", "1");


        VisualizationImageServer<String, String> vv = new VisualizationImageServer<>(new CircleLayout<>(g),
                new Dimension(600, 400));

        Transformer<String, String> transformer = new Transformer<String, String>() {
            @Override
            public String transform(String arg0) {
                return arg0;
            }
        };
        vv.getRenderContext().setVertexLabelTransformer(transformer);

        JFrame frame = new JFrame("My Graph");
        frame.setLocationRelativeTo(null);
        frame.getContentPane().add(vv);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
