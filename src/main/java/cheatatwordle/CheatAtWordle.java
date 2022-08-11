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
import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import enums.LetterColors;
import logic.GameLogic;

/**
 * @author casey wilson
 */
public class CheatAtWordle extends JFrame {

    JTextField wordEntry;
    JLabel wordEntryLabel, previousGuessesLabel, barGraphLabel;

    JButton addWord;

    PreviousGuesses previousGuesses;

    GameLogic gl;

    ArrayList<Guess> guesses = new ArrayList<>();

    ArrayList<Character> unavailableLetters = new ArrayList<>();

    LetterColorSelection letterColorSelection;

    BarGraph barGraph;

    BarGraphAmountLabel barGraphAmountLabel;

    BarGraphLetterLabel barGraphLetterLabel;

    WordsRemaining wordsRemaining;
    JPanel mainPanel;

    TopPanel topPanel;

    CheatAtWordle() {

        gl = new GameLogic();

        Container contentPane = getContentPane();

        // enable explicit positioning of GUI components
        contentPane.setLayout(null);

        // Set up mainPanel
        mainPanel = new JPanel();
        mainPanel.setBounds(0, 0, 1000, 800);
        mainPanel.setLayout(null);
        contentPane.add(mainPanel);

        //Top Panel/Logo
        topPanel = new TopPanel();
        mainPanel.add(topPanel);

        //Word Entry Label
        wordEntryLabel = new JLabel("Enter Word");
        wordEntryLabel.setBounds(50, 140, 100, 20);
        mainPanel.add(wordEntryLabel);

        //Word Entry
        wordEntry = new JTextField();
        wordEntry.setBounds(50, 160, 120, 30);
        wordEntry.setDocument(new JTextFieldValidation(5));
        mainPanel.add(wordEntry);

        //Add Word Button
        addWord = new JButton();
        addWord.setText("Enter");
        addWord.setBackground(new Color(LetterColors.GREEN.getR(), LetterColors.GREEN.getG(), LetterColors.GREEN.getB()));
        addWord.setForeground(Color.WHITE);
        addWord.setBounds(50, 580, 120, 35);
        addWord.addActionListener((ActionEvent evt) -> {
            createGuess(evt);
        });
        mainPanel.add(addWord);


        //Previous Guesses Label and Instantiation
        previousGuessesLabel = new JLabel("Words Entered So Far");
        previousGuessesLabel.setBounds(200, 140, 150, 20);
        mainPanel.add(previousGuessesLabel);

        previousGuesses = new PreviousGuesses(this);
        mainPanel.add(previousGuesses);

        //Letter Color Section Instantiation
        letterColorSelection = new LetterColorSelection();

        mainPanel.add(letterColorSelection);

        //Remaining Words Section and Instantiation
        wordsRemaining = new WordsRemaining(gl);
        mainPanel.add(wordsRemaining);

        //Bar Graph Label
        barGraphLabel = new JLabel("Letter Usage In Remaining Words");
        barGraphLabel.setBounds(720, 141, 200, 15);
        mainPanel.add(barGraphLabel);

        //Bar Graph Section and Instantiation

        barGraph = new BarGraph();
        barGraph.createBarGraph(gl.getLetterAmounts());
        mainPanel.add(barGraph);

        //Bar Graph Amount Label (right) and Instantiation

        barGraphAmountLabel = new BarGraphAmountLabel();
        barGraphAmountLabel.createBarGraphAmountLabels(gl.getLetterAmounts());
        mainPanel.add(barGraphAmountLabel);

        //Bar Graph Letter Label (left) and Instantiation
        barGraphLetterLabel = new BarGraphLetterLabel();
        mainPanel.add(barGraphLetterLabel);

        setTitle("Cheat At Wordle"); // set title bar string
        setSize(900, 700); // set window size
        setVisible(true); // display window
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
        gl.setLetterAmounts();
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

        wordsRemaining.setWordListDisplayArea(words);
        letterColorSelection.resetBoxes();
        wordsRemaining.setWordsRemainingLabel("Words Remaining: " + gl.getWordsRemainingCount());

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
        wordsRemaining.setWordsRemainingLabel("Words Remaining: " + gl.getWordsRemainingCount());
        wordsRemaining.setWordListDisplayArea(words);
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
        cheatAtWordle.setBounds(0, 0, 1000, 800);
        cheatAtWordle.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
