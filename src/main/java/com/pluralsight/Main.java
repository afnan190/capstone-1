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

    public static void addDeposit() {
        System.out.print("Description: ");
        String description = scanner.nextLine();

        System.out.print("Vendor: ");
        String vendor = scanner.nextLine();

        System.out.print("Amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        String date = LocalDate.now().toString();
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        Transaction transaction = new Transaction(date, time, description, vendor, amount);
        transactions.add(transaction);
        FileHandle.writeTransaction(transaction);

        System.out.println("Deposit saved!");
    }

    public static void makePayment() {
        System.out.print("Description: ");
        String description = scanner.nextLine();

        System.out.print("Vendor: ");
        String vendor = scanner.nextLine();

        System.out.print("Amount: ");
        double amount = Double.parseDouble(scanner.nextLine());
        amount = amount * -1;

        String date = LocalDate.now().toString();
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        Transaction transaction = new Transaction(date, time, description, vendor, amount);
        transactions.add(transaction);
        FileHandle.writeTransaction(transaction);

        System.out.println("Payment saved!");
    }

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

    public static void printTransaction(Transaction t) {
        System.out.println(t.getDate() + " | " + t.getTime() + " | " + t.getDescription()
                + " | " + t.getVendor() + " | " + t.getAmount());
    }

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
        for {
            if {

            }
        }
    }

    public static void previousMonth() {
        for {
            if {
            }
        }
    }

    public static void yearNow() {
        for {
            if {
            }
        }
    }

    public static void previousYear() {
        for  {
            if {

            }
        }
    }

    public static void searchByVendor() {
        System.out.print("Vendor name: ");
        for {
            if  {
            }
        }
    }
}