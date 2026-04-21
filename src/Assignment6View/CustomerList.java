package Assignment6View;

import Assignment6Controller.AccountDTO;
import Assignment6Model.BankAccount;
import Assignment6Model.BankCustomer;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;

/**
 * CustomerList frame - displays a list of BankCustomer objects.
 *
 * @author karunmehta
 */
public class CustomerList extends JFrame implements ActionListener {

    private JList<BankCustomer> custList;
    private DefaultListModel<BankCustomer> listModel;
    private JButton detailsBtn;
    private JButton showAccountsBtn;
    private JButton cancelBtn;

    // Constructor accepts a list of customers to display
    public CustomerList(List<BankCustomer> customers) {
        initComponents();
        populateList(customers);
    }

    public CustomerList() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Customer List");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Title
        JLabel title = new JLabel("List of Customers", SwingConstants.CENTER);
        title.setFont(new Font("Helvetica Neue", Font.BOLD, 16));
        add(title, BorderLayout.NORTH);

        // List
        listModel = new DefaultListModel<>();
        custList = new JList<>(listModel);
        custList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        custList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof BankCustomer) {
                    BankCustomer c = (BankCustomer) value;
                    setText(c.getCustomerNumber() + " - " + c.getName());
                }
                return this;
            }
        });
        JScrollPane scrollPane = new JScrollPane(custList);
        scrollPane.setPreferredSize(new Dimension(380, 250));
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        centerPanel.add(new JLabel("Customer List:"), BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        detailsBtn = new JButton("Show Details");
        showAccountsBtn = new JButton("Show Accounts");
        cancelBtn = new JButton("Cancel");
        detailsBtn.addActionListener(this);
        showAccountsBtn.addActionListener(this);
        cancelBtn.addActionListener(this);
        btnPanel.add(detailsBtn);
        btnPanel.add(showAccountsBtn);
        btnPanel.add(cancelBtn);
        add(btnPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    public void populateList(List<BankCustomer> customers) {
        listModel.clear();
        if (customers != null) {
            for (BankCustomer c : customers) {
                listModel.addElement(c);
            }
        }
    }

    /**
     * Opens CustomerDetail frame for the selected customer.
     */
    private void showCustomerDetail() {
        BankCustomer selected = custList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Please select a customer from the list.");
            return;
        }
        CustomerDetail detail = new CustomerDetail(selected);
        detail.setVisible(true);
    }

    /**
     * Opens AccountList frame for accounts belonging to the selected customer.
     */
    private void showCustomerAccounts() {
        BankCustomer selected = custList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Please select a customer from the list.");
            return;
        }
        List<BankAccount> accounts = AccountDTO.findAccountsByCustomerId(selected.getCustomerNumber());
        if (accounts == null || accounts.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No accounts found for customer: " + selected.getName());
        } else {
            AccountList accountListFrame = new AccountList(accounts, selected);
            accountListFrame.setVisible(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == detailsBtn) {
            showCustomerDetail();
        } else if (e.getSource() == showAccountsBtn) {
            showCustomerAccounts();
        } else if (e.getSource() == cancelBtn) {
            this.dispose();
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new CustomerList());
    }

}
