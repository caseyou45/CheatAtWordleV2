/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cheatatwordle;

import Validation.Validator;
import cheatatwordle.BarGraph.BarGraph;
import cheatatwordle.BarGraph.BarGraphAmountLabel;
import cheatatwordle.BarGraph.BarGraphLetterLabel;
import classes.Guess;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import logic.GameLogic;

/**
 * @author casey
 */
public class CheatAtWordle extends JFrame {

    String guessedWord = "";
    JTextField wordEntry;
    JLabel wordEntryLabel, wordsRemainingLabel, previousGuessesLabel;

    JButton addWord;

    JTextArea wordListDisplayArea;
    JScrollPane scroll;

    PreviousGuesses previousGuesses;

    GameLogic gl;

    ArrayList<Guess> guesses = new ArrayList<>();

    ArrayList<Character> unavailableLetters = new ArrayList<>();

    JComboBox<WordOrderingStrategy> wordOrderingStrategyJComboBox;

    LetterColorSelection letterColorSelection;

    BarGraph barGraph;
    BarGraphLetterLabel barGraphLabel;

    BarGraphAmountLabel barGraphAmountLabel;


    CheatAtWordle() {

        gl = new GameLogic();

        JPanel mainPanel = new JPanel();
        Container contentPane = getContentPane();

        // enable explicit positioning of GUI components
        contentPane.setLayout(null);

        // Set up mainPanel
        mainPanel = new JPanel();
        mainPanel.setBounds(0, 0, 1200, 700);
        mainPanel.setLayout(null);
        contentPane.add(mainPanel);

        //Word Entry Label
        wordEntryLabel = new JLabel("Enter Word");
        wordEntryLabel.setBounds(50, 30, 100, 20);
        mainPanel.add(wordEntryLabel);

        //Word Entry
        wordEntry = new JTextField();
        wordEntry.setBounds(50, 50, 100, 25);
        wordEntry.setDocument(new JTextFieldValidation(5));
        mainPanel.add(wordEntry);

        //Add Word Button
        addWord = new JButton();
        addWord.setText("Add Word");
        addWord.setBackground(new Color(106, 150, 100));
        addWord.setForeground(Color.WHITE);
        addWord.setBounds(50, 460, 120, 25);
        addWord.addActionListener((ActionEvent evt) -> {
            createGuess(evt);
        });
        mainPanel.add(addWord);


        //Words Remaining Label
        wordsRemainingLabel = new JLabel("Words Remaining: ");
        wordsRemainingLabel.setBounds(235, 25, 150, 30);
        mainPanel.add(wordsRemainingLabel);


        //Panel Listing Available Words
        wordListDisplayArea = new JTextArea();
        wordListDisplayArea.setEditable(false);
        wordListDisplayArea.setVisible(true);

        String words = gl.getWordsForDisplay();
        wordListDisplayArea.setText(words);

        //Title for Remaining Words With Call to Get Remaining Word Count From Game Logic
        wordsRemainingLabel.setText("Words Remaining: " + gl.getWordsRemainingCount());


        //Adding wordListDisplayArea/Scroll Pane
        scroll = new JScrollPane(wordListDisplayArea);
        scroll.setBounds(235, 55, 150, 300);
        mainPanel.add(scroll);


        //Previous Guesses Label and Instantiation
        previousGuessesLabel = new JLabel("Previous Guesses");
        previousGuessesLabel.setBounds(420, 24, 150, 30);
        mainPanel.add(previousGuessesLabel);

        previousGuesses = new PreviousGuesses(this);
        mainPanel.add(previousGuesses);

        //Letter Color Section Instantiation
        letterColorSelection = new LetterColorSelection();

        mainPanel.add(letterColorSelection);


        //Order Selection
        wordOrderingStrategyJComboBox = new JComboBox(WordOrderingStrategy.values());
        wordOrderingStrategyJComboBox.setBounds(235, 380, 150, 30);


        JButton jButtonReorderWords = new JButton("Reorder Words");
        jButtonReorderWords.setBounds(235, 415, 150, 30);
        jButtonReorderWords.addActionListener((ActionEvent evt) -> {
            reOrderWords(evt);
        });
        mainPanel.add(jButtonReorderWords);

        mainPanel.add(wordOrderingStrategyJComboBox);

        barGraph = new BarGraph();
        barGraph.createBarGraph(gl.getLetterAmounts());
        mainPanel.add(barGraph);

        barGraphLabel = new BarGraphLetterLabel();
        mainPanel.add(barGraphLabel);

        barGraphAmountLabel = new BarGraphAmountLabel();
        barGraphAmountLabel.createBarGraphAmountLabels(gl.getLetterAmounts());
        mainPanel.add(barGraphAmountLabel);

        setTitle("Cheat At Wordle"); // set title bar string
        setSize(900, 700); // set window size
        setVisible(true); // display window
    }


