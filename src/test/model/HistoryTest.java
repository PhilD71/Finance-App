package model;


import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class HistoryTest {
    private SavingAccount sav = new SavingAccount(100, SaveType.SAVING);
    private History history = sav.getHistory();

    @Test
    public void testTransaction() {
        try {
            sav.transact(new Transaction(100, LocalDate.of(2020, 10, 1), Type.CARD, "test"));
        } catch (Exception e){
            fail("No exception should be thrown!");
        }
        assertEquals(100,history.getMap().get("2020-10-01"));
    }

    @Test
    public void testTwoTransaction(){
        try {
            sav.transact(new Transaction(100, LocalDate.of(2020, 10, 1), Type.CARD, "test"));
            sav.transact(new Transaction(100,LocalDate.of(2020,10,2),Type.CARD,"test"));
        } catch (Exception e){
            fail("No exception should be thrown!");
        }
        assertEquals(100,history.getMap().get("2020-10-02"));
        assertEquals(2,history.getMap().size());
    }

    @Test
    public void testTwoOnSameDay(){
        try {
            sav.transact(new Transaction(100, LocalDate.of(2020, 10, 1), Type.CARD, "test"));
            sav.transact(new Transaction(100,LocalDate.of(2020,10,2),Type.CARD,"test"));
            sav.transact(new Transaction(100,LocalDate.of(2020,10,2),Type.CARD,"test"));
        } catch (Exception e){
            fail("No exception should be thrown!");
        }
        assertEquals(200,history.getMap().get("2020-10-02"));
        assertEquals(2,history.getMap().size());
    }

    @Test
    public void testGoal(){
        history.setGoal(200);
        try{
            assertFalse(history.checkGoal());
            sav.transact(new Transaction(100, LocalDate.of(2020, 10, 1), Type.CARD, "test"));
            assertTrue(history.checkGoal());
        } catch (Exception e){
            fail();
        }
    }
}
