package ui;

import model.Account;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * A screen that shows a histogram of previous savings
 */
public class HistoryViewer extends JFrame {
    private Account acc;

    /**
     * MODIFIES: This
     * EFFECTS: Creates an instance of historyviewer
     * @param acc Account
     */
    public HistoryViewer(Account acc) {
        super("Saving History");
        this.acc = acc;
        displayPanel();
    }

    /**
     * MODIFIES: This
     * EFFECTS: Sets up the panel for display
     */
    private void displayPanel() {
        HistoryPanel panel = new HistoryPanel();
        panel = addAllDates(panel);
        panel.layoutHistogram();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(panel);
        setLocationByPlatform(true);
        pack();
        setVisible(true);
    }

    /**
     * MODIFIES: HistoryPanel
     * EFFECTS: takes all the History data from account, makes bars, and puts them all on a HistoryPanel.
     * @param panel history panel
     * @return HistoryPanel
     */
    private HistoryPanel addAllDates(HistoryPanel panel) {
        Map<String, Integer> transactions = acc.getHistory().getMap();
        for (Map.Entry<String,Integer> entry : transactions.entrySet()) {
            panel.addBar(entry.getKey(), entry.getValue(), Color.ORANGE);
        }

        return panel;
    }
}
