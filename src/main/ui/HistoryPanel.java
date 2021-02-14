package ui;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;

/**
 * A JPanel histogram of transaction history
 *
 * templated off stackoverflow camicker's Bar Graph template
 * https://stackoverflow.com/questions/29708147/custom-graph-java-swing
 */
public class HistoryPanel extends JPanel {
    private static final int CHART_HEIGHT = 200;
    private static final int BAR_WIDTH = 50;
    private static final int BAR_GAP = 10;

    private JPanel graph;
    private JPanel labels;
    private List<Bar> bars = new ArrayList<Bar>();

    /**
     * MODIFIES: This
     * EFFECTS: Creates a history panel with bars and labels
     */
    public HistoryPanel() {
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setLayout(new BorderLayout());
        graph = new JPanel(new GridLayout(1, 0, BAR_GAP, 0));

        Border outer = new MatteBorder(1, 1, 1, 1, Color.BLACK);
        Border inner = new EmptyBorder(10, 10, 0, 10);
        Border compound = new CompoundBorder(outer, inner);
        graph.setBorder(compound);

        labels = new JPanel(new GridLayout(1, 0, BAR_GAP, 0));
        labels.setBorder(new EmptyBorder(5, 10, 0, 10));
        add(graph, BorderLayout.CENTER);
        add(labels, BorderLayout.PAGE_END);
    }

    /**
     * MODIFIES: This
     * EFFECTS: adds a bar to the panel with label, value and colour
     * @param label String Label
     * @param value Int value
     * @param color Color color
     */
    public void addBar(String label, int value, Color color) {
        Bar bar = new Bar(label, value, color);
        bars.add(bar);
    }

    /**
     * MODIFIES: This
     * EFFECTS: Places label at bottom of each corresponding bar. Aligns everything
     */
    public void layoutHistogram() {
        for (Bar bar : bars) {
            JLabel label = new JLabel(bar.getValue() + "");
            label.setHorizontalTextPosition(JLabel.CENTER);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalTextPosition(JLabel.TOP);
            label.setVerticalAlignment(JLabel.BOTTOM);
            int barHeight = (bar.getValue() * CHART_HEIGHT) / getMaxValue();
            Icon icon = new Colour(bar.getColor(), BAR_WIDTH, barHeight);
            label.setIcon(icon);
            graph.add(label);
            JLabel barLabel = new JLabel(bar.getLabel());
            barLabel.setHorizontalAlignment(JLabel.CENTER);
            labels.add(barLabel);
        }
    }

    /**
     * EFFECTS: returns the height of the tallest bar
     * @return int height
     */
    private int getMaxValue() {
        int max = 0;

        for (Bar bar : bars) {
            if (max < bar.getValue()) {
                max = bar.getValue();
            }
        }
        return max;
    }

}
