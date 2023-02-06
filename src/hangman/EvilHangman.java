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
        char guess;
        boolean flag = false;
        EvilHangmanGame evil = new EvilHangmanGame();
        evil.startGame(file, wordLength);
        while(life != 0) {
            System.out.println("You have " + life + " guesses left");
            System.out.println("Used letters: " + evil.getGuessedLetters());
            System.out.println("Word: " + evil.getSubKey());
            boolean repeat = true;
            while(repeat) {
                System.out.print("Enter guess: ");
                guess = input.next().charAt(0);
                try {
                    evil.makeGuess(guess);
                    repeat = false;
                } catch (GuessAlreadyMadeException ex) {
                    ex.GuessAlreadyMadeError();
                }
                for (char c : evil.getSubKey().toCharArray()) {
                    if (guess == c) {
                        solved++;
                    }
                }
                if (compare == solved) {
                    life--;
                } else {
                    compare = solved;
                }
            }
            if(!evil.getSubKey().contains("-")) {
                flag = true;
                break;
            }
        }
        if(flag) {
            System.out.println("You win! You guessed the word: " + evil.getSubKey());
        } else {
            System.out.println("Sorry, you lost! The word was: " + evil.getWordSet());
        }

    }

}
