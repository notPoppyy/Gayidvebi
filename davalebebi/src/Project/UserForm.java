package Project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.*;
import java.text.DecimalFormat;

public class UserForm {
    private JFrame frame;
    private JTextField productCodeField, totalAmountField, discountField, increaseField;
    private JTable table;
    private DefaultTableModel model;

    public void showForm() {
        frame = new JFrame("Worker");
        frame.setSize(1920, 1080);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        
        
     
        
        JLabel welcomeLabel = new JLabel("Welcome, " + Session.currentStaffName + " (" + Session.currentStaffRole + ")");
        welcomeLabel.setBounds(20, 20, 400, 30);
        welcomeLabel.setFont(new Font("BPG Glaho", Font.BOLD, 16));
        frame.add(welcomeLabel);
     
   
       
        
        
        
        JLabel Logout = new JLabel("Logout");
		Logout.setBounds(1465,-5,100,80);
		Logout.setForeground(Color.WHITE);
		Logout.setFont(new Font("Arial",Font.BOLD,14));
		frame.add(Logout);
		Logout.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				Logout.setText("<html><u><span style='color:White;'>Logout</span></u></html>");
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				Logout.setText("<html><u><span style='color:Black;'>Logout</span></u></html>");
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				LoginForm lo = new LoginForm();
				lo.Login();
				frame.dispose();
			
				
			}
		});
  

        JLabel labelProductCode = new JLabel("Enter Product Code");
        labelProductCode.setFont(new Font("Arial", Font.BOLD, 20));
        labelProductCode.setBounds(1210, -85, 200, 300);
        frame.add(labelProductCode);

        JLabel labelTotal = new JLabel("Total Amount");
        labelTotal.setBounds(900, 680, 190, 30);
        labelTotal.setFont(new Font("Arial", Font.BOLD, 19));
        frame.add(labelTotal);

        JLabel labelDiscount = new JLabel("Discount(%)");
        labelDiscount.setBounds(750, 680, 190, 30);
        labelDiscount.setFont(new Font("Arial", Font.BOLD, 19));
        frame.add(labelDiscount);

        increaseField = new JTextField();
        increaseField.setBounds(1150, 80, 50, 30);
        increaseField.setFont(new Font("Arial", Font.BOLD, 19));
        frame.add(increaseField);

        discountField = new JTextField();
        discountField.setBounds(750, 713, 120, 30);
        discountField.setFont(new Font("Arial", Font.BOLD, 19));
        frame.add(discountField);

        productCodeField = new JTextField();
        productCodeField.setBounds(1210, 80, 190, 30);
        productCodeField.setFont(new Font("Arial", Font.BOLD, 19));
        frame.add(productCodeField);

        totalAmountField = new JTextField();
        totalAmountField.setBounds(900, 714, 133, 30);
        frame.add(totalAmountField);

        JButton enterButton = new JButton("Enter");
        enterButton.setBounds(1210, 115, 190, 30);
        frame.add(enterButton);
        frame.getRootPane().setDefaultButton(enterButton);

        enterButton.addActionListener(e -> {
            String productCode = productCodeField.getText();
            if (!productCode.isEmpty()) {
                addOrUpdateProduct(productCode);
                productCodeField.setText("");
            }
        });

        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(1253, 150, 100, 30);
        frame.add(clearButton);

        clearButton.addActionListener(e -> {
            model.setRowCount(0);
            totalAmountField.setText("");
            discountField.setText("");
        });

        String[] columns = {"ProductName", "ProductPrice", "Quantity", "TotalPrice", "ProductID"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(35, 45, 1000, 715);
        frame.add(scrollPane);

        table.getColumnModel().getColumn(4).setMinWidth(0);
        table.getColumnModel().getColumn(4).setMaxWidth(0);
        table.getColumnModel().getColumn(4).setWidth(0);

        JButton payButton = new JButton("PAY");
        payButton.setBounds(1253, 255, 100, 30);
        frame.add(payButton);

        payButton.addActionListener(e -> processPayment());

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(1253, 185, 100, 30);
        frame.add(deleteButton);

        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                model.removeRow(selectedRow);
                updateTotal();
            }
        });

        JButton discountButton = new JButton("Discount");
        discountButton.setBounds(1253, 220, 100, 30);
        frame.add(discountButton);

        discountButton.addActionListener(e -> applyDiscount());

        JButton increaseButton = new JButton("+");
        increaseButton.setBounds(1150, 115, 50, 30);
        increaseButton.setFont(new Font("Arial", Font.BOLD, 19));
        frame.add(increaseButton);

        increaseButton.addActionListener(e -> increaseQuantity());

        JLabel background2 = new JLabel(new ImageIcon(getClass().getResource("userbackground.jpg")));
        background2.setBounds(-240, 0, 1555, 1080);
        background2.setLayout(null);
        frame.add(background2);

        JLabel background = new JLabel(new ImageIcon(getClass().getResource("Side.jpg")));
        background.setBounds(600, 0, 1920, 1080);
        background.setLayout(null);
        frame.add(background);

        frame.setVisible(true);
    }

    private void addOrUpdateProduct(String productCode) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT ProductsID, ProductName, ProductPrice FROM Products WHERE ProductCode = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, productCode);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String name = rs.getString("ProductName");
                double price = rs.getDouble("ProductPrice");
                int id = rs.getInt("ProductsID");
                boolean found = false;

                for (int i = 0; i < model.getRowCount(); i++) {
                    if (model.getValueAt(i, 0).equals(name)) {
                        int qty = (int) model.getValueAt(i, 2);
                        qty++;
                        model.setValueAt(qty, i, 2);
                        model.setValueAt(qty * price, i, 3);
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    model.addRow(new Object[]{name, price, 1, price, id});
                }
                updateTotal();
            } else {
                JOptionPane.showMessageDialog(frame, "პროდუქტი ვერ მოიძებნა.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateTotal() {
        double total = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            double price = (double) model.getValueAt(i, 1);
            int qty = (int) model.getValueAt(i, 2);
            total += price * qty;
        }
        totalAmountField.setText(String.format("%.2f ლ", total));
    }

    private void applyDiscount() {
        try {
            double discount = Double.parseDouble(discountField.getText());
            double total = Double.parseDouble(totalAmountField.getText().replace("ლ", "").trim());
            double discountedTotal = total - (total * discount / 100);
            totalAmountField.setText(String.format("%.2f ლ", discountedTotal));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "შეიყვანეთ სწორი რიცხვები");
        }
    }

    private void increaseQuantity() {
        int selectedRow = table.getSelectedRow();
        try {
            int increment = Integer.parseInt(increaseField.getText());
            if (selectedRow != -1) {
                int qty = (int) model.getValueAt(selectedRow, 2);
                double price = (double) model.getValueAt(selectedRow, 1);
                int newQty = qty + increment;
                model.setValueAt(newQty, selectedRow, 2);
                model.setValueAt(price * newQty, selectedRow, 3);
                updateTotal();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "გთხოვთ შეიყვანოთ მთელი რიცხვი");
        }
    }

    private void processPayment() {
        for (int i = 0; i < model.getRowCount(); i++) {
            int qty = (int) model.getValueAt(i, 2);
            int id = (int) model.getValueAt(i, 4);
            String name = model.getValueAt(i, 0).toString();
            double price = (double) model.getValueAt(i, 1);
            double total = price * qty;

            try (Connection conn = DBConnection.getConnection()) {
                PreparedStatement psUpdate = conn.prepareStatement("UPDATE Products SET ProductAmount = ProductAmount - ? WHERE ProductsID = ?");
                psUpdate.setInt(1, qty);
                psUpdate.setInt(2, id);
                psUpdate.executeUpdate();

                PreparedStatement psInsert = conn.prepareStatement("INSERT INTO Sales (ProductsID, ProductName, Quantity, TotalPrice, Date, StaffID) VALUES (?, ?, ?, ?, ?, ?)");
                psInsert.setInt(1, id);
                psInsert.setString(2, name);
                psInsert.setInt(3, qty);
                psInsert.setBigDecimal(4, new BigDecimal(total));
                psInsert.setDate(5, new java.sql.Date(System.currentTimeMillis()));
                psInsert.setInt(6, Session.currentStaffID);
                psInsert.executeUpdate();

                ResultSet rs = conn.prepareStatement("SELECT ProductName, ProductAmount FROM Products WHERE ProductsID = " + id).executeQuery();
                if (rs.next()) {
                    System.out.println("გადახდის შემდეგ დარჩა: " + rs.getString("ProductName") + " - " + rs.getInt("ProductAmount"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        new PayForm().PayFormm();
    }

    public JTable getTable() {
        return table;
    }

    public DefaultTableModel getModel() {
        return model;
    }
}