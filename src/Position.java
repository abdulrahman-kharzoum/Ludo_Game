import java.util.Objects;

public class Position {
    public int x;
    public int y;

    public Position(int id){
        this.x = id%19;
        this.y = id/19;
    }



    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
