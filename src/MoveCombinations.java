import java.math.BigInteger;
import java.util.*;

public class MoveCombinations {

    static HashMap<String, Pair<List<Move>, Double>> allMoves = new HashMap<>();

    static {
        int[] numbers = {2, 3, 4, 0, 1, 5, 6};
        generateAllMovesCombinations(new ArrayList<>(), numbers, false, 5);
    }

    static void generateAllMovesCombinations(List<Integer> current, int[] numbers, boolean finished, int count) {
        if (count == 0 || finished) {
            List<Integer> res = new ArrayList<>(current);
            res.sort(Comparator.comparingInt(o -> o));
            List<Move> listAsMoves = new ArrayList<>();
            for (Integer num : res) {
                listAsMoves.add(new Move(num));
            }
            allMoves.put(res.toString(), new Pair<>(listAsMoves, getProbability(listAsMoves)));
            return;
        }

        for (int i = 0; i < numbers.length; i++) {
            int number = numbers[i];
            current.add(number);
            generateAllMovesCombinations(current, numbers, i < 3, count - 1);
            current.remove(current.size() - 1);
        }
    }

    static List<List<List<Move>>> combinations = new ArrayList<>();
    static List<List<Integer>> temp = new ArrayList<>();
    static Set<String> set = new HashSet<>();

    static void getPossiblePieceCombinations(List<Move> moves) {
        combinations = new ArrayList<>();
        temp = new ArrayList<>();
        set.clear();

        HashMap<Integer, Integer> movesCount = new HashMap<>();
        for (Move move : moves) {
            if (!movesCount.containsKey(move.steps)) movesCount.put(move.steps, 0);
            movesCount.put(move.steps, movesCount.get(move.steps) + 1);
        }
        boolean flag = true;
        for (Map.Entry<Integer, Integer> entry : movesCount.entrySet()) {
            if (entry.getValue() == 0) continue;
            temp = new ArrayList<>();
            set.clear();
            List<Integer> result = new ArrayList<>();
            result.add(0);
            result.add(0);
            result.add(0);
            result.add(0);
            generateCombinationsForOneMove(result, 4, entry.getValue());
            merge(entry.getKey(), flag);
            flag = false;
        }
    }

    static void merge(int defaultValue, boolean isInitial) {
        List<List<List<Move>>> copyCombinations = new ArrayList<>();
        for (List<Integer> comb : temp) {
            List<List<Move>> moveTemp = new ArrayList<>();
            for (Integer num : comb) {
                List<Move> moves = new ArrayList<>();
                for (int j = 0; j < num; j++) {
                    moves.add(new Move(defaultValue));
                }
                moveTemp.add(moves);
            }
            if (isInitial) {
                copyCombinations.add(moveTemp);
                continue;
            }
            for (List<List<Move>> pieces : combinations) {
                List<List<Move>> copyPieces = new ArrayList<>();
                for (int i = 0; i < pieces.size(); i++) {
                    List<Move> copyMoves = new ArrayList<>(pieces.get(i));
                    copyMoves.addAll(moveTemp.get(i));
                    copyPieces.add(copyMoves);
                }
                copyCombinations.add(copyPieces);
            }
        }
        combinations = copyCombinations;
    }


    static void generateCombinationsForOneMove(List<Integer> result, int pieceNum, int count) {
        if (count == 0) {
            if (!set.contains(result.toString())) {
                set.add(result.toString());
                temp.add(result);
            }
            return;
        }
        for (int i = 0; i < pieceNum; i++) {
            List<Integer> copyResult = new ArrayList<>(result);
            copyResult.set(i, copyResult.get(i) + 1);
            generateCombinationsForOneMove(copyResult, pieceNum, count - 1);
        }
    }

    public static double getProbability(List<Move> moves) {

        //law = permutations * move_1.probability * move_2.probability * .....
        double probs = 1;
        int repeats[] = {0, 0, 0, 0, 0, 0, 0};

        for (Move move : moves) {
            probs *= move.prob;

            switch (move.steps) {
                case 2:
                    repeats[0]++;
                    break;
                case 3:
                    repeats[1]++;
                    break;
                case 4:
                    repeats[2]++;
                    break;
                case 6:
                    repeats[3]++;
                    break;
                case 10:
                    repeats[4]++;
                    break;
                case 12:
                    repeats[5]++;
                    break;
                case 25:
                    repeats[6]++;
                    break;
                default:
            }
        }

        int numerator = 0;
        int denominator = 1;
        for (int i : repeats) {
            if (i != 0) {
                numerator += i;
                denominator *= getFactory(i);
            }
        }

        int permutations = getFactory(numerator - 1) / denominator;

        return permutations * probs;
    }

    public static int getFactory(int n) {
        int factory = 1;
        for (int i = 1; i <= n; i++) {
            factory *= i;
        }
        return factory;
    }
}
