/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cheatatwordle;

import classes.Guess;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.accessibility.AccessibleContext;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.EventListenerList;
import javax.swing.plaf.ComponentUI;

/**
 * @author casey wilson
 */
public class PreviousGuesses extends JPanel {

    private final Font font = new Font("Calibri", Font.BOLD, 20);
    private int height = 30;
    private int width = 30;


    CheatAtWordle cheatAtWordle;

    ArrayList<JPanel> panels = new ArrayList<>();


    public PreviousGuesses(CheatAtWordle cheatAtWordle) {


        setBounds(200, 160, 250, 300);
        setBorder(new LineBorder(Color.GRAY, 1));
        GridLayout layout = new GridLayout(6, 1);
        layout.setHgap(10);
        layout.setVgap(10);
        setLayout(layout);
        this.cheatAtWordle = cheatAtWordle;

    }


    public void addGuess(Guess guess) {
        createNewWordDisplay(guess);

    }

    public void createNewWordDisplay(Guess guess) {

        JPanel panel = new JPanel();
        panel.setSize(250, 60);
        GridLayout layout = new GridLayout(1, 6);


        //First Letter
        JLabel jLabelFirst = new JLabel(String.valueOf(guess.getFirstLetterChar()));
        jLabelFirst.setPreferredSize(new Dimension(width, height));
        jLabelFirst.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelFirst.setFont(font);
        jLabelFirst.setForeground(Color.white);
        jLabelFirst.setOpaque(true);
        jLabelFirst.setBackground(new Color(
                guess.getFirstLetterColor().getR(),
                guess.getFirstLetterColor().getG(),
                guess.getFirstLetterColor().getB()));


        panel.add(jLabelFirst);


        //Second Letter
        JLabel jLabelSecond = new JLabel(String.valueOf(guess.getSecondLetterChar()));
        jLabelSecond.setPreferredSize(new Dimension(width, height));
        jLabelSecond.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelSecond.setVerticalAlignment(SwingConstants.CENTER);
        jLabelSecond.setFont(font);
        jLabelSecond.setForeground(Color.white);
        jLabelSecond.setOpaque(true);
        jLabelSecond.setBackground(new Color(
                guess.getSecondLetterColor().getR(),
                guess.getSecondLetterColor().getG(),
                guess.getSecondLetterColor().getB()));


        panel.add(jLabelSecond);


        //Third Letter
        JLabel jLabelThird = new JLabel(String.valueOf(guess.getThirdLetterChar()));
        jLabelThird.setPreferredSize(new Dimension(width, height));
        jLabelThird.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelThird.setVerticalAlignment(SwingConstants.CENTER);
        jLabelThird.setFont(font);
        jLabelThird.setForeground(Color.white);
        jLabelThird.setOpaque(true);
        jLabelThird.setBackground(new Color(
                guess.getThirdLetterColor().getR(),
                guess.getThirdLetterColor().getG(),
                guess.getThirdLetterColor().getB()));


        panel.add(jLabelThird);


        //Fourth Letter
        JLabel jLabelFourth = new JLabel(String.valueOf(guess.getFourthLetterChar()));
        jLabelFourth.setPreferredSize(new Dimension(width, height));
        jLabelFourth.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelFourth.setVerticalAlignment(SwingConstants.CENTER);
        jLabelFourth.setFont(font);
        jLabelFourth.setForeground(Color.white);
        jLabelFourth.setOpaque(true);
        jLabelFourth.setBackground(new Color(
                guess.getFourthLetterColor().getR(),
                guess.getFourthLetterColor().getG(),
                guess.getFourthLetterColor().getB()));


        panel.add(jLabelFourth);


        //Fifth Letter
        JLabel jLabelFifth = new JLabel(String.valueOf(guess.getFifthLetterChar()));
        jLabelFifth.setPreferredSize(new Dimension(width, height));
        jLabelFifth.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelFifth.setVerticalAlignment(SwingConstants.CENTER);
        jLabelFifth.setFont(font);
        jLabelFifth.setForeground(Color.white);
        jLabelFifth.setOpaque(true);
        jLabelFifth.setBackground(new Color(
                guess.getFifthLetterColor().getR(),
                guess.getFifthLetterColor().getG(),
                guess.getFifthLetterColor().getB()));


        panel.add(jLabelFifth);


        JButton jButtonRemove = new JButton("DEL");
        jButtonRemove.addActionListener((ActionEvent evt) -> {

            for (JPanel p : panels) {
                remove(p);
            }
            this.revalidate();
            this.repaint();

            cheatAtWordle.removeGuess(guess);


        });
        jButtonRemove.setPreferredSize(new Dimension(width + width, height));
        jButtonRemove.setFont(new Font("Courier", Font.PLAIN, 10));

        panel.add(jButtonRemove);
        panels.add(panel);

        add(panel);

    }


}
