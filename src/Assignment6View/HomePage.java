package Assignment6View;

import Assignment6Controller.CustomerDTO;
import Assignment6Model.BankCustomer;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;

/**
 *
 * @author karunmehta
 */
public class HomePage extends JFrame implements ActionListener {

    JButton jbSearch, jbAdd, jbShowAll;

    HomePage() {
        setLayout(null);

        ImageIcon iIcon = new ImageIcon(ClassLoader.getSystemResource("icons/customer.jpg"));
        Image anImage = iIcon.getImage().getScaledInstance(1000, 630, Image.SCALE_DEFAULT);
        ImageIcon iIcon2 = new ImageIcon(anImage);
        JLabel theLabel = new JLabel(iIcon2);
        theLabel.setBounds(0, 0, 1100, 630);
        add(theLabel);

        JLabel heading = new JLabel("Application Main Menu");
        heading.setBounds(100, 100, 400, 40);
        heading.setFont(new Font("Tahoma", Font.BOLD, 25));
        theLabel.add(heading);

        jbShowAll = new JButton("Show All Customers");
        jbShowAll.setBounds(100, 160, 200, 40);
        jbShowAll.addActionListener(this);
        theLabel.add(jbShowAll);

        jbSearch = new JButton("Search Customer(s)");
        jbSearch.setBounds(100, 210, 200, 40);
        jbSearch.addActionListener(this);
        theLabel.add(jbSearch);

        jbAdd = new JButton("Add New Customer");
        jbAdd.setBounds(100, 260, 200, 40);
        jbAdd.addActionListener(this);
        theLabel.add(jbAdd);

        setSize(1120, 630);
        setLocation(250, 100);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == jbShowAll) {
            List<BankCustomer> allCustomers = CustomerDTO.getAllCustomers();
            if (allCustomers == null || allCustomers.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No customers found in the database.");
            } else {
                CustomerList listFrame = new CustomerList(allCustomers);
                listFrame.setVisible(true);
            }
        } else if (ae.getSource() == jbAdd) {
            new CustomerFrame();
        } else if (ae.getSource() == jbSearch) {
            new CustomerSearch();
            this.setVisible(true);
        }
    }

    public static void main(String[] args) {
        new HomePage();
    }

}
