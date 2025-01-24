import java.util.Objects;

public class Move {
    int steps;
    String name;
    double prob;

    public Move(int steps) {
        this.steps = steps;
        switch (steps) {
            case 1 -> {
                this.name = "One";
                this.prob = 1.0 / 6.0;
                break;
            }
            case 2 -> {
                this.name = "Two";
                this.prob = 1.0 / 6.0;
                break;
            }
            case 3 -> {
                this.name = "Three";
                this.prob = 1.0 / 6.0;
                break;
            }
            case 4 -> {
                this.name = "Four";
                this.prob = 1.0 / 6.0;
                break;
            }
            case 5 -> {
                this.name = "Five";
                this.prob = 1.0 / 6;
                break;
            }
                case 6 -> {
                this.name = "Six";
                this.prob = 1.0 / 6;
                break;
            }

        }
    }

    public boolean isSix() {

        return steps == 6;

    }
    public boolean isOne() {

        return steps == 1;

    }

    public boolean isKill() {
        ///todo
        return false;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return steps == move.steps;
    }

    @Override
    public int hashCode() {
        return Objects.hash(steps);
    }
}