import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class MainFrame extends JFrame {

    private Graph graph;
    private List<Demand> demands;
    private GraphPanel panel;
    private JLabel infoLabel;
    private JTextField demandField;

    public MainFrame(Graph graph, List<Demand> demands) {

        this.graph = graph;
        this.demands = demands;

        setTitle("Dynamic Traffic Routing & Congestion Simulator");
        setSize(1200, 950);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        panel = new GraphPanel(graph);
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        // ===== Controls Bar =====
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 8));
        controls.setBackground(new Color(245,245,245));
        controls.setBorder(new EmptyBorder(8,8,8,8));

        JLabel demandLabel = new JLabel("Demand:");
        demandLabel.setFont(new Font("Arial", Font.BOLD, 14));

        demandField = new JTextField("1000", 8);

        JButton runButton = new JButton("Run");
        JButton clearButton = new JButton("Clear");
        JButton loadButton = new JButton("Load");

        runButton.setFocusPainted(false);
        clearButton.setFocusPainted(false);
        loadButton.setFocusPainted(false);

        runButton.addActionListener(e -> runSimulation());

        clearButton.addActionListener(e -> panel.clearSelection());

        loadButton.addActionListener(e -> loadGraph());

        controls.add(demandLabel);
        controls.add(demandField);
        controls.add(runButton);
        controls.add(clearButton);
        controls.add(loadButton);

        //Status Bar 
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(new Color(250,250,250));
        bottom.setBorder(new EmptyBorder(6,10,6,10));

        infoLabel = new JLabel("Select source and target vertices.");
        infoLabel.setFont(new Font("Arial", Font.BOLD, 14));

        bottom.add(infoLabel, BorderLayout.WEST);

        panel.setInfoLabel(infoLabel);
        panel.setDemandField(demandField);

        add(controls, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void runSimulation() {
        FlowAllocator allocator = new FlowAllocator(graph);
        allocator.runSimulation(demands, 3);
        panel.repaint();
    }

    private void loadGraph() {
        LoadedData data = GraphIO.load("test.txt");
        this.graph = data.graph;
        this.demands = data.demands;

        panel.setGraph(graph);
        panel.repaint();

        infoLabel.setText("Graph loaded successfully.");
    }
}