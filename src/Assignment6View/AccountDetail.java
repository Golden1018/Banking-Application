package Assignment6View;

import Assignment6Controller.AccountDTO;
import Assignment6Model.BankAccount;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * AccountDetail frame - view and update a single BankAccount.
 *
 * @author karunmehta
 */
public class AccountDetail extends JFrame implements ActionListener {

    private BankAccount account;
    private String customerName;

    private JTextField customerField;
    private JTextField acctTypeField;
    private JTextField balanceField;
    private JTextField createDateField;

    private JButton saveBtn;
    private JButton showTransactionsBtn;
    private JButton closeBtn;

    public AccountDetail(BankAccount account, String customerName) {
        this.account = account;
        this.customerName = customerName;
        initComponents();
        populateFields();
    }

    public AccountDetail() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Account Detail");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Title
        JLabel title = new JLabel("Account Detail", SwingConstants.CENTER);
        title.setFont(new Font("Helvetica Neue", Font.BOLD, 16));
        add(title, BorderLayout.NORTH);

        // Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Customer:"), gbc);
        gbc.gridx = 1;
        customerField = new JTextField(18);
        customerField.setEditable(false);
        formPanel.add(customerField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Account Type:"), gbc);
        gbc.gridx = 1;
        acctTypeField = new JTextField(18);
        acctTypeField.setEditable(false);
        formPanel.add(acctTypeField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Balance:"), gbc);
        gbc.gridx = 1;
        balanceField = new JTextField(18);
        formPanel.add(balanceField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Create Date:"), gbc);
        gbc.gridx = 1;
        createDateField = new JTextField(18);
        createDateField.setEditable(false);
        formPanel.add(createDateField, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 10));
        saveBtn = new JButton("Save");
        showTransactionsBtn = new JButton("Show Transactions");
        closeBtn = new JButton("Close");

        saveBtn.addActionListener(this);
        showTransactionsBtn.addActionListener(this);
        closeBtn.addActionListener(this);

        btnPanel.add(saveBtn);
        btnPanel.add(showTransactionsBtn);
        btnPanel.add(closeBtn);
        add(btnPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private void populateFields() {
        if (account == null) return;
        customerField.setText(customerName != null ? customerName : "");
        acctTypeField.setText(account.getType() != null ? account.getType() : "");
        balanceField.setText(String.format("%.2f", account.getBalance()));
        createDateField.setText(account.getCreateDate() != null ? account.getCreateDate().toString() : "");
    }

    /**
     * Saves updated account balance to the database.
     */
    private void saveAccount() {
        if (account == null) return;
        try {
            double newBalance = Double.parseDouble(balanceField.getText().trim());
            account.setBalance(newBalance);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid balance value. Please enter a number.");
            return;
        }

        int result = AccountDTO.performUpdate(account);
        if (result > 0) {
            JOptionPane.showMessageDialog(this, "Account Updated Successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "Update failed. Please check the data and try again.");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveBtn) {
            saveAccount();
        } else if (e.getSource() == showTransactionsBtn) {
            if (account != null) {
                AccountSearch searchFrame = new AccountSearch(account.getAccountNum());
                searchFrame.setVisible(true);
            }
        } else if (e.getSource() == closeBtn) {
            this.dispose();
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new AccountDetail());
    }

}
