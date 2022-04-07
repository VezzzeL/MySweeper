package bin;

class Flag {
    private Matrix flagMatrix;
    private int totalFlags, countOfClosedBoxes;

     void start(){
        flagMatrix=new Matrix(Box.CLOSED);
        countOfClosedBoxes=Ranges.getSize().x * Ranges.getSize().y;
    }
    Box get(Coordinate coordinate){
         return flagMatrix.get(coordinate);
    }
    void toggleFlagToBox(Coordinate coordinate){
        switch (flagMatrix.get(coordinate)){
            case FLAGGED -> setClosedToBox(coordinate);
            case CLOSED -> setFlaggedToBox(coordinate);
        }
    }
    void setOpenedToBox(Coordinate coordinate) {
         flagMatrix.set(coordinate,Box.OPENED);
         countOfClosedBoxes--;
    }

    private void setFlaggedToBox(Coordinate coordinate) {
        flagMatrix.set(coordinate,Box.FLAGGED);
    }

    void setClosedToBox(Coordinate coordinate) {
        flagMatrix.set(coordinate,Box.CLOSED);
    }

    int getCountOfClosedBoxes() {
        return countOfClosedBoxes;
    }
    public void setBombedToBox(Coordinate coordinate){
         flagMatrix.set(coordinate, Box.BOMB);
    }

    void setNoBombToFlaggedSafeBox(Coordinate coordinate) {
        if(flagMatrix.get(coordinate) == Box.FLAGGED){
            flagMatrix.set(coordinate,Box.NBOMB);
        }
    }

    void setOpenedToCloseBombBox(Coordinate coordinate) {
         if(flagMatrix.get(coordinate) == Box.CLOSED){
            flagMatrix.set(coordinate,Box.OPENED);
        }
    }

    int getCountOfFlaggedBoxesAround(Coordinate coordinate){
        int count=0;
        for(Coordinate around : Ranges.getCoordinateAround(coordinate)){
            if(flagMatrix.get(around) == Box.FLAGGED){
                count++;
            }
        }
        return count;
    }
}
