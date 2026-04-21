package Assignment6Controller;

import Assignment6Model.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * DTO (Data Transfer Object) for BankAccount.
 * Modeled after CustomerDTO.
 *
 * @author karunmehta
 */
public class AccountDTO {

    static AccountDAO accountDAO = new AccountDAO();

    public AccountDTO() {}

    public static BankAccount accountByID(int anId) {
        BankAccount account = null;
        try {
            account = accountDAO.get(anId);
        } catch (SQLException se) {
            System.out.println(se.getMessage());
        }
        if (account != null)
            System.out.println("Fetched Account ID " + anId + ": " + account.toString());
        return account;
    }

    // Returns all accounts for a given customer
    public static List<BankAccount> findAccountsByCustomerId(int custId) {
        List<BankAccount> list = new ArrayList<>();
        try {
            list = accountDAO.getByCustomerId(custId);
        } catch (SQLException se) {
            System.out.println(se.getMessage());
        }
        return list;
    }

    public static int performUpdate(BankAccount account) {
        int updateResult = -1;
        try {
            updateResult = accountDAO.update(account);
        } catch (SQLException se) {
            System.out.println(se.getMessage());
        }
        if (updateResult > 0)
            System.out.println("Account Update Successful: " + account.toString());
        return updateResult;
    }

    public static int performCreate(HashMap hm) {
        int updateResult = -1;
        String type = (String) hm.get("type");
        BankAccount act;
        if ("CH".equalsIgnoreCase(type))
            act = new CheckingAccount();
        else
            act = new SavingsAccount();

        if (hm.containsKey("custNum"))
            act.setCustNum((int) hm.get("custNum"));
        if (hm.containsKey("balance"))
            act.setBalance((double) hm.get("balance"));

        try {
            updateResult = accountDAO.create(act);
        } catch (SQLException se) {
            System.out.println(se.getMessage());
        }
        if (updateResult > 0)
            System.out.println("Account Create Successful: " + act.toString());
        return updateResult;
    }

}
