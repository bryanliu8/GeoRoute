import java.util.*;
public class main {
    public static void main(String[] args) {

        Graph graph = new Graph();

        Vertex A = new Vertex(1, 0, 0, "A");
        Vertex B = new Vertex(2, 0, 0, "B");
        Vertex C = new Vertex(3, 0, 0, "C");
        Vertex D = new Vertex(4, 0, 0, "D");

        graph.addVertex(A);
        graph.addVertex(B);
        graph.addVertex(C);
        graph.addVertex(D);

        Edge AB = graph.addEdge(A, B, 3);
        Edge BC = graph.addEdge(B, C, 3);
        Edge AD = graph.addEdge(A, D, 10);
        Edge DC = graph.addEdge(D, C, 10);

        AD.setFreeFlowTime(1.3);
        DC.setFreeFlowTime(1.3);

        List<Demand> demands = new ArrayList<>();
        demands.add(new Demand(A, C, 3.0));

        FlowAllocator allocator = new FlowAllocator(graph);
        allocator.runSimulation(demands, 5);
    }
}