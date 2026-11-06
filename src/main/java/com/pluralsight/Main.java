package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Transaction> transactions = new ArrayList<>();

    //reads every line in the csv and turns it into a Transaction
    public static ArrayList<Transaction> readTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<>();

        //Descriptions
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/transactions.csv"));

            //skip header line (usually header is there)
            reader.readLine();
            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split("\\|");

                if (parts.length == 5) {
                    String date = parts[0];
                    String time = parts[1];
                    String description = parts[2];
                    String vendor = parts[3];
                    double amount = Double.parseDouble(parts[4]); //text to number

                    Transaction transaction = new Transaction(date, time, description, vendor, amount);
                    transactions.add(transaction);
                }
            }

            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading the file.");
        }

        return transactions;
    }

    // adds one transaction to the end of the csv
    public static void writeTransaction(Transaction t) {
        try {
            //to ensure we don't erase old entries: append = true
            FileWriter writer = new FileWriter("src/main/resources/transactions.csv", true);
            writer.write(t.getDate() + "|" + t.getTime() + "|" + t.getDescription() + "|"
                    + t.getVendor() + "|" + t.getAmount() + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving the transaction.");
        }
    }

    public static void main(String[] args) {
        // load everything from the file when the app starts
        transactions = readTransactions();

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

        // today's date and time so the user doesn't have to type them
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        //cool goal would be to schedule a deposit

        Transaction transaction = new Transaction(date, time, description, vendor, amount);
        transactions.add(transaction); //shows up in the ledger
        writeTransaction(transaction); //saved for next time

        System.out.println("Deposit saved!");
    }

    //Make Payment (same as add deposit but the amount is saved negative
    public static void makePayment() {
        System.out.print("Description: ");
        String description = scanner.nextLine();

        System.out.print("Vendor: ");
        String vendor = scanner.nextLine();

        double amount = askAmount();
        amount = amount * -1;

        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        //cool goal would be to schedule a payment

        Transaction transaction = new Transaction(date, time, description, vendor, amount);
        transactions.add(transaction);
        writeTransaction(transaction);

        System.out.println("Payment saved!");
    }

    //helper method
    //included because of use case 'eleven'
    //keeps asking until the user types a real number (ex. eleven)
    public static double askAmount() {
        while (true) {
            System.out.print("Amount: ");
            try {
                return Double.parseDouble(scanner.nextLine()); //return leaves the loop
            } catch (Exception e) {
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

            // new transactions are added to the end of the list,
            // so I loop backwards to show the newest first
            switch (choice) {
                case "A":
                    for (int i = transactions.size() - 1; i >= 0; i--) {
                        printTransaction(transactions.get(i));
                    }
                    break;
                case "D":
                    //deposits are positive amounts
                    for (int i = transactions.size() - 1; i >= 0; i--) {
                        if (transactions.get(i).getAmount() > 0) {
                            printTransaction(transactions.get(i));
                        }
                    }
                    break;
                case "P":
                    //payments are negative amounts
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
                    inLedger = false; //back to home
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    //Need to output transaction
    // prints one transaction, used by the ledger and all the reports
    public static void printTransaction(Transaction t) {
        System.out.println(t.getDate() + " | " + t.getTime() + " | " + t.getDescription()
                + " | " + t.getVendor() + " | " + t.getAmount());
    }

    //Report Screen
    // 0 goes back to the ledger
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

    // this month: same month and same year as today
    public static void monthToDate() {
        LocalDate today = LocalDate.now();

        for (Transaction t : transactions) {

            // date is a String, so turn it into a LocalDate
            LocalDate date = LocalDate.parse(t.getDate());
            if (date.getMonthValue() == today.getMonthValue() && date.getYear() == today.getYear()) {
                printTransaction(t);
            }
        }
    }

    // last month: minusMonths(1) January going back to December
    public static void previousMonth() {
        LocalDate lastMonth = LocalDate.now().minusMonths(1);

        for (Transaction t : transactions) {
            LocalDate date = LocalDate.parse(t.getDate());
            if (date.getMonthValue() == lastMonth.getMonthValue() && date.getYear() == lastMonth.getYear()) {
                printTransaction(t);
            }
        }
    }

    // everything from this year
    public static void yearNow() {
        int thisYear = LocalDate.now().getYear();

        for (Transaction t : transactions) {
            LocalDate date = LocalDate.parse(t.getDate());
            if (date.getYear() == thisYear) {
                printTransaction(t);
            }
        }
    }

    // everything from last year
    public static void previousYear() {
        int lastYear = LocalDate.now().getYear() - 1;

        for (Transaction t : transactions) {
            LocalDate date = LocalDate.parse(t.getDate());
            if (date.getYear() == lastYear) {
                printTransaction(t);
            }
        }
    }

    // shows every transaction from the vendor the user types
    //goal would be search by specific day/time
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