import java.util.List;

public class FlowAllocator{
    private Graph graph;

    public FlowAllocator(Graph graph) {
        this.graph = graph;
    }

    private void assignDemand(Demand d) {
        Vertex source = d.getSource();
        Vertex target = d.getTarget();
        double amount = d.getAmount();

        // 1. Find shortest path (uses Dijkstra)
        List<Vertex> path = graph.findPath(source, target);

        if (path.isEmpty()){
            System.out.println("No path from " + source + " to " + target); 
            return;
        } 

        // 2. Push flow along path
        for (int i = 0; i < path.size() - 1; i++) {
            Vertex u = path.get(i);
            Vertex v = path.get(i + 1);

            Edge e = graph.getEdge(u, v); 
            if(e != null){
                e.updateFlow(amount);
            }
        }
    }

    //O(V+E) for each vertex and edge visited once
    //design issue: reset every iteration. Static for now. 
    //Later flow acummulation, gradual convergence, time-based simulation
    private void resetFlows() {
    for (Vertex v : graph.getVertices()) {
        for (Edge e : graph.getNeighbors(v)) {
            e.resetFlow();
        }
    }
    }

    //debug method
    private void printStats(int iteration){
        System.out.println("Iteration " + iteration);
        for(Vertex v : graph.getVertices()){
            for(Edge e : graph.getNeighbors(v)){
                System.out.println(e.getSource() + " -> " + e.getTarget() + 
                " | flow: " + e.getFlow() + " | time: " + e.getTravelTime());
            }
        }
    }

    // Main simulation entry
    public void runSimulation(List<Demand> demands, int iterations) {
        for (int t = 0; t < iterations; t++) {
            // 1. Reset or prepare state (optional for now)
            //resetFlows();
            // 2. Assign demand based on demands
            for (Demand d : demands) {
                assignDemand(d);
            }
            // 3. (Optional) Update edge metrics  smoothing
            //updateEdgeStates();

            // 4. Debug / observe system
            printStats(t);
        }
    }
}