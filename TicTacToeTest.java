import java.util.Scanner;

public class TicTacToeTest {
    public static void main(String[] args){
        playAgainstCPU();
    }

    private static void playAgainstCPU(){
        // ////////// variable declaration ///////////////////////
        CPUPlayer cpu;
        Mark playerMark;
        boolean useMinMax;
        Board board = new Board();
        Mark currentTurn = Mark.X;
        Scanner scanner = new Scanner(System.in);

        // //////// user input ///////////////////////////////////
        System.out.println("Vous voulez jouer X ou O ? X commence toujours");
        String playerChoice = scanner.nextLine().trim().toLowerCase();
        switch (playerChoice) {
            case "x":
                playerMark = Mark.X;
                cpu = new CPUPlayer(Mark.O);
                break;

            case "o":
                playerMark = Mark.O;
                cpu = new CPUPlayer(Mark.X);
                break;

            default:
                System.out.println("Choix invalide, vous jouez X par défaut");
                playerMark = Mark.X;
                cpu = new CPUPlayer(Mark.O);
        }

        System.out.println("MinMax ou AlphaBeta ? (m/a)");
        playerChoice = scanner.nextLine().trim().toLowerCase();
        useMinMax = switch (playerChoice) {
            case "m" -> true;
            case "a" -> false;
            default -> {
                System.out.println("Choix invalide, utilisation minMax par défaut");
                yield true;
            }
        };

        // ///////////// main loop ///////////////////////////////
        while(!board.isFull() && board.evaluate(currentTurn) == 0){
            board.printBoard();
            if(currentTurn == playerMark){
                boolean moveOk = false;
                while(!moveOk){
                    System.out.println("ligne (0-2) :");
                    int ligne = scanner.nextInt();
                    System.out.println("colonne (0-2) :");
                    int colonne = scanner.nextInt();
                    if (board.isCellEmpty(ligne, colonne)){
                        Move move = new Move(ligne, colonne);
                        board.play(move, playerMark);
                        moveOk = true;
                    }
                    else {
                        System.out.println("invalid input, try again");
                    }
                }
            }
            else {
                System.out.println("CPU is thinking...");
                Move nextMove = useMinMax
                        ? cpu.getNextMoveMinMax(board).getFirst()
                        : cpu.getNextMoveAB(board).getFirst();
                board.play(nextMove, currentTurn);
                System.out.println("CPU explored " + cpu.getNumOfExploredNodes() + " nodes.");
            }
            currentTurn = (currentTurn == Mark.X) ? Mark.O : Mark.X;
        }
        scanner.close();
        // ///////////// results ///////////////////////////////
        printEndGameResults(board, playerMark);

    }

    private static void printEndGameResults(Board board, Mark playerMark){
        board.printBoard();
        int result = board.evaluate(playerMark);
        if (result == 100){
            System.out.println("Vous avez gagné !");
        }
        else if (result == -100){
            System.out.println("L'ordinateur a gagné !");
        }
        else {
            System.out.println("Match nul !");
        }
    }
}
