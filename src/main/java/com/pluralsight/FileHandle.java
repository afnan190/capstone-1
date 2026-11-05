package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileHandle {
    private static final String FILE_NAME = "src/main/resources/transactions.csv";

    public static ArrayList<Transaction> readTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");

                String date = parts[0];
                String time = parts[1];
                String description = parts[2];
                String vendor = parts[3];
                double amount = Double.parseDouble(parts[4]);

                Transaction transaction = new Transaction(date, time, description, vendor, amount);
                transactions.add(transaction);
            }

            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading the file.");
        }

        return transactions;
    }

    public static void writeTransaction(Transaction t) {
        try {
            FileWriter writer = new FileWriter(FILE_NAME, true);
            writer.write(t.getDate() + "|" + t.getTime() + "|" + t.getDescription() + "|"
                    + t.getVendor() + "|" + t.getAmount() + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving the transaction.");
        }
    }
}