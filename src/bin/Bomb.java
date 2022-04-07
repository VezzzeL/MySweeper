package bin;

public class Bomb {
    private Matrix bombMatrix;
    private int totalBombs;

    public Bomb(int totalBombs) {
        this.totalBombs = totalBombs;
        fixBombsCount();
    }

    void start(){
        bombMatrix=new Matrix(Box.EMPTY);
        for(int i=0;i<totalBombs;i++){
            placeBomb();
        }
    }

    Box get(Coordinate coordinate){
        return bombMatrix.get(coordinate);
    }

    private void fixBombsCount(){
        int maxBombs=(Ranges.getSize().x*Ranges.getSize().y)/2;
        if(totalBombs>maxBombs){
            totalBombs=maxBombs;
        }
    }

    private void placeBomb(){
        while (true) {
            Coordinate coordinate = Ranges.getRandomCoordinate();
            if (Box.BOMB == bombMatrix.get(coordinate)) {
                continue;
            }
            bombMatrix.set(coordinate, Box.BOMB);
            incNumberAroundBomb(coordinate);
            break;
        }
    }
    private void incNumberAroundBomb(Coordinate coordinate){
        for(Coordinate around:Ranges.getCoordinateAround(coordinate)) {
            if(Box.BOMB != bombMatrix.get(around)){
                bombMatrix.set(around, bombMatrix.get(around).getNextBoxNumber());
            }
        }
    }

    public int getTotalBombs() {
        return totalBombs;
    }
    public void renewBombs(int countOfBombs){
        if(totalBombs!=countOfBombs){
            totalBombs=countOfBombs;
        }
    }
}
