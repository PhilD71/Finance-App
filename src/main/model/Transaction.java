package model;

import org.json.JSONObject;
import persistence.Saveable;

import java.time.LocalDate;

/**
 * A transaction that has a value, date, payment type, direction and category
 */
public class Transaction implements Saveable {
    private int value;
    private LocalDate date;
    private Type type;
    private String category;

    //TODO: Make category an ENUM

    /**
     * MODIFIES: this
     * EFFECTS: creates a transaction with value, date, type, outgoing and category
     *
     * @param value    value of transaction
     * @param date     date of transaction
     * @param type     type of transaction
     * @param category category of transaction
     */
    public Transaction(int value, LocalDate date, Type type, String category) {
        this.value = value;
        this.date = date;
        this.type = type;
        this.category = category;
    }

    public int getValue() {
        return value;
    }

    public LocalDate getDate() {
        return date;
    }

    public Type getType() {
        return type;
    }

    public boolean isNegative() {
        return value < 0;
    }

    public String getCategory() {
        return category;
    }

    /**
     * MODIFIES: json object
     * EFFECTS: Creates a json object of each transaction
     *
     * @return json object of transaction with fields value date type outgoing and category
     */
    @Override
    public JSONObject saveToJson() {
        JSONObject transactionJson = new JSONObject();
        transactionJson.put("value", value);
        transactionJson.put("date", date.toString());
        transactionJson.put("type", type.toString());
        transactionJson.put("category", category);
        return transactionJson;
    }

    /**
     * EFFECTS: Returns the transaction as a string
     * @return string with Transaction details
     */
    public String transactionToString() {
        String outgoing;
        if (isNegative()) {
            outgoing = "outgoing";
        } else {
            outgoing = "incoming";
        }
        return ("Amount: " + value + " | Date: " + date.toString()
                + " | Type: " + type + " | Direction: " + outgoing + " | Category: " + category);
    }
}
