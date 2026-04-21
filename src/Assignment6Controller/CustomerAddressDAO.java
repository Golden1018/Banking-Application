package Assignment6Controller;

import Assignment6Model.*;
import java.sql.*;
import java.util.HashMap;
import javax.swing.JOptionPane;

/**
 *
 * @author karunmehta
 */
public class CustomerAddressDAO implements DAOInterface<CustomerAddress> {

    static Connection connection = null;
    PreparedStatement pStatement;
    ResultSet result;

    CustomerAddressDAO() {
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
    public int create(CustomerAddress addr) throws SQLException {
        ensureConnected();
        int res = -1;
        pStatement = connection.prepareStatement(AddressDataConnection.getInsert());
        pStatement.setInt(1, addr.getID());
        pStatement.setInt(2, addr.getStreetNum());
        pStatement.setString(3, addr.getStreetName());
        pStatement.setString(4, addr.getCity());
        pStatement.setString(5, addr.getState());
        pStatement.setInt(6, addr.getZip());
        res = pStatement.executeUpdate();
        return res;
    }

    @Override
    public CustomerAddress get(int custID) throws SQLException {
        ensureConnected();
        pStatement = connection.prepareStatement(AddressDataConnection.getSelect());
        pStatement.setInt(1, custID);
        result = pStatement.executeQuery();

        CustomerAddress addr = null;
        if (result.next()) {
            addr = new CustomerAddress(
                result.getInt("custid"),
                result.getInt("streetnum"),
                result.getString("streetname"),
                result.getString("city"),
                result.getString("state"),
                result.getInt("zip")
            );
        }
        return addr;
    }

    @Override
    public int update(CustomerAddress addr) throws SQLException {
        ensureConnected();
        int res = -1;
        pStatement = connection.prepareStatement(AddressDataConnection.getUpdate());
        pStatement.setInt(1, addr.getStreetNum());
        pStatement.setString(2, addr.getStreetName());
        pStatement.setString(3, addr.getCity());
        pStatement.setString(4, addr.getState());
        pStatement.setInt(5, addr.getZip());
        pStatement.setInt(6, addr.getID());
        res = pStatement.executeUpdate();
        return res;
    }

    @Override
    public int delete(CustomerAddress addr) throws SQLException {
        ensureConnected();
        int res = -1;
        pStatement = connection.prepareStatement(AddressDataConnection.getDelete());
        pStatement.setInt(1, addr.getID());
        res = pStatement.executeUpdate();
        return res;
    }

}
