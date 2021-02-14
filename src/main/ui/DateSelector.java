package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DateTimeException;
import java.time.LocalDate;

/**
 * A window to sort transactions by date
 */
public class DateSelector extends JFrame implements ActionListener {

    private Controller controller;
    private JButton enter;
    private JTextField text;

    /**
     * MODIFIES: This
     * EFFECTS: Creates a date selector window
     * @param controller account controller needed to send actions to
     */
    public DateSelector(Controller controller) {
        super("Personal Finance Tracker");
        this.controller = controller;
        setupFrame();
        add(displayTopWindow());
        add(displayBotWindow());
        pack();
    }

    /**
     * MODIFIES: This
     * EFFECTS: Sets up frame with panels and close details
     */
    private void setupFrame() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(2, 1));
        setVisible(true);
        setResizable(false);
    }

    /**
     * EFFECTS: Creates a panel with text
     * @return JPanel
     */
    private JPanel displayTopWindow() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Enter the date you would like to retrieve transactions from");
        label.setVerticalAlignment(SwingConstants.CENTER);
        panel.add(label);
        return panel;
    }

    /**
     * EFFECTS: Creates a panel with a button and a text field for input
     * @return JPanel
     */
    private JPanel displayBotWindow() {
        JPanel panel = new JPanel();
        text = new JTextField("yyyy-mm-dd",25);
        text.setHorizontalAlignment(SwingConstants.LEFT);
        text.addActionListener(this);
        panel.add(text);
        enter = new JButton("Enter");
        enter.setActionCommand("enter");
        enter.addActionListener(this);
        panel.add(enter);
        return panel;
    }

    /**
     * MODIFIES: Controller
     * EFFECTS: When the button is pressed, the string in the text field gets sent to the controller.
     * Prompts user to re-enter details if error detected
     * @param e button press event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String dateString = text.getText();
        try {
            String year = dateString.substring(0, dateString.indexOf('-'));
            dateString = dateString.substring(dateString.indexOf('-') + 1);
            String month = dateString.substring(0, dateString.indexOf('-'));
            String day = dateString.substring(dateString.indexOf('-') + 1);
            controller.setDate(LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day)));
            controller.closeWindows();
            controller.viewDate();
        } catch (Exception exp) {
            new StartupErrorPopup("Check format! (yyyy-mm-dd)", WindowConstants.DISPOSE_ON_CLOSE);
        }
    }


}
