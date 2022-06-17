import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Hangman {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("C:\\Users\\Boom\\Documents\\java\\Hangman\\src\\cities.txt"));
        Scanner keyboard = new Scanner(System.in);
        List<String> words = new ArrayList<>();

        //picking random word
        while (in.hasNext()) {
            words.add(in.nextLine());
        }
        Random rand = new Random();
        String word = words.get(rand.nextInt(words.size()));
        List<Character> playerGuess = new ArrayList<>();
        printWordState(word, playerGuess);
        int wrongCount = 0;
        while (true) {
            printHangManState(wrongCount);
            if (wrongCount >= 6) {
                System.out.println("You lose");
                break;
            }
            if (!inputPlayerGuess(keyboard, word, playerGuess)) {
                wrongCount++;
            }

            if (printWordState(word, playerGuess)) {
                System.out.println("you win");
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

    private static boolean inputPlayerGuess(Scanner keyboard, String word, List<Character> playerGuess) {
        System.out.print("please enter a letter: ");
        String letterGuess = keyboard.nextLine();
        //check for valid input
        while (letterGuess.length() != 1 || Character.isDigit(letterGuess.charAt(0))) {
            System.out.println("invalid input, try again");
            letterGuess = keyboard.next();
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