//package ui;
//
//import model.SavingGoal;
//
//import java.time.LocalDate;
//import java.util.Scanner;
//
//This class is not used yet. Written for future functionality.
//
//public class SavingGoalCreator {
//    private Scanner scanner = new Scanner(System.in);
//
//    public SavingGoalCreator() {
//    }
//
//    public SavingGoal createSavingGoal() {
//        System.out.println("What amount would you like to save?");
//        int savingAmount = scanner.nextInt();
//        System.out.println("When would you like to save it by?");
//        System.out.println("Year:");
//        int year = scanner.nextInt();
//        System.out.println("Month:");
//        int month = scanner.nextInt();
//        System.out.println("Day:");
//        int day = scanner.nextInt();
//        LocalDate date = LocalDate.of(year,month,day);
//        return new SavingGoal(savingAmount,date);
//    }
//
//
//}
