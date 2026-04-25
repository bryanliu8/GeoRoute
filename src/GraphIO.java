import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphIO{
    public static void save(Graph graph, List<Demand> demands, String filename) {
    try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
        // Vertices
        for (Vertex v : graph.getVertices()) {
            writer.println("VERTEX," + v.getId() + "," + v.getX() + "," + v.getY() + "," + v.getLabel());
        }

        // Edges
        for (Vertex v : graph.getVertices()) {
            for (Edge e : graph.getNeighbors(v)) {
                writer.println("EDGE," +
                    e.getSource().getId() + "," +
                    e.getTarget().getId() + "," +
                    e.getCapacity() + "," +
                    e.getFreeFlowTime());
            }
        }

        // Demands
        for (Demand d : demands) {
            writer.println("DEMAND," +
                d.getSource().getId() + "," +
                d.getTarget().getId() + "," +
                d.getAmount());
        }

        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    public static LoadedData load(String filename) {
        Graph graph = new Graph();
        List<Demand> demands = new ArrayList<>();
        Map<Integer, Vertex> vertexMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                switch (parts[0]) {
                    case "VERTEX":
                        int id = Integer.parseInt(parts[1]);
                        int x = Integer.parseInt(parts[2]);
                        int y = Integer.parseInt(parts[3]);
                        String label = parts[4];

                        Vertex v = new Vertex(id, x, y, label);
                        graph.addVertex(v);
                        vertexMap.put(id, v);
                        break;

                    case "EDGE":
                        Vertex src = vertexMap.get(Integer.parseInt(parts[1]));
                        Vertex tgt = vertexMap.get(Integer.parseInt(parts[2]));
                        double cap = Double.parseDouble(parts[3]);
                        double fft = Double.parseDouble(parts[4]);

                        Edge e = graph.addEdge(src, tgt, cap);
                        e.setFreeFlowTime(fft);
                        break;

                    case "DEMAND":
                        Vertex s = vertexMap.get(Integer.parseInt(parts[1]));
                        Vertex t = vertexMap.get(Integer.parseInt(parts[2]));
                        double amt = Double.parseDouble(parts[3]);

                        demands.add(new Demand(s, t, amt));
                        break;
                }
            }
            }catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
            }

        return new LoadedData(graph, demands);
    }
}