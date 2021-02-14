package model;

import model.exceptions.InsufficientFundsException;
import model.exceptions.TransactionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {
    private Account acc;
    private Account accTwo;
    private final int BALANCE = 1000;
    private final int INCREMENT = 20;

    @BeforeEach
    public void runBefore() {
        acc = new ChequingAccount(BALANCE, SaveType.CHEQUING);
        for (int i = 1; i <= 31; i++) {
            try {
                acc.transact(new Transaction(INCREMENT, LocalDate.of(2020, 10, i), Type.TRANSFER, "test"));
            } catch (TransactionException e) {
                fail();
            }
        }
    }

    @Test
    public void testDeposit() {
        assertEquals(BALANCE + (INCREMENT * 31), acc.getBalance());
        assertEquals(31, acc.getTransactions().size());
    }

    @Test
    public void testGetTransactionsOnDay() {
        List<Transaction> transactionList = acc.getTransactionsOnDay(LocalDate.of(2020, 10, 1));
        assertEquals(1, transactionList.size());
        assertEquals(LocalDate.of(2020, 10, 1), transactionList.get(0).getDate());
    }

    @Test
    public void testGetTransactionsOnDayEmpty() {
        List<Transaction> transactionList = acc.getTransactionsOnDay(LocalDate.of(2020, 11, 1));
        assertEquals(0, transactionList.size());

    }

    @Test
    public void testGetTransactionsFromCategory() {
        try {
            acc.transact(new Transaction(100, LocalDate.of(2020, 11, 1), Type.TRANSFER, "test2"));
        } catch (TransactionException e) {
            fail();
        }
        List<Transaction> transactionList = acc.getTransactionsFromCategory("test");
        assertEquals(31, transactionList.size());
        String str = "test";
        for (int i = 0; i < 31; i++) {
            assertTrue(str.equals(transactionList.get(i).getCategory()));
        }
    }

    @Test
    public void testGetTransactionsFromCategoryEmpty() {
        assertTrue(acc.getTransactionsFromCategory("not the right name").isEmpty());
    }

    @Test
    public void testGetTransactionsFromType() {
        try {
            acc.transact(new Transaction(100, LocalDate.of(2020, 10, 1), Type.CARD, "test"));
        } catch (TransactionException e) {
            fail();
        }
        List<Transaction> transactionList = acc.getTransactionsFromType(Type.TRANSFER);
        assertEquals(31, transactionList.size());
        for (int i = 0; i < 31; i++) {
            assertTrue(transactionList.get(i).getType() == Type.TRANSFER);
        }
    }

    @Test
    public void testGetTransactionsFromTypeEmpty() {
        assertEquals(0, acc.getTransactionsFromType(Type.CARD).size());
    }

    @Test
    public void testConstructorThreeParams() {
        accTwo = new ChequingAccount(BALANCE, new ArrayList<Transaction>(), SaveType.CHEQUING);
        assertEquals(BALANCE, accTwo.getBalance());
        assertTrue(accTwo.getTransactions().isEmpty());
        assertEquals(SaveType.valueOf("CHEQUING"), accTwo.getSaveType());
    }

    @Test
    public void sortTransactions(){
        try {
            acc.transact(new Transaction(INCREMENT, LocalDate.of(2020, 10, 1), Type.TRANSFER, "test"));
            acc.sortTransactions();
        } catch (TransactionException e) {
            fail();
        }
        assertEquals(31, acc.getTransactions().get(31).getDate().getDayOfMonth());
    }


}
