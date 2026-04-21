package Assignment6Controller;

import Assignment6Model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author karunmehta
 */
public class CustomerDAO implements DAOInterface<BankCustomer> {

    static Connection connection = null;
    PreparedStatement pStatement;
    ResultSet result;

    CustomerDAO() {
        connection = DataConnection.getDBConnection();
    }

    private void ensureConnected() {
        connection = DataConnection.getDBConnection();
    }

    public static void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Override
    public int create(BankCustomer cust) throws SQLException {
        ensureConnected();
        int res = -1;
        pStatement = connection.prepareStatement(CustomerDataConnection.getInsert());
        pStatement.setString(1, cust.getFirstName());
        pStatement.setString(2, cust.getLastName());
        pStatement.setString(3, cust.getEmail());
        pStatement.setString(4, cust.getPhone());
        pStatement.setString(5, cust.getBirthday());
        res = pStatement.executeUpdate();
        return res;
    }

    @Override
    public BankCustomer get(int anID) throws SQLException {
        ensureConnected();
        pStatement = connection.prepareStatement(CustomerDataConnection.getSelect());
        pStatement.setInt(1, anID);
        result = pStatement.executeQuery();

        BankCustomer customer = null;
        if (result.next()) {
            customer = new BankCustomer(result.getInt("id"),
                    result.getString("first_name"), result.getString("last_name"));
            customer.setEmail(result.getString("email"));
            customer.setPhone(result.getString("phone"));
            if (result.getString("birthday") != null)
                customer.setBirthday(result.getString("birthday"));
        }
        return customer;
    }

    // Returns all customers in the DB
    public List<BankCustomer> getAll() throws SQLException {
        ensureConnected();
        pStatement = connection.prepareStatement(CustomerDataConnection.getSelectAll());
        result = pStatement.executeQuery();

        List<BankCustomer> list = new ArrayList<>();
        while (result.next()) {
            BankCustomer c = new BankCustomer(result.getInt("id"),
                    result.getString("first_name"), result.getString("last_name"));
            c.setEmail(result.getString("email"));
            c.setPhone(result.getString("phone"));
            if (result.getString("birthday") != null)
                c.setBirthday(result.getString("birthday"));
            list.add(c);
        }
        return list;
    }

    // Search customers by city (via joined customeraddress table)
    public List<BankCustomer> findByCity(String city) throws SQLException {
        ensureConnected();
        pStatement = connection.prepareStatement(CustomerDataConnection.getSelectByCity());
        pStatement.setString(1, city);
        result = pStatement.executeQuery();

        List<BankCustomer> list = new ArrayList<>();
        while (result.next()) {
            BankCustomer c = new BankCustomer(result.getInt("id"),
                    result.getString("first_name"), result.getString("last_name"));
            c.setEmail(result.getString("email"));
            c.setPhone(result.getString("phone"));
            if (result.getString("birthday") != null)
                c.setBirthday(result.getString("birthday"));
            list.add(c);
        }
        return list;
    }

    @Override
    public int update(BankCustomer cust) throws SQLException {
        ensureConnected();
        int res = -1;
        pStatement = connection.prepareStatement(CustomerDataConnection.getUpdate());
        pStatement.setString(1, cust.getFirstName());
        pStatement.setString(2, cust.getLastName());
        pStatement.setString(3, cust.getEmail());
        pStatement.setString(4, cust.getPhone());
        pStatement.setInt(5, cust.getCustomerNumber());
        res = pStatement.executeUpdate();
        return res;
    }

    @Override
    public int delete(BankCustomer cust) throws SQLException {
        ensureConnected();
        int res = -1;
        pStatement = connection.prepareStatement(CustomerDataConnection.getDelete());
        pStatement.setInt(1, cust.getCustomerNumber());
        res = pStatement.executeUpdate();
        return res;
    }

    public HashMap validateLogin(String id) {
        HashMap hm = null;
        try {
            ensureConnected();
            pStatement = connection.prepareStatement(CustomerDataConnection.getAdmin());
            pStatement.setString(1, id);
            result = pStatement.executeQuery();
            if (result.next()) {
                hm = new HashMap();
                hm.put("ID", result.getString("userid"));
                hm.put("PWD", result.getString("pwd"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage() + " Try again..");
        }
        return hm;
    }

}
