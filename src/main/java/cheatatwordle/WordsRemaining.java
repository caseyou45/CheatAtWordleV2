package cheatatwordle;

import enums.LetterColors;
import enums.WordOrderingStrategy;
import logic.GameLogic;

import javax.lang.model.element.Element;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class WordsRemaining extends JPanel {

    JLabel wordsRemainingLabel;

    JScrollPane jScrollPane;

    GameLogic gameLogic;

    ButtonGroup radioButtonGroup;

    JRadioButton alphabeticalRadio, reverseAlphabeticalRadio, randomRadio, englishLangUsageRadio, wordsRemainingUsageRadio, removeMostWords;

    JList<String> jListRemainingWords;

    DefaultListModel<String> listRemainingWords;


    public WordsRemaining(GameLogic gameLogic, JTextField wordEntry) {
        this.gameLogic = gameLogic;
        setLayout(null);


        setBounds(480, 143, 200, 550);


        //Words Remaining Label
        wordsRemainingLabel = new JLabel("Words Remaining: ");
        wordsRemainingLabel.setBounds(0, 0, 150, 15);
        add(wordsRemainingLabel);


        //JList Listing Available Words
        listRemainingWords = new DefaultListModel<>();
        for (String w : gameLogic.getAllRemainingWords()) {
            listRemainingWords.addElement(w);
        }

        jListRemainingWords = new JList<>(listRemainingWords);
        jListRemainingWords.addListSelectionListener(arg0 -> {
            if (!arg0.getValueIsAdjusting()) {
                wordEntry.setText(jListRemainingWords.getSelectedValue());
            }
        });

        //Title for Remaining Words With Call to Get Remaining Word Count From Game Logic
        wordsRemainingLabel.setText("Words Remaining: " + gameLogic.getWordsRemainingCount());
        add(wordsRemainingLabel);

        //Adding wordListDisplayArea/Scroll Pane
        jScrollPane = new JScrollPane(jListRemainingWords);
        jScrollPane.setBounds(0, 18, 200, 200);

        add(jScrollPane);

        //Order Selection Group
        radioButtonGroup = new ButtonGroup();


        //Radio for Alphabetical Sort
        alphabeticalRadio = new JRadioButton(String.valueOf(WordOrderingStrategy.ALPHABETICAL));
        alphabeticalRadio.setBounds(0, 235, 200, 15);
        alphabeticalRadio.setToolTipText("Order the remaining words alphabetically (a -> z).");
        radioButtonGroup.add(alphabeticalRadio);
        //Set Alphabetical To Default
        alphabeticalRadio.setSelected(true);
        add(alphabeticalRadio);

        //Radio for Reverse-Alphabetical Sort
        reverseAlphabeticalRadio = new JRadioButton(String.valueOf(WordOrderingStrategy.REVERSE_ALPHABETICAL));
        reverseAlphabeticalRadio.setBounds(0, 265, 200, 15);
        reverseAlphabeticalRadio.setToolTipText("<html>Order the remaining words reverse alphabetically (z -> a).</html>");
        radioButtonGroup.add(reverseAlphabeticalRadio);
        add(reverseAlphabeticalRadio);

        //Radio for Random
        randomRadio = new JRadioButton(String.valueOf(WordOrderingStrategy.RANDOM));
        randomRadio.setBounds(0, 295, 200, 15);
        randomRadio.setToolTipText("<html>Randomize the words. Each press with Random <br/> selected generates a new sequence.</html>");
        radioButtonGroup.add(randomRadio);
        add(randomRadio);

        //Radio for English Sort
        englishLangUsageRadio = new JRadioButton(String.valueOf(WordOrderingStrategy.BY_LETTER_USAGE_IN_ENGLISH_LANG));
        englishLangUsageRadio.setBounds(0, 320, 220, 30);
        englishLangUsageRadio.setToolTipText("<html>Some letters are used more frequently than others <br/> in the English Language. This" +
                "method scores each word accordingly <br/> and then orders them by that score. [Letter use from most to least  <br/> frequent: " +
                "e, a, r, i, o, t, n, s, l, c, u, d, p, m, h, g, b, f, y, w, k, v, x, z, j, q].</html>");
        radioButtonGroup.add(englishLangUsageRadio);
        add(englishLangUsageRadio);

        //Radio for Words Remaining Sort
        wordsRemainingUsageRadio = new JRadioButton(String.valueOf(WordOrderingStrategy.BY_LETTER_USAGE_IN_REMAINING_WORDS));
        wordsRemainingUsageRadio.setBounds(0, 360, 240, 30);
        wordsRemainingUsageRadio.setToolTipText("<html>As the game proceeds, the counts of every letter in  <br/> all the words changes." +
                "This method gives a higher <br/> priority to the words that contain more of the <br/> most frequent letters.</html>");
        radioButtonGroup.add(wordsRemainingUsageRadio);

        add(wordsRemainingUsageRadio);

        //Radio for Alphabetical Sort
        removeMostWords = new JRadioButton(String.valueOf(WordOrderingStrategy.REMOVE_AS_MANY_WORDS_AS_POSSIBLE));
        removeMostWords.setBounds(0, 400, 240, 30);
        removeMostWords.setToolTipText("<html>This method ALSO gives a higher priority to the words that <br/> contain more of the most frequent letters." +
                "But it does not <br/> limit itself to the words remaining list.</html>");
        radioButtonGroup.add(removeMostWords);
        add(removeMostWords);


        JButton jButtonReorderWords = new JButton("Reorder Words");
        jButtonReorderWords.setBounds(25, 450, 150, 25);
        jButtonReorderWords.addActionListener((ActionEvent evt) -> reOrderWords());
        jButtonReorderWords.setBackground(new Color(
                LetterColors.GRAY.getR(),
                LetterColors.GRAY.getG(),
                LetterColors.GRAY.getB()));
        jButtonReorderWords.setForeground(Color.WHITE);

        add(jButtonReorderWords);


    }


    public void reOrderWords() {
        WordOrderingStrategy wordOrderingStrategy = null;

        if (alphabeticalRadio.isSelected()) {
            wordOrderingStrategy = WordOrderingStrategy.ALPHABETICAL;
        } else if (reverseAlphabeticalRadio.isSelected()) {
            wordOrderingStrategy = WordOrderingStrategy.REVERSE_ALPHABETICAL;
        } else if (randomRadio.isSelected()) {
            wordOrderingStrategy = WordOrderingStrategy.RANDOM;
        } else if (englishLangUsageRadio.isSelected()) {
            wordOrderingStrategy = WordOrderingStrategy.BY_LETTER_USAGE_IN_ENGLISH_LANG;
        } else if (wordsRemainingUsageRadio.isSelected()) {
            wordOrderingStrategy = WordOrderingStrategy.BY_LETTER_USAGE_IN_REMAINING_WORDS;
        } else if (removeMostWords.isSelected()) {
            wordOrderingStrategy = WordOrderingStrategy.REMOVE_AS_MANY_WORDS_AS_POSSIBLE;

        }

        if (removeMostWords.isSelected()) {
            wordsRemainingLabel.setText("All Words: " + gameLogic.getAllWordsCount());

        } else {
            wordsRemainingLabel.setText("Words Remaining: " + gameLogic.getWordsRemainingCount());
        }

        gameLogic.reorderWords(wordOrderingStrategy);

        updateWordsRemainingJList(wordOrderingStrategy);

    }

    private void updateWordsRemainingJList(WordOrderingStrategy wordOrderingStrategy) {
        listRemainingWords.removeAllElements();
        for (String w : gameLogic.updateWordsForDisplay(wordOrderingStrategy)) {
            listRemainingWords.addElement(w);
        }

    }


    public void setWordsRemainingLabel(String text) {
        wordsRemainingLabel.setText(text);

    }


}
