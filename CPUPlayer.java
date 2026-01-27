import java.util.ArrayList;

// IMPORTANT: Il ne faut pas changer la signature des méthodes de cette classe, ni le nom de la classe.
// Vous pouvez par contre ajouter d'autres méthodes (ça devrait être le cas)
class CPUPlayer {
    // Contient le nombre de noeuds visités (le nombre d'appel à la fonction MinMax ou Alpha Beta)
    // Normalement, la variable devrait être incrémentée au début de votre MinMax ou Alpha Beta.
    private int numExploredNodes;
    private final Mark cpuMark;

    // Le constructeur reçoit en paramètre le joueur MAX (X ou O)
    public CPUPlayer(Mark cpu) {
        numExploredNodes = 0;
        cpuMark = cpu;
    }

    // Ne pas changer cette méthode
    public int getNumOfExploredNodes() {
        return numExploredNodes;
    }

    //Pour le générateur de coups, il est préférable d’explorer les cases du plateau
    // de gauche à droite et de haut en bas comme le programme de test.
    // Retourne la liste des coups possibles.  Cette liste contient plusieurs coups
    // possibles si et seuleument si plusieurs coups ont le même score.
    public ArrayList<Move> getNextMoveMinMax(Board board) {
        numExploredNodes = 1;
        ArrayList<Move> bestMoves = new ArrayList<>();
        ArrayList<Move> possibleMoves = board.getAllPossibleMoves();
        int bestScore = Integer.MIN_VALUE;

        for (Move move : possibleMoves) {
            board.play(move, cpuMark);
            int score = minMax(board, false);
            if (score > bestScore) {
                bestScore = score;
                bestMoves.clear();
                bestMoves.add(move);
            } else if (score == bestScore) {
                bestMoves.add(move);
            }
            board.play(move, Mark.EMPTY);
        }
        return bestMoves;
    }

    private int minMax(Board board, boolean isMax) {
        numExploredNodes++;
        int score = board.evaluate(cpuMark);
        if (score != 0 || board.isFull()){
            return score;
        }
        ArrayList<Move> possibleMoves = board.getAllPossibleMoves();
        if (isMax) {
            int maxScore = Integer.MIN_VALUE;
            for (Move move : possibleMoves) {
                board.play(move, cpuMark);
                int scoreMove = minMax(board, false);
                if (scoreMove > maxScore) {
                    maxScore = scoreMove;
                }
                board.play(move, Mark.EMPTY);
            }
            return maxScore;
        }
        else {
            int minScore = Integer.MAX_VALUE;
            for (Move move : possibleMoves) {
                board.play(move, cpuMark == Mark.X ? Mark.O : Mark.X);
                int scoreMove = minMax(board, true);
                if (scoreMove < minScore) {
                    minScore = scoreMove;
                }
                board.play(move, Mark.EMPTY);
            }
            return minScore;
        }
    }

    //Pour le générateur de coups, il est préférable d’explorer les cases du plateau
    // de gauche à droite et de haut en bas comme le programme de test.
    // Retourne la liste des coups possibles.  Cette liste contient plusieurs coups
    // possibles si et seuleument si plusieurs coups ont le même score.
    public ArrayList<Move> getNextMoveAB(Board board) {
        numExploredNodes = 0;
        ArrayList<Move> possibleMoves = board.getAllPossibleMoves();
        return possibleMoves;
    }
}
