package Assignment6Controller;

/**
 *
 * @author karunmehta
 */
public class AccountDataConnection extends DataConnection {

    private static final String INSERT_SQL =
        "INSERT INTO bankaccount (cust_num, balance, create_date, acct_type) VALUES (?, ?, ?, ?)";
    private static final String SELECT_SQL_BYID =
        "SELECT * FROM bankaccount WHERE acct_num = ?";
    private static final String SELECT_SQL_BY_CUSTOMER =
        "SELECT * FROM bankaccount WHERE cust_num = ?";
    private static final String UPDATE_SQL =
        "UPDATE bankaccount SET cust_num = ?, balance = ?, create_date = ?, acct_type = ? WHERE acct_num = ?";
    private static final String DELETE_SQL =
        "DELETE FROM bankaccount WHERE acct_num = ?";

    public static String getInsert() { return INSERT_SQL; }
    public static String getUpdate() { return UPDATE_SQL; }
    public static String getSelect() { return SELECT_SQL_BYID; }
    public static String getSelectByCustomer() { return SELECT_SQL_BY_CUSTOMER; }
    public static String getDelete() { return DELETE_SQL; }

}
