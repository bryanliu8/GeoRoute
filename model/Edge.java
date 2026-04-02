class Edge{
    //private int id;
    private Vertex source;
    private Vertex target;
    private double capacity;
    private double flow;
    private double travelTime;
    //private double freeFlowTime, smoothedTravelTime;
    //private double previousFlow, double;

    public Edge(Vertex source, Vertex target, double capacity) {
        this.source = source;
        this.target = target;
        this.capacity = capacity;
        this.flow = 0;
    }

    /*
    public void updateFlow(f: double){
    }

    public double getCongestion(){
    }
    */


}