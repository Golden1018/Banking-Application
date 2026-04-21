# Banking Application

A Java-based banking application developed for CS413 Assignment 6. This desktop application allows users to manage bank accounts, customers, and transactions through a GUI interface.

## Features

- Customer management (create, search, view details)
- Bank account management (checking and savings accounts)
- Account transactions and transaction history
- Customer address management
- Admin functionality

## Project Structure

```
src/
├── Assignment6Model/       # Data models
│   ├── BankAccount.java
│   ├── CheckingAccount.java
│   ├── SavingsAccount.java
│   └── CustomerAddress.java
│
├── Assignment6Controller/  # Business logic & database access
│   ├── AccountDAO.java
│   ├── CustomerDAO.java
│   ├── AdminDAO.java
│   ├── AccountTransactionDAO.java
│   ├── CustomerAddressDAO.java
│   └── DataConnection.java
│
└── Assignment6View/        # GUI screens
    ├── HomePage.java
    ├── CustomerFrame.java
    ├── CustomerList.java
    ├── CustomerDetail.java
    ├── CustomerSearch.java
    ├── AccountList.java
    ├── AccountDetail.java
    ├── AccountSearch.java
    ├── TransactionSummary.java
    └── CustomerAddressFrame.java
```

## Technologies Used

- Java (Swing for GUI)
- JDBC (database connectivity)
- MVC Architecture (Model-View-Controller)
- NetBeans IDE

## How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/Golden1018/Banking-Application.git
   ```

2. Open the project in NetBeans IDE

3. Set up your database and update the connection settings in `DataConnection.java`

4. Build and run the project

## Course

CS413 — Object-Oriented Programming / Database Application Development
