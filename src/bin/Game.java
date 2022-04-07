package bin;

public class Game {
    private Bomb bomb;
    private Flag flag;
    private GameState state;
    public int countBombs;

    public Game(int cols,int rows,int bombs) {
        countBombs=bombs;
        Ranges.setSize(new Coordinate(cols,rows));
        bomb=new Bomb(countBombs);
        flag=new Flag();
    }

    public void start(){
        bomb.renewBombs(countBombs);
        bomb.start();
        flag.start();
        state=GameState.PLAYING;
    }

    public Box getBox(Coordinate coordinate){
        if(flag.get(coordinate)==Box.OPENED){
            return bomb.get(coordinate);
        }else{
            return flag.get(coordinate);
        }
    }

    public GameState getState() {
        return state;
    }

    public void pressLeftButton(Coordinate coordinate) {
        if(gameOver()) return;
        openBox(coordinate);
        checkIfWin();
    }

    public void pressRightButton(Coordinate coordinate) {
        if(gameOver()) return;
        flag.toggleFlagToBox(coordinate);
    }
    private void openBox(Coordinate coordinate){
        switch (flag.get(coordinate)){
            case OPENED -> setOpenedToClosedBoxesAroundNumber(coordinate);
            case CLOSED -> {
                switch (bomb.get(coordinate)){
                    case EMPTY -> openBoxAround(coordinate);
                    case BOMB -> openBombs(coordinate);
                    default -> flag.setOpenedToBox(coordinate);
                }
            }
        }
    }
    private void openBoxAround(Coordinate coordinate){
        flag.setOpenedToBox(coordinate);
        for(Coordinate around : Ranges.getCoordinateAround(coordinate)){
            openBox(around);
        }
    }
    private void checkIfWin(){
        if(state==GameState.PLAYING){
            if(flag.getCountOfClosedBoxes() == bomb.getTotalBombs()){
                state=GameState.WIN;
            }
        }
    }
    private void openBombs(Coordinate bombed){
        state = GameState.LOST;
        flag.setBombedToBox(bombed);
        for(Coordinate coordinate : Ranges.getCoordinates()){
            if (bomb.get(coordinate)==Box.BOMB){
                flag.setOpenedToCloseBombBox(coordinate);
            }else{
                flag.setNoBombToFlaggedSafeBox(coordinate);
            }
        }
    }
    private boolean gameOver(){
        if(state == getState().PLAYING){
            return false;
        }else {
            return true;
        }
    }
    void setOpenedToClosedBoxesAroundNumber(Coordinate coordinate) {
        if (bomb.get(coordinate) != Box.BOMB) {
            if (flag.getCountOfFlaggedBoxesAround(coordinate) == bomb.get(coordinate).getNumber()){
                for(Coordinate around : Ranges.getCoordinateAround(coordinate)){
                    if(flag.get(around) == Box.CLOSED){
                        openBox(around);
                    }
                }
            }
        }
    }
}

