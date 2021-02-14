package model;

import model.exceptions.TransactionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SavingAccountTest {
    private SavingAccount acc;
    private SavingAccount accTwo;
    private SavingAccount accThree;
    private List<Transaction> transactionList = new ArrayList<>();
    private final int BALANCE = 1000;

    @BeforeEach
    public void runBefore() {
        acc = new SavingAccount(BALANCE, SaveType.SAVING);
    }


    @Test
    public void testSavingsPredictions() {
        for (int i = 1; i <= 31; i++) {
            try {
                acc.transact(new Transaction(10, LocalDate.of(2020, 10, i), Type.TRANSFER, "test"));
            } catch (TransactionException e) {
                fail();
            }
        }
        assertEquals(1310, acc.getBalance());
        int oneYearPred = 1310 + 3650;
        int fiveYearPred = 1310 + 5 * 3650;
        assertEquals(oneYearPred, acc.savingsPrediction(1));
        assertEquals(fiveYearPred, acc.savingsPrediction(5));
    }

    @Test
    public void testSavingsPredictionsWithOne() {
        try {
            acc.transact(new Transaction(10, LocalDate.of(2020, 10, 1), Type.TRANSFER, "test"));
        } catch(TransactionException e) {
            fail();
        }
        int oneYearPred = 1010 + 3650;
        assertEquals(oneYearPred, acc.savingsPrediction(1));
    }

    @Test
    public void testSavingsPredictionUnsortedList() {
        try {
            acc.transact(new Transaction(10, LocalDate.of(2020, 10, 2), Type.TRANSFER, "test"));
        } catch (TransactionException e){
            fail();
        }
        try {
            acc.transact(new Transaction(10, LocalDate.of(2020, 10, 1), Type.TRANSFER, "test"));
        } catch (TransactionException e) {
            fail();
        }
        try {
            acc.transact(new Transaction(10, LocalDate.of(2020, 10, 3), Type.TRANSFER, "test"));
        } catch (TransactionException e){
            fail();
        }
        int oneYearPred = 1030 + 3650;
        assertEquals(oneYearPred, acc.savingsPrediction(1));
    }

    @Test
    public void testIllegalTransact(){
        try {
            acc.transact(new Transaction(-100, LocalDate.of(2020,10,2),Type.TRANSFER,"test"));
            fail();
        } catch (TransactionException e){
            //pass
        }
    }

    @Test
    public void testConstructorTwoParam() {
        accTwo = new SavingAccount(BALANCE, SaveType.SAVING);
        assertEquals(BALANCE, accTwo.getBalance());
    }

    @Test
    public void testConstructorThreeParam() {
        accThree = new SavingAccount(BALANCE, new ArrayList<Transaction>(), SaveType.SAVING);
        assertEquals(BALANCE, accThree.getBalance());
        assertTrue(accThree.getTransactions().isEmpty());
    }

    @Test
    public void testImplementingHistory(){
        createTransactions();
        SavingAccount accFour = new SavingAccount(BALANCE, transactionList, SaveType.SAVING);
        for (int i = 1; i <= 9; i++) {
            assertEquals(10,accFour.getHistory().getMap().get("2020-10-0" + i));
        }
        assertEquals(9,accFour.getHistory().getMap().size());
    }

    public void createTransactions(){
        for (int i = 1; i <= 9; i++) {
            transactionList.add(new Transaction(10,LocalDate.of(2020,10,i),Type.CARD,"test "+ i));
        }
    }

    @Test
    public void testHistoryGoals(){
        createTransactions();
        SavingAccount accFour = new SavingAccount(0, transactionList, SaveType.SAVING);
        accFour.setGoal(100);
        assertEquals(100,accFour.getGoal());
        assertFalse(accFour.checkGoal());
        try {
            accFour.transact(new Transaction(10,LocalDate.of(2020,10,10),Type.CARD,"test "+ 10));
        } catch (TransactionException e) {
            fail();
        }
        assertTrue(accFour.checkGoal());
    }
}
