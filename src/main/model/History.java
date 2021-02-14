package model;

import java.util.HashMap;
import java.util.Map;

/**
 * Keeps a record of incoming money and the date
 */
public class History {
    private Account acc;
    private HashMap<String, Integer> hashMap;
    private int goal;

    /**
     * MODIFIES: This
     * EFFECTS: creates a History object
     * @param acc Account
     */
    public History(Account acc) {
        this.acc = acc;
        hashMap = new HashMap<>();
        goal = 0;
    }

    /**
     * MODIFIES: This
     * EFFECTS: adds transaction to map with date and value
     *
     * @param t transaction
     */
    public void addTransaction(Transaction t) {
        int value = t.getValue();
        String date = t.getDate().toString();
        if (hashMap.containsKey(date)) {
            value += hashMap.get(date);
            hashMap.remove(date);
        }
        hashMap.put(date, value);
    }

    public int getGoal() {
        return goal;
    }

    /**
     * REQUIRES: goal > 0
     * MODIFIES: This
     * EFFECTS: Sets saving goal for this account
     * @param goal integer greater than 0
     */
    public void setGoal(int goal) {
        this.goal = goal;
    }

    public boolean checkGoal() {
        int sum = 0;
        for (Map.Entry<String, Integer> map : hashMap.entrySet()) {
            sum += map.getValue();
        }
        return sum + acc.getInitialBalance() >= goal;
    }


    public HashMap getMap() {
        return hashMap;
    }
}
