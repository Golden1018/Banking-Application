/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Assignment6Model;

import java.time.*;

/**
 *
 * @author karunmehta
 */
public abstract class BankAccount implements Comparable<BankAccount> {
    
    // Create instance variables
    public int accountNum;
    public LocalDate createDate;
    public double balance;
    public String type;
    public int custNum;
    
    static String bankName = "DefaultBankName";
    private int currentNumber = 0;
    
        //1-arg constructor
    BankAccount() {
        accountNum = currentNumber++;
        createDate = LocalDate.now();
        
    }
    
    BankAccount(int actNum) {
        accountNum = actNum;
        createDate = LocalDate.now();
        
    }    
    
    // Setters and getters for the FriscoCommunityBankAccount object
    
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

    public String getCustNum() {
        return type;
    }      

    public void setCustNum(int num) {
        custNum = num;
    }    
    
    @Override
    public int compareTo(BankAccount ba) {
        return this.compareTo(ba);
    }
    
    @Override
    public String toString() {
        
        String str = "[ ";
        
        str += "Num: " + (this.getAccountNum() + " Create Date: " + this.createDate.toString() + " Balance: " + this.getBalance());
        
        return str + " ]";
        
    }
    
    public boolean withdraw(double amnt) {

        boolean result = false;

        if(this.balance > amnt) {
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

        if(acct.balance > amnt) {
            acct.setBalance(balance - amnt);
            this.setBalance(balance + amnt);
            result = true;
        }
        
        return result;
    
    }
    
}

