/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assignment6Model;

import java.util.*;

/**
 *
 * @author kmehta
 */
public class BankCustomer implements Comparable<BankCustomer> {

    // Define static variables to be used in the customer processing 
    private static ArrayList<BankCustomer> customers = new ArrayList<>();
    
    private static int custCount = 0;
    String firstName;
    String lastName;
    String phone;
    String email;
    String birthday;
    int customerNumber;
    List accounts = new ArrayList<BankAccount>();
    CustomerAddress address;

    public BankCustomer() {}
    
    //2-Arg Constructor that creates a new instance, adds self to customer collection and increments the static counter
    public BankCustomer(String fName, String lName) {
        firstName = fName;
        lastName = lName;
        customers.add(this);
        customerNumber = ++custCount;
    }

    public BankCustomer(int num, String fName, String lName) {
        firstName = fName;
        lastName = lName;
        customers.add(this);
        customerNumber = num;
    }    
    
    //All setters and getters for cusotmer 

    public String getEmail() {
        return email;
    }
    
    public void setEmail(String em) {
        email = em;
    }    
    
    public void setAddress(CustomerAddress anAddress) {
        address = anAddress;
    }
    
    
    public String getName() {
        return (firstName + " " + lastName);
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setFirstName(String nm) {
        firstName = nm;
    }
    
    public void setLastName(String nm) {
        lastName = nm;
    }

    public List getAccounts() {
        return accounts;
    }
    
    public CustomerAddress getAddress() {
        return address;
    }
    
    public void setAccounts(List acts) {
        accounts = acts;
    }    
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String ph) {
        phone = ph;
    }        

    public String getBirthday() {
        return birthday;
    }
    
    public void setBirthday(String bd) {
        birthday = bd;
    }
    
    public void addAccount(BankAccount anAct) {
        accounts.add(anAct);
    }

    public static ArrayList<BankCustomer> getCustomers() {
        //Will add code later to get all bank customers from the Database
        // in collaboration with Customer DTO and DAO objects
        return customers;
    }
    
    public static int getCustCount() {
        return custCount;
    }
    
    public int getCustomerNumber() {
        return customerNumber;
    }
    
    public int compareTo(BankCustomer cust) {

        int num1 = this.getCustomerNumber();
        int num2 = cust.getCustomerNumber();
        
        if(num1 == num2) return 0;
        else if(num1 > num2) return 1;
        else return -1;

    }
    
    public static BankCustomerBuilder builder() {
        return new BankCustomerBuilder();
    }
    
    
    public String toString() {
        
       String str = "\n";
       
       str += this.getCustomerNumber();
       str += " : " + this.getName();
       str += " : " + this.getAddress();
       System.out.println(str);
       
       return str;
       
    }
  
}
