import java.util.*;
public class main {
    public static void main(String[] args) {
        Graph graph = Graph.generateGridGraph(50, 50); // start smaller for GUI
        List<Vertex> vertices = new ArrayList<>(graph.getVertices());

        Vertex start = vertices.get(0);
        Vertex end = vertices.get(vertices.size() - 1);

        List<Demand> demands = new ArrayList<>();
        demands.add(new Demand(start, end, 50.0));

        MainFrame main = new MainFrame(graph, demands);



    }
}