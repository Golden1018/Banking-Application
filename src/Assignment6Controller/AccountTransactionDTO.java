package Assignment6Controller;

import Assignment6Model.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author karunmehta
 */
public class AccountTransactionDTO {

    static AccountTransactionDAO ato = new AccountTransactionDAO();

    public AccountTransactionDTO() {}

    public static BankAccountTransaction transactionByAccountID(int anId) {
        BankAccountTransaction tran = null;
        try {
            tran = ato.get(anId);
        } catch (SQLException se) {
            System.out.println(se.getMessage());
        }
        if (tran != null)
            System.out.println("Fetched transaction for account " + anId);
        return tran;
    }

    // Returns all transactions for a given account ID
    public static List<BankAccountTransaction> findTransactionsByAccountId(int acctId) {
        List<BankAccountTransaction> list = new ArrayList<>();
        try {
            list = ato.getAllByAccountId(acctId);
        } catch (SQLException se) {
            System.out.println(se.getMessage());
        }
        return list;
    }

    public static int performUpdate(BankAccountTransaction at) {
        int updateResult = -1;
        try {
            updateResult = ato.update(at);
        } catch (SQLException se) {
            System.out.println(se.getMessage());
        }
        if (updateResult > 0)
            System.out.println("Transaction Update Successful");
        return updateResult;
    }

    public static int performCreate(HashMap hm) {
        int updateResult = -1;
        BankAccountTransaction at = new BankAccountTransaction();
        at.setCreateDate((Timestamp) hm.get("createdate"));
        at.setAmount((double) hm.get("amount"));
        at.setDescription((String) hm.get("summary"));
        at.setID((int) hm.get("id"));
        at.setType((String) hm.get("type"));
        try {
            updateResult = ato.create(at);
        } catch (SQLException se) {
            System.out.println(se.getMessage());
        }
        if (updateResult > 0)
            System.out.println("Transaction Create Successful");
        return updateResult;
    }

}
