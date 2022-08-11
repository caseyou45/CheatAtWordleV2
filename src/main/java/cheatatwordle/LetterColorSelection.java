package cheatatwordle;

import enums.LetterColors;

import javax.swing.*;
import java.awt.*;

public class LetterColorSelection extends JPanel {

    JLabel wordEntryLabel,
            firstLetterJLabel,
            secondLetterJLabel,
            thirdLetterJLabel,
            fourthLetterJLabel,
            fifthLetterJLabel;
    JComboBox<LetterColors> firstLetterComboBox,
            secondLetterComboBox,
            thirdLetterComboBox,
            fourthLetterComboBox,
            fifthLetterComboBox;


    LetterColorSelection() {


        setBounds(50, 200, 120, 350);
        GridLayout layout = new GridLayout(10, 1);
        layout.setHgap(10);
        layout.setVgap(10);
        setLayout(layout);


        //First Letter Box Label
        firstLetterJLabel = new JLabel("1st Letter Color");
        add(firstLetterJLabel);

        //First Letter Entry Box
        firstLetterComboBox = new JComboBox<>(LetterColors.values());
        add(firstLetterComboBox);

        //Second Letter Box Label
        secondLetterJLabel = new JLabel("2nd Letter Color");
        add(secondLetterJLabel);

        //Second Letter Entry Box
        secondLetterComboBox = new JComboBox<>(LetterColors.values());
        add(secondLetterComboBox);

        //Third Letter Box Label
        thirdLetterJLabel = new JLabel("3rd Letter Color");
        add(thirdLetterJLabel);

        //Third Letter Entry Box
        thirdLetterComboBox = new JComboBox<>(LetterColors.values());
        add(thirdLetterComboBox);

        //Fourth Letter Box Label
        fourthLetterJLabel = new JLabel("4th Letter Color");
        add(fourthLetterJLabel);

        //Fourth Letter Entry Box
        fourthLetterComboBox = new JComboBox<>(LetterColors.values());
        add(fourthLetterComboBox);

        //Fifth Letter Box Label
        fifthLetterJLabel = new JLabel("5th Letter Color");
        add(fifthLetterJLabel);

        //Fifth Letter Entry Box
        fifthLetterComboBox = new JComboBox<>(LetterColors.values());
        add(fifthLetterComboBox);

    }

    public void resetBoxes() {
        firstLetterComboBox.setSelectedIndex(0);
        secondLetterComboBox.setSelectedIndex(0);
        thirdLetterComboBox.setSelectedIndex(0);
        fourthLetterComboBox.setSelectedIndex(0);
        fifthLetterComboBox.setSelectedIndex(0);

    }


}
