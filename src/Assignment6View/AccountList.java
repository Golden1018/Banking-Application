package Assignment6View;

import Assignment6Model.BankAccount;
import Assignment6Model.BankCustomer;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;

/**
 * AccountList frame - displays a list of BankAccount objects for a customer.
 *
 * @author karunmehta
 */
public class AccountList extends JFrame implements ActionListener {

    private BankCustomer customer;
    private JList<BankAccount> accountList;
    private DefaultListModel<BankAccount> listModel;
    private JButton showDetailBtn;
    private JButton cancelBtn;

    public AccountList(List<BankAccount> accounts, BankCustomer customer) {
        this.customer = customer;
        initComponents();
        populateList(accounts);
    }

    public AccountList() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Account List");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Title
        JLabel title = new JLabel("Bank Account List", SwingConstants.CENTER);
        title.setFont(new Font("Helvetica Neue", Font.BOLD, 16));
        if (customer != null) {
            title.setText("Accounts for: " + customer.getName());
        }
        add(title, BorderLayout.NORTH);

        // List
        listModel = new DefaultListModel<>();
        accountList = new JList<>(listModel);
        accountList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        accountList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof BankAccount) {
                    BankAccount a = (BankAccount) value;
                    setText("Acct #" + a.getAccountNum() + " | " + a.getType()
                            + " | Balance: $" + String.format("%.2f", a.getBalance()));
                }
                return this;
            }
        });
        JScrollPane scrollPane = new JScrollPane(accountList);
        scrollPane.setPreferredSize(new Dimension(400, 220));

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        centerPanel.add(new JLabel("Account List:"), BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        showDetailBtn = new JButton("Show Detail");
        cancelBtn = new JButton("Cancel");
        showDetailBtn.addActionListener(this);
        cancelBtn.addActionListener(this);
        btnPanel.add(showDetailBtn);
        btnPanel.add(cancelBtn);
        add(btnPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    public void populateList(List<BankAccount> accounts) {
        listModel.clear();
        if (accounts != null) {
            for (BankAccount a : accounts) {
                listModel.addElement(a);
            }
        }
    }

    /**
     * Opens AccountDetail frame for the selected account.
     */
    private void showAccountDetail() {
        BankAccount selected = accountList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Please select an account from the list.");
            return;
        }
        String customerName = (customer != null) ? customer.getName() : "";
        AccountDetail detail = new AccountDetail(selected, customerName);
        detail.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == showDetailBtn) {
            showAccountDetail();
        } else if (e.getSource() == cancelBtn) {
            this.dispose();
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new AccountList());
    }

}
