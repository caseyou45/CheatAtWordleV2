package cheatatwordle;

import enums.LetterColors;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

public class TopPanel extends JPanel {

    JLabel logo, instructions;

    public TopPanel() {
        setBounds(20, 10, 415, 115);
        setBackground(new Color(
                LetterColors.GREEN.getR(),
                LetterColors.GREEN.getG(),
                LetterColors.GREEN.getB()
        ));
        setBorder(new LineBorder(new Color(
                LetterColors.GRAY.getR(),
                LetterColors.GRAY.getG(),
                LetterColors.GRAY.getB()
        ), 1));
        setLayout(null);

        logo = new JLabel();
        logo.setBounds(20, 10, 150, 25);
        logo.setText("Cheat at Wordle");
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Calibri", Font.BOLD, 20));
        add(logo);


        instructions = new JLabel();
        instructions.setBounds(20, 15, 375, 100);
        instructions.setText("<html>1) Enter the words you play. View and manage the guesses you make. <br/>" +
                "2) Use the list of words to make the next best guess. The list will shrink with each play <br/>" +
                "3) Order the list using different methods. <br/> " +
                "4) See which letters are the most common throughout the remaining words.</html>");
        instructions.setForeground(Color.WHITE);
        instructions.setFont(new Font("Calibri", Font.BOLD, 12));
        add(instructions);

    }
}
