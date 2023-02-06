package hangman;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class EvilHangman {

    public static void main(String[] args) throws EmptyDictionaryException, IOException, GuessAlreadyMadeException {
        File file = new File(args[0]);
        int wordLength = Integer.parseInt(args[1]);
        int life = Integer.parseInt(args[2]);
        int solved = 0;
        int compare = 0;
        Scanner input = new Scanner(System.in);
        String guess;
        EvilHangmanGame evil = new EvilHangmanGame();
        evil.startGame(file, wordLength);
        while(life != 0) {
            System.out.println("You have " + life + " guesses left");
            System.out.println("Used letters: " + evil.getGuessedLetters());
            System.out.println("Word: " + evil.getSubKey());
            boolean repeat = true;
            while(repeat) {
                boolean flag = true;
                System.out.print("Enter guess: ");
                guess = input.next().toLowerCase();
                while (guess.length() != 1 || guess.charAt(0) < 'a' || guess.charAt(0) > 'z') {
                    System.out.print("Invalid input! Enter guess: ");
                    guess = guess = input.next().toLowerCase();
                }
                try {
                    evil.makeGuess(guess.charAt(0));
                    repeat = false;
                } catch (GuessAlreadyMadeException ex) {
                    ex.GuessAlreadyMadeError();
                    flag = false;
                }
                for (char c : evil.getSubKey().toCharArray()) {
                    if (guess.charAt(0) == c) {
                        solved++;
                    }
                }
                if (compare == solved && flag) {
                    life--;
                    if(life == 0) {
                        System.out.println("Sorry, there are no " + guess);
                    } else {
                        System.out.println("Sorry, there are no " + guess + "\n");
                    }
                }
                if (compare != solved && flag) {
                    if(solved == wordLength) {
                        System.out.println("Yes, there is " + (solved - compare) + " " + guess);
                    } else {
                        System.out.println("Yes, there is " + (solved - compare) + " " + guess + "\n");
                        compare = solved;
                    }
                }
            }
            if(solved == wordLength) {
                break;
            }
        }
        if(solved == wordLength) {
            System.out.println("You win! You guessed the word: " + evil.getSubKey());
        } else {
            System.out.println("Sorry, you lost! The word was: " + evil.getWordSet());
        }

    }

}
