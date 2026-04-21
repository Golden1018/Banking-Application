# Banking Application

A Java desktop banking application developed for CS413 Assignment 6. Manages customers, bank accounts, and transactions through a Swing GUI, backed by a MySQL database.

## Features

- Customer management (create, search, view details)
- Bank account management (checking and savings accounts)
- Account transactions and transaction history
- Customer address management
- Admin login

## Project Structure

```
src/
├── Assignment6Model/       # Data models
│   ├── BankAccount.java
│   ├── CheckingAccount.java
│   ├── SavingsAccount.java
│   ├── BankCustomer.java
│   ├── CustomerAddress.java
│   └── BankAccountTransaction.java
│
├── Assignment6Controller/  # Database access (DAO pattern)
│   ├── DataConnection.java
│   ├── AccountDAO.java
│   ├── CustomerDAO.java
│   ├── AdminDAO.java
│   ├── AccountTransactionDAO.java
│   └── CustomerAddressDAO.java
│
└── Assignment6View/        # Swing GUI screens
    ├── BankApplicationMain.java
    ├── UserLogin.java
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

## Technologies

- Java 17 with Swing
- JDBC + MySQL (`mysql-connector-j-9.6.0.jar`)
- MVC architecture

## Prerequisites

- JDK 17+
- MySQL running on `localhost:3306`
- A MySQL database named `CS413`

## Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/Golden1018/Banking-Application.git
   ```

2. Create the database:
   ```sql
   CREATE DATABASE CS413;
   ```

3. Update credentials in `src/Assignment6Controller/DataConnection.java` if your MySQL username/password differ from the defaults (`root` / empty password).

## Running in VS Code

1. Install the [Extension Pack for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack).
2. Open the project folder in VS Code.
3. Press **Run** (top-right) or **F5** — VS Code picks up `.vscode/launch.json` which includes the MySQL driver on the classpath automatically.

## Course

CS413 — Object-Oriented Programming / Database Application Development
