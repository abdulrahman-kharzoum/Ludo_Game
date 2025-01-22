public class Node {
    Node parent;
    Board board;
    char player;

    public Node(Node parent, Board board, char player) {
        this.parent = parent;
        this.board = board;
        this.player = player;
    }
}
