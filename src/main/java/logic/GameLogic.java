/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logic;

import enums.WordOrderingStrategy;
import classes.Guess;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author casey
 */
public class GameLogic {

    private static final File allFiveLetterWordsFile = new File("src/main/java/resources/listOfWordsFinal.txt");
    private static final File lettersByEngUsageFile = new File("src/main/java/resources/lettersByPop.txt");


    private ArrayList<String> allRemainingWords = new ArrayList<>();
    private ArrayList<String> allWords = new ArrayList<>();

    private final ArrayList<String> removedWords = new ArrayList<>();
    LinkedHashMap<String, Integer> wordsRankedByLetterEngUsage = new LinkedHashMap<>();
    LinkedHashMap<String, Integer> wordsRankedByLetterRemainingWords = new LinkedHashMap<>();

    private int[] letterAmounts = new int[26];
    private int[] lettersByEngUsage = new int[26];

    /**
     * GameLogic constructor loads the game's words in from file and the letters of the
     * English language in order of most common usage. It then calls setLetterAmounts(), which
     * scores all words accordingly.
     */
    public GameLogic() {
        //Load every word in to arrays
        loadWordsFromFile();
        //Load array containing English language's letters ordered by usage
        loadLettersByEngUsageFile();
        //Explicit loading of letterAmounts Array. Also called when graph is made.
        setLetterAmounts();

    }


