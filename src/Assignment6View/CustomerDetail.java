package Assignment6View;

import Assignment6Controller.AccountDTO;
import Assignment6Controller.CustomerAddressDTO;
import Assignment6Controller.CustomerDTO;
import Assignment6Model.BankAccount;
import Assignment6Model.BankCustomer;
import Assignment6Model.CustomerAddress;
import java.awt.*;
import java.awt.event.*;
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
    private JButton showAccountsBtn;
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
        showAccountsBtn = new JButton("Show Accounts");
        closeBtn = new JButton("Close");

        saveBtn.addActionListener(this);
        updateAddressBtn.addActionListener(this);
        showAccountsBtn.addActionListener(this);
        closeBtn.addActionListener(this);

        btnPanel.add(saveBtn);
        btnPanel.add(updateAddressBtn);
        btnPanel.add(showAccountsBtn);
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

    /**
     * Saves updated customer data to the database.
     */
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

    /**
     * Opens the AccountList frame showing accounts for this customer.
     */
    private void findCustomerAccounts() {
        if (customer == null) return;
        List<BankAccount> accounts = AccountDTO.findAccountsByCustomerId(customer.getCustomerNumber());
        if (accounts == null || accounts.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No accounts found for this customer.");
        } else {
            AccountList accountListFrame = new AccountList(accounts, customer);
            accountListFrame.setVisible(true);
        }
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
        } else if (e.getSource() == showAccountsBtn) {
            findCustomerAccounts();
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
