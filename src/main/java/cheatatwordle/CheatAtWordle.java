package cheatatwordle;

import Validation.Validator;
import cheatatwordle.BarGraph.BarGraph;
import cheatatwordle.BarGraph.BarGraphAmountLabel;
import cheatatwordle.BarGraph.BarGraphLetterLabel;
import classes.Guess;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
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

    List<Guess> guesses = new ArrayList<>();

    List<Character> unavailableLetters = new ArrayList<>();

    LetterColorSelection letterColorSelection;

    BarGraph barGraph;

    BarGraphAmountLabel barGraphAmountLabel;

    BarGraphLetterLabel barGraphLetterLabel;

    WordsRemaining wordsRemaining;
    JPanel mainPanel;

    TopPanel topPanel;

    JMenuBar menuBar;

    JMenu previousMenu;

    JMenuItem previousMenuItem;

    PreviousWords previousWords;


    public CheatAtWordle() {

        gl = new GameLogic();

        Container contentPane = getContentPane();

        // enable explicit positioning of GUI components
        contentPane.setLayout(null);


        // Set up mainPanel
        mainPanel = new JPanel();
        mainPanel.setBounds(0, 0, 1000, 800);
        mainPanel.setLayout(null);
        contentPane.add(mainPanel);

        menuBar = new JMenuBar();

        previousMenu = new JMenu();
        previousMenu.setMnemonic('p');
        previousMenu.setText("Previous Words");


        previousMenuItem = new JMenuItem();
        previousMenuItem.setMnemonic('m');
        previousMenuItem.setText("Manage Words");
        previousMenuItem.addActionListener(evt -> openPreviousWordsActionPerformed());

        previousMenu.add(previousMenuItem);

        menuBar.add(previousMenu);

        setJMenuBar(menuBar);

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
        addWord.addActionListener((ActionEvent evt) -> createGuess());
        mainPanel.add(addWord);


        //Previous Guesses Label and Instantiation
        previousGuessesLabel = new JLabel("Words Entered");
        previousGuessesLabel.setBounds(200, 140, 150, 20);
        mainPanel.add(previousGuessesLabel);

        previousGuesses = new PreviousGuesses(this);
        mainPanel.add(previousGuesses);

        //Letter Color Section Instantiation
        letterColorSelection = new LetterColorSelection();

        mainPanel.add(letterColorSelection);

        //Remaining Words Section and Instantiation
        wordsRemaining = new WordsRemaining(gl, wordEntry);
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


    //Method to open the panel displaying previous words used by Wordle
    private void openPreviousWordsActionPerformed() {
        previousWords = new PreviousWords(gl);
        previousWords.setVisible(true);
        previousWords.setLocationRelativeTo(this);
    }


    //Creates the guess once the player has pressed the button
    private void createGuess() {
        Stopwatch s = new Stopwatch();

        //Ensures the entered word is valid. Will display error(s) to user if not.
        if (Validator.isTextWithinLengthRange(wordEntry, 5, 5)
                && Validator.isTextFreeOfGrayLetters(unavailableLetters, wordEntry)
                && guesses.size() < 6 && Validator.hasAlreadyBeenUsedByWordle(gl.getAllPreviousWords(), wordEntry)
        ) {


            //Created a Guess object

            Guess guess = new Guess(wordEntry.getText(),
                    (LetterColors) letterColorSelection.firstLetterComboBox.getSelectedItem(),
                    (LetterColors) letterColorSelection.secondLetterComboBox.getSelectedItem(),
                    (LetterColors) letterColorSelection.thirdLetterComboBox.getSelectedItem(),
                    (LetterColors) letterColorSelection.fourthLetterComboBox.getSelectedItem(),
                    (LetterColors) letterColorSelection.fifthLetterComboBox.getSelectedItem()
            );


            //Processes the guess. Each method below performs the processes
            //necessary for the game.
            guesses.add(guess);
            //Analyzes the guess
            processGuessAnalysis(guess);
            //Track letters now unavailable
            keepTrackOfUnavailableLetters(guess);
            //Handles display changes after guess
            handleDisplayChangesAfterGuess(guess);
            //Updates bar graph
            setBarGraphChanges();

            System.out.println(guess.getGuessAsWord() + " " + s.elapsedTime());
        }

    }


    //Handles the changes to the bar graph and related objects
    private void setBarGraphChanges() {
        gl.setLetterAmounts();
        barGraph.createBarGraph(gl.getLetterAmounts());
        barGraphAmountLabel.updateBarGraphAmountLabels(gl.getLetterAmounts());
    }


    //Handles control of which letters are no longer able to be played
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


    //Handles various display changes after guess.
    private void handleDisplayChangesAfterGuess(Guess guess) {
        previousGuesses.addGuess(guess);
        wordsRemaining.reOrderWords();
        letterColorSelection.resetBoxes(guess);
        wordsRemaining.setWordsRemainingLabel("Words Remaining: " + gl.getWordsRemainingCount());

    }

    //If player chooses to remove a guess, this method controls that process.
    public void removeGuess(Guess guess) {

        guesses.remove(guess);
        
        unavailableLetters.clear();

        gl.reloadWordsAfterGuessRemoval();

        for (Guess g : guesses) {
            processGuessAnalysis(g);
            keepTrackOfUnavailableLetters(g);
            previousGuesses.addGuess(g);

        }

        wordsRemaining.setWordsRemainingLabel("Words Remaining: " + gl.getWordsRemainingCount());
        wordsRemaining.reOrderWords();
        letterColorSelection.resetBoxes();
        setBarGraphChanges();

        this.revalidate();
        this.repaint();
    }


    //The core of the analysis in the game. This goes through remaining letters and removes
    // them accordingly.
    private void processGuessAnalysis(Guess guess) {
        gl.removeWordsWithGrayLetters(guess);
        gl.removeWithoutGreenLetters(guess);
        gl.removeWithoutYellowLettersAnywhere(guess);
    }


    //Controls how many and what case of letters can be enetered in to box.
    static class JTextFieldValidation extends PlainDocument {

        private final int limit;

        JTextFieldValidation(int limit) {
            super();
            this.limit = limit;
        }

        public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
            if (str == null) {
                return;
            }

            if (Character.isLetter(str.charAt(0)) && (getLength() + str.length()) <= limit) {
                super.insertString(offset, str.toLowerCase(), attr);
            }

        }
    }


    //Stopwatch (version of Princeton implementation) for testing purposes.
    private static class Stopwatch {
        private final long start;


        public Stopwatch() {
            start = System.currentTimeMillis();
        }


        public double elapsedTime() {
            long now = System.currentTimeMillis();
            return (now - start) / 1000.0;
        }


    }


    public static void main(String[] args) {
        CheatAtWordle cheatAtWordle = new CheatAtWordle();
        cheatAtWordle.setBounds(0, 0, 1000, 800);
        cheatAtWordle.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
