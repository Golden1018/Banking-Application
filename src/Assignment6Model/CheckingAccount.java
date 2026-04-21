package Assignment6Model;

/**
 *
 * @author kmehta
 */
public class CheckingAccount extends BankAccount {

    public CheckingAccount() {
        super();
        this.setType("CH");
    }

    // Constructor used when loading from DB: actNum is the account number
    public CheckingAccount(int actNum) {
        super(actNum);
        this.setType("CH");
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
