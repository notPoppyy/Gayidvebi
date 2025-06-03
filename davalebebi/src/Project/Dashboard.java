package Project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Dashboard {

    private JLabel lblTotalSales;
    private JLabel lblTotalProfit;
    private JLabel lblStaffCount;
    private JTable tblRecentSales;

    public void ShowForm() {
        JFrame frame = new JFrame("Dashboard");
        frame.setSize(900, 600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        
       JButton btnBack = new JButton("⬅️ Back");
        btnBack.setBounds(20, 500, 100, 30);
        btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminForm ad = new AdminForm();
				ad.ShowForm();
				frame.dispose();
				
			}
		});
        frame.add(btnBack);

        JPanel topPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        lblTotalSales = createDashboardCard(topPanel, "Total Sales", "0 ₾", new Color(52, 152, 219));
        lblTotalProfit = createDashboardCard(topPanel, "Total Profit", "0 ₾", new Color(46, 204, 113));
        lblStaffCount = createDashboardCard(topPanel, "Staff Count", "0", new Color(155, 89, 182));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(topPanel, BorderLayout.NORTH);

        String[] columnNames = {"SaleID", "ProductsID", "ProductsName", "Quantity", "TotalPrice", "Date", "StaffID", "Profit"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        tblRecentSales = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(tblRecentSales);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Recent Sales"));
        frame.add(scrollPane, BorderLayout.CENTER);

        try (Connection conn = DBConnection.getConnection()) {
       
            String SQL = """
                    SELECT 
                        Sales.SalesID, Sales.ProductsID, Products.ProductName,
                        Sales.Quantity, Sales.TotalPrice, Sales.Date, Sales.StaffID,
                        Products.RecivePrice,
                        (Sales.TotalPrice - (Sales.Quantity * Products.RecivePrice)) as Profit
                    FROM Sales
                    INNER JOIN Products ON Sales.ProductsID = Products.ProductsID
                    """;

            PreparedStatement ps = conn.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();

            double TotalProfit = 0;
            while (rs.next()) {
                int saleID = rs.getInt("SalesID");
                int productsID = rs.getInt("ProductsID");
                String productName = rs.getString("ProductName");
                int quantity = rs.getInt("Quantity");
                double totalPrice = rs.getDouble("TotalPrice");
                String date = rs.getString("Date");
                int staffID = rs.getInt("StaffID");
                double profit = rs.getDouble("Profit");

                TotalProfit += profit;

                model.addRow(new Object[]{saleID, productsID, productName, quantity, totalPrice, date, staffID, profit});
            }

            lblTotalProfit.setText(String.format("%.2f ₾", TotalProfit));

          
            String TotalSQL = "SELECT SUM(TotalPrice) AS TotalSalesAmount FROM Sales";
            PreparedStatement ps1 = conn.prepareStatement(TotalSQL);
            ResultSet rs1 = ps1.executeQuery();
            if (rs1.next()) {
                double totalSales = rs1.getDouble("TotalSalesAmount");
                lblTotalSales.setText(String.format("%.2f ₾", totalSales));
            }

          
            String staffSQL = "SELECT COUNT(*) AS StaffCount FROM Staff";
            PreparedStatement ps2 = conn.prepareStatement(staffSQL);
            ResultSet rs2 = ps2.executeQuery();
            if (rs2.next()) {
                int staffCount = rs2.getInt("StaffCount");
                lblStaffCount.setText(String.valueOf(staffCount));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        frame.setVisible(true);
        
    }

    public JLabel createDashboardCard(JPanel parentPanel, String title, String value, Color color) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(color);
        panel.setPreferredSize(new Dimension(280, 100));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel lblValue = new JLabel(value);
        lblValue.setForeground(Color.WHITE);
        lblValue.setFont(new Font("Arial", Font.BOLD, 24));
        lblValue.setHorizontalAlignment(SwingConstants.RIGHT);

        panel.add(lblTitle, BorderLayout.NORTH);
        panel.add(lblValue, BorderLayout.SOUTH);
        parentPanel.add(panel);

        return lblValue;
    }
}
