package Assignment6Controller;

import Assignment6Model.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author karunmehta
 */
public class AccountDAO implements DAOInterface<BankAccount> {

    static Connection connection = null;
    PreparedStatement pStatement;
    ResultSet result;
    final String checking = "CH";
    final String saving = "SV";

    AccountDAO() {
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
    public int create(BankAccount act) throws SQLException {
        ensureConnected();
        int res = -1;
        pStatement = connection.prepareStatement(AccountDataConnection.getInsert());
        pStatement.setInt(1, act.getCustNumInt());
        pStatement.setDouble(2, act.getBalance());
        pStatement.setString(3, act.getCreateDate() != null ? act.getCreateDate().toString() : LocalDate.now().toString());
        pStatement.setString(4, act.getType());
        res = pStatement.executeUpdate();
        return res;
    }

    @Override
    public BankAccount get(int anID) throws SQLException {
        ensureConnected();
        pStatement = connection.prepareStatement(AccountDataConnection.getSelect());
        pStatement.setInt(1, anID);
        result = pStatement.executeQuery();

        BankAccount account = null;
        if (result.next()) {
            String acctType = result.getString("acct_type");
            if (checking.equalsIgnoreCase(acctType))
                account = new CheckingAccount(result.getInt("acct_num"));
            else
                account = new SavingsAccount(result.getInt("acct_num"));

            account.setBalance(result.getDouble("balance"));
            account.setCustNum(result.getInt("cust_num"));
            String dateStr = result.getString("create_date");
            if (dateStr != null) {
                account.setCreateDate(parseDate(dateStr));
            }
        }
        return account;
    }

    // Retrieve all accounts belonging to a specific customer
    public List<BankAccount> getByCustomerId(int custId) throws SQLException {
        ensureConnected();
        pStatement = connection.prepareStatement(AccountDataConnection.getSelectByCustomer());
        pStatement.setInt(1, custId);
        result = pStatement.executeQuery();

        List<BankAccount> list = new ArrayList<>();
        while (result.next()) {
            String acctType = result.getString("acct_type");
            BankAccount account;
            if (checking.equalsIgnoreCase(acctType))
                account = new CheckingAccount(result.getInt("acct_num"));
            else
                account = new SavingsAccount(result.getInt("acct_num"));

            account.setBalance(result.getDouble("balance"));
            account.setCustNum(result.getInt("cust_num"));
            String dateStr = result.getString("create_date");
            if (dateStr != null) {
                account.setCreateDate(parseDate(dateStr));
            }
            list.add(account);
        }
        return list;
    }

    @Override
    public int update(BankAccount act) throws SQLException {
        ensureConnected();
        int res = -1;
        pStatement = connection.prepareStatement(AccountDataConnection.getUpdate());
        pStatement.setInt(1, act.getCustNumInt());
        pStatement.setDouble(2, act.getBalance());
        pStatement.setString(3, act.getCreateDate() != null ? act.getCreateDate().toString() : LocalDate.now().toString());
        pStatement.setString(4, act.getType());
        pStatement.setInt(5, act.getAccountNum());
        res = pStatement.executeUpdate();
        return res;
    }

    @Override
    public int delete(BankAccount act) throws SQLException {
        ensureConnected();
        int res = -1;
        pStatement = connection.prepareStatement(AccountDataConnection.getDelete());
        pStatement.setInt(1, act.getAccountNum());
        res = pStatement.executeUpdate();
        return res;
    }

    private LocalDate parseDate(String dateStr) {
        // Try multiple date formats
        String[] patterns = {"yyyy-MM-dd", "MM-dd-yyyy", "dd-MM-yyyy"};
        for (String pattern : patterns) {
            try {
                return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
            } catch (DateTimeParseException e) {
                // try next pattern
            }
        }
        return LocalDate.now();
    }

}
