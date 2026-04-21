package Assignment6Model;

import java.time.*;

/**
 *
 * @author karunmehta
 */
public abstract class BankAccount implements Comparable<BankAccount> {

    public int accountNum;
    public LocalDate createDate;
    public double balance;
    public String type;
    public int custNum;

    static String bankName = "DefaultBankName";
    private int currentNumber = 0;

    BankAccount() {
        accountNum = currentNumber++;
        createDate = LocalDate.now();
    }

    BankAccount(int actNum) {
        accountNum = actNum;
        createDate = LocalDate.now();
    }

    public java.time.LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(java.time.LocalDate aDate) {
        createDate = aDate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double aBalance) {
        balance = aBalance;
    }

    public String getType() {
        return type;
    }

    public void setType(String tp) {
        type = tp;
    }

    public int getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(int num) {
        accountNum = num;
    }

    public String getName() {
        return bankName;
    }

    public void setBankName(String name) {
        bankName = name;
    }

    // Returns custNum as String
    public String getCustNum() {
        return String.valueOf(custNum);
    }

    // Returns custNum as int
    public int getCustNumInt() {
        return custNum;
    }

    public void setCustNum(int num) {
        custNum = num;
    }

    @Override
    public int compareTo(BankAccount ba) {
        if (this.accountNum == ba.accountNum) return 0;
        return this.accountNum > ba.accountNum ? 1 : -1;
    }

    @Override
    public String toString() {
        String str = "[ ";
        str += "Num: " + this.getAccountNum() + " Type: " + this.getType()
             + " Balance: " + this.getBalance()
             + " Create Date: " + (this.createDate != null ? this.createDate.toString() : "N/A");
        return str + " ]";
    }

    public boolean withdraw(double amnt) {
        boolean result = false;
        if (this.balance > amnt) {
            balance -= amnt;
            result = true;
        }
        return result;
    }

    public boolean deposit(double amnt) {
        this.setBalance(balance + amnt);
        return true;
    }

    public boolean transfer(BankAccount acct, double amnt) {
        boolean result = false;
        if (this.balance > amnt) {
            this.setBalance(this.balance - amnt);
            acct.setBalance(acct.getBalance() + amnt);
            result = true;
        }
        return result;
    }

}
