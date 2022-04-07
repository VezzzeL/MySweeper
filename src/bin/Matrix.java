package bin;

class Matrix {
    private Box[][]matrix;

    Matrix(Box defaultBox) {
        matrix=new Box[Ranges.getSize().x][Ranges.getSize().y];
        for(Coordinate coordinate : Ranges.getCoordinates()){
            matrix[coordinate.x][coordinate.y] = defaultBox;
        }
    }
    Box get(Coordinate coordinate){
        if (Ranges.inRange(coordinate)){
            return matrix[coordinate.x][coordinate.y];

        }else{
            return null;
        }
    }

    void set(Coordinate coordinate, Box box){
        matrix[coordinate.x][coordinate.y] = box;
    }

}
