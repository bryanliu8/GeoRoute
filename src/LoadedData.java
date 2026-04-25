import java.util.List;

public class LoadedData {
    public Graph graph;
    public List<Demand> demands;

    public LoadedData(Graph g, List<Demand> d) {
        this.graph = g;
        this.demands = d;
    }
}