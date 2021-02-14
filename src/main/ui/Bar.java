package ui;

import java.awt.*;

/**
 * Bar object for bar chart
 */
public class Bar {
    private String label;
    private int value;
    private Color color;

    /**
     * MODIFIES: This
     * EFFECTS: creates a bar object
     * @param label label of bar
     * @param value height of bar
     * @param color colour of bar
     */
    public Bar(String label, int value, Color color) {
        this.label = label;
        this.value = value;
        this.color = color;
    }

    public String getLabel() {
        return label;
    }

    public int getValue() {
        return value;
    }

    public Color getColor() {
        return color;
    }
}
