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

    private static final File allFiveLetterWordsFile = new File("src/main/java/resources/listOfWordsFinal.txt");
    private static final File lettersByEngUsageFile = new File("src/main/java/resources/lettersByPop.txt");


    private ArrayList<String> allRemainingWords = new ArrayList<>();
    private final ArrayList<String> removedWords = new ArrayList<>();
    LinkedHashMap<String, Integer> wordsWeightedByLetterEngUsage = new LinkedHashMap<>();


    private int[] letterAmounts = new int[26];
    private int[] lettersByEngUsage = new int[26];

    public GameLogic() {
        loadWordsFromFile();
        loadLettersByEngUsageFile();

    }

    private void loadWordsFromFile() {
        Scanner sc = null;
        try {
            sc = new Scanner(allFiveLetterWordsFile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GameLogic.class.getName()).log(Level.SEVERE, null, ex);
        }

        while (sc.hasNextLine()) {
            String data = sc.nextLine();
            allRemainingWords.add(data);
        }
        sc.close();

    }

    private void loadLettersByEngUsageFile() {
        Scanner sc = null;
        try {
            sc = new Scanner(lettersByEngUsageFile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GameLogic.class.getName()).log(Level.SEVERE, null, ex);
        }

        int i = 26;
        while (sc.hasNextLine()) {
            char data = sc.nextLine().charAt(0);
            int pos = data - 97;
            lettersByEngUsage[pos] = i;
            i--;
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

    public void weightWordsByLetter(int[] letterSource, LinkedHashMap destinationHashMap) {
        for (String w : allRemainingWords) {
            int sum = 0;
            sum += letterSource[w.charAt(0) - 97];
            sum += letterSource[w.charAt(1) - 97];
            sum += letterSource[w.charAt(2) - 97];
            sum += letterSource[w.charAt(3) - 97];
            sum += letterSource[w.charAt(4) - 97];
            destinationHashMap.put(w, sum);
        }


    }


    public void reorderWords(WordOrderingStrategy strategy) {
        if (strategy == WordOrderingStrategy.ALPHABETICAL) {

            Collections.sort(allRemainingWords);
        } else if (strategy == WordOrderingStrategy.REVERSE_ALPHABETICAL) {
            Collections.sort(allRemainingWords);
            Collections.reverse(allRemainingWords);
        } else if (strategy == WordOrderingStrategy.RANDOM) {
            Collections.shuffle(allRemainingWords);
        } else if (strategy == WordOrderingStrategy.BY_LETTER_USAGE_IN_ENGLISH_LANG) {
            weightWordsByLetter(lettersByEngUsage, wordsWeightedByLetterEngUsage);
            LinkedHashMap<String, Integer> sortedRemainingWords = wordsWeightedByLetterEngUsage
                    .entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .collect(LinkedHashMap::new,
                            (col, e) -> col.put(e.getKey(), e.getValue()),
                            HashMap::putAll);

            allRemainingWords = new ArrayList<>(sortedRemainingWords.keySet());

        }
    }


}
