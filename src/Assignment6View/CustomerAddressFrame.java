package Assignment6View;

import Assignment6Controller.CustomerAddressDTO;
import Assignment6Model.BankCustomer;
import Assignment6Model.CustomerAddress;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * CustomerAddressFrame - view and update a customer's address.
 *
 * @author karunmehta
 */
public class CustomerAddressFrame extends JFrame implements ActionListener {

    private BankCustomer customer;
    private CustomerAddress address;

    private JTextField streetNumField;
    private JTextField streetNameField;
    private JTextField cityField;
    private JTextField stateField;
    private JTextField zipField;

    private JButton saveBtn;
    private JButton cancelBtn;

    public CustomerAddressFrame(BankCustomer customer) {
        this.customer = customer;
        this.address = customer.getAddress();
        initComponents();
        populateFields();
    }

    private void initComponents() {
        setTitle("Customer Address");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Title
        JLabel title = new JLabel("Customer Address", SwingConstants.CENTER);
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
        formPanel.add(new JLabel("Street Number:"), gbc);
        gbc.gridx = 1;
        streetNumField = new JTextField(15);
        formPanel.add(streetNumField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Street Name:"), gbc);
        gbc.gridx = 1;
        streetNameField = new JTextField(15);
        formPanel.add(streetNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("City:"), gbc);
        gbc.gridx = 1;
        cityField = new JTextField(15);
        formPanel.add(cityField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("State:"), gbc);
        gbc.gridx = 1;
        stateField = new JTextField(15);
        formPanel.add(stateField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Zip:"), gbc);
        gbc.gridx = 1;
        zipField = new JTextField(15);
        formPanel.add(zipField, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        saveBtn = new JButton("Save");
        cancelBtn = new JButton("Cancel");
        saveBtn.addActionListener(this);
        cancelBtn.addActionListener(this);
        btnPanel.add(saveBtn);
        btnPanel.add(cancelBtn);
        add(btnPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private void populateFields() {
        if (address == null) return;
        streetNumField.setText(String.valueOf(address.getStreetNum()));
        streetNameField.setText(address.getStreetName() != null ? address.getStreetName() : "");
        cityField.setText(address.getCity() != null ? address.getCity() : "");
        stateField.setText(address.getState() != null ? address.getState() : "");
        zipField.setText(String.valueOf(address.getZip()));
    }

    /**
     * Saves the updated address to the database.
     */
    private void saveCustomerAddress() {
        if (address == null) {
            address = new CustomerAddress();
            address.setID(customer.getCustomerNumber());
        }

        try {
            address.setStreetNum(Integer.parseInt(streetNumField.getText().trim()));
        } catch (NumberFormatException ex) {
            address.setStreetNum(0);
        }
        address.setStreet(streetNameField.getText().trim());
        address.setCity(cityField.getText().trim());
        address.setState(stateField.getText().trim());
        try {
            address.setZip(Integer.parseInt(zipField.getText().trim()));
        } catch (NumberFormatException ex) {
            address.setZip(0);
        }
        address.setID(customer.getCustomerNumber());

        int result = CustomerAddressDTO.performUpdate(address);
        if (result > 0) {
            customer.setAddress(address);
            JOptionPane.showMessageDialog(this, "Address Updated Successfully.");
            this.dispose();
        } else {
            // If update returned no rows, try insert (new address)
            result = CustomerAddressDTO.performCreate(new java.util.HashMap<java.lang.String, java.lang.Object>() {{
                put("custid", address.getID());
                put("streetnum", address.getStreetNum());
                put("streetname", address.getStreetName());
                put("city", address.getCity());
                put("state", address.getState());
                put("zip", address.getZip());
            }});
            if (result > 0) {
                customer.setAddress(address);
                JOptionPane.showMessageDialog(this, "Address Saved Successfully.");
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to save address.");
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveBtn) {
            saveCustomerAddress();
        } else if (e.getSource() == cancelBtn) {
            this.dispose();
        }
    }

}
