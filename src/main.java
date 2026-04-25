import java.util.*;
public class main {
    public static void main(String[] args) {
        Graph graph = Graph.generateGridGraph(100, 100);

        // Convert to list so indexing works
        List<Vertex> vertices = new ArrayList<>(graph.getVertices());

        Vertex start = vertices.get(0);
        Vertex end = vertices.get(vertices.size() - 1);

        List<Demand> demands = new ArrayList<>();
        demands.add(new Demand(start, end, 50.0));

        // Save + Load test
        GraphIO.save(graph, demands, "test.txt");
        LoadedData data = GraphIO.load("test.txt");

        // Use loaded graph
        Graph loadedGraph = data.graph;

        List<Vertex> loadedVertices = new ArrayList<>(loadedGraph.getVertices());
        Vertex loadedStart = loadedVertices.get(0);
        Vertex loadedEnd = loadedVertices.get(loadedVertices.size() - 1);

        // Test Dijkstra
        List<Vertex> path = loadedGraph.findPath(loadedStart, loadedEnd);

        //FlowAllocator allocator = new FlowAllocator(data.graph);
        //allocator.runSimulation(data.demands, 5);
        long startTime = System.nanoTime();
        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        System.out.println("Vertices: " + loadedGraph.getVertices().size());
        System.out.println("Path length: " + path.size());
        System.out.println("Time (ns): " + duration);
        System.out.println("Time (ms): " + (duration / 1_000_000.0));
        /*int runs = 10;
        long total = 0;

        for (int i = 0; i < runs; i++) {
            long t1 = System.nanoTime();
            loadedGraph.findPath(loadedStart, loadedEnd);
            long t2 = System.nanoTime();
            total += (t2 - t1);
        }
        
        System.out.println("Average time (ms): " + (total / runs) / 1_000_000.0);
        */
    }
}