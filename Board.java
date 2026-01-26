import java.util.ArrayList;

// IMPORTANT: Il ne faut pas changer la signature des méthodes de cette classe, ni le nom de la classe.
// Vous pouvez par contre ajouter d'autres méthodes (ça devrait être le cas)
class Board
{
    private Mark[][] board;

    // Ne pas changer la signature de cette méthode
    public Board() {
        board = new Mark[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = Mark.EMPTY;
            }
        }
    }

    // Place la pièce 'mark' sur le plateau, à la position spécifiée dans Move m
    // Ne pas changer la signature de cette méthode
    public void play(Move m, Mark mark) {
        board[m.getRow()][m.getCol()] = mark;
    }

    // retourne  100 pour une victoire, -100 pour une défaite, 0 pour un match nul
    // Ne pas changer la signature de cette méthode
    public int evaluate(Mark mark){
        Mark opponentMark = (mark == Mark.X) ? Mark.O : Mark.X;
        // lignes
        for (int i = 0; i < 3; i++){
            if (board[i][0] == mark && board[i][1] == mark && board[i][2] == mark){
                return 100;
            }
            else if (board[i][0] == opponentMark && board[i][1] == opponentMark && board[i][2] == opponentMark){
                return -100;
            }
        }
        // colonnes
        for (int i = 0; i < 3; i++){
            if (board[0][i] == mark && board[1][i] == mark && board[2][i] == mark){
                return 100;
            }
            else if (board[0][i] == opponentMark && board[1][i] == opponentMark && board[2][i] == opponentMark){
                return -100;
            }
        }
        // diagonales
        if ((board[0][0] == mark && board[1][1] == mark && board[2][2] == mark) ||
                (board[0][2] == mark && board[1][1] == mark && board[2][0] == mark)){
            return 100;
        }
        if ((board[0][0] == opponentMark && board[1][1] == opponentMark && board[2][2] == opponentMark) ||
                ((board[0][2] == opponentMark && board[1][1] == opponentMark && board[2][0] == opponentMark))){
            return -100;
        }
        return 0;
    }

    public ArrayList<Move> getAllPossibleMoves(){
        ArrayList<Move> possibleMoves = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                if (board[i][j] == Mark.EMPTY){
                    possibleMoves.add(new Move(i, j));
                }
            }
        }
        return possibleMoves;
    }

    public boolean isCellEmpty(int row, int col){
        if (row < 0 || row > 2 || col < 0 || col > 2){
            return false;
        }
        return board[row][col] == Mark.EMPTY;
    }

    public boolean isFull(){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                if (board[i][j] == Mark.EMPTY){
                    return false;
                }
            }
        }
        return true;
    }

    public void printBoard(){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                switch (board[i][j]){
                    case X:
                        System.out.print(" X ");
                        break;
                    case O:
                        System.out.print(" O ");
                        break;
                    case EMPTY:
                        System.out.print(" . ");
                        break;
                }
            }
            System.out.println();
        }
    }
}
