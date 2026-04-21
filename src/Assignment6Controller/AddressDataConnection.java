package Assignment6Controller;

/**
 *
 * @author karunmehta
 */
public class AddressDataConnection extends DataConnection {

    private static final String INSERT_SQL =
        "INSERT INTO customeraddress (custid, streetnum, streetname, city, state, zip) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_SQL_BYID =
        "SELECT * FROM customeraddress WHERE custid = ?";
    private static final String UPDATE_SQL =
        "UPDATE customeraddress SET streetnum = ?, streetname = ?, city = ?, state = ?, zip = ? WHERE custid = ?";
    private static final String DELETE_SQL =
        "DELETE FROM customeraddress WHERE id = ?";

    public AddressDataConnection() {}

    public static String getInsert() { return INSERT_SQL; }
    public static String getUpdate() { return UPDATE_SQL; }
    public static String getSelect() { return SELECT_SQL_BYID; }
    public static String getDelete() { return DELETE_SQL; }

}
