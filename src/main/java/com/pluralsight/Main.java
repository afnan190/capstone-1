package com.pluralsight;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Transaction> transactions = FileHandle.readTransactions();

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
                    System.out.println("TODO: add deposit");
                    break;
                case "P":
                    System.out.println("TODO: make payment");
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
}