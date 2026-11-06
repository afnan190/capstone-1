package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Transaction> transactions = new ArrayList<>();

    public static void main(String[] args) {
        transactions = FileHandle.readTransactions();

        //Need to add comments to explain thinking
        boolean running = true;

        while (running) {
            System.out.println("\n--- Home ---");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine().toUpperCase();

            //Switch case necessary
            switch (choice) {
                case "D":
                    addDeposit();
                    break;
                case "P":
                    makePayment();
                    break;
                case "L":
                    ledgerScreen();
                    break;
                case "X":
                    running = false;
                    break;
               //included if there is an invalid choice listed
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    //Add Deposit
    public static void addDeposit() {
        System.out.print("Description: ");
        String description = scanner.nextLine();

        System.out.print("Vendor: ");
        String vendor = scanner.nextLine();

        double amount = askAmount();

        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        Transaction transaction = new Transaction(date, time, description, vendor, amount);
        transactions.add(transaction);
        FileHandle.writeTransaction(transaction);

        System.out.println("Deposit saved!");
    }

    //Make Payment
    public static void makePayment() {
        System.out.print("Description: ");
        String description = scanner.nextLine();

        System.out.print("Vendor: ");
        String vendor = scanner.nextLine();

        double amount = askAmount();
        amount = amount * -1;

        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        Transaction transaction = new Transaction(date, time, description, vendor, amount);
        transactions.add(transaction);
        FileHandle.writeTransaction(transaction);

        System.out.println("Payment saved!");
    }

    //helper method
    public static double askAmount() {
        while (true) {
            System.out.print("Amount: ");
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number");
            }
        }
    }

    //Ledger Screen
    public static void ledgerScreen() {
        boolean inLedger = true;

        while (inLedger) {
            System.out.println("\n--- Ledger ---");
            System.out.println("A) All");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine().toUpperCase();

            switch (choice) {
                case "A":
                    for (int i = transactions.size() - 1; i >= 0; i--) {
                        printTransaction(transactions.get(i));
                    }
                    break;
                case "D":
                    for (int i = transactions.size() - 1; i >= 0; i--) {
                        if (transactions.get(i).getAmount() > 0) {
                            printTransaction(transactions.get(i));
                        }
                    }
                    break;
                case "P":
                    for (int i = transactions.size() - 1; i >= 0; i--) {
                        if (transactions.get(i).getAmount() < 0) {
                            printTransaction(transactions.get(i));
                        }
                    }
                    break;
                case "R":
                    reportsScreen();
                    break;
                case "H":
                    inLedger = false;
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    //Need to output transaction
    public static void printTransaction(Transaction t) {
        System.out.println(t.getDate() + " | " + t.getTime() + " | " + t.getDescription()
                + " | " + t.getVendor() + " | " + t.getAmount());
    }

    //Report Screen
    public static void reportsScreen() {
        boolean inReports = true;

        while (inReports) {
            System.out.println("\n--- Reports ---");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    monthToDate();
                    break;
                case "2":
                    previousMonth();
                    break;
                case "3":
                    yearNow();
                    break;
                case "4":
                    previousYear();
                    break;
                case "5":
                    searchByVendor();
                    break;
                case "0":
                    inReports = false;
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    public static void monthToDate() {
        LocalDate today = LocalDate.now();

        for (Transaction t : transactions) {
            LocalDate date = LocalDate.parse(t.getDate());
            if (date.getMonthValue() == today.getMonthValue() && date.getYear() == today.getYear()) {
                printTransaction(t);
            }
        }
    }

    public static void previousMonth() {
        LocalDate lastMonth = LocalDate.now().minusMonths(1);

        for (Transaction t : transactions) {
            LocalDate date = LocalDate.parse(t.getDate());
            if (date.getMonthValue() == lastMonth.getMonthValue() && date.getYear() == lastMonth.getYear()) {
                printTransaction(t);
            }
        }
    }

    public static void yearNow() {
        int thisYear = LocalDate.now().getYear();

        for (Transaction t : transactions) {
            LocalDate date = LocalDate.parse(t.getDate());
            if (date.getYear() == thisYear) {
                printTransaction(t);
            }
        }
    }

    public static void previousYear() {
        int lastYear = LocalDate.now().getYear() - 1;

        for (Transaction t : transactions) {
            LocalDate date = LocalDate.parse(t.getDate());
            if (date.getYear() == lastYear) {
                printTransaction(t);
            }
        }
    }

    public static void searchByVendor() {
        System.out.print("Vendor name: ");
        String vendor = scanner.nextLine();

        for (Transaction t : transactions) {
            if (t.getVendor().equals(vendor)) {
                printTransaction(t);
            }
        }
    }
}