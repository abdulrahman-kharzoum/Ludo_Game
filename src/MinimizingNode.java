import java.util.ArrayList;
import java.util.List;

public class MinimizingNode extends Node {
    List<Move> moveList;

    //This Node Will Get List<Move>, And Apply It On Every Pieces, Then Take The Min Evaluation Of Them
    public MinimizingNode(Node parent, Board board, char player, List<Move> moveList) {
        super(parent, board, player);
        this.moveList = moveList;
    }

    Pair<Node, Double> getMinEvaluation(int depth) {
        char pl2 = 'h';
        if (player == 'h') pl2 = 'c';
        //Apply Moves On All Pieces, Resulting Expecting Nodes
        //Return The Min Evaluation Of Children With The Current Node
        if (Structure.isFinal(this)) {
            if (Structure.isWinner('c', this)) {
                return new Pair<>(this, Double.MAX_VALUE);
            } else {
                return new Pair<>(this, Double.NEGATIVE_INFINITY);
            }
        }
        if (depth == 0) return new Pair<>(this, Structure.evaluate(board, player));
        List<Pair<Node, Double>> children = new ArrayList<>();
        MoveCombinations.getPossiblePieceCombinations(moveList);
        for (List<List<Move>> pieces : MoveCombinations.combinations) {
            Board copyBoard = new Board(board);
            boolean smthWrong = false;
            for (int j = 0; j < pieces.size(); j++) {
                List<Move> moves = pieces.get(j);
                List<Move> copyMove = new ArrayList<>(moves);
                for (int k = 0; k < moves.size(); k++) {
                    for (int i = 0; i < copyMove.size(); i++) {
                        if (copyMove.get(i).isOne() && copyBoard.piecesComputer[j] == 55 && Structure.canMove(copyBoard, player, j, copyMove.get(i))) {
                            Structure.applyMove(copyBoard, j, copyMove.get(i), player);
                            copyMove.clear();
                            break;
                        }
                        if (Structure.canMove(copyBoard, pl2, j, copyMove.get(i))) {
                            Structure.applyMove(copyBoard, j, copyMove.get(i), pl2);
                            copyMove.remove(i);
                            break;
                        }
                    }
                }
                if (copyMove.size() != 0) {
                    smthWrong = true;
                    break;
                }
            }
            if (!smthWrong) {
                ExpectingNode expectingNode = new ExpectingNode(this, copyBoard, pl2);
                Pair<Node, Double> pair = expectingNode.getAverageEvaluation("max", depth - 1);
                children.add(new Pair<>(expectingNode, pair.value));
            }
        }
        Pair<Node, Double> min = new Pair<>(this, Double.MAX_VALUE);
        for (var child : children) {
            if (min.value > child.value) {
                min = child;
            }
        }
        return min;
    }
}
