import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Hangman {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner keyboard = new Scanner(System.in);
        String city;
        System.out.print("Choose single or multiplayer(1or2): ");
        Scanner sc = new Scanner(System.in);
        checkValidInput(sc, "[12]", "Invalid input(1-2): ");
        int players = sc.nextInt();
        String player1 = "";
        String player = "";
        String player2 = "";

        if (players == 2) {                                                        //choosing names
            System.out.print("Enter player 1 name: ");
            player1 = sc.next();
            System.out.print("Enter player 2 name: ");
            player2 = sc.next();
            player = player1;                                                      //player 1 starts the game
            player2 = checkSameName(sc, player1, player2);
        }
        Scanner in = new Scanner(new File("C:\\Users\\Boom\\Documents\\java\\Hangman\\src\\cities.txt"));//dictionary
        List<String> words = new ArrayList<>();
        city = getRandomWord(in, words);                                           //picking random city
        List<Character> playerGuesses = new ArrayList<>();                         //chosen letters
        printWordState(city, playerGuesses);
        String chooseToContinue;
        int wrongCount = 0;
        int points1 = 0;
        int points2 = 0;
        while (true) {
            //changing players & counting errors
            if (!inputPlayerGuess(keyboard, city, playerGuesses, player)) {
                wrongCount++;
                player = changePlayerTurn(player1, player, player2);
            }
            printHangManState(wrongCount);

            if (players == 2) {
                if (wrongCount == 6) {                                              //loosing condition
                    printLost("You lose!", "The city was: " + city);

                } else if (printWordState(city, playerGuesses)) {                   //winning condition,distributing points
                    System.out.println(player + " " + "You win!");
                    if (player.equals(player1)) {
                        points1++;
                    } else {
                        points2++;
                    }

                }
                if (wrongCount == 6 || checkWordState(city, playerGuesses)) {

                    chooseToContinue = getChooseToContinue(sc);
                    if (chooseToContinue.equals("n")) {
                        printFinalScore(player1, player2, points1, points2);
                        break;
                    } else if (chooseToContinue.equals("y")) {
                        wrongCount = 0;
                        city = getRandomWord(in, words);
                        playerGuesses.clear();
                    }
                }


            } else {
                if (wrongCount == 6) {                                                  //loosing condition
                    printLost("You lose!", "The city was: " + city);
                    break;
                } else if (printWordState(city, playerGuesses)) {                          //winning condition,distributing points
                    System.out.println(player + " " + "You win!");
                    break;
                }
            }
        }
    }

    private static void printLost(String x, String city) {
        System.out.println(x);
        System.out.println(city);
    }

    private static String changePlayerTurn(String player1, String player, String player2) {
        if (player.equals(player1)) {
            player = player2;
        } else {
            player = player1;
        }
        return player;
    }

    private static String checkSameName(Scanner sc, String player1, String player2) {
        while (true) {
            if (player2.equals(player1)) {                             //check for same name
                System.out.print("Enter another name: ");
                player2 = sc.next();
            } else {
                break;
            }

        }
        return player2;
    }


    private static String getChooseToContinue(Scanner sc) {

        System.out.print("would you like another game(y or n): ");

        checkValidInput(sc, "[yn]", "Invalid input(y or n): ");

        return sc.next();
    }

    private static void printFinalScore(String player1, String player2, int points1, int points2) {
        System.out.println("FINAL SCORE: \n" + player1 + " = " + points1 + "  " + player2 + " = " + points2);
    }

    private static void checkValidInput(Scanner sc, String pattern, String s) {
        while (!sc.hasNext(pattern)) {
            System.out.print(s);
            sc.next();
        }
    }

    private static String getRandomWord(Scanner in, List<String> words) {
        String city;
        while (in.hasNext()) {
            words.add(in.nextLine());
        }
        Random rand = new Random();
        city = words.get(rand.nextInt(words.size()));
        return city;
    }

    private static void printHangManState(int wrongCount) {
        if (wrongCount >= 1) {
            printLost("=====", "  0");
        }
        if (wrongCount == 2) {
            System.out.println("  |");
        }
        if (wrongCount >= 3) {
            System.out.print(" -|");
            if (wrongCount >= 4) {
                System.out.println("-");
            } else {
                System.out.println();
            }
        }
        if (wrongCount >= 5) {
            System.out.print(" / ");
            if (wrongCount >= 6) {
                System.out.println("\\");
            } else {
                System.out.println();
            }
        }
        switch (wrongCount) {
            case 1 -> System.out.println("\n");
            case 2, 3, 4 -> System.out.println();
        }
    }

    private static boolean inputPlayerGuess(Scanner keyboard, String word, List<Character> playerGuess, String player) {
        System.out.println("************************************");
        System.out.print(player + " please enter a letter: ");
        String letterGuesses = keyboard.nextLine();
        //check for valid input
        while (letterGuesses.length() != 1 || Character.isDigit(letterGuesses.charAt(0))) {
            System.out.println("Invalid input, try again");
            letterGuesses = keyboard.nextLine();
        }
        playerGuess.add(letterGuesses.charAt(0));
        return word.contains(letterGuesses);
    }

    private static boolean printWordState(String word, List<Character> playerGuess) {
        int correctCount = 0;
        for (int i = 0; i < word.length(); i++) {
            if (playerGuess.contains(word.charAt(i))) {
                System.out.print(word.charAt(i));
                correctCount++;
            } else {
                System.out.print("_");
            }
        }
        System.out.println();
        return (word.length() == correctCount);
    }

    private static boolean checkWordState(String word, List<Character> playerGuess) {
        int correctCount = 0;
        for (int i = 0; i < word.length(); i++) {
            if (playerGuess.contains(word.charAt(i))) {
                correctCount++;
            }
        }
        return (word.length() == correctCount);
    }
}