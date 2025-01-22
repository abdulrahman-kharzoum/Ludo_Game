import java.util.*;

import java.util.List;

public class Structure {
    public static String getSymbol(char player, int piece) {

        if (player == 'h') {
            switch (piece) {
                case 0:
                    return "➀";
                case 1:
                    return "➁";
                case 2:
                    return "➂";
                case 3:
                    return "➃";
            }
        } else {
            switch (piece) {
                case 0:
                    return "❶";
                case 1:
                    return "❷";
                case 2:
                    return "❸";
                case 3:
                    return "❹";
            }
        }
        return "you entered a wrong piece number";
    }
    static void applyMove(Board board, int pieceIndex, Move move, char player) {
        int[] playerPieces = (player == Board.C) ? board.piecesComputer : board.piecesHuman;
        int[] enemyPieces = (player == Board.C) ? board.piecesHuman : board.piecesComputer;
        int[] playerPath = (player == Board.C) ? Board.pathComputer : Board.pathHuman;
        int[] enemyPath = (player == Board.C) ? Board.pathHuman : Board.pathComputer;

        int currentPosition = playerPieces[pieceIndex];
        int newPosition = currentPosition + move.steps;

        // Update the piece's position
        playerPieces[pieceIndex] = newPosition;

        // Check if the new position kills any enemy pieces
        int killCount = 0;
        for (int i = 0; i < enemyPieces.length; i++) {
            if (enemyPieces[i] >= 0 && enemyPath[enemyPieces[i]] == playerPath[newPosition]) {
                enemyPieces[i] = -1; // Send the enemy piece back to the starting point
                killCount++;
            }
        }

        // If any enemy pieces were killed, allow the player to throw the dice again
        if (killCount > 0) {
            // Only allow one additional throw, regardless of how many pieces were killed
            board.additionalThrow = true;
        }
    }
    static boolean outOfBounds(Board board, char player, int pieceIndex, Move move) {
        int[] playerPieces = (player == Board.C) ? board.piecesComputer : board.piecesHuman;
        int pathIndex = playerPieces[pieceIndex];
        int nextPathIndex = pathIndex + move.steps;
        int[] playerPath = (player == Board.C) ? Board.pathComputer : Board.pathHuman;
        // Check if the move is out of bounds
        return nextPathIndex >= playerPath.length;
    }
    static boolean canMove(Board board, char player, int pieceIndex, Move move) {
        int[] playerPieces = (player == Board.C) ? board.piecesComputer : board.piecesHuman;
        int[] enemyPieces = (player == Board.C) ? board.piecesHuman : board.piecesComputer;
        int[] playerPath = (player == Board.C) ? Board.pathComputer : Board.pathHuman;
        int[] enemyPath = (player == Board.C) ? Board.pathHuman : Board.pathComputer;

        int currentPosition = playerPieces[pieceIndex];
        int newPosition = currentPosition + move.steps;

        // If the piece is out of the board and the move is not a "six"
        if (currentPosition < 0 && !move.isSix()) {
            return false;
        }

        // Check if the move is out of bounds
        if (newPosition >= playerPath.length) {
            return false;
        }

        // Check if the new position is blocked by two or more enemy pieces
        int enemyCount = 0;
        for (int piece : enemyPieces) {
            if (piece >= 0 && enemyPath[piece] == playerPath[newPosition]) {
                enemyCount++;
                if (enemyCount >= 2) {
                    return false; // Cannot jump over two or more enemy pieces
                }
            }
        }

        // Move is valid
        return true;
    }
    public static List<Move> throwDice(int count) {
        if (count <= 0) return new ArrayList<>(); // Base case: stop recursion if count is 0 or less
        List<Move> moves = new ArrayList<>();
        Random random = new Random();

        // Simulate a dice roll (1 to 6)
        int diceResult = random.nextInt(6) + 1;

        // Create a Move object based on the dice result
        Move move = new Move(diceResult);
        moves.add(move);

        // If the result is 6, the player gets another turn (recursive call)
        if (diceResult == 6) {
            moves.addAll(throwDice(count - 1)); // Recursively throw the dice again
        }

        return moves;
    }
    static boolean isFinal(Node node) {
        //Tested and working
        return isWinner('c', node) || isWinner('h', node);
    }
    static boolean isWinner(char player, Node node) {
        //Tested and working
        Board board = node.board;
        int[] pieces = player == 'h' ? board.piecesHuman : board.piecesComputer;
        int[] path = player == 'h' ? Board.pathHuman : Board.pathComputer;
        int finishLineId = player == 'h' ? 127 : 97;
        for (int piece : pieces) {
            if (piece == -1 || path[piece] != finishLineId) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        // Example: Throw the dice up to 3 times (or until no 6 is rolled)
        List<Move> moves = throwDice(3);
        System.out.println("Dice Throws: " + moves);
    }
}
