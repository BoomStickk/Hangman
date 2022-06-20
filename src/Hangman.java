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
        int players = sc.nextInt();
        String player1 = "";
        String player = "";
        String player2 = "";

        while (true) {
            if (players == 2) {
                System.out.print("Enter player 1 name: ");
                player1 = sc.next();
                System.out.print("Enter player 2 name: ");
                player2 = sc.next();

                player = player1;
                while (true){
                    if(player2.equals(player1)){
                        System.out.println("Enter another name: ");
                        player2=sc.next();
                    }else {
                        break;
                    }
                }
                break;
            } else if (players == 1) {
                break;
            } else {
                System.out.print("Invalid input, try again: ");
                players = sc.nextInt();
            }
        }


        Scanner in = new Scanner(new File("C:\\Users\\Boom\\Documents\\java\\Hangman\\src\\cities.txt"));

        List<String> words = new ArrayList<>();

        //picking random word
        city = getRandomWord(in, words);

        List<Character> playerGuess = new ArrayList<>();
        printWordState(city, playerGuess);

        String chooseToContinue;
        int wrongCount = 0;
        int points1 = 0;
        int points2 = 0;
        while (true) {
            printHangManState(wrongCount);
            if (wrongCount >= 6) {
                System.out.println("You lose!");
                System.out.println("The city was: " + city);
                if (players == 2) {
                    System.out.print("would you like another game: ");
                    chooseToContinue = sc.next();
                    if (chooseToContinue.equals("n")) {
                        System.out.println("SCORE: \n" + player1 + " = " + points1 + "  " + player2 + " = " + points2);
                        break;
                    } else if (chooseToContinue.equals("y")) {
                        wrongCount = 0;
                        city = getRandomWord(in, words);
                        playerGuess.clear();

                    } else {
                        //invalid input check
                        while ((!chooseToContinue.equals("n") && !chooseToContinue.equals("y")) || Character.isDigit(chooseToContinue.charAt(0))) {
                            System.out.print("Invalid input(choose y or n): ");
                            playerGuess.clear();
                            wrongCount = 0;
                            chooseToContinue = sc.next();
                        }
                    }
                }
                if (players == 1) {
                    break;
                }
            }

            //changing players
            if (!inputPlayerGuess(keyboard, city, playerGuess, player)) {
                wrongCount++;
                if(player.equals(player1)){
                    player=player2;
                } else {
                    player=player1;

                }

            }


            if (printWordState(city, playerGuess)) {
                System.out.println(player + " " + "You win!");
                if(player.equals(player1)){
                    points1++;
                } else {
                    points2++;

                }


                if (players == 2) {
                    System.out.print("would you like another game(y or n): ");
                    chooseToContinue = sc.next();
                    if (chooseToContinue.equals("n")) {
                        System.out.println("SCORE: \n" + player1 + " = " + points1 + "  " + player2 + " = " + points2);  //there
                        break;
                    } else if (chooseToContinue.equals("y")) {
                        wrongCount = 0;
                        city = getRandomWord(in, words);
                        playerGuess.clear();


                    } else {
                        //invalid input check
                        while ((!chooseToContinue.equals("n") && !chooseToContinue.equals("y")) || Character.isDigit(chooseToContinue.charAt(0))) {
                            System.out.print("Invalid input(choose y or n): ");
                            chooseToContinue = sc.next();
                            playerGuess.clear();
                            wrongCount = 0;
                        }
                    }
                }
                if (players == 1) {
                    break;
                }
            }
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
        System.out.println("=====================================");
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