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
        Scanner name = new Scanner(System.in);
        int players = name.nextInt();
        String player1 = "";
        String player = "";
        String player2 = "";

        while (true) {
            if (players == 2) {
                System.out.print("Enter player 1 name: ");
                player1 = name.next();
                System.out.print("Enter player 2 name: ");
                player2 = name.next();
                player = player1;
                break;
            } else if (players == 1) {
                break;
            } else {
                System.out.print("Invalid input, try again: ");
                players = name.nextInt();
            }
        }


        Scanner in = new Scanner(new File("C:\\Users\\Boom\\Documents\\java\\Hangman\\src\\cities.txt"));

        List<String> words = new ArrayList<>();

        //picking random word
        while (in.hasNext()) {
            words.add(in.nextLine());
        }
        Random rand = new Random();
        city = words.get(rand.nextInt(words.size()));


        List<Character> playerGuess = new ArrayList<>();
        printWordState(city, playerGuess);
        int wrongCount = 0;
        while (true) {
            printHangManState(wrongCount);
            if (wrongCount >= 6) {
                System.out.println("You lose!");
                System.out.println("The city was: " + city);
                break;
            }
            if (!inputPlayerGuess(keyboard, city, playerGuess, player)) {           //hmm
                wrongCount++;
                player = player2;
                player2 = player1;
                player1 = player;

            }

            if (printWordState(city, playerGuess)) {
                System.out.println(player + " " + "You win!");
                break;
            }
        }

    }

    private static void printHangManState(int wrongCount) {
        if (wrongCount == 1) {
            System.out.println("|-------");
            System.out.println("|    O");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");


        } else if (wrongCount == 2) {
            System.out.println("|-------");
            System.out.println("|    O");
            System.out.println("|    |");
            System.out.println("|");
            System.out.println("|");

        } else if (wrongCount == 3) {
            System.out.println("|-------");
            System.out.println("|    O");
            System.out.println("|   -|");
            System.out.println("|");
            System.out.println("|");

        } else if (wrongCount == 4) {
            System.out.println("|-------");
            System.out.println("|    O");
            System.out.println("|   -|-");
            System.out.println("|");
            System.out.println("|");

        } else if (wrongCount == 5) {
            System.out.println("|-------");
            System.out.println("|    O");
            System.out.println("|   -|-");
            System.out.println("|   /");
            System.out.println("|");

        } else if (wrongCount == 6) {
            System.out.println("|-------");
            System.out.println("|    O");
            System.out.println("|   -|-");
            System.out.println("|   / \\");
            System.out.println("|");
        }
    }

    private static boolean inputPlayerGuess(Scanner keyboard, String word, List<Character> playerGuess, String player) {
        System.out.print(player + " please enter a letter: ");
        String letterGuess = keyboard.nextLine();
        //check for valid input
        while (letterGuess.length() != 1 || Character.isDigit(letterGuess.charAt(0))) {
            System.out.println("Invalid input, try again");
            letterGuess = keyboard.nextLine();
        }
        playerGuess.add(letterGuess.charAt(0));
        return word.contains(letterGuess);

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
        System.out.println();
        return (word.length() == correctCount);
    }
}