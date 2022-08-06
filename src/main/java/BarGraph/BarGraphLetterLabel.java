package BarGraph;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BarGraphLetterLabel extends JPanel {

    ArrayList<JLabel> jLabels;

    public BarGraphLetterLabel() {
        setBounds(805, 50, 10, 530);
        GridLayout layout = new GridLayout(26, 1);
        setLayout(layout);

        jLabels = new ArrayList<>();


        for (int i = 0; i < 26; i++) {
            JLabel temp = new JLabel(String.valueOf((char) (i + 65)));
            temp.setFont(new Font("Courier", Font.PLAIN, 11));
            jLabels.add(temp);
            add(temp);
        }


    }
}
