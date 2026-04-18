

class Edge{
    //private int id;
    private Vertex source;
    private Vertex target;
    private double capacity;
    private double flow;
    private double travelTime;
    private double freeFlowTime;
    private double smoothedTravelTime;
    private double previousFlow;

    public Edge(Vertex source, Vertex target, double capacity) {
        this.source = source;
        this.target = target;
        this.capacity = capacity;
        this.flow = 0;
        this.freeFlowTime = 1.0;
    }
    public Vertex getTarget(){
        return target;
    }
    public Vertex getSource(){
        return source;
    }
    public double getFlow(){
        return flow;
    }
    
    public double updateFlow(double f){
        previousFlow = flow; //keeping prev cap for congestion
        flow += f; //cap needed in congestion
        return flow;
    }

    public double getTravelTime(){
        if ( capacity == 0) return Double.POSITIVE_INFINITY;
        return freeFlowTime*( 1 + 0.15*Math.pow(flow/capacity,4));
    }
    
    public void resetFlow(){
        flow = 0.0;
    }
    //public double getCongestion(){}
    
}