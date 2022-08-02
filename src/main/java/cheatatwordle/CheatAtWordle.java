/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cheatatwordle;

import Validation.Validator;
import classes.Guess;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    JLabel wordEntryLabel, wordsRemainingLabel;

    JButton addWord;

    JTextArea wordListDisplayArea;
    JScrollPane scroll;

    PreviousGuesses previousGuesses;

    GameLogic gl;

    ArrayList<Guess> guesses = new ArrayList<>();

    ArrayList<Character> unavailableLetters = new ArrayList<>();


    LetterColorSelection letterColorSelection;

    CheatAtWordle() {

        gl = new GameLogic();

        JPanel mainPanel = new JPanel();
        Container contentPane = getContentPane();

        // enable explicit positioning of GUI components
        contentPane.setLayout(null);

        // Set up mainPanel
        mainPanel = new JPanel();
        mainPanel.setBounds(0, 0, 900, 700);
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
        addWord.setBounds(50, 450, 100, 25);
        addWord.addActionListener((ActionEvent evt) -> {
            createGuess(evt);
        });
        mainPanel.add(addWord);


        //Words Remaining Label
        wordsRemainingLabel = new JLabel("Words Remaining: ");
        wordsRemainingLabel.setBounds(350, 350, 150, 30);
        mainPanel.add(wordsRemainingLabel);


        //Panel Listing Available Words
        wordListDisplayArea = new JTextArea();
        wordListDisplayArea.setEditable(false);
        wordListDisplayArea.setVisible(true);

        String words = gl.getWordsForDisplay();
        wordListDisplayArea.setText(words);

        scroll = new JScrollPane(wordListDisplayArea);
        scroll.setBounds(350, 30, 150, 300);

        previousGuesses = new PreviousGuesses(this);
        letterColorSelection = new LetterColorSelection();

        mainPanel.add(letterColorSelection);
        mainPanel.add(previousGuesses);
        mainPanel.add(scroll);

        wordsRemainingLabel.setText("Words Remaining: " + gl.getWordsRemainingCount());
        setTitle("Cheat At Wordle"); // set title bar string
        setSize(900, 700); // set window size
        setVisible(true); // display window
    }

    private void createGuess(ActionEvent evt) {
        if (Validator.isTextWithinLengthRange(wordEntry, 5, 5)
                && Validator.isTextFreeOfGrayLetters(unavailableLetters, wordEntry)
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

        }
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
