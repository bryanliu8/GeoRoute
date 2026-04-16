

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

    public Edge getEdge(Vertex source, Vertex target){
        for(Edge e : getNeighbors(source)){
            if(e.getTarget().equals(target)){
                return e;
            }
        }
        return null;
    }

    //straight up Dijkstra (inefficient) Leave alone for now
    public List<Vertex> findPath(Vertex start, Vertex end){
        // 1. Initialize
        HashMap<Vertex, Double> dist = new HashMap<>();
        HashMap<Vertex, Vertex> prev = new HashMap<>();
       //  for (each vertex v)
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


    //debug method
    private void printStats(int iteration){
        System.out.println("Iteration " + iteration);
        for(Vertex v : getVertices()){
            for(Edge e : getNeighbors(v)){
                System.out.println(e.getSource() + " -> " + e.getTarget() + 
                " | flow: " + e.getFlow() + " | time: " + e.getTravelTime());
            }
        }
    }
}