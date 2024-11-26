package table;

public class Fork {

    boolean isFree;

    public Fork() {
        this.isFree = true;
    }

    public boolean getState() {
        return isFree;
    }

    public void take(){
        this.isFree = false;
    }

    public void putDown(){
        this.isFree = true;
    }
}
