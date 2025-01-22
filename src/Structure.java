import java.util.*;

import java.util.List;

public class Structure {

    public static String[][] Board2array(Board b) {
        String[][] arr = new String[17][17];
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 17; j++) {

                // Playable cell
                if ((i > 6 && i < 10) || (j > 6 && j < 10)) {
                    // Check for starting cells FIRST
                    if (j == 9 && i == 1) {
                        arr[i][j] = "  ⬇⬇ ";
                    } else if (j == 7 && i == 15) {
                        arr[i][j] = " ⬆⬆ |";
                    }else if (j == 1 && i == 7)  {
                        arr[i][j] = " ❌ |";
                    }else if (j == 14 && i == 7)  {
                        arr[i][j] = " ❌ |";
                    }else if (j == 15 && i == 9)  {
                        arr[i][j] = " ❌ |";
                    }else if (j == 2 && i == 7)  {
                        arr[i][j] = " __ |";
                    }else if (j == 2 && i == 9)  {
                        arr[i][j] = " ❌ |";
                    }else  if (j == 7 && i == 2) {
                        arr[i][j] = " ❌ |";
                    }
                    // Then check for vertical dividers
                    else if (j == 5 || j == 9) {
                        arr[i][j] = " ___ ";
                    } else {
                        arr[i][j] = " __ |";
                    }
                }

                // Non-playable cells (white spaces)
                else {
                    arr[i][j] = "     ";
                }

                // Horizontal lines (rows 6 and 10)
                if (i == 6 || i == 10) {
                    arr[i][j] = "—————";
                }

                // Vertical lines (columns 6 and 10)
                if (j == 6 || j == 10) {
                    arr[i][j] = "|";
                }
            }
        }
