package ui.old;

import javax.swing.*;
import java.awt.*;

/**
 * Creates a bar of a set height and colour, with a shadow
 */
public class Colour implements Icon {
    private static final int BEHIND = 3;

    private Color color;
    private int width;
    private int height;

    /**
     * MODIFIES: This
     * EFFECTS: creates a colour object
     * @param color colour
     * @param width width of shadow
     * @param height height of shadow
     */
    public Colour(Color color, int width, int height) {
        this.color = color;
        this.width = width;
        this.height = height;
    }

    @Override
    public int getIconWidth() {
        return width;
    }

    @Override
    public int getIconHeight() {
        return height;
    }

    /**
     * EFFECTS: Draw the bar and shadow at the specified location.
     * @param comp component
     * @param graph graphic to draw shadow on
     * @param x x coord
     * @param y y coord
     */
    @Override
    public void paintIcon(Component comp, Graphics graph, int x, int y) {
        graph.setColor(color);
        graph.fillRect(x, y, width - BEHIND, height);
        graph.setColor(Color.GRAY);
        graph.fillRect(x + width - BEHIND, y + BEHIND, BEHIND, height - BEHIND);
    }
}
