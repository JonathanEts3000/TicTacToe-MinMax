import java.util.ArrayList;

// IMPORTANT: Il ne faut pas changer la signature des méthodes de cette classe, ni le nom de la classe.
// Vous pouvez par contre ajouter d'autres méthodes (ça devrait être le cas)
class CPUPlayer {
    // Contient le nombre de noeuds visités (le nombre d'appel à la fonction MinMax ou Alpha Beta)
    // Normalement, la variable devrait être incrémentée au début de votre MinMax ou Alpha Beta.
    private int numExploredNodes;
    private Mark cpuMark;

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
        numExploredNodes++;
        ArrayList<Move> possibleMoves = board.getAllPossibleMoves();
        return possibleMoves;
    }

    //Pour le générateur de coups, il est préférable d’explorer les cases du plateau
    // de gauche à droite et de haut en bas comme le programme de test.
    // Retourne la liste des coups possibles.  Cette liste contient plusieurs coups
    // possibles si et seuleument si plusieurs coups ont le même score.
    public ArrayList<Move> getNextMoveAB(Board board) {
        numExploredNodes++;
        ArrayList<Move> possibleMoves = board.getAllPossibleMoves();
        return possibleMoves;
    }
}