// safe
//        for (int i = 0; i < 8; i++) {
//            Position pos = new Position(Board.safeCells[i]);
//            fixPosition(pos); // Now capped at 18
//            if (pos.x != 9) arr[pos.y][pos.x] = "_><_|";
//            else arr[pos.y][pos.x] = " _><_";
//        }

        //Add Human pieces
        Map<Integer, String> pieces = new HashMap<>();
        for (int i = 0; i < 4; i++) {
            int id = b.piecesHuman[i];

            //if the piece is out of the board
            if (id < 0) {
                arr[14 + (i / 2)][11 + (i % 2)] = "  " + getSymbol('h', i) + "  ";
            }
            //the piece is on board
            else {

                int newCell = Board.pathHuman[id];

                //piece alone on cell
                if (!pieces.containsKey(newCell)) {

                    Position pos = new Position(newCell);
                    fixPosition(pos);
                    StringBuilder str;
                    if (pos.x == 5 || pos.x == 9) str = new StringBuilder(" ___ ");
                    else str = new StringBuilder(" __ |");

                    str.replace(i, i + 1, getSymbol('h', i));
                    pieces.put(newCell, str.toString());
                }
                //piece with other pieces on cell
                else {
                    String old = pieces.get(newCell);
                    String symb = getSymbol('h', i);

                    StringBuilder str = new StringBuilder(old);
                    str.replace(i, i + 1, symb);

                    pieces.replace(newCell, str.toString());
                }
            }
        }

        //Add Computer Pieces
        for (int i = 0; i < 4; i++) {
            int id = b.piecesComputer[i];

            //if the piece is out of the board
            if (id < 0) {
                arr[3 + (i / 2)][11 + (i % 2)] = "  " + getSymbol('c', i) + "  ";
            }
            //the piece is on board
            else {
                int newCell = Board.pathComputer[id];

                //piece alone on cell
                if (!pieces.containsKey(newCell)) {
                    Position pos = new Position(newCell);
                    fixPosition(pos);
                    StringBuilder str;
                    if (pos.x == 5 || pos.x == 9) str = new StringBuilder(" ___ ");
                    else str = new StringBuilder(" __ |");

                    str.replace(i, i + 1, getSymbol('c', i));
                    pieces.put(newCell, str.toString());
                }
                //piece with other pieces on cell
                else {
                    String old = pieces.get(newCell);
                    String symb = getSymbol('c', i);

                    StringBuilder str = new StringBuilder(old);
                    str.replace(i, i + 1, symb);

                    pieces.replace(newCell, str.toString());
                }
            }
        }

        //Draw pieces on Board
        for (Map.Entry<Integer, String> entry : pieces.entrySet()) {
            Position p = new Position(entry.getKey());

            fixPosition(p);
            arr[p.y][p.x] = entry.getValue();
        }


        return arr;
    }

    //fix the position for Board Print
    //fix the position for Board Print
    static void fixPosition(Position p) {
        if (p.y >= 9) p.y += 2;
        else if (p.y >= 6) p.y++;

        if (p.x >= 9) p.x += 2;
        else if (p.x >= 6) p.x++;
    }
    static void print(Board board) {

        String[][] arr = Structure.Board2array(board);

        System.out.println("\n" + "                               " + "_______________");
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 17; j++) {
                System.out.print(arr[i][j]);
            }
            System.out.println();
        }
        System.out.println("                               " + "———————————————");
    }


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

    static double evaluate(Board board, char player) {
        float value = 0;

        // Steps Moved
        for (int posIndex : board.piecesComputer) {
            if (posIndex == -1) {
                value -= 10; // Penalty for not being in play
            } else {
                value += posIndex + 1; // Reward for moving forward
                if (board.isSafe(posIndex)) value += 10; // Reward for being in a safe position
                if (isInHomeColumn(posIndex)) value += 100; // Reward for being in the home column
            }
        }
        for (int posIndex : board.piecesHuman) {
            if (posIndex == -1) {
                value += 10; // Penalty for the human player not being in play
            } else {
                value -= posIndex + 1; // Penalty for the human player moving forward
                if (board.isSafe(posIndex)) value -= 10; // Penalty for the human player being safe
                if (isInHomeColumn(posIndex)) value -= 100; // Penalty for the human player being in the home column
            }
        }

        // Is Someone Behind You?
        for (int posIndexC : board.piecesComputer) {
            for (int posIndexH : board.piecesHuman) {
                if (posIndexC == -1 || posIndexH == -1) continue;
                int diff = posIndexH - posIndexC;
                if (diff == 1 || diff == 2 || diff == 3 || diff == 4 || diff == 6 || diff == 10 || diff == 11 || diff == 12 || diff == 25 || diff == 26)
                    value += 15; // Reward for being ahead
                else if (diff == -1 || diff == -2 || diff == -3 || diff == -4 || diff == -6 || diff == -10 || diff == -11 || diff == -12 || diff == -25 || diff == -26)
                    value -= 15; // Penalty for being behind
            }
        }

        // Reward for Killing Opponent Pieces
        for (int posIndexC : board.piecesComputer) {
            for (int posIndexH : board.piecesHuman) {
                if (posIndexC != -1 && posIndexH != -1 && posIndexC == posIndexH) {
                    value += 50; // Reward for killing an opponent's piece
                }
            }
        }

        // Penalty for Blocking Own Pieces
        for (int i = 0; i < board.piecesComputer.length; i++) {
            for (int j = i + 1; j < board.piecesComputer.length; j++) {
                if (board.piecesComputer[i] != -1 && board.piecesComputer[i] == board.piecesComputer[j]) {
                    value -= 20; // Penalty for blocking own pieces
                }
            }
        }

        // Adjust for Player Perspective
        if (player == 'h') value = -value;

        return value;
    }

    // Helper method to check if a piece is in the home column
    static boolean isInHomeColumn(int posIndex) {
        // Implement logic to check if the position is in the home column
        return posIndex >= 80 && posIndex <= 83; // Example: Home column is positions 80-83
    }

    public static void main(String[] args) {
        // Example: Throw the dice up to 3 times (or until no 6 is rolled)
//        List<Move> moves = throwDice(3);
//        System.out.println("Dice Throws: " + moves);

        // Initialize the board
        Board board = new Board();

        // Set up some test positions
        board.piecesHuman[0] = -1; // Human piece 0 on position 10
        board.piecesHuman[1] = -1; // Human piece 1 on position 20
        board.piecesHuman[2] = -1; // Human piece 2 off the board
        board.piecesHuman[3] = -1; // Human piece 3 on position 30

        board.piecesComputer[0] = -1; // Computer piece 0 on position 15
        board.piecesComputer[1] = -1; // Computer piece 1 on position 25
        board.piecesComputer[2] = -1; // Computer piece 2 off the board
        board.piecesComputer[3] = -1; // Computer piece 3 on position 35


//        // Assign paths for human and computer (example mappings)
//        for (int i = 0; i < 57; i++) {
//            Board.pathHuman[i] = i;  // Human path: 0, 1, 2, ..., 56
//            Board.pathComputer[i] = i + 50;  // Computer path offset by 50
//        }

        // Print the board
        print(board);
    }


}
