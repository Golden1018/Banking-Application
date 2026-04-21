package Assignment6Controller;

import Assignment6Model.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author karunmehta
 */
public class CustomerDTO {

    static CustomerDAO cd = new CustomerDAO();

    public CustomerDTO() {}

    public static BankCustomer customerByID(int anId) {
        BankCustomer customer = null;
        try {
            customer = cd.get(anId);
        } catch (SQLException se) {
            System.out.println(se.getMessage());
        }
        if (customer != null)
            System.out.println("Fetched Customer ID " + anId + ": " + customer.toString());
        return customer;
    }

    // Returns all customers from the DB
    public static List<BankCustomer> getAllCustomers() {
        List<BankCustomer> list = new ArrayList<>();
        try {
            list = cd.getAll();
        } catch (SQLException se) {
            System.out.println(se.getMessage());
        }
        return list;
    }

    // Search customers by city
    public static List<BankCustomer> findCustomersByCity(String city) {
        List<BankCustomer> list = new ArrayList<>();
        try {
            list = cd.findByCity(city);
        } catch (SQLException se) {
            System.out.println(se.getMessage());
        }
        return list;
    }

    public static int performUpdate(BankCustomer aCustomer) {
        int updateResult = -1;
        try {
            updateResult = cd.update(aCustomer);
        } catch (SQLException se) {
            System.out.println(se.getMessage());
        }
        if (updateResult > 0)
            System.out.println("Customer Update Successful: " + aCustomer.toString());
        return updateResult;
    }

    public static int performCreate(HashMap hm) {
        int updateResult = -1;
        BankCustomer bc = new BankCustomer();
        bc.setEmail((String) hm.get("email"));
        bc.setLastName((String) hm.get("lname"));
        bc.setFirstName((String) hm.get("firstName"));
        bc.setPhone((String) hm.get("phone"));
        bc.setBirthday((String) hm.get("birthday"));
        try {
            updateResult = cd.create(bc);
        } catch (SQLException se) {
            System.out.println(se.getMessage());
        }
        if (updateResult > 0)
            System.out.println("Customer Create Successful: " + bc.toString());
        return updateResult;
    }

}
