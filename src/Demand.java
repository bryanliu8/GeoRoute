

public class Demand{
    private Vertex source;
    private Vertex target;
    private double amount;

    //constructor
    public Demand(Vertex source, Vertex target ,double amount){
       this.source = source;
       this.target = target;
       this.amount = amount;
    }

    //getters
    public double getAmount(){
        return amount;
    }
    public Vertex getSource(){
        return source;
    }
    public Vertex getTarget(){
        return target;
    }
}