import java.util.Scanner;

public class TicTacToeTest {
    public static void main(String[] args){
        playAgainstCPU();
        //minMaxVsAlphaBeta();
    }

    private static void minMaxVsAlphaBeta() {
        int games = 100;
        int draws = 0, winsMinMax = 0, winsAlphaBeta = 0;
        java.util.Random rnd = new java.util.Random();

        long totalNanoMin = 0, totalNanoAB = 0;
        long movesMin = 0, movesAB = 0;

        for (int g = 0; g < games; g++) {
            Board board = new Board();
            CPUPlayer cpuMin = new CPUPlayer(Mark.O);
            CPUPlayer cpuAB = new CPUPlayer(Mark.X);
            Mark current = Mark.X;

            while (board.evaluate(Mark.X) == 0 && !board.isFull()) {
                java.util.ArrayList<Move> choices;
                if (current == Mark.O) {
                    long t0 = System.nanoTime();
                    choices = cpuMin.getNextMoveMinMax(board);
                    long t1 = System.nanoTime();
                    totalNanoMin += (t1 - t0);
                    movesMin++;
                } else {
                    long t0 = System.nanoTime();
                    choices = cpuAB.getNextMoveAB(board);
                    long t1 = System.nanoTime();
                    totalNanoAB += (t1 - t0);
                    movesAB++;
                }

                if (choices.isEmpty()) break;
                Move chosen = choices.get(rnd.nextInt(choices.size()));
                board.play(chosen, current);
                current = (current == Mark.X) ? Mark.O : Mark.X;
            }

            int resultFromX = board.evaluate(Mark.X);
            if (resultFromX == 100) winsMinMax++;
            else if (resultFromX == -100) winsAlphaBeta++;
            else draws++;
        }

        System.out.println("After " + games + " games: MinMax wins=" + winsMinMax
                + ", AlphaBeta wins=" + winsAlphaBeta + ", draws=" + draws);

        double totalMsMin = totalNanoMin / 1_000_000.0;
        double totalMsAB = totalNanoAB / 1_000_000.0;
        double avgMsMin = (movesMin > 0) ? (totalMsMin / movesMin) : 0.0;
        double avgMsAB = (movesAB > 0) ? (totalMsAB / movesAB) : 0.0;

        System.out.println("MinMax total time: " + totalMsMin + " ms, moves=" + movesMin
                + ", avg=" + avgMsMin + " ms/move");
        System.out.println("AlphaBeta total time: " + totalMsAB + " ms, moves=" + movesAB
                + ", avg=" + avgMsAB + " ms/move");
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
