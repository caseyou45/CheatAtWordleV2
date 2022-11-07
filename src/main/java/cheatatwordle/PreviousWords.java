package cheatatwordle;

import Validation.Validator;
import enums.LetterColors;
import logic.GameLogic;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PreviousWords extends JFrame {


    List<String> allPreviousWords;
    JScrollPane jScrollPane;
    GameLogic gl;

    JList<String> jList;

    DefaultListModel<String> list;
    JLabel jLabelPreviousWords, addWordLabel, mainLabel;
    JButton removeWord, addWord, saveChanges;

    JTextField jTextFieldAddWord;


    public PreviousWords(GameLogic gl) {

        setBounds(0, 0, 600, 600);
        setLayout(null);
        setTitle("Wordle Words");

        this.gl = gl;

        mainLabel = new JLabel("View and Manage Words Used by Wordle");
        mainLabel.setFont(new Font("Calibri", Font.BOLD, 18));
        mainLabel.setBounds(135, 30, 400, 50);
        add(mainLabel);

        allPreviousWords = gl.getAllPreviousWords();
        createJList();


        jLabelPreviousWords = new JLabel("View or Remove Words");
        jLabelPreviousWords.setBounds(100, 80, 200, 30);
        add(jLabelPreviousWords);

        removeWord = new JButton("Remove Selected Word");
        removeWord.setBounds(100, 310, 200, 30);
        removeWord.addActionListener((ActionEvent evt) -> {
            allPreviousWords.remove(jList.getSelectedValue());
            list.removeElement(jList.getSelectedValue());

        });
        removeWord.setBackground(new Color(LetterColors.GRAY.getR(), LetterColors.GRAY.getG(), LetterColors.GRAY.getB()));
        removeWord.setForeground(Color.WHITE);
        add(removeWord);

        addWordLabel = new JLabel("Add Word to List");
        addWordLabel.setBounds(350, 80, 200, 30);
        add(addWordLabel);

        jTextFieldAddWord = new JTextField();
        jTextFieldAddWord.setBounds(350, 105, 120, 30);
        jTextFieldAddWord.setDocument(new JTextFieldValidation(5));
        add(jTextFieldAddWord);

        addWord = new JButton("Add Word");
        addWord.setBounds(350, 150, 120, 30);
        addWord.addActionListener((ActionEvent evt) -> {
            if (Validator.isTextWithinLengthRange(jTextFieldAddWord, 5, 5)) {
                allPreviousWords.add(jTextFieldAddWord.getText());
                list.addElement((jTextFieldAddWord.getText()));
            }
        });
        addWord.setBackground(new Color(LetterColors.GRAY.getR(), LetterColors.GRAY.getG(), LetterColors.GRAY.getB()));
        addWord.setForeground(Color.WHITE);
        add(addWord);

        saveChanges = new JButton("Save Changes");
        saveChanges.setBounds(350, 500, 150, 30);
        saveChanges.addActionListener((ActionEvent evt) -> {
            try {
                gl.saveChangesToFile(allPreviousWords);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
        saveChanges.setBackground(new Color(LetterColors.GREEN.getR(), LetterColors.GREEN.getG(), LetterColors.GREEN.getB()));
        saveChanges.setForeground(Color.WHITE);
        add(saveChanges);

    }

    private void createJList() {

        list = new DefaultListModel<>();
        for (String w : allPreviousWords) {
            list.addElement(w);
        }


        jList = new JList<>(list);
        jScrollPane = new JScrollPane(jList);
        jScrollPane.setBounds(100, 105, 200, 200);
        add(jScrollPane);

    }

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

            //Only allow insertions if entry is a letter and is below 5 characters in length
            //Further validation occurs after word is submitted
            if (Character.isLetter(str.charAt(0)) && (getLength() + str.length()) <= limit) {
                super.insertString(offset, str.toLowerCase(), attr);
            }

        }
    }
}
