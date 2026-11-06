# Accounting Ledger

A Java command-line app that tracks deposits and payments and saves them to `src/main/resources/transactions.csv`.

## Screens
- **Home:** D) Add Deposit, P) Make Payment (Debit), L) Ledger, X) Exit
- **Ledger:** A) All, D) Deposits, P) Payments, R) Reports, H) Home (newest first)
- **Reports:** 1) Month To Date, 2) Previous Month, 3) Year To Date, 4) Previous Year, 5) Search by Vendor, 0) Back

## How to run
1. Open the project in IntelliJ.
2. Run `Main.java`.
3. Type a letter or number from the menu and press Enter.


## File format
```
date|time|description|vendor|amount
2023-04-15|10:13:25|ergonomic keyboard|Amazon|-89.50
```
Deposits are positive, payments are negative.

![img.png](img.png)

Challenges: What I Initially had was a three class structure

![](/Users/afnanrahman/Desktop/Screenshot 2026-11-06 at 9.53.50 AM.png)





![img_1.png](img_1.png)




![img_2.png](img_2.png)
