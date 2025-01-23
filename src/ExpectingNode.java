import java.util.ArrayList;
import java.util.List;

public class ExpectingNode extends Node {
    //This Node Will Get List<Move>, And Apply It On Every Pieces, Then Take The Max Evaluation Of Them
    public ExpectingNode(Node parent, Board board, char player) {
        super(parent, board, player);
    }

    Pair<Node, Double> getAverageEvaluation(String behavior, int depth) {
        //Apply Moves On All Pieces, Resulting Expecting Nodes
        //Return The Average Evaluation Of Children With The Current Node
        //Average Evaluation Is Multiplying Each Child's Evaluation With Its Probability
        if (Structure.isFinal(this)) {
            if (Structure.isWinner('c', this)) {
                return new Pair<>(this, Double.MAX_VALUE);
            } else {
                return new Pair<>(this, Double.NEGATIVE_INFINITY);
            }
        }
        if (depth == 0) return new Pair<>(this, Structure.evaluate(board, player));
        List<Pair<List<Move>, Double>> availableMoves = new ArrayList<>(MoveCombinations.allMoves.values());
        Pair<Node, Double> avg = new Pair<>(this, 0.0);
        if (behavior.equals("max")) {
            List<MaximizingNode> children = new ArrayList<>();
            for (var move : availableMoves) {
                children.add(new MaximizingNode(parent, board, player, move.key));
            }
            for (int i = 0; i < children.size(); i++) {
                MaximizingNode child = children.get(i);
                avg.value += availableMoves.get(i).value * child.getMaxEvaluation(depth - 1).value;
            }
        } else {
            List<MinimizingNode> children = new ArrayList<>();
            for (var move : availableMoves) {
                children.add(new MinimizingNode(parent, board, player, move.key));
            }
            for (int i = 0; i < children.size(); i++) {
                MinimizingNode child = children.get(i);
                avg.value += availableMoves.get(i).value * child.getMinEvaluation(depth - 1).value;
            }
        }
        return avg;
    }
}
