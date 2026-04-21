package Assignment6View;

import Assignment6Controller.AccountTransactionDTO;
import Assignment6Model.BankAccountTransaction;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;

/**
 * AccountSearch frame - search transactions by account ID.
 *
 * @author karunmehta
 */
public class AccountSearch extends JFrame implements ActionListener {

    private JTextField accountIdField;
    private JButton searchBtn;
    private JButton cancelBtn;

    // Constructor pre-fills the account ID (called from AccountDetail)
    public AccountSearch(int accountId) {
        initComponents();
        accountIdField.setText(String.valueOf(accountId));
    }

    public AccountSearch() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Search Transactions");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Title
        JLabel title = new JLabel("Search Account Transactions", SwingConstants.CENTER);
        title.setFont(new Font("Helvetica Neue", Font.BOLD, 16));
        add(title, BorderLayout.NORTH);

        // Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Account ID:"), gbc);
        gbc.gridx = 1;
        accountIdField = new JTextField(15);
        formPanel.add(accountIdField, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        searchBtn = new JButton("Search");
        cancelBtn = new JButton("Cancel");
        searchBtn.addActionListener(this);
        cancelBtn.addActionListener(this);
        btnPanel.add(searchBtn);
        btnPanel.add(cancelBtn);
        add(btnPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Queries all transactions for the entered account ID and opens TransactionSummary.
     */
    private void findTransactions() {
        String idText = accountIdField.getText().trim();
        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an Account ID.");
            return;
        }
        int acctId;
        try {
            acctId = Integer.parseInt(idText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Account ID must be a number.");
            return;
        }

        List<BankAccountTransaction> transactions = AccountTransactionDTO.findTransactionsByAccountId(acctId);
        if (transactions == null || transactions.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No transactions found for Account ID: " + acctId);
        } else {
            TransactionSummary summary = new TransactionSummary(transactions, acctId, "");
            summary.setVisible(true);
            this.dispose();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchBtn) {
            findTransactions();
        } else if (e.getSource() == cancelBtn) {
            this.dispose();
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new AccountSearch());
    }

}
