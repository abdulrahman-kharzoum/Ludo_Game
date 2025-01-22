import java.util.Objects;

public class Move {
    int steps;
    String name;
    double prob;

    public Move(int steps) {
        this.steps = steps;
        switch (steps) {
            case 1 -> {
                this.name = "khal";
                this.prob = 1;
                break;
            }
            case 2 -> {
                this.name = "dua";
                this.prob = 0.31104;
                break;
            }
            case 3 -> {
                this.name = "three";
                this.prob = 0.27648;
                break;
            }
            case 4 -> {
                this.name = "four";
                this.prob = 0.13824;
                break;
            }
            case 6 -> {
                this.name = "shaka";
                this.prob = 0.046656;
                break;
            }
            case 10 -> {
                this.name = "dest";
                this.prob = 0.186624;
                break;
            }
            case 12 -> {
                this.name = "bara";
                this.prob = 0.004096;
                break;
            }
            case 25 -> {
                this.name = "bnj";
                this.prob = 0.036864;
                break;
            }
        }
    }

    public boolean isKhal() {
        return steps == 1;
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