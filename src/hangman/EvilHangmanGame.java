package hangman;
import java.io.File;
import java.io.IOException;
import java.util.*;
public class EvilHangmanGame implements IEvilHangmanGame{
    private Set<String> wordSet;
    private SortedMap<String, Set<String>> partition;
    private String subKey;
    private SortedSet<Character> guessedLetters;

    public EvilHangmanGame() {
        wordSet = new HashSet<>();
        partition = new TreeMap<>();
        guessedLetters = new TreeSet<>();
    }
    @Override
    public void startGame(File dictionary, int wordLength) throws IOException, EmptyDictionaryException {
        Scanner scanner = new Scanner(dictionary);
        wordSet.clear();
        while(scanner.hasNext()) {
            String word = scanner.next();
            if(word.length() == wordLength) {
                wordSet.add(word);
            }
        }
        if(wordSet.size() == 0) {
            throw new EmptyDictionaryException();
        }
        StringBuilder initKey = new StringBuilder();
        for(int i = 0; i < wordLength; i++) {
            initKey.append('-');
        }
        subKey = initKey.toString();
    }
    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
        partition.clear();
        char lower = Character.toLowerCase(guess);
        if(guessedLetters.contains(lower)) {
            throw new GuessAlreadyMadeException();
        }
        for(String word : wordSet) {
            getSubsetKey(word, lower);
        }
        int size = 0;
        for(SortedMap.Entry<String, Set<String>> map : partition.entrySet()) {
            if(size < map.getValue().size()) {
                size = map.getValue().size();
                wordSet.clear();
                wordSet.addAll(map.getValue());
                subKey = map.getKey();
            }
        }
        guessedLetters.add(lower);

        return wordSet;
    }
    @Override
    public SortedSet<Character> getGuessedLetters() {
        return guessedLetters;
    }
    private String getSubsetKey(String word, char guess) {
        StringBuilder key = new StringBuilder();
        for(int i = 0; i < word.length(); i++) {
            if(word.charAt(i) == guess) {
                key.append(guess);
            } else {
                if(subKey.charAt(i) == '-') {
                    key.append('-');
                } else {
                    key.append(subKey.charAt(i));
                }
            }
        }
        if(!partition.containsKey(key.toString())) {
            Set<String> pSet = new HashSet<>();
            pSet.add(word);
            partition.put(key.toString(), pSet);
        } else {
            partition.get(key.toString()).add(word);
        }

        return key.toString();
    }
    public String getSubKey() {
        return subKey;
    }
    public String getWordSet() {
        return wordSet.stream().findFirst().get();
    }
}
