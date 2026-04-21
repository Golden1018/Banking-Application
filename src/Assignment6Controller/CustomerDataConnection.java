package Assignment6Controller;

/**
 *
 * @author karunmehta
 */
public class CustomerDataConnection extends DataConnection {

    private static final String INSERT_SQL =
        "INSERT INTO Customer (first_name, last_name, email, phone, birthday) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_SQL_BYID =
        "SELECT * FROM Customer WHERE id = ?";
    private static final String SELECT_SQL_ALL =
        "SELECT * FROM Customer";
    private static final String SELECT_SQL_BY_CITY =
        "SELECT c.* FROM Customer c JOIN customeraddress ca ON c.id = ca.custid WHERE ca.city = ?";
    private static final String UPDATE_SQL =
        "UPDATE Customer SET first_name = ?, last_name = ?, email = ?, phone = ? WHERE id = ?";
    private static final String DELETE_SQL =
        "DELETE FROM Customer WHERE id = ?";
    private static final String SELECT_ADMIN =
        "SELECT * FROM admin WHERE userid = ?";

    public CustomerDataConnection() {}

    public static String getInsert() { return INSERT_SQL; }
    public static String getUpdate() { return UPDATE_SQL; }
    public static String getSelect() { return SELECT_SQL_BYID; }
    public static String getSelectAll() { return SELECT_SQL_ALL; }
    public static String getSelectByCity() { return SELECT_SQL_BY_CITY; }
    public static String getDelete() { return DELETE_SQL; }
    public static String getAdmin() { return SELECT_ADMIN; }

}
