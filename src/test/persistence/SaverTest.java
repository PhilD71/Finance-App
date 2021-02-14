package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class SaverTest {
    @Test
    public void testSaverInvalidFile(){
        try{
            Account acc = new ChequingAccount(100,SaveType.CHEQUING);
            Saver saver = new Saver("./data/my\0illegal:filename.json");
            saver.open();
            fail("IOexception was expected");
        }
        catch (IOException e){
            //pass
        }
    }

    @Test
    public void testSaverAccountNoTransactions(){
        String location = "./data/saverTestNoTransactSave.json";
        try{
            SavingAccount acc = new SavingAccount(100, SaveType.SAVING);
            Saver saver = new Saver(location);
            saver.open();
            saver.writeAccount(acc);
            saver.close();

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
    public void testChequingAccountNoTransactions(){
        String location = "./data/saverTestNoTransactCheque.json";
        try{
            ChequingAccount acc = new ChequingAccount(100, SaveType.CHEQUING);
            Saver saver = new Saver(location);
            saver.open();
            saver.writeAccount(acc);
            saver.close();

            Loader loader = new Loader(location);
            acc = (ChequingAccount) loader.read();
            assertEquals(100,acc.getBalance());
        } catch (FileNotFoundException e) {
            fail("something isn't right");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    public void testSaverAccountTransactions(){
        String location = "./data/saverTestTransactSave.json";
        List<Transaction> transactionList = new ArrayList<Transaction>();
        for(int i = 1; i <=10;i++){
            transactionList.add(new Transaction(i, LocalDate.of(2020,10,i), Type.TRANSFER, "test"));
        }

        try{
            SavingAccount acc = new SavingAccount(100, transactionList, SaveType.SAVING);
            Saver saver = new Saver(location);
            saver.open();
            saver.writeAccount(acc);
            saver.close();

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

    @Test
    public void testChequingAccountTransactions(){
        String location = "./data/saverTestTransactCheque.json";
        List<Transaction> transactionList = new ArrayList<Transaction>();
        for(int i = 1; i <=10;i++){
            transactionList.add(new Transaction(i, LocalDate.of(2020,10,i), Type.TRANSFER, "test"));
        }

        try{
            ChequingAccount acc = new ChequingAccount(100, transactionList, SaveType.CHEQUING);
            Saver saver = new Saver(location);
            saver.open();
            saver.writeAccount(acc);
            saver.close();

            Loader loader = new Loader(location);
            acc = (ChequingAccount) loader.read();
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
