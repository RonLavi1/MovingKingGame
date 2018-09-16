import java.io.Console;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

class MovingKing {

    private static final int BOARD_SIZE = 8; //Default Board Size
    private boolean hasNextMove;
    private boolean[][] markedPlaces;
    private int numOfMovesPlayed;
    private int currentX;
    private int currentY;

    /**
     * Constructor
     */
    MovingKing() {
        numOfMovesPlayed = 0;
        hasNextMove = true;
        markedPlaces = new boolean[BOARD_SIZE][BOARD_SIZE];
        clearBoard();
        setStaringPoint();
    }

    /**
     * Randomize and sets the starting point
     */
    private void setStaringPoint() {
        Random rand = new Random();
        currentX = rand.nextInt(8);
        currentY = rand.nextInt(8);
        markedPlaces[currentX][currentY] = true;

    }

    /**
     * empty the visited locations (Sets all the markedPlaces to false
     */
    private void clearBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                markedPlaces[i][j] = false;
            }
        }
    }

    /**
     * Prints the Board
     */
    private void printBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (currentX == i && currentY == j) {
                    System.out.print("O "); //King sign
                    continue;
                }
                if (markedPlaces[i][j]) {
                    System.out.print("X "); //Place visited sign
                } else {
                    System.out.print("_ "); // yet to be visited sign
                }
            }
            System.out.println();
        }
    }

    /**
     * Handle the scenario the user choose a human vs human
     */
    void humanPlaying() {
        while (hasNextMove) {
            printBoard();
            if (numOfMovesPlayed % 2 == 0) {
                System.out.println("Player one turn:");
            }
            else
            {
                System.out.println("Player two turn:");

            }
//            System.out.println();
            playTurn();
            numOfMovesPlayed += 1;
            hasNextMove = hasNextMove();
        }
        System.out.println();
        printBoard();
        if (numOfMovesPlayed % 2 == 1) {
            System.out.println("Player one has won");
        } else {
            System.out.println("Player two has won");
        }
    }

    /**
     * Handle the scenario the user choose a human vs Computer
     */
    void playAgainstComputer() {
        while (hasNextMove) {
            printBoard();
            System.out.println();
            if (numOfMovesPlayed % 2 == 0) {
                System.out.println("Computer turn");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    System.out.println("Computer was unable to think, you won!");
                }

                dominoStrategy();
            } else {
                System.out.println("Human turn");
                playTurn();
            }
            numOfMovesPlayed += 1;
            hasNextMove = hasNextMove();
        }
        System.out.println();
        printBoard();
        if (numOfMovesPlayed % 2 == 1) {
            System.out.println("The computer has Won");
        } else {
            System.out.println("Congrats on defeating the computer");
        }
    }

    /**
     * Gets input from the user of his choice of opponent (1 playing a human 2 playing a computer)
     * @return User's choice
     */
    int getUserOpponentChoice() {
        int version;
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("Enter 1 For playing against Human or 2 to Play against the computer :");
                version = sc.nextInt();
                if (version == 1 || version == 2) {
                    break;
                } else {
                    System.out.println("Invalid input");
                }
            } catch (Exception e) {
                System.out.println("Invalid input");
            }
        }
        return version;
    }

    /**
     * Handles the game
     */
    void play() {
        welcomeMsg();
        int version = 0;
        version = getUserOpponentChoice();
        if (version == 1) {
            humanPlaying();
        } else if (version == 2) {
            playAgainstComputer();
        }
    }

    /**
     * welcomeMsg
     */
    private void welcomeMsg() {
        System.out.println("Welcome to the Moving King game !");
        System.out.println("The goal is to have a possible movement on the board");
        System.out.println("O represents the king, X place visited, _ yet to be visited");
        System.out.println("each turn you may choose one of the following actions:");
        System.out.println("1:advance to your left down");
        System.out.println("2:advance down");
        System.out.println("3:advance to your right down");
        System.out.println("4:advance to your left");
        System.out.println("6:advance to your right");
        System.out.println("7:advance to your upper left");
        System.out.println("8:advance up");
        System.out.println("9:advance to your upper right");
//        System.out.println("5: Printing the rules");

//   1:advance to your left down     /
    }

    /**
     * Check if there exists next move to play from current location
     * @return true if there is, false o.w
     */
    private boolean hasNextMove() {
        if (currentX > 0 && currentX < BOARD_SIZE - 1) {
            if (currentY > 0 && currentY < BOARD_SIZE - 1) {
                return !(markedPlaces[currentX][currentY - 1] && markedPlaces[currentX][currentY + 1] && markedPlaces[currentX + 1][currentY - 1] && markedPlaces[currentX + 1][currentY] && markedPlaces[currentX + 1][currentY + 1] && markedPlaces[currentX - 1][currentY] && markedPlaces[currentX - 1][currentY + 1] && markedPlaces[currentX - 1][currentY - 1]);
            } else if (currentY == 0) {
                return !(markedPlaces[currentX - 1][currentY] && markedPlaces[currentX + 1][currentY] && markedPlaces[currentX - 1][currentY + 1] && markedPlaces[currentX][currentY + 1] && markedPlaces[currentX + 1][currentY + 1]);
            } else if (currentY == BOARD_SIZE - 1) {
                return !(markedPlaces[currentX - 1][currentY] && markedPlaces[currentX - 1][currentY - 1] && markedPlaces[currentX][currentY - 1] && markedPlaces[currentX + 1][currentY - 1] && markedPlaces[currentX + 1][currentY]);
            }
        } else if (currentX == 0) {
            if (currentY > 0 && currentY < BOARD_SIZE - 1) {
                return !(markedPlaces[currentX][currentY - 1] && markedPlaces[currentX][currentY + 1] && markedPlaces[currentX + 1][currentY] && markedPlaces[currentX + 1][currentY - 1] && markedPlaces[currentX + 1][currentY + 1]);
            }
            if (currentY == 0) {
                return !(markedPlaces[currentX + 1][currentY] && markedPlaces[currentX][currentY + 1] && markedPlaces[currentX + 1][currentY + 1]);
            }
            if (currentY == BOARD_SIZE - 1) {
                return !(markedPlaces[currentX][currentY - 1] && markedPlaces[currentX + 1][currentY] && markedPlaces[currentX + 1][currentY - 1]);
            }
        } else if (currentX == BOARD_SIZE - 1)
            if (currentY > 0 && currentY < BOARD_SIZE - 1) {
                return !(markedPlaces[currentX][currentY - 1] && markedPlaces[currentX][currentY + 1] && markedPlaces[currentX - 1][currentY] && markedPlaces[currentX - 1][currentY + 1] && markedPlaces[currentX - 1][currentY - 1]);
            }
        if (currentY == 0) {
            return !(markedPlaces[currentX - 1][currentY] && markedPlaces[currentX - 1][currentY + 1] && markedPlaces[currentX][currentY + 1]);
        }
        if (currentY == BOARD_SIZE - 1) {
            return !(markedPlaces[currentX - 1][currentY] && markedPlaces[currentX - 1][currentY - 1] && markedPlaces[currentX][currentY - 1]);
        }
        return false;
    }

    /**
     * Handles Human turn, Gets input and when valid updates the current move and coordinates.
     */
    private void playTurn() {
        int action = 0;
        Scanner sc = new Scanner(System.in);

        while (true) {
            try {
                action = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid Action Please enter numbers between 1 and 9 (except 5)");
            }
            if (action == 5 || action > 9 || action < 1) {
                System.out.println("Invalid Action Please enter numbers between 1 and 9 (except 5)");
            } else {
                if (playAction(action)) {
                    updateCurrentPlace(action);
                    markedPlaces[currentX][currentY] = true;
                    break;
                } else {
                    System.out.println("Invalid move Please try another direction");
                }
            }

        }
    }

    /**
     * update Current coordinates of the king
     * @param action action taken by player
     */
    private void updateCurrentPlace(int action) {
        switch (action) {
            case 1:
//                currentX -= 1;
//                currentY += 1;
                currentX += 1;
                currentY -= 1;
                break;
            case 2:
                currentX += 1;
//                currentY += 1;
                break;
            case 3:
                currentX += 1;
                currentY += 1;
                break;
            case 4:
//                currentX -= 1;
                currentY -= 1;

                break;
            case 6:
//                currentX += 1;
                currentY += 1;
                break;
            case 7:
                currentX -= 1;
                currentY -= 1;
                break;
            case 8:
//                currentY -= 1;
                currentX -= 1;
                break;
            case 9:
                currentX -= 1;
                currentY += 1;
                break;
            default:
        }
    }

    /**
     * Checks if an action taken is valid
     * @param action action by the player
     * @return true if the action is valid. false o.w
     */
    private boolean playAction(int action) {
        try {
            switch (action) {
                case 1:
//                return !markedPlaces[currentX-1][currentY+1];
                    return !markedPlaces[currentX + 1][currentY - 1];

                case 2:
//                return !markedPlaces[currentX][currentY+1];
                    return !markedPlaces[currentX + 1][currentY];

                case 3:
                    return !markedPlaces[currentX + 1][currentY + 1];
                case 4:
//                return !markedPlaces[currentX-1][currentY];
                    return !markedPlaces[currentX][currentY - 1];

//            case 5:
//                return !markedPlaces[currentX][currentY];
                case 6:
                    return !markedPlaces[currentX][currentY + 1];

//            return !markedPlaces[currentX+1][currentY];
                case 7:
                    return !markedPlaces[currentX - 1][currentY - 1];
                case 8:
//                return !markedPlaces[currentX][currentY-1];
                    return !markedPlaces[currentX - 1][currentY];
                case 9:
                    return !markedPlaces[currentX - 1][currentY + 1];
                default:
                    return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Computer heuristic to always win
     */
    private void dominoStrategy() {
        if (currentX % 2 == 0) {
            updateCurrentPlace(2);
            markedPlaces[currentX][currentY] = true;

        } else {
            updateCurrentPlace(8);
            markedPlaces[currentX][currentY] = true;

        }
    }
}