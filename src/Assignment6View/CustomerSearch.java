package Assignment6View;

import Assignment6Controller.CustomerDTO;
import Assignment6Model.BankCustomer;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;

/**
 * CustomerSearch frame - search customers by city.
 *
 * @author karunmehta
 */
public class CustomerSearch extends JFrame implements ActionListener {

    private JTextField cityField;
    private JButton searchBtn;
    private JButton cancelBtn;
    private JLabel titleLabel;

    public CustomerSearch() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Search Customer");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Title
        titleLabel = new JLabel("Search Customer", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 16));
        add(titleLabel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("City:"), gbc);
        gbc.gridx = 1;
        cityField = new JTextField(18);
        formPanel.add(cityField, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Button panel
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
     * Retrieves customers from the DB whose address city matches the entered city.
     * Opens CustomerList frame with the results.
     */
    private void findCustomer() {
        String city = cityField.getText().trim();
        if (city.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a city name.");
            return;
        }
        List<BankCustomer> customers = CustomerDTO.findCustomersByCity(city);
        if (customers == null || customers.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No customers found in city: " + city);
        } else {
            CustomerList listFrame = new CustomerList(customers);
            listFrame.setVisible(true);
            this.dispose();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchBtn) {
            findCustomer();
        } else if (e.getSource() == cancelBtn) {
            this.dispose();
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new CustomerSearch());
    }

}
