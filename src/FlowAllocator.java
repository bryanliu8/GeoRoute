import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlowAllocator{
    private Graph graph;

    public FlowAllocator(Graph graph) {
        this.graph = graph;
    }

    public double computePathCost(List<Vertex> path ){
        double cost = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            Vertex u = path.get(i);
            Vertex v = path.get(i + 1);

            Edge e = graph.getEdge(u, v); //get edge
            if (e != null) {
                cost += e.getTravelTime();
            }
        }
        return cost;
    }
    public void pushFlow(List<Vertex> path, double amount){
        for (int i = 0; i < path.size() - 1; i++) {
            Vertex u = path.get(i);
            Vertex v = path.get(i + 1);

            Edge e = graph.getEdge(u, v);
            if (e != null) {
                e.updateFlow(amount);
            }
        }
    }
    //Logit based flow assignment Lite-ver (yet to use logit formula)
    private void assignFlow(Demand d) {
        Vertex source = d.getSource();
        Vertex target = d.getTarget();
        List<Vertex> path1 = graph.findPath(source, target);

        //build alternative path
        Vertex alt = null;
        for (Edge e : graph.getNeighbors(source)) {
            Vertex candidate = e.getTarget();
            if (!candidate.equals(path1.get(1))) {
                alt = candidate;
                break;
            }
        }
        List<Vertex> path2 = Arrays.asList(source, alt, target);
        List<List<Vertex>> paths = new ArrayList<>();
        paths.add(path1);
        paths.add(path2);

        double alpha = 1.0;
        List<Double> weights = new ArrayList<>();
        double totalWeight = 0;
        for (List<Vertex> path : paths) {
            double cost = computePathCost(path);
            double w = Math.exp(-alpha * cost);
            weights.add(w);
            totalWeight += w;
        }

        for (int i = 0; i < paths.size(); i++) {
            double prob = weights.get(i) / totalWeight;
            double portion = d.getAmount() * prob;
            pushFlow(paths.get(i), portion);
        }
    }

    //O(V+E) for each vertex and edge visited once
    //design issue: reset every iteration. Static for now. 
    //Later flow acummulation, gradual convergence, time-based simulation
    /*private void resetFlows() {
    for (Vertex v : graph.getVertices()) {
        for (Edge e : graph.getNeighbors(v)) {
            e.resetFlow();
        }
    }
    }*/

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
            //Assign demand based on demands
            for (Demand d : demands) {
                assignFlow(d);
            }
            // 3.Optional Update edge metrics  smoothing
            //updateEdgeStates();

            printStats(t);
        }
    }
}