package hangman;

public class GuessAlreadyMadeException extends Exception {
    public void GuessAlreadyMadeError() {
        System.out.print("Guess already made! ");
    }
}
