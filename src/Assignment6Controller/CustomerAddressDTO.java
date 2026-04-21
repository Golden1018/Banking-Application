package Assignment6Controller;

import Assignment6Model.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author karunmehta
 */
public class CustomerAddressDTO {

    static CustomerAddressDAO ad = new CustomerAddressDAO();

    public CustomerAddressDTO() {}

    public static CustomerAddress customerAddressByID(int custId) {
        CustomerAddress addr = null;
        try {
            addr = ad.get(custId);
        } catch (SQLException se) {
            System.out.println(se.getMessage());
        }
        if (addr != null)
            System.out.println("Fetched address for customer " + custId + ": " + addr.toString());
        return addr;
    }

    public static int performUpdate(CustomerAddress addr) {
        int updateResult = -1;
        try {
            updateResult = ad.update(addr);
        } catch (SQLException se) {
            System.out.println(se.getMessage());
        }
        if (updateResult > 0)
            System.out.println("Address Update Successful: " + addr.toString());
        return updateResult;
    }

    public static int performCreate(HashMap hm) {
        int updateResult = -1;
        CustomerAddress ca = new CustomerAddress();
        ca.setID((int) hm.get("custid"));
        ca.setStreetNum((int) hm.get("streetnum"));
        ca.setStreet((String) hm.get("streetname"));
        ca.setCity((String) hm.get("city"));
        ca.setState((String) hm.get("state"));
        ca.setZip((int) hm.get("zip"));
        try {
            updateResult = ad.create(ca);
        } catch (SQLException se) {
            System.out.println(se.getMessage());
        }
        if (updateResult > 0)
            System.out.println("Address Create Successful: " + ca.toString());
        return updateResult;
    }

}
