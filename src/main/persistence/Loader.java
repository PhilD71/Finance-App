package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Creates a user instance of account from a saved JSON file. Templated off JsonReader class in JsonSerializationDemo
 */
public class Loader {
    private String fileLocation;

    //EFFECTS: Constructs a Loader to load file
    public Loader(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    //EFFECTS: reads an account from file and returns it
    //throws IOException if an error occurs.
    public Account read() throws IOException {
        String data = loadFile(fileLocation);
        JSONObject dataObject = new JSONObject(data);

        return parseAccount(dataObject);
    }


    //EFFECTS: reads source file as string and returns
    private String loadFile(String fileLocation) throws IOException {
        StringBuilder fileLoader = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(fileLocation), StandardCharsets.UTF_8)) {
            stream.forEach(s -> fileLoader.append(s));
        }

        return fileLoader.toString();
    }

    //EFFECTS: parses an account
    private Account parseAccount(JSONObject object) {
        JSONArray transactionArray = object.getJSONArray("transactions");
        List<Transaction> transactionList = new ArrayList<Transaction>();
        for (Object json : transactionArray) {
            JSONObject nextTransaction = (JSONObject) json;
            transactionList.add(parseTransaction(nextTransaction));
        }
        int balance = object.getInt("balance");
        SaveType type = SaveType.valueOf(object.getString("account type"));
        if (isSaving(object)) {
            return new SavingAccount(balance, transactionList, type);
        }
        return new ChequingAccount(balance, transactionList, type);
    }

    //EFFECTS: Parses a transaction
    private Transaction parseTransaction(JSONObject object) {
        int value = object.getInt("value");
        LocalDate date = stringToDate(object.getString("date"));
        Type type = Type.valueOf(object.getString("type"));
//        boolean outgoing = object.getBoolean("outgoing");
        String category = object.getString("category");
        return new Transaction(value, date, type, category);
    }

    //EFFECTS: checks to see if saved object is a saving or chequing account
    private boolean isSaving(JSONObject jsonObject) {
        SaveType type = SaveType.valueOf(jsonObject.getString("account type"));
        return (type == SaveType.SAVING);
    }

    //EFFECTS: Converts a String format date into a localdate
    public LocalDate stringToDate(String date) {
        String year = date.substring(0, date.indexOf('-'));
        date = date.substring(date.indexOf('-') + 1);
        String month = date.substring(0, date.indexOf('-'));
        String day = date.substring(date.indexOf('-') + 1);
        return LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
    }
}
