package Assignment6View;

import Assignment6Model.BankAccountTransaction;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;

/**
 * TransactionSummary frame - displays all transactions for an account.
 *
 * @author karunmehta
 */
public class TransactionSummary extends JFrame implements ActionListener {

    private JTextField acctIdField;
    private JTextField custNameField;
    private JList<BankAccountTransaction> tranList;
    private DefaultListModel<BankAccountTransaction> listModel;
    private JButton doneBtn;

    public TransactionSummary(List<BankAccountTransaction> transactions, int acctId, String custName) {
        initComponents();
        acctIdField.setText(String.valueOf(acctId));
        custNameField.setText(custName != null ? custName : "");
        populateList(transactions);
    }

    public TransactionSummary() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Transaction Summary");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Title
        JLabel title = new JLabel("Transaction Summary", SwingConstants.CENTER);
        title.setFont(new Font("Helvetica Neue", Font.BOLD, 16));
        add(title, BorderLayout.NORTH);

        // Info panel
        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBorder(BorderFactory.createEmptyBorder(8, 15, 5, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 6, 4, 6);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        infoPanel.add(new JLabel("Account ID:"), gbc);
        gbc.gridx = 1;
        acctIdField = new JTextField(8);
        acctIdField.setEditable(false);
        infoPanel.add(acctIdField, gbc);

        gbc.gridx = 2;
        infoPanel.add(new JLabel("Customer Name:"), gbc);
        gbc.gridx = 3;
        custNameField = new JTextField(14);
        custNameField.setEditable(false);
        infoPanel.add(custNameField, gbc);

        add(infoPanel, BorderLayout.NORTH);

        // Wrap title and info in a single north panel
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(title, BorderLayout.NORTH);
        northPanel.add(infoPanel, BorderLayout.CENTER);
        add(northPanel, BorderLayout.NORTH);

        // Transaction list
        listModel = new DefaultListModel<>();
        tranList = new JList<>(listModel);
        tranList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof BankAccountTransaction) {
                    BankAccountTransaction t = (BankAccountTransaction) value;
                    String date = t.getCreateDate() != null ? t.getCreateDate().toString() : "N/A";
                    String type = t.getType() != null ? t.getType() : "N/A";
                    String desc = t.getDescription() != null ? t.getDescription() : "";
                    setText(date + " | " + type + " | $" + String.format("%.2f", t.getAmount())
                            + (desc.isEmpty() ? "" : " | " + desc));
                }
                return this;
            }
        });

        JScrollPane scrollPane = new JScrollPane(tranList);
        scrollPane.setPreferredSize(new Dimension(480, 220));

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        centerPanel.add(new JLabel("Transactions:"), BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // Done button
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        doneBtn = new JButton("Done");
        doneBtn.addActionListener(this);
        btnPanel.add(doneBtn);
        add(btnPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    public void populateList(List<BankAccountTransaction> transactions) {
        listModel.clear();
        if (transactions != null) {
            for (BankAccountTransaction t : transactions) {
                listModel.addElement(t);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == doneBtn) {
            this.dispose();
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new TransactionSummary());
    }

}
