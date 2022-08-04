/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logic;

import cheatatwordle.WordOrderingStrategy;
import classes.Guess;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static java.util.Map.entry;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author casey
 */
public class GameLogic {

    private static final File allFiveLetterWords = new File("src/main/java/resources/listOfWordsFinal.txt");

    private ArrayList<String> allRemainingWords = new ArrayList<>();
    private final ArrayList<String> removedWords = new ArrayList<>();

    private int[] letterAmounts = new int[26];

    public GameLogic() {
        loadWordsFromFile();
    }

    private void loadWordsFromFile() {
        Scanner sc = null;
        try {
            sc = new Scanner(allFiveLetterWords);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GameLogic.class.getName()).log(Level.SEVERE, null, ex);
        }

        while (sc.hasNextLine()) {
            String data = sc.nextLine();
            allRemainingWords.add(data);
        }
        sc.close();

    }

    public String getWordsForDisplay() {
        String words = "";
        for (String w : allRemainingWords) {
            words += w + "\n";
        }
        return words;
    }

    public void removeWordsWithGrayLetters(Guess guess) {
        ArrayList<Character> grayCharacters = guess.allGrayLetters();

        for (int i = 0; i < allRemainingWords.size(); i++) {
            for (char c : grayCharacters) {
                if (allRemainingWords.get(i).indexOf(c) != -1) {
                    removedWords.add(allRemainingWords.get(i));
                    break;
                }
            }

        }

        allRemainingWords.removeAll(removedWords);
    }

    public void removeWithoutGreenLetters(Guess guess) {
        HashMap<Integer, Character> greenLetters = guess.allGreenLetters();

        for (int i = 0; i < allRemainingWords.size(); i++) {
            for (Map.Entry<Integer, Character> greenLetterEntry : greenLetters.entrySet()) {
                Integer index = greenLetterEntry.getKey();
                Character letter = greenLetterEntry.getValue();
                if (letter != allRemainingWords.get(i).charAt(index)) {
                    removedWords.add(allRemainingWords.get(i));
                    break;
                }
            }
        }

        allRemainingWords.removeAll(removedWords);
    }

    public void removeWithoutYellowLettersAnywhere(Guess guess) {
        HashMap<Integer, Character> yellowLetters = guess.allYellowLetters();

        for (int i = 0; i < allRemainingWords.size(); i++) {
            for (Map.Entry<Integer, Character> greenLetterEntry : yellowLetters.entrySet()) {
                Integer index = greenLetterEntry.getKey();
                Character letter = greenLetterEntry.getValue();
                if (letter == allRemainingWords.get(i).charAt(index)) {
                    removedWords.add(allRemainingWords.get(i));
                } else if (allRemainingWords.get(i).indexOf(letter) == -1) {
                    removedWords.add(allRemainingWords.get(i));
                }
            }
        }
        allRemainingWords.removeAll(removedWords);

    }

    public int getWordsRemainingCount() {
        return allRemainingWords.size();
    }

    public void reloadWordsAfterGuessRemoval() {
        allRemainingWords.addAll(removedWords);
        removedWords.clear();
    }

    public int[] getLetterAmounts() {

        Arrays.fill(letterAmounts, 0);

        for (String w : allRemainingWords) {

            letterAmounts[w.charAt(0) - 97]++;
            letterAmounts[w.charAt(1) - 97]++;
            letterAmounts[w.charAt(2) - 97]++;
            letterAmounts[w.charAt(3) - 97]++;
            letterAmounts[w.charAt(4) - 97]++;

        }

        return letterAmounts;
    }

    public void reorderWords(WordOrderingStrategy strategy) {
        if (strategy == WordOrderingStrategy.ALPHABETICAL) {

            Collections.sort(allRemainingWords);
        } else if (strategy == WordOrderingStrategy.REVERSE_ALPHABETICAL) {
            Collections.reverse(allRemainingWords);


        }
    }
}
