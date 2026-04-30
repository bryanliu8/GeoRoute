import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;


public class GraphPanel extends JPanel {

    private JLabel infoLabel;
    private Graph graph;
    private Vertex selectedSource = null;
    private Vertex selectedTarget = null;
    private List<Vertex> selectedPath = new ArrayList<>();
    private JTextField demandField;

    public GraphPanel(Graph graph) {
        this.graph = graph;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                handleClick(e.getX(), e.getY());
            }
        });
        
    }
    public void setInfoLabel( JLabel label){
        this.infoLabel = label;
    }
    
    public void setDemandField(JTextField field) {
        this.demandField = field;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }
    //override here for protected
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (graph == null) return;
        int scale = 9;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );
        setBackground(Color.WHITE);

        for (Vertex v : graph.getVertices()) {
            for (Edge e : graph.getNeighbors(v)) {
                int x1 = v.getX() * scale;
                int y1 = v.getY() * scale;
                int x2 = e.getTarget().getX() * scale;
                int y2 = e.getTarget().getY() * scale;

                double flow = e.getFlow();
                int thickness = (int) Math.min(7, 1 + flow / 5);
                //low flow = gray, high flow = red
                int red = (int)Math.min(255, 80 + flow * 10);
                int green = (int)Math.max(60, 180 - flow * 8);

                g2.setColor(new Color(red, green, green));
                g2.setStroke(new BasicStroke(thickness));
                g2.drawLine(x1, y1, x2, y2);
            }
        }

        g2.setStroke(new BasicStroke(1));
        g2.setColor(Color.DARK_GRAY);

        for (Vertex v : graph.getVertices()) {
            int x = v.getX() * scale;
            int y = v.getY() * scale;
            g2.fillOval(x - 1, y - 1, 4, 4);
        }

        if (selectedPath != null && selectedPath.size() > 1) {
            g2.setColor(new Color(30, 100, 255));
            g2.setStroke(new BasicStroke(4));

            for (int i = 0; i < selectedPath.size() - 1; i++) {
                Vertex a = selectedPath.get(i);
                Vertex b = selectedPath.get(i + 1);

                int x1 = a.getX() * scale;
                int y1 = a.getY() * scale;
                int x2 = b.getX() * scale;
                int y2 = b.getY() * scale;

                g2.drawLine(x1, y1, x2, y2);
            }
        }

        if (selectedSource != null) {
            g2.setColor(new Color(0, 180, 0));
            int x = selectedSource.getX() * scale;
            int y = selectedSource.getY() * scale;
            g2.fillOval(x - 5, y - 5, 10, 10);
        }

        if (selectedTarget != null) {
            g2.setColor(new Color(220, 0, 0));
            int x = selectedTarget.getX() * scale;
            int y = selectedTarget.getY() * scale;
            g2.fillOval(x - 5, y - 5, 10, 10);
        }
    }

    private Vertex findNearestVertex(int x, int y){
        Vertex closest = null;
        double minDist = Double.MAX_VALUE;
        for(Vertex v: graph.getVertices()){
            int vx = v.getX() *5;
            int vy = v.getY() *5;
            double dist = Math.hypot(vx - x, vy-y);
            if(dist<minDist){
                minDist = dist;
                closest = v;
            }
        }
        return closest;
    }

    private void handleClick(int x, int y) {
        Vertex clicked = findNearestVertex(x, y);
        if (clicked == null) return;
        if (selectedSource == null) {
            selectedSource = clicked;
            infoLabel.setText("Source: " + selectedSource.getLabel());
            System.out.println("Source selected: " + clicked.getLabel());
        } else if (selectedTarget == null) {
            selectedTarget = clicked;
            selectedPath = graph.findPath(selectedSource, selectedTarget);

            double demandAmount = 1000.0;
            try {
                demandAmount = Double.parseDouble(demandField.getText());
            } catch (Exception ex) {
                demandAmount = 1000.0;
            }

            List<Demand> demands = new ArrayList<>();
            demands.add(new Demand(selectedSource, selectedTarget, demandAmount));
            FlowAllocator allocator = new FlowAllocator(graph);
            long start = System.nanoTime();
            allocator.runSimulation(demands, 3);

            long end = System.nanoTime();
            double ms = (end - start) / 1_000_000.0;
            exportResults(demandAmount, ms);
            infoLabel.setText(
                "Source: " + selectedSource.getLabel()
                + " Target: " + selectedTarget.getLabel()
                + " Demand: " + demandAmount
                + " Runtime: " + ms + " ms"
            );
        } else {
            selectedSource = clicked;
            selectedTarget = null;
            selectedPath.clear();
            infoLabel.setText("Source: "+ selectedSource.getLabel());
            System.out.println("Reset. New source: " + clicked.getLabel());
        }
        repaint();
    }

    private void exportResults(double demandAmount, double runtimeMs) {
        try {
            PrintWriter out = new PrintWriter("results.txt");
            out.println("Traffic Simulation Results");
            out.println("Source: " + selectedSource.getLabel());
            out.println("Target: " + selectedTarget.getLabel());
            out.println("Demand: " + demandAmount);
            out.println("Runtime(ms): " + runtimeMs);
            out.println();

            for (Vertex v : graph.getVertices()) {
                for (Edge e : graph.getNeighbors(v)) {
                    out.println(
                        e.getSource().getLabel() + " -> " +
                        e.getTarget().getLabel() +
                        " Flow=" + e.getFlow() +
                        " Time=" + e.getTravelTime()
                    );
                }
            }
            out.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void clearSelection(){
        selectedSource = null;
        selectedTarget = null;
        selectedPath.clear();
        repaint();
    }
}