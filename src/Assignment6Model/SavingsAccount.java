package Assignment6Model;

/**
 *
 * @author kmehta
 */
public class SavingsAccount extends BankAccount {

    public SavingsAccount() {
        super();
        balance = 2000;
        this.setType("SV");
    }

    // Constructor used when loading from DB: actNum is the account number
    public SavingsAccount(int actNum) {
        super(actNum);
        this.setType("SV");
    }

    @Override
    public int compareTo(BankAccount ba) {
        if (ba.getCreateDate().compareTo(this.getCreateDate()) > 0)
            return -1;
        else if (ba.getCreateDate().compareTo(this.getCreateDate()) < 0)
            return 1;
        else return 0;
    }

}
