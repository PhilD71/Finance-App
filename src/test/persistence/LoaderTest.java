package persistence;

import model.Account;
import model.SavingAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class LoaderTest {
    @Test
    public void testLoaderNoFile() {
        Loader loader = new Loader("./data/noSuchFile.json");
        try {
            Account acc = loader.read();
            fail("IOException expected");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    public void testLoaderEmptyAccount(){
        String location = "./data/saverTestNoTransactSave.json";
        try{
            SavingAccount acc;
            Loader loader = new Loader(location);
            acc = (SavingAccount) loader.read();
            assertEquals(100,acc.getBalance());
        } catch (FileNotFoundException e) {
            fail("something isn't right");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    public void testLoaderAccountTransaction() {
        try{
            String location = "./data/saverTestTransactSave.json";
            SavingAccount acc;
            Loader loader = new Loader(location);
            acc = (SavingAccount) loader.read();
            assertEquals(100,acc.getBalance());
            for(int i =0; i<10;i++) {
                assertEquals(i+1, acc.getTransactions().get(i).getValue());
            }
        } catch (FileNotFoundException e) {
            fail("something isn't right");
        } catch (IOException e) {
            //pass
        }
    }

}
