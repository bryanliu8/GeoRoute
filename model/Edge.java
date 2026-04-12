class Edge{
    //private int id;
    private Vertex source;
    private Vertex target;
    private double capacity;
    private double flow;
    private double travelTime;
    //private double freeFlowTime, smoothedTravelTime;
    //private double previousFlow;

    public Edge(Vertex source, Vertex target, double capacity) {
        this.source = source;
        this.target = target;
        this.capacity = capacity;
        this.flow = 0;
    }
    public Vertex getTarget(Vertex source){
        Vertex v = target; 
        return v;
    }
    
    public void updateFlow(){
    }

    public double getTravelTime(){
        //flow capacity congestion

    }
    
    //public double getCongestion(){}
}