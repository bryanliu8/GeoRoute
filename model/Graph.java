import java.util.*;

public class Graph {
    private Map<Vertex, List<Edge>> adjacencyList;

    public Graph() {
        adjacencyList = new HashMap<>();
    }

    public void addVertex(Vertex v) {
        adjacencyList.putIfAbsent(v, new ArrayList<>());
    }

    public void addEdge(Vertex source, Vertex target, double capacity) {
        Edge e = new Edge(source, target, capacity);
        adjacencyList.get(source).add(e);
    }

    public List<Edge> getNeighbors(Vertex v) {
        return adjacencyList.getOrDefault(v, new ArrayList<>());
    }

    public boolean hasVertex(Vertex v){
        return adjacencyList.containsKey(v);
    }

    //straight up Dijkstra
    /*public List<Vertex> findPath(Vertex start, Vertex end){

    }
    */
}