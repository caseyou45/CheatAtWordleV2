package cheatatwordle.BarGraph;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BarGraphAmountLabel extends JPanel {

    ArrayList<JLabel> jLabels;


    public BarGraphAmountLabel() {
        setBounds(886, 157, 30, 530);
        GridLayout layout = new GridLayout(26, 1);
        setLayout(layout);


    }


    public void createBarGraphAmountLabels(int[] letterAmounts) {
        jLabels = new ArrayList<>();

        for (int letterAmount : letterAmounts) {
            JLabel tempLabel = new JLabel(String.valueOf(letterAmount));
            tempLabel.setFont(new Font("Courier", Font.PLAIN, 11));
            add(tempLabel);
            jLabels.add(tempLabel);
        }


    }

    public void updateBarGraphAmountLabels(int[] letterAmounts) {
        for (int i = 0; i < letterAmounts.length; i++) jLabels.get(i).setText(String.valueOf(letterAmounts[i]));
    }


}
