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

    public Set<Vertex> getVertices(){
        return adjacencyList.keySet();
    }

    //straight up Dijkstra (inefficient)
    public List<Vertex> findPath(Vertex start, Vertex end){
        // 1. Initialize
        HashMap<Vertex, Double> dist = new HashMap<>();
        HashMap<Vertex, Vertex> prev = new HashMap<>();     
        Graph graph = new Graph();
       //  for (each vertex v)
       for(Vertex v : graph.getVertices()){
            dist.put(v, Double.POSITIVE_INFINITY); //dist[v] = infinity 
       }
       // dist[start] = 0
       dist.put(start, 0.0);
       PriorityQueue<Vertex> pq = new PriorityQueue<>(Comparator.comparingDouble(dist::get));
        // 2. Push start into PQ
        pq.add(start);

        // 3. While PQ not empty: IM HERE
        while(!pq.isEmpty()){
            Vertex u = pq.poll(); //with smallest dist
            //for each edge (u -> v)
            for(Edge e : graph.getNeighbors(u)){
                Vertex v = e.getTarget();
                double alt = dist.get(u) + e.getTravelTime();
                if(alt < dist.get(v)){
                    dist.put(v,alt);
                    prev.put(v, u);
                    pq.add(v); 
                }
            }
        }
        //Reconstruct Path using prev map
        List<Vertex> path = new ArrayList<>();
        Vertex current  = end;
        while(current != null){
            //add to path
            path.add(current);
            //move to previous
            current = prev.get(current);
        }
        Collections.reverse(path);
        return path;
    }

}