package cheatatwordle.BarGraph;

import logic.GameLogic;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class BarGraphAmountLabel extends JPanel {

    ArrayList<JLabel> jLabels;


    public BarGraphAmountLabel() {
        setBounds(990, 50, 30, 530);
        GridLayout layout = new GridLayout(26, 1);
        setLayout(layout);


    }


    public void createBarGraphAmountLabels(int[] letterAmounts) {
        jLabels = new ArrayList<>();

        for (int i = 0; i < letterAmounts.length; i++) {
            JLabel tempLabel = new JLabel(String.valueOf(letterAmounts[i]));
            tempLabel.setFont(new Font("Courier", Font.PLAIN, 11));
            add(tempLabel);
            jLabels.add(tempLabel);
        }


    }

    public void updateBarGraphAmountLabels(int[] letterAmounts) {
        for (int i = 0; i < letterAmounts.length; i++) jLabels.get(i).setText(String.valueOf(letterAmounts[i]));
    }


}
