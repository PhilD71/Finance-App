package model;

import model.exceptions.InsufficientFundsException;
import model.exceptions.TransactionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ChequingAccountTest {
    private ChequingAccount acc;
    private ChequingAccount accTwo;
    private static final int BALANCE = 1000;

    @BeforeEach
    public void runBefore() {
        acc = new ChequingAccount(BALANCE, SaveType.CHEQUING);
        for (int i = 1; i <= 10; i++) {
            try {
                acc.transact(new Transaction(-10, LocalDate.of(2020, 10, i), Type.TRANSFER, "test"));
            } catch (TransactionException e) {
                fail();
            }
        }
    }

    @Test
    public void testWithdraw() {
        try {
            acc.transact(new Transaction(-10, LocalDate.of(2020, 10, 11), Type.TRANSFER, "test"));
        } catch (TransactionException e) {
            fail();
        }
        assertEquals(BALANCE - 110, acc.getBalance());
        assertEquals(11,acc.getHistory().getMap().size());


    }

    @Test
    public void testWithdrawTooMuch() {

        assertThrows(InsufficientFundsException.class, () -> { //test using a lambda
            acc.transact(new Transaction(-2000, LocalDate.of(2020, 10, 11), Type.TRANSFER, "test"));
        });

        assertEquals(BALANCE - 100, acc.getBalance());
    }

    @Test
    public void testGetOutgoingTransactions() {
        try {
            acc.transact(new Transaction(-1000, LocalDate.of(2020, 10, 12), Type.TRANSFER, "test"));

        } catch (TransactionException e) {
            //pass
        } finally {
            assertEquals(10, acc.getTransactionsWithDirection(true).size());
            for (int i = 0; i < 10; i++) {
                assertTrue(acc.getTransactionsWithDirection(true).get(i).isNegative());
            }
        }
    }

    @Test
    public void testGetIncomingTransactions() {
        try {
            assertTrue(acc.getTransactionsWithDirection(false).isEmpty());
            acc.transact(new Transaction(1000, LocalDate.of(2020, 10, 12), Type.TRANSFER, "test"));
        } catch (TransactionException e) {
            fail("we do have enough money");
        } finally {
            assertEquals(1, acc.getTransactionsWithDirection(false).size());
        }
    }


    @Test
    public void testConstructorTwoParams() {
        accTwo = new ChequingAccount(BALANCE, new ArrayList<Transaction>(), SaveType.CHEQUING);
        assertEquals(BALANCE, accTwo.getBalance());
        assertTrue(accTwo.getTransactions().isEmpty());
    }

}