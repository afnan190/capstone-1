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

        boolean running = true;

        while (running) {
            System.out.println("\n--- Home ---");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine().toUpperCase();

            switch (choice) {
                case "D":
                    addDeposit();
                    break;
                case "P":
                    makePayment();
                    break;
                case "L":
                    for (Transaction t : transactions) {
                        System.out.println(t.getDate() + " | " + t.getTime() + " | " + t.getDescription()
                                + " | " + t.getVendor() + " | " + t.getAmount());
                    }
                    break;
                case "X":
                    running = false;
                    break;
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
}