package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {
    public Transaction transaction;
    public Transaction transactionTwo;
    @BeforeEach
    public void runBefore(){
        transaction = new Transaction(100, LocalDate.of(2020,10,1),Type.TRANSFER, "test");
        transactionTwo = new Transaction(-100, LocalDate.of(2020,10,1),Type.TRANSFER, "test");
    }

    @Test
    public void testConstructor(){
        assertEquals(100,transaction.getValue());
        assertEquals(LocalDate.of(2020,10,1),transaction.getDate());
        assertEquals(Type.TRANSFER,transaction.getType());
        assertFalse(transaction.isNegative());
        assertEquals("test",transaction.getCategory());
    }

    @Test public void testTransactionToString(){
        String expected = "Amount: " + transaction.getValue() + " | Date: 2020-10-01"
                + " | Type: " + transaction.getType() + " | Direction: incoming" + " | Category: test";
        String expectedTwo =  "Amount: " + transactionTwo.getValue() + " | Date: 2020-10-01"
                + " | Type: " + transactionTwo.getType() + " | Direction: outgoing" + " | Category: test";
        assertEquals(expected, transaction.transactionToString());
        assertEquals(expectedTwo,transactionTwo.transactionToString());

    }
}