    /**
     * Loads all words in from file. It stores them in to two ArrayLists, allRemaining (which
     * will be trimmed of words as the game proceeds) and allWords, which is not trimmed.
     */
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
            allWords.add(data);
        }
        sc.close();

    }

    /**
     * Loads the letters of the English language in order of most common usage.
     * It stores the relative score of those letters in an integer array. Example: 'e' is the most common. The integer equivalence of 'e' is 4
     * (e - 97). In the 4 position of the array, a 26 is placed. The array is then accessed at the next letter
     * and that position is set to 25.
     */
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

    /**
     * @param wordOrderingStrategy
     * @return String that is words formatted for the WordsRemaining display
     * <p>
     * Overloaded Method.
     * If the wordOrderingStrategy is REMOVE_AS_MANY_WORDS_AS_POSSIBLE, every word (allWords) is used to create the return String.
     * Otherwise, allRemainingWords is used.
     */
    public String getWordsForDisplay(WordOrderingStrategy wordOrderingStrategy) {
        String words = "";
        for (String w : wordOrderingStrategy == WordOrderingStrategy.REMOVE_AS_MANY_WORDS_AS_POSSIBLE ? allWords : allRemainingWords) {
            words += "   " + w + "\n";
        }
        return words;
    }

    /**
     * @return String that is words formatted for the WordsRemaining display
     * <p>
     * Overloaded Method.
     * Simply returns allRemainingWords formatted as a String.
     */
    public String getWordsForDisplay() {
        String words = "";
        for (String w : allRemainingWords) {
            words += "   " + w + "\n";
        }
        return words;
    }


    /**
     * @param guess The goal of this method is to remove from the AllRemainingWords ArrayList any words than contain
     *              letters that have been marked as GRAY.
     */
    public void removeWordsWithGrayLetters(Guess guess) {
        ArrayList<Character> grayCharacters = guess.allGrayLetters();

        for (int i = 0; i < allRemainingWords.size(); i++) {
            for (char c : grayCharacters) {
                //If the word has a letter than is GRAY, it is added to an ArrayList
                if (allRemainingWords.get(i).indexOf(c) != -1) {
                    removedWords.add(allRemainingWords.get(i));
                    break;
                }
            }

        }

        //All words that are in the removedWords ArrayList are removed from allRemainingWords
        allRemainingWords.removeAll(removedWords);
    }

    /**
     * @param guess The goal of this method is to remove from allRemainingWords any word that does not have a
     *              letter that has been marked as GREEN in a certain position in the word.
     */
    public void removeWithoutGreenLetters(Guess guess) {
        HashMap<Integer, Character> greenLetters = guess.allGreenLetters();

        for (int i = 0; i < allRemainingWords.size(); i++) {
            //Uses the allGreenLetters method from the guess object to get a HashMap of the
            //position and character of the GREEN letters.
            for (Map.Entry<Integer, Character> greenLetterEntry : greenLetters.entrySet()) {
                Integer index = greenLetterEntry.getKey();
                Character letter = greenLetterEntry.getValue();
                //If the required letter is not in the specified position, the word is stored for removal.
                if (letter != allRemainingWords.get(i).charAt(index)) {
                    removedWords.add(allRemainingWords.get(i));
                    break;
                }
            }
        }
        //All words that are in the removedWords ArrayList are removed from allRemainingWords
        allRemainingWords.removeAll(removedWords);
    }

    /**
     * @param guess The goal of this method is to remove from allRemainingWords any word that does not have a
     *              letter that has been marked as GREEN in a certain position in the word.
     */
    public void removeWithoutYellowLettersAnywhere(Guess guess) {
        HashMap<Integer, Character> yellowLetters = guess.allYellowLetters();

        for (int i = 0; i < allRemainingWords.size(); i++) {
            //Uses the allYellowLetters method from the guess object to get a HashMap of the
            //position and character of the YELLOW letters.
            for (Map.Entry<Integer, Character> yellowLetterEntry : yellowLetters.entrySet()) {
                Integer index = yellowLetterEntry.getKey();
                Character letter = yellowLetterEntry.getValue();
                //If the letter is in the position marked as a YELLOW letter or
                //if the letter is not in the word at all, the word is marked for removal.
                if (letter == allRemainingWords.get(i).charAt(index)) {
                    removedWords.add(allRemainingWords.get(i));
                } else if (allRemainingWords.get(i).indexOf(letter) == -1) {
                    removedWords.add(allRemainingWords.get(i));
                }
            }
        }
        //All words that are in the removedWords ArrayList are removed from allRemainingWords
        allRemainingWords.removeAll(removedWords);

    }

    /**
     * @return size of the allRemainingWords ArrayList
     */
    public int getWordsRemainingCount() {
        return allRemainingWords.size();
    }

    /**
     * @return size of allWords ArrayList
     */
    public int getAllWordsCount() {
        return allWords.size();
    }


    /**
     * Method is used by the main class to return any removed words back
     * to the allRemainingWords ArrayList.
     */
    public void reloadWordsAfterGuessRemoval() {
        allRemainingWords.addAll(removedWords);
        removedWords.clear();
    }

    /**
     * @return the letterAmounts array, which is a count of how many words each letter is in.
     */
    public int[] getLetterAmounts() {
        return letterAmounts;
    }


    /**
     * Fills the letterAmounts array with 0s. And then calls the method
     * that can re-score the letterAmounts array.
     */
    public void setLetterAmounts() {
        Arrays.fill(letterAmounts, 0);
        //Iterates through allRemainingWords, calling the setWordScoreIgnoringRepeatingLetters() method
        for (String w : allRemainingWords) {
            setWordScoreIgnoringRepeatingLetters(letterAmounts, w);
        }
    }


    /**
     * @param arr  array of letters
     * @param word word from an ArrayList
     */
    public void setWordScoreIgnoringRepeatingLetters(int[] arr, String word) {
        //calls removeDuplicates to remove any repeated letters in a word
        char[] wordWithoutRepeats = removeDuplicate(word);
        //scores each letters presence in the word in the array
        for (char c : wordWithoutRepeats) {
            arr[c - 97]++;
        }

    }

    /**
     * @param wordWithRepeats
     * @return word without repeated characters
     * <p>
     * Takes in a word and returns a charArray of that word without
     * any repeated characters
     */
    static char[] removeDuplicate(String wordWithRepeats) {
        HashSet<Character> s = new LinkedHashSet<>(5);
        String word = "";
        for (char x : wordWithRepeats.toCharArray())
            s.add(x);

        for (char x : s)
            word += x;

        return word.toCharArray();
    }


    /**
     * @param letterSource
     * @param destinationHashMap
     * @param wordSource         Takes in a letterSource, which is an array of scored letters; a destinationHashMap, where the score will be recorded;
     *                           and the source of the words.
     */
    public void weightWordsByLetter(int[] letterSource, LinkedHashMap destinationHashMap, ArrayList<String> wordSource) {
        for (String w : wordSource) {
            int sum = 0;
            char[] word = removeDuplicate(w);

            for (char c : word) {
                sum += letterSource[c - 97];
            }
            destinationHashMap.put(w, sum);
        }
    }


    /**
     * @param strategy Reorders the allRemainingWords ArrayList according the user selected strategy.
     */
    public void reorderWords(WordOrderingStrategy strategy) {
        if (strategy == WordOrderingStrategy.ALPHABETICAL) {

            //Uses Collections.sort() to sort it alphabetically
            Collections.sort(allRemainingWords);
        } else if (strategy == WordOrderingStrategy.REVERSE_ALPHABETICAL) {
            //Uses Collections.sort() to sort it alphabetically and then reverse the sort
            Collections.sort(allRemainingWords);
            Collections.reverse(allRemainingWords);
        } else if (strategy == WordOrderingStrategy.RANDOM) {
            //Uses Collections.shuffle() to shuffle allRemainingWords
            Collections.shuffle(allRemainingWords);
        } else if (strategy == WordOrderingStrategy.BY_LETTER_USAGE_IN_ENGLISH_LANG) {
            //Uses weightWordsByLetter() to rank the words according to their usage in the English language

            weightWordsByLetter(lettersByEngUsage, wordsRankedByLetterEngUsage, allRemainingWords);

            //Sorts wordsRankedByLetterEngUsage accordingly
            LinkedHashMap<String, Integer> sortedRemainingWords = wordsRankedByLetterEngUsage
                    .entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .collect(LinkedHashMap::new,
                            (col, e) -> col.put(e.getKey(), e.getValue()),
                            HashMap::putAll);

            allRemainingWords = new ArrayList<>(sortedRemainingWords.keySet());

        } else if (strategy == WordOrderingStrategy.BY_LETTER_USAGE_IN_REMAINING_WORDS) {
            //Uses weightWordsByLetter() to rank the words according to letter usage of the remaining
            //words in play. Uses the same logic, method, and HashMap as BY_LETTER_USAGE_IN_ENGLISH_LANG

            wordsRankedByLetterRemainingWords.clear();

            weightWordsByLetter(letterAmounts, wordsRankedByLetterRemainingWords, allRemainingWords);

            LinkedHashMap<String, Integer> sortedRemainingWords = wordsRankedByLetterRemainingWords
                    .entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .collect(LinkedHashMap::new,
                            (col, e) -> col.put(e.getKey(), e.getValue()),
                            HashMap::putAll);

            allRemainingWords = new ArrayList<>(sortedRemainingWords.keySet());

        } else if (strategy == WordOrderingStrategy.REMOVE_AS_MANY_WORDS_AS_POSSIBLE) {
            //Uses weightWordsByLetter() to rank the words according to letter usage of all the words.
            //Uses the same logic, method, and HashMap as BY_LETTER_USAGE_IN_ENGLISH_LANG
            wordsRankedByLetterRemainingWords.clear();

            weightWordsByLetter(letterAmounts, wordsRankedByLetterRemainingWords, allWords);

            LinkedHashMap<String, Integer> sortedRemainingWords = wordsRankedByLetterRemainingWords
                    .entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .collect(LinkedHashMap::new,
                            (col, e) -> col.put(e.getKey(), e.getValue()),
                            HashMap::putAll);

            allWords = new ArrayList<>(sortedRemainingWords.keySet());

        }


    }


}
