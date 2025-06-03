package Project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;

public class Storage {
	private void refillFromStorage() {
	    try (Connection conn = DBConnection.getConnection()) {
	        for (int i = 0; i < productModel.getRowCount(); i++) {
	            int productId = Integer.parseInt(productModel.getValueAt(i, 0).toString());
	            int currentAmount = Integer.parseInt(productModel.getValueAt(i, 3).toString());

	            if (currentAmount <= 0) {
	               
	                String sqlFind = "SELECT TOP 1 * FROM Storage WHERE ProductsID = ? AND Quantity > 0 ORDER BY ExpireDate ASC";
	                PreparedStatement psFind = conn.prepareStatement(sqlFind);
	                psFind.setInt(1, productId);
	                ResultSet rs = psFind.executeQuery();

	                if (rs.next()) {
	                    int storageId = rs.getInt("StorageID");
	                    int storageQty = rs.getInt("Quantity");
	                    int refillQty = Math.min(storageQty, 20); 

	                  
	                    String updateProduct = "UPDATE Products SET ProductAmount = ProductAmount + ? WHERE ProductsID = ?";
	                    PreparedStatement psUpdateProduct = conn.prepareStatement(updateProduct);
	                    psUpdateProduct.setInt(1, refillQty);
	                    psUpdateProduct.setInt(2, productId);
	                    psUpdateProduct.executeUpdate();

	                   
	                    String updateStorage = "UPDATE Storage SET Quantity = Quantity - ? WHERE StorageID = ?";
	                    PreparedStatement psUpdateStorage = conn.prepareStatement(updateStorage);
	                    psUpdateStorage.setInt(1, refillQty);
	                    psUpdateStorage.setInt(2, storageId);
	                    psUpdateStorage.executeUpdate();
	                }
	            }
	        }

	        JOptionPane.showMessageDialog(frame, "პროდუქცია შეივსო სთორეიჯიდან სადაც საჭირო იყო");
	        productModel.setRowCount(0);
	        storageModel.setRowCount(0);
	        loadProductData();
	        loadStorageData();

	    } catch (Exception ex) {
	        JOptionPane.showMessageDialog(frame, "შეცდომა შევსებისას: " + ex.getMessage());
	        ex.printStackTrace();
	    }
	}

	
    JFrame frame;
    JTable productTable, storageTable;
    DefaultTableModel productModel, storageModel;
    JButton btnAddRow, btnSave;

    public void ShowForm() {
        frame = new JFrame("Product & Storage Management");
        frame.setSize(1000, 600);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
   
        JButton btnRefillFromStorage = new JButton("Refill From Storage");
        btnRefillFromStorage.setBounds(820, 340, 150, 30);
        frame.add(btnRefillFromStorage);

    
        btnRefillFromStorage.addActionListener(e -> refillFromStorage());

       
       JButton btnBack = new JButton("⬅️ Back");
       btnBack.setBounds(20, 500, 100, 30);
       frame.add(btnBack);
       btnBack.addActionListener(new ActionListener() {
    	  
			public void actionPerformed(ActionEvent e) {
				AdminForm ad = new AdminForm();
				ad.ShowForm();
				frame.dispose();
				
			}
		});
        
      
        
        productModel = new DefaultTableModel(
                new String[]{"ProductsID", "ProductName", "ProductPrice", "ProductAmount", "Size", "ReciveDate", "RecivePrice", "CategoryID", "Status", "ProductCode"}, 0
        );
        productTable = new JTable(productModel);
        JScrollPane productScroll = new JScrollPane(productTable);
        productScroll.setBounds(20, 20, 460, 300);
        frame.add(productScroll);

       
        storageModel = new DefaultTableModel(
                new String[]{"ProductCode", "BuyPrice", "SellPrice", "Quantity", "ExpireDate", "addedDate", "ProductsID"}, 0
        );
        storageTable = new JTable(storageModel);
        JScrollPane storageScroll = new JScrollPane(storageTable);
        storageScroll.setBounds(500, 20, 460, 300);
        frame.add(storageScroll);

      
        btnAddRow = new JButton("Add Storage Row");
        btnAddRow.setBounds(500, 340, 150, 30);
        frame.add(btnAddRow);

        btnSave = new JButton("Save Storage");
        btnSave.setBounds(660, 340, 150, 30);
        frame.add(btnSave);

       
        loadProductData();
        loadStorageData();

       
        btnAddRow.addActionListener(e -> storageModel.addRow(new Object[]{"", 0.0, 0.0, 0, "2025-12-31", "2025-06-03", 0}));

      
        btnSave.addActionListener(e -> saveStorageData());

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void loadProductData() {
        try (Connection conn = DBConnection.getConnection()) {
            String SQL = "SELECT * FROM Products";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                productModel.addRow(new Object[]{
                        rs.getInt("ProductsID"),
                        rs.getString("ProductName"),
                        rs.getBigDecimal("ProductPrice"),
                        rs.getInt("ProductAmount"),
                        rs.getString("Size"),
                        rs.getDate("ReciveDate"),
                        rs.getBigDecimal("RecivePrice"),
                        rs.getInt("CategoryID"),
                        rs.getString("Status"),
                        rs.getString("ProductCode")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadStorageData() {
        try (Connection conn = DBConnection.getConnection()) {
            String SQL = "SELECT * FROM Storage";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                storageModel.addRow(new Object[]{
                        rs.getString("ProductCode"),
                        rs.getBigDecimal("BuyPrice"),
                        rs.getBigDecimal("SellPrice"),
                        rs.getInt("Quantity"),
                        rs.getDate("ExpireDate"),
                        rs.getDate("addedDate"),
                        rs.getInt("ProductsID")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveStorageData() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO Storage (ProductCode, BuyPrice, SellPrice, Quantity, ExpireDate, addedDate, ProductsID) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            for (int i = 0; i < storageModel.getRowCount(); i++) {
                ps.setString(1, storageModel.getValueAt(i, 0).toString());
                ps.setBigDecimal(2, new java.math.BigDecimal(storageModel.getValueAt(i, 1).toString()));
                ps.setBigDecimal(3, new java.math.BigDecimal(storageModel.getValueAt(i, 2).toString()));
                ps.setInt(4, Integer.parseInt(storageModel.getValueAt(i, 3).toString()));
                ps.setDate(5, java.sql.Date.valueOf(storageModel.getValueAt(i, 4).toString()));
                ps.setDate(6, java.sql.Date.valueOf(storageModel.getValueAt(i, 5).toString()));
                ps.setInt(7, Integer.parseInt(storageModel.getValueAt(i, 6).toString()));
                ps.executeUpdate();
            }

            JOptionPane.showMessageDialog(frame, "Storage მონაცემები შენახულია წარმატებით");
            storageModel.setRowCount(0);
            loadStorageData();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "შეცდომა: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
