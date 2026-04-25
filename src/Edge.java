

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
        this.previousFlow = 0.0;
    }
    public void setFreeFlowTime(double t) {
        this.freeFlowTime = t;
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
    public double getPreviousFlow(){
        return previousFlow;
    }
    public double getCapacity() { return capacity; }
    public double getFreeFlowTime() { return freeFlowTime; }
    
    public void updateFlow(double f){
        flow += f; //cap needed in congestion
    }
    public void savePreviousFlow(){
        previousFlow = flow;//prev cap for congestion
    }
    public void resetFlow(){
        flow = 0.0;
    }
    //apply smoothing
    public void applySmoothing(double lambda){
        flow = lambda * flow + (1 - lambda) * previousFlow;
    }
    public double getTravelTime(){
        if ( capacity == 0) return Double.POSITIVE_INFINITY;
        return freeFlowTime*( 1 + 0.15*Math.pow(flow/capacity,4));
    }
    //public double getCongestion(){}
    
}