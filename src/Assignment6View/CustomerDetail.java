package Assignment6View;

import Assignment6Controller.AccountDTO;
import Assignment6Controller.AccountTransactionDTO;
import Assignment6Controller.CustomerAddressDTO;
import Assignment6Controller.CustomerDTO;
import Assignment6Model.BankAccount;
import Assignment6Model.BankCustomer;
import Assignment6Model.CustomerAddress;
import java.awt.*;
import java.awt.event.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.swing.*;

/**
 * CustomerDetail frame - view and update a single BankCustomer.
 *
 * @author karunmehta
 */
public class CustomerDetail extends JFrame implements ActionListener {

    private BankCustomer customer;

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField addressField;

    private JButton saveBtn;
    private JButton updateAddressBtn;
    private JButton addTransactionBtn;
    private JButton closeBtn;

    public CustomerDetail(BankCustomer customer) {
        this.customer = customer;
        initComponents();
        populateFields();
    }

    public CustomerDetail() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Customer Detail");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Title
        JLabel title = new JLabel("Customer Detail", SwingConstants.CENTER);
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
        formPanel.add(new JLabel("First Name:"), gbc);
        gbc.gridx = 1;
        firstNameField = new JTextField(18);
        formPanel.add(firstNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Last Name:"), gbc);
        gbc.gridx = 1;
        lastNameField = new JTextField(18);
        formPanel.add(lastNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        emailField = new JTextField(18);
        formPanel.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        phoneField = new JTextField(18);
        formPanel.add(phoneField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Address:"), gbc);
        gbc.gridx = 1;
        addressField = new JTextField(18);
        addressField.setEditable(false);
        formPanel.add(addressField, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        saveBtn = new JButton("Save");
        updateAddressBtn = new JButton("Update Address");
        addTransactionBtn = new JButton("Add Transaction");
        closeBtn = new JButton("Close");

        saveBtn.addActionListener(this);
        updateAddressBtn.addActionListener(this);
        addTransactionBtn.addActionListener(this);
        closeBtn.addActionListener(this);

        btnPanel.add(saveBtn);
        btnPanel.add(updateAddressBtn);
        btnPanel.add(addTransactionBtn);
        btnPanel.add(closeBtn);
        add(btnPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private void populateFields() {
        if (customer == null) return;
        firstNameField.setText(customer.getFirstName() != null ? customer.getFirstName() : "");
        lastNameField.setText(customer.getLastName() != null ? customer.getLastName() : "");
        emailField.setText(customer.getEmail() != null ? customer.getEmail() : "");
        phoneField.setText(customer.getPhone() != null ? customer.getPhone() : "");

        // Load address from DB if not already on the customer object
        CustomerAddress addr = customer.getAddress();
        if (addr == null) {
            addr = CustomerAddressDTO.customerAddressByID(customer.getCustomerNumber());
            if (addr != null) customer.setAddress(addr);
        }
        addressField.setText(addr != null ? addr.toString() : "No address on file");
    }

    private void saveCustomer() {
        if (customer == null) return;
        customer.setFirstName(firstNameField.getText().trim());
        customer.setLastName(lastNameField.getText().trim());
        customer.setEmail(emailField.getText().trim());
        customer.setPhone(phoneField.getText().trim());

        int result = CustomerDTO.performUpdate(customer);
        if (result > 0) {
            JOptionPane.showMessageDialog(this, "Customer Detail Updated Successfully..");
        } else {
            JOptionPane.showMessageDialog(this, "Update failed. Please check the data and try again.");
        }
    }

    private void showAddTransactionDialog() {
        if (customer == null) return;

        List<BankAccount> accounts = AccountDTO.findAccountsByCustomerId(customer.getCustomerNumber());
        if (accounts == null || accounts.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No accounts found for this customer.");
            return;
        }

        JDialog dialog = new JDialog(this, "Add Transaction", true);
        dialog.setLayout(new BorderLayout(10, 10));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(12, 16, 8, 16));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 6, 5, 6);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Account selector
        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("Account:"), gbc);
        gbc.gridx = 1;
        JComboBox<BankAccount> accountCombo = new JComboBox<>();
        accountCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof BankAccount) {
                    BankAccount a = (BankAccount) value;
                    setText("Acct #" + a.getAccountNum() + " | " + a.getType()
                            + " | $" + String.format("%.2f", a.getBalance()));
                }
                return this;
            }
        });
        for (BankAccount a : accounts) accountCombo.addItem(a);
        form.add(accountCombo, gbc);

        // Transaction type
        gbc.gridx = 0; gbc.gridy = 1;
        form.add(new JLabel("Type:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> typeCombo = new JComboBox<>(new String[]{"Deposit", "Withdraw", "Transfer"});
        form.add(typeCombo, gbc);

        // Amount
        gbc.gridx = 0; gbc.gridy = 2;
        form.add(new JLabel("Amount:"), gbc);
        gbc.gridx = 1;
        JTextField amountField = new JTextField(12);
        form.add(amountField, gbc);

        // Description
        gbc.gridx = 0; gbc.gridy = 3;
        form.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        JTextField descField = new JTextField(12);
        form.add(descField, gbc);

        dialog.add(form, BorderLayout.CENTER);

        // Buttons
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        JButton submitBtn = new JButton("Submit");
        JButton cancelBtn = new JButton("Cancel");

        submitBtn.addActionListener(ev -> {
            BankAccount selectedAccount = (BankAccount) accountCombo.getSelectedItem();
            String type = (String) typeCombo.getSelectedItem();
            String amountText = amountField.getText().trim();
            String desc = descField.getText().trim();

            if (amountText.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please enter an amount.");
                return;
            }
            double amount;
            try {
                amount = Double.parseDouble(amountText);
                if (amount <= 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter a valid positive amount.");
                return;
            }

            HashMap<String, Object> hm = new HashMap<>();
            hm.put("id", selectedAccount.getAccountNum());
            hm.put("type", type);
            hm.put("amount", amount);
            hm.put("summary", desc);
            hm.put("createdate", new Timestamp(new Date().getTime()));

            int result = AccountTransactionDTO.performCreate(hm);
            if (result > 0) {
                JOptionPane.showMessageDialog(dialog, "Transaction added successfully.");
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Failed to add transaction. Please try again.");
            }
        });

        cancelBtn.addActionListener(ev -> dialog.dispose());

        btnRow.add(submitBtn);
        btnRow.add(cancelBtn);
        dialog.add(btnRow, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveBtn) {
            saveCustomer();
        } else if (e.getSource() == updateAddressBtn) {
            if (customer != null) {
                CustomerAddressFrame addrFrame = new CustomerAddressFrame(customer);
                addrFrame.setVisible(true);
            }
        } else if (e.getSource() == addTransactionBtn) {
            showAddTransactionDialog();
        } else if (e.getSource() == closeBtn) {
            this.dispose();
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            BankCustomer testCustomer = new BankCustomer(1, "John", "Doe");
            new CustomerDetail(testCustomer);
        });
    }

}
