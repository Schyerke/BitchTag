package bitchtag.bitchtag.game;

public enum GameMinuteLastHelper {
    INSTANCE;

    private int minuteLast = 5;

    public int getMinuteLast() {
        return minuteLast;
    }

    public void setMinuteLast(int minuteLast) {
        this.minuteLast = minuteLast;
    }

    public void add5MinuteLast(){
        if(this.minuteLast + 5 >= 30){
            return;
        }
        this.minuteLast = this.minuteLast + 5;
    }

    public void remove5MinuteLast(){
        if(this.minuteLast - 5 <= 5){
            return;
        }
        this.minuteLast = this.minuteLast - 5;
    }

}
