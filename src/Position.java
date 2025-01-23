import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Position {
    public int x;
    public int y;

    private static final Map<Integer, Position> manualFixes = new HashMap<>();

    static {
        manualFixes.put(1110, new Position(15, 7));
        manualFixes.put(136, new Position(6, 8));
        manualFixes.put(86, new Position(7, 1));
    }

    public Position(int id) {
        this.x = id % 15;
        this.y = id / 15;

        // Apply manual fixes if defined
        if (manualFixes.containsKey(id)) {
            Position fixed = manualFixes.get(id);
            this.x = fixed.x;
            this.y = fixed.y;
        }
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}