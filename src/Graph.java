

import java.util.*;

public class Graph {
    private Map<Vertex, List<Edge>> adjacencyList;

    public Graph() {
        adjacencyList = new HashMap<>();
    }

    public void addVertex(Vertex v) {
        adjacencyList.putIfAbsent(v, new ArrayList<>());
    }

    public Edge addEdge(Vertex u, Vertex v, double capacity) {
        Edge e = new Edge(u, v, capacity);
        adjacencyList.putIfAbsent(u, new ArrayList<>());
        adjacencyList.get(u).add(e);
        return e;
        //adjacencyList.get(u).add(e);
    }

    public List<Edge> getNeighbors(Vertex v) {
        return adjacencyList.getOrDefault(v, new ArrayList<>());
    }

    public boolean hasVertex(Vertex v){
        return adjacencyList.containsKey(v);
    }

    public Set<Vertex> getVertices(){
        return adjacencyList.keySet();
    }

    public Edge getEdge(Vertex source, Vertex target){
        for(Edge e : getNeighbors(source)){
            if(e.getTarget().equals(target)){
                return e;
            }
        }
        return null;
    }
    //Generate 100 x 100 grid
    public static Graph generateGridGraph(int rows, int cols) {
        Graph graph = new Graph();

        Vertex[][] grid = new Vertex[rows][cols];
        int id = 1;
        //vertices
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Vertex v = new Vertex(id++, i, j, "(" + i + "," + j + ")");
                grid[i][j] = v;
                graph.addVertex(v);
            }
        }
        //Edges
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                //to the right
                if (j + 1 < cols) {
                    graph.addEdge(grid[i][j], grid[i][j + 1], 10);
                }
                //to the down neighbor
                if (i + 1 < rows) {
                    graph.addEdge(grid[i][j], grid[i + 1][j], 10);
                }
            }
        }
        return graph;
    }


    //straight up Dijkstra Goal: 2-3 paths
    public List<Vertex> findPath(Vertex start, Vertex end){
        //Initialize
        HashMap<Vertex, Double> dist = new HashMap<>();
        HashMap<Vertex, Vertex> prev = new HashMap<>();
       //for (each vertex v)
       for(Vertex v : getVertices()){
            dist.put(v, Double.POSITIVE_INFINITY); //dist[v] = infinity 
       }
       dist.put(start, 0.0);
       PriorityQueue<Vertex> pq = new PriorityQueue<>(Comparator.comparingDouble(dist::get));
        pq.add(start);

        // 3. While PQ not empty: 
        while(!pq.isEmpty()){
            Vertex u = pq.poll(); 
            if(u.equals(end)) break;
            for(Edge e : getNeighbors(u)){
                Vertex v = e.getTarget();
                double alt = dist.get(u) + e.getTravelTime();
                if(alt < dist.get(v)){
                    dist.put(v,alt);
                    prev.put(v, u);
                    pq.add(v); //acceptable
                }
            }
        }
        if(!prev.containsKey(end) && !end.equals(start)){
            return new ArrayList<>();
        }
        //Reconstruct Path using prev map
        List<Vertex> path = new ArrayList<>();
        Vertex current  = end;
        while(current != null){
            path.add(current);//add to path
            current = prev.get(current);
        }
        Collections.reverse(path);
        return path;
    }
    
}