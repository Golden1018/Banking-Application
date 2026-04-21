package Assignment6Controller;

import Assignment6Model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author karunmehta
 */
public class AccountTransactionDAO implements DAOInterface<BankAccountTransaction> {

    static Connection connection = null;
    PreparedStatement pStatement;
    ResultSet result;

    AccountTransactionDAO() {
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
    public int create(BankAccountTransaction at) throws SQLException {
        ensureConnected();
        int res = -1;
        pStatement = connection.prepareStatement(TransactionDataConnection.getInsert());
        pStatement.setTimestamp(1, at.getCreateDate());
        pStatement.setString(2, at.getType());
        pStatement.setDouble(3, at.getAmount());
        pStatement.setString(4, at.getDescription());
        pStatement.setInt(5, at.id());
        res = pStatement.executeUpdate();
        return res;
    }

    // Retrieve a single transaction by account ID (returns first match)
    @Override
    public BankAccountTransaction get(int acctID) throws SQLException {
        ensureConnected();
        pStatement = connection.prepareStatement(TransactionDataConnection.getSelect());
        pStatement.setInt(1, acctID);
        result = pStatement.executeQuery();

        BankAccountTransaction at = null;
        if (result.next()) {
            at = new BankAccountTransaction();
            at.setCreateDate(result.getTimestamp("create_date"));
            at.setType(result.getString("tran_type"));
            at.setAmount(result.getDouble("amount"));
            at.setDescription(result.getString("summary"));
            at.setID(result.getInt("acct_id"));
        }
        return at;
    }

    // Retrieve ALL transactions for a given account ID
    public List<BankAccountTransaction> getAllByAccountId(int acctID) throws SQLException {
        ensureConnected();
        pStatement = connection.prepareStatement(TransactionDataConnection.getSelect());
        pStatement.setInt(1, acctID);
        result = pStatement.executeQuery();

        List<BankAccountTransaction> list = new ArrayList<>();
        while (result.next()) {
            BankAccountTransaction at = new BankAccountTransaction();
            at.setCreateDate(result.getTimestamp("create_date"));
            at.setType(result.getString("tran_type"));
            at.setAmount(result.getDouble("amount"));
            at.setDescription(result.getString("summary"));
            at.setID(result.getInt("acct_id"));
            list.add(at);
        }
        return list;
    }

    @Override
    public int update(BankAccountTransaction t) throws SQLException {
        System.out.println("Once created, cannot update transaction for an account");
        return -1;
    }

    @Override
    public int delete(BankAccountTransaction t) throws SQLException {
        System.out.println("Once created, cannot delete transaction for an account");
        return -1;
    }

}
