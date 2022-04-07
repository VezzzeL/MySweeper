package bin;

public class Coordinate {
    public int x,y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Coordinate){
            Coordinate to=(Coordinate) o;
            return to.x==x && to.y==y;
        }
        return super.equals(o);
    }
}
