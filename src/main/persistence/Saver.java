package persistence;

import model.Account;
import model.ChequingAccount;
import model.SaveType;
import model.SavingAccount;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Saves the account info into a JSon. Json Serialization Demo shown in class was used as a template for this class.
 */
public class Saver {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String fileLocation;

    //EFFECTS: constructs an instance of Saver
    public Saver(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    //MODIFIES: this
    //EFFECTS: opens writer, throws FileNotFoundException if cannot save at this file
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(fileLocation));
    }

    //REQUIRES: Account
    //MODIFIES: this
    //EFFECTS: writes JSON representation of account to file
    public void writeAccount(Account acc) {
        JSONObject jsonAcc = acc.saveToJson();
        writer.print(jsonAcc.toString(TAB));
    }

//    //REQUIRES: Chequing Account
//    //MODIFIES: this
//    //EFFECTS: writes JSON representation of chequing user account to file
//    public void writeChequing(ChequingAccount acc) {
//        JSONObject jsonAcc = acc.saveToJson();
//        writer.print(jsonAcc.toString(TAB));
//    }

    //MODIFIES: this
    //EFFECTS: closes writer
    public void close() {
        writer.close();
    }
}
