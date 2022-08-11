package cheatatwordle.BarGraph;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.*;

public class BarGraph extends JPanel {

    private ArrayList<Integer> widthOfBars;

    public BarGraph() {
        setBounds(723, 160, 160, 520);
        setBorder(new LineBorder(Color.BLACK, 1));

    }

    public void createBarGraph(int[] arr) {
        widthOfBars = new ArrayList<>();


        int max = Integer.MIN_VALUE;
        for (int n : arr) if (n > max) max = n;

        for (int i = 0; i < 26; i++) {
            double n = (double) arr[i] / max;
            widthOfBars.add((int) Math.round(n * 150));
        }

        repaint();

    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < widthOfBars.size(); i++) {
            g.drawRect(0, i * 20, widthOfBars.get(i), 20);
            g.setColor(new Color(widthOfBars.get(i), widthOfBars.get(i), widthOfBars.get(i)));
            g.fillRect(0, i * 20, widthOfBars.get(i), 20);
            g.setColor(Color.BLACK);
        }


    }

    public Dimension getPreferredSize() {
        return new Dimension(200, 20); // appropriate constants
    }

    public ArrayList<Integer> getWidthOfBars() {
        return widthOfBars;
    }
}
