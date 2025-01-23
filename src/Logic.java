import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Logic {
    public static void main(String[] args) {
        Board initialBoard = new Board();
        Node root = new Node(null, initialBoard, 'h');
        Structure.print(initialBoard);
        Node node = root;
        while (!Structure.isFinal(node)) {
//            computer2Turn(node);
            node = humanTurn(node);
            if (!Structure.isFinal(node)) node = computerTurn(node);
        }
    }

    static Scanner scanner = new Scanner(System.in);

    public static Node humanTurn(Node node) {
        System.out.println("It's your turn");
        List<Move> humanMoves = Structure.throwDice(3);
        while (!humanMoves.isEmpty()) {
            //Choosing a move
            System.out.println("Your moves :");
            int k = 1;
            for (Move mv : humanMoves) {
                System.out.println(k + " - [" + mv + "]");
                k++;
            }

            //if all moves are invalid
            boolean canMove = false;
            for (Move move : humanMoves) {
                for (int i = 0; i < node.board.piecesHuman.length; i++) {
                    if (Structure.canMove(node.board, 'h', i, move)) {
                        canMove = true;
                    }
                }
            }
            if (!canMove) {
                break;
            }

            System.out.print("Select a move to play : ");

            int selectedMove = scanner.nextInt() - 1;
            if (selectedMove >= humanMoves.size()) {
                System.out.println("wrong input, try again.");
                continue;
            }

            Move humanSelectedMove = humanMoves.get(selectedMove);

            //checking valid pieces
            List<Integer> validPieces = new ArrayList<>();

            for (int i = 0; i < node.board.piecesHuman.length; i++) {
                if (Structure.canMove(node.board, 'h', i, humanSelectedMove)) {
                    validPieces.add(i);
                }
            }

            //if no valid pieces
            if (validPieces.isEmpty()) {
                System.out.println("you can't move any piece with the selected move");
                if (humanMoves.size() == 1) {
                    humanMoves.remove(0);
                }
                continue;
            }

            //choosing a piece
            System.out.println("The pieces you can move with [ " + humanSelectedMove + " ] :");
            k = 1;
            for (Integer piece : validPieces) {
                System.out.println(k + " - [" + Structure.getSymbol('h', piece) + "]");
                k++;
            }
            System.out.print("Choose a piece to move : ");

            int selectedPiece = scanner.nextInt() - 1;
            if (selectedPiece >= validPieces.size()) {
                System.out.println("wrong input, try again.");
                continue;
            }

            humanMoves.remove(selectedMove);
            Structure.applyMove(node.board, validPieces.get(selectedPiece), humanSelectedMove, 'h');

            Structure.print(node.board);
        }

        System.out.println("Your turn end");
        return node;
    }

    public static Node computerTurn(Node node) {
        List<Move> moves = Structure.throwDice(3);
        Node nextNode = new MaximizingNode(node, node.board, 'c', moves).getMaxEvaluation(1).key;
        System.out.println("Computer have moves:" + moves);
        Structure.print(nextNode.board);
        System.out.println("Computer turn end");
        return nextNode;
    }

    public static Node computer2Turn(Node node) {
        List<Move> moves = Structure.throwDice(3);
        Node nextNode = new MaximizingNode(node, node.board, 'h', moves).getMaxEvaluation(2).key;
        System.out.println("Umm Zaki have moves:" + moves);
        Structure.print(nextNode.board);
        System.out.println("Umm Zaki turn end");
        return nextNode;
    }
}
