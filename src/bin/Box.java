package bin;

public enum Box {
    EMPTY,
    NUM1,
    NUM2,
    NUM3,
    NUM4,
    NUM5,
    NUM6,
    NUM7,
    NUM8,
    BOMB,
    OPENED,
    CLOSED,
    FLAGGED,
    NBOMB;

    public Object image;

    Box getNextBoxNumber(){
        return Box.values()[this.ordinal()+1];
    }
    int getNumber(){
        return this.ordinal();
    }
}
