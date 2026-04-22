

class Vertex{
    private int id;
    private int x;
    private int y;
    private String label;

    public Vertex(int id, int x, int y, String label){
        this.id = id;
        this.x = x;
        this.y = y;
        this.label = label;
    }
    public int getId(){
        return id;
    }
    @Override //readable printStats
    public String toString(){
        return label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vertex)) return false;
        Vertex v = (Vertex) o;
        return id == v.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
    /* 
    public Vertex getCoordinate(){

    }

    public boolean isHotspot(){
        return false;
    }
    */
    
}