    public void reOrderWords(ActionEvent evt) {
        gl.reorderWords((WordOrderingStrategy) wordOrderingStrategyJComboBox.getSelectedItem());
        String words = gl.getWordsForDisplay();
        wordListDisplayArea.setText(words);
        scroll.getViewport().setViewPosition(new Point(0, 0));

    }

    private void createGuess(ActionEvent evt) {
        if (Validator.isTextWithinLengthRange(wordEntry, 5, 5)
                && Validator.isTextFreeOfGrayLetters(unavailableLetters, wordEntry)
                && guesses.size() < 6
        ) {

            Guess guess = new Guess(wordEntry.getText(),
                    (LetterColors) letterColorSelection.firstLetterComboBox.getSelectedItem(),
                    (LetterColors) letterColorSelection.secondLetterComboBox.getSelectedItem(),
                    (LetterColors) letterColorSelection.thirdLetterComboBox.getSelectedItem(),
                    (LetterColors) letterColorSelection.fourthLetterComboBox.getSelectedItem(),
                    (LetterColors) letterColorSelection.fifthLetterComboBox.getSelectedItem()
            );

            guesses.add(guess);
            processGuessAnalysis(guess);
            keepTrackOfUnavailableLetters(guess);
            handleDisplayChangesAfterGuess(guess);
            setBarGraphChanges();

        }

    }

    public void setBarGraphChanges() {
        barGraph.createBarGraph(gl.getLetterAmounts());
        barGraphAmountLabel.updateBarGraphAmountLabels(gl.getLetterAmounts());
    }


    private void keepTrackOfUnavailableLetters(Guess guess) {
        if (guess.getFirstLetterColor() == LetterColors.GRAY && !unavailableLetters.contains(guess.getFirstLetterChar()))
            unavailableLetters.add(guess.getFirstLetterChar());
        if (guess.getSecondLetterColor() == LetterColors.GRAY && !unavailableLetters.contains(guess.getSecondLetterChar()))
            unavailableLetters.add(guess.getSecondLetterChar());
        if (guess.getThirdLetterColor() == LetterColors.GRAY && !unavailableLetters.contains(guess.getThirdLetterChar()))
            unavailableLetters.add(guess.getThirdLetterChar());
        if (guess.getFourthLetterColor() == LetterColors.GRAY && !unavailableLetters.contains(guess.getFourthLetterChar()))
            unavailableLetters.add(guess.getFourthLetterChar());
        if (guess.getFifthLetterColor() == LetterColors.GRAY && !unavailableLetters.contains(guess.getFifthLetterChar()))
            unavailableLetters.add(guess.getFifthLetterChar());

    }

    private void handleDisplayChangesAfterGuess(Guess guess) {
        previousGuesses.addGuess(guess);
        String words = gl.getWordsForDisplay();

        wordListDisplayArea.setText(words);
        letterColorSelection.resetBoxes();
        wordsRemainingLabel.setText("Words Remaining: " + gl.getWordsRemainingCount());

    }

    public void removeGuess(Guess guess) {
        guesses.remove(guess);
        unavailableLetters.clear();

        gl.reloadWordsAfterGuessRemoval();

        for (Guess g : guesses) {
            processGuessAnalysis(g);
            keepTrackOfUnavailableLetters(g);
            previousGuesses.addGuess(g);

        }

        String words = gl.getWordsForDisplay();
        wordsRemainingLabel.setText("Words Remaining: " + gl.getWordsRemainingCount());

        wordListDisplayArea.setText(words);
        letterColorSelection.resetBoxes();
        setBarGraphChanges();

        this.revalidate();
        this.repaint();
    }

    private void processGuessAnalysis(Guess guess) {
        gl.removeWordsWithGrayLetters(guess);
        gl.removeWithoutGreenLetters(guess);
        gl.removeWithoutYellowLettersAnywhere(guess);
    }

    class JTextFieldValidation extends PlainDocument {

        private int limit;

        JTextFieldValidation(int limit) {
            super();
            this.limit = limit;
        }

        JTextFieldValidation(int limit, boolean upper) {
            super();
            this.limit = limit;
        }

        public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
            if (str == null) {
                return;
            }

            //Only allow insertions if entry is a letter and is below 5 characters in length
            //Further validation occurs after word is submitted
            if (Character.isLetter(str.charAt(0)) && (getLength() + str.length()) <= limit) {
                super.insertString(offset, str, attr);
            }

        }
    }

    public static void main(String[] args) {
        CheatAtWordle cheatAtWordle = new CheatAtWordle();
        cheatAtWordle.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
