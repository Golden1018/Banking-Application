/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Assignment6Controller;

import Assignment6Controller.*;
import Assignment6Model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author karunmehta
 */
public class AccountDAO implements DAOInterface<BankAccount>{

    static Connection connection = null;
    PreparedStatement pStatement;
    ResultSet result; 
    final String checking = "CH";
    final String saving = "SV";

    AccountDAO() {

        connection = DataConnection.getDBConnection();

    }
               
    // Method to disconnect from the database
    public static void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    // Method to insert a user into the database
    @Override
    public int create(BankAccount act) throws SQLException {
        
        int res = -1;
        pStatement = connection.prepareStatement(AccountDataConnection.getInsert());
        pStatement.setInt(1, act.getAccountNum());
        pStatement.setDouble(2, act.getBalance());
        res = pStatement.executeUpdate();
        disconnect();
        
        return res;
    }

    // Method to retrieve a user from the database by ID
    @Override
    public BankAccount get(int anID) throws SQLException {

        pStatement = connection.prepareStatement(AccountDataConnection.getSelect());
        pStatement.setInt(1,anID);
        result = pStatement.executeQuery();
        
        BankAccount updatedAct = null;
        if (result.next()) {
            if(result.getString("acct_type").equalsIgnoreCase(checking))
                updatedAct = new CheckingAccount( result.getInt("id"));
            else
                updatedAct = new SavingsAccount( result.getInt("id"));
            
            updatedAct.setBalance(result.getFloat("balance"));
            LocalDate ld = createLocalDate(result.getString("create_date"));
            updatedAct.setCreateDate(ld);
        }
      
        return updatedAct;
    }

    private LocalDate createLocalDate(String dateStr) {


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-mm-yyyy");

        //convert String to LocalDate
        LocalDate localDate = LocalDate.parse(dateStr, formatter);
        
        return localDate;
        
    }
    
    // Method to update a user in the database
    @Override
    public int update(BankAccount act) throws SQLException {
        
        int result = -1;
       
        pStatement = connection.prepareStatement(AccountDataConnection.getUpdate());
        pStatement.setString(1, act.getCustNum());
        pStatement.setDouble(1, act.getBalance());

        pStatement.setString(2, act.getCreateDate().toString());
        pStatement.setString(3, act.getType());
        pStatement.setInt(4, act.getAccountNum());
        result = pStatement.executeUpdate();
        
        return result;
    }

    // Method to delete a user from the database
    @Override
    public int delete(BankAccount act) throws SQLException {
        
        int res = -1;
        
        pStatement = connection.prepareStatement(AccountDataConnection.getDelete());
        pStatement.setInt(1, act.getAccountNum());
        res = pStatement.executeUpdate();
        
        return res;
    }  
    
}
