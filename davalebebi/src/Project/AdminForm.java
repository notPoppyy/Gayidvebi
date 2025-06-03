package Project;

import java.awt.Color;
import java.sql.*;

import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;








public class AdminForm {

	
	
	public void ShowForm() {
		JFrame Frame = new JFrame("Administration");
		Frame.setSize(830,600);
		Frame.setLayout(null);
		Frame.setResizable(false);
		Frame.setLocationRelativeTo(null);
		
		
		 JLabel welcomeLabel = new JLabel("Welcome " + Session.currentStaffName + " (" + Session.currentStaffRole + ")");
	        welcomeLabel.setBounds(20, 5, 400, 30);
	        welcomeLabel.setFont(new Font("BPG Glaho", Font.BOLD, 16));
	        Frame.add(welcomeLabel);
		
		JLabel Categoryy = new JLabel("Manage Category");
		Categoryy.setBounds(590,110,500,80);
		Categoryy.setFont(new Font("Arial",Font.BOLD,24));
		Frame.add(Categoryy);
		
		JLabel Products = new JLabel("Manage Products");
		Products.setBounds(10,110,500,80);
		Products.setFont(new Font("Arial",Font.BOLD,24));
		Frame.add(Products);
		
		JLabel Accounts  = new JLabel("Accounts");
		
		Accounts.setBounds(260+250,24,100,80);
		Accounts.setForeground(Color.WHITE);
		Accounts.setFont(new Font("Arial",Font.BOLD,14));
		Frame.add(Accounts);
		Accounts.addMouseListener(new MouseListener() {
			
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
				Accounts.setText("<html><u><span style='color:White;'>Accounts</span></u></html>");
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				Accounts.setText("<html><u><span style='color:Black;'>Accounts</span></u></html>");
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				Accounts ac = new Accounts();
				ac.ShowForm();
				Frame.dispose();
				
			}
		});
		
		
		
		JLabel Storage = new JLabel("Storage");
		Storage.setBounds(260,24,100,80);
		Storage.setForeground(Color.WHITE);
		Storage.setFont(new Font("Arial",Font.BOLD,14));
		Frame.add(Storage);
		
		Storage.addMouseListener(new MouseListener() {
			
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
				Storage.setText("<html><u><span style='color:White;'>Storage</span></u></html>");
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				Storage.setText("<html><u><span style='color:Black;'>Storage</span></u></html>");
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				Storage st = new Storage();
				st.ShowForm();
				Frame.dispose();
				
			}
		});
		
		
		
		JLabel Logout = new JLabel("Logout");
		Logout.setBounds(750,24,100,80);
		Logout.setForeground(Color.WHITE);
		Logout.setFont(new Font("Arial",Font.BOLD,14));
		Frame.add(Logout);
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
				Frame.dispose();
			
				
			}
		});
		
		
		JLabel Dashboard = new JLabel("Dashboard");
		Dashboard.setBounds(10,24,100,80);
		Dashboard.setForeground(Color.WHITE);
		Dashboard.setFont(new Font("Arial",Font.BOLD,14));
		Frame.add(Dashboard);
		Dashboard.addMouseListener(new MouseListener() {
			
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
				Dashboard.setText("<html><u><span style='color:White;'>Dashboard</span></u></html>");
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				Dashboard.setText("<html><u><span style='color:Black;'>Dashboard</span></u></html>");
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				Dashboard ds = new Dashboard();
				ds.ShowForm();
				Frame.dispose();
				
				
				
			}
		});
		
		
		
		;
		
		String[] ColumnNames = {"ProductName","ProductPrice","ProductAmount","ProductSize","ReciveDate","RecivePrice","CategoryID","Status","ProductCode"};
		DefaultTableModel Model = new DefaultTableModel(ColumnNames,0);
		JTable Table = new JTable(Model);
		JScrollPane scrollPane = new JScrollPane(Table);
		scrollPane.setBounds(20,200,570,200);
		Frame.add(scrollPane);
		
		
		try(Connection Conn = DBConnection.getConnection()){
			String sql = "Select * from Products";
			PreparedStatement ps = Conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String name = rs.getString("ProductName");
				double price = rs.getDouble("ProductPrice");
				String size = rs.getString("size");
				int amount= rs.getInt("ProductAmount");
				double recive = rs.getDouble("RecivePrice");
				String ReciveDate = rs.getString("ReciveDate");
				int code = rs.getInt("ProductCode");
				int categoryid = rs.getInt("CategoryID");
				String status = rs.getString("Status");
				Model.addRow(new Object[] {name,price,amount,size,ReciveDate,recive,categoryid,status,code});
			}
			
			
			
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
			
		
		String[] CategoryColumns = {"CategoryName","ID"};
		DefaultTableModel Model1 = new DefaultTableModel(CategoryColumns,0);
		JTable Table1 = new JTable(Model1);
		JScrollPane scrollPane2 = new JScrollPane(Table1);
		TableColumnModel columnModel = Table1.getColumnModel();
		columnModel.getColumn(1).setPreferredWidth(11);
		columnModel.getColumn(0).setPreferredWidth(130);
		scrollPane2.setBounds(618,200,150,200);
		Frame.add(scrollPane2);
		
		try(Connection conn = DBConnection.getConnection()){
			String Category = "Select *from Category";
			PreparedStatement ps1 = conn.prepareStatement(Category);
			
			ResultSet rs1 = ps1.executeQuery();
			
			while(rs1.next()) {
				String categoryname = rs1.getString("CategoryName");
				int categoryID = rs1.getInt("CategoryID");
				Model1.addRow(new Object[] {categoryname,categoryID});
			}
			
		} catch (SQLException e1) {
			
			
			e1.printStackTrace();
		}
		
		JLabel Search = new JLabel("Search:");
		Search.setBounds(485,394,100,80);
		Search.setFont(new Font("Arial",Font.BOLD,19));
		Frame.add(Search);
		
		JTextField SearchField = new JTextField();
		SearchField.setBounds(475,450,100,30);
		Frame.add(SearchField);
		
		SearchField.getDocument().addDocumentListener(new DocumentListener() {
			
			
			@SuppressWarnings("unused")
			public void filterTable() {
				String searchText = SearchField.getText().trim().toLowerCase();
				Model.setRowCount(0);
				try (Connection conn = DBConnection.getConnection()) {
		            String sql = "SELECT * FROM Products WHERE LOWER(ProductName) LIKE ?";
		            PreparedStatement ps = conn.prepareStatement(sql);
		            ps.setString(1, "%" + searchText + "%");

		            ResultSet rs = ps.executeQuery();
		            while (rs.next()) {
		                String name = rs.getString("ProductName");
		                double price = rs.getDouble("ProductPrice");
		                int amount = rs.getInt("ProductAmount");
		                String size = rs.getString("Size");
		                String reciveDate = rs.getString("ReciveDate");
		                double recivePrice = rs.getDouble("RecivePrice");
		                int categoryID = rs.getInt("CategoryID");
		                String status = rs.getString("Status");
		                int code = rs.getInt("ProductCode");

		                Model.addRow(new Object[]{name, price, amount, size, reciveDate, recivePrice, categoryID, status, code});
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
				
			}
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				filterTable();
				
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				filterTable();
				
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				filterTable();
				
			}
		});
		
		JButton SearchButton = new JButton("Search");
		SearchButton.setBounds(475,490,100,30);
		SearchButton.setFont(new Font("Arial",Font.BOLD,19));
		Frame.add(SearchButton);
		SearchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String SearchTXT = SearchField.getText();
				
				for(int k=Model.getRowCount() -1 ;k >= 0  ; k--) {
					String ProductName = Model.getValueAt(k, 0).toString();
					
					if(!ProductName.equalsIgnoreCase(SearchTXT)) {
						Model.removeRow(k);
					}
					
					
					if(Model.getRowCount()==0) {
						JOptionPane.showMessageDialog(null,"ასეთი პროდუქტი არ არის ცხრილში","შეცდომა",JOptionPane.ERROR_MESSAGE);
					}
				}
				
				
				
				
				
			}
		});
		
		
		
		JButton saveButton = new JButton("Save");
		saveButton.setBounds(360,420,100,30);
		saveButton.setFont(new Font("Arial",Font.BOLD,19));
		Frame.add(saveButton);
		saveButton.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				
				
				int row = Table.getRowCount() - 1;
		        if (row < 0) {
		            JOptionPane.showMessageDialog(null, "ცხრილი ცარიელია", "შეცდომა", JOptionPane.ERROR_MESSAGE);
		            return;
		        }

		        for (int col = 0; col < 9; col++) {
		            Object value = Table.getValueAt(row, col);
		            if (value == null || value.toString().trim().isEmpty()) {
		                JOptionPane.showMessageDialog(null, "შეიყვანეთ ყველა ველი ცხრილში", "შეცდომა", JOptionPane.ERROR_MESSAGE);
		                return;
		            }
		        }

		        try {
		            String ProductName = Table.getValueAt(row, 0).toString();
		            double ProductPrice = Double.parseDouble(Table.getValueAt(row, 1).toString());
		            int ProductAmount = Integer.parseInt(Table.getValueAt(row, 2).toString());
		            String Size = Table.getValueAt(row, 3).toString();
		            String ReciveDate = Table.getValueAt(row, 4).toString();
		            double RecivePrice = Double.parseDouble(Table.getValueAt(row, 5).toString());
		            int CategoryID = Integer.parseInt(Table.getValueAt(row, 6).toString());
		            String Status = Table.getValueAt(row, 7).toString();
		            int ProductCode = Integer.parseInt(Table.getValueAt(row, 8).toString());

		            try (Connection conn = DBConnection.getConnection()) {
		                String Insert = "INSERT INTO Products(ProductName, ProductPrice, ProductAmount, Size, ReciveDate, RecivePrice, CategoryID, Status, ProductCode) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		                PreparedStatement ps = conn.prepareStatement(Insert);
		                ps.setString(1, ProductName);
		                ps.setDouble(2, ProductPrice);
		                ps.setInt(3, ProductAmount);
		                ps.setString(4, Size);
		                ps.setString(5, ReciveDate);
		                ps.setDouble(6, RecivePrice);
		                ps.setInt(7, CategoryID);
		                ps.setString(8, Status);
		                ps.setInt(9, ProductCode);

		                int check = ps.executeUpdate();
		                if (check > 0) {
		                    JOptionPane.showMessageDialog(null, "ჩაემატა ბაზაში პროდუქტი", "წარმატება", JOptionPane.INFORMATION_MESSAGE);
		                }

		            } catch (SQLException ex) {
		                ex.printStackTrace();
		            }

		        } catch (Exception ex) {
		            
		        }
		    }
		});
		
		JButton addButton = new JButton("Add");
		addButton.setBounds(15,420,100,30);
		addButton.setFont(new Font("Arial",Font.BOLD,19));
		Frame.add(addButton);
		
		addButton.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel Model = (DefaultTableModel) Table.getModel();
				Model.addRow(new Object[] {"","","","","",""});
				
				
				
			}
		});
		
		JButton editButton = new JButton("Edit");
		editButton.setBounds(130,420,100,30);
		editButton.setFont(new Font("Arial",Font.BOLD,19));
		Frame.add(editButton);
		
		editButton.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				int selectedRow = Table.getSelectedRow();
				if(selectedRow == -1 ) {
					JOptionPane.showMessageDialog(null, "გთხოვთ მონიშნოთ პროდუქტი განახლებისთვის", "შეცდომა", JOptionPane.ERROR_MESSAGE);
		            return;
				}
				String productName = Table.getValueAt(selectedRow, 0) != null ? Table.getValueAt(selectedRow, 0).toString().trim() : "";
		        String productPriceStr = Table.getValueAt(selectedRow, 1) != null ? Table.getValueAt(selectedRow, 1).toString().trim() : "";
		        String productAmountStr = Table.getValueAt(selectedRow, 2) != null ? Table.getValueAt(selectedRow, 2).toString().trim() : "";
		        String size = Table.getValueAt(selectedRow, 3) != null ? Table.getValueAt(selectedRow, 3).toString().trim() : "";
		        String reciveDate = Table.getValueAt(selectedRow, 4) != null ? Table.getValueAt(selectedRow, 4).toString().trim() : "";
		        String recivePriceStr = Table.getValueAt(selectedRow, 5) != null ? Table.getValueAt(selectedRow, 5).toString().trim() : "";
		        String categoryIdStr = Table.getValueAt(selectedRow, 6) != null ? Table.getValueAt(selectedRow, 6).toString().trim() : "";
		        String status = Table.getValueAt(selectedRow, 7) != null ? Table.getValueAt(selectedRow, 7).toString().trim() : "";
		        String productCodeStr = Table.getValueAt(selectedRow, 8) != null ? Table.getValueAt(selectedRow, 8).toString().trim() : "";
	            
	            if(productCodeStr.isEmpty()) {
	            	JOptionPane.showMessageDialog(null, "ProductCode სვეტი აუცილებელია განახლებისთვის", "შეცდომა", JOptionPane.ERROR_MESSAGE);
	                return;
	            }
	            int productCode = Integer.parseInt(productCodeStr);
	        
	            StringBuilder sql = new StringBuilder("UPDATE Products SET ");
	            List<Object> List = new ArrayList<>();

	            if (!productName.isEmpty()) {
	                sql.append("ProductName = ?, ");
	                List.add(productName);
	            }
	            if (!productPriceStr.isEmpty()) {
	                sql.append("ProductPrice = ?, ");
	                List.add(Double.parseDouble(productPriceStr));
	            }
	            if (!productAmountStr.isEmpty()) {
	                sql.append("ProductAmount = ?, ");
	                List.add(Integer.parseInt(productAmountStr));
	            }
	            if (!size.isEmpty()) {
	                sql.append("Size = ?, ");
	                List.add(size);
	            }
	            if (!reciveDate.isEmpty()) {
	                sql.append("ReciveDate = ?, ");
	                List.add(reciveDate);
	            }
	            if (!recivePriceStr.isEmpty()) {
	                sql.append("RecivePrice = ?, ");
	                List.add(Double.parseDouble(recivePriceStr));
	            }
	            if (!categoryIdStr.isEmpty()) {
	                sql.append("CategoryID = ?, ");
	                List.add(Integer.parseInt(categoryIdStr));
	            }
	            if (!status.isEmpty()) {
	                sql.append("Status = ?, ");
	                List.add(status);
	                
	            }
	            sql.setLength(sql.length() - 2);
	            sql.append(" WHERE ProductCode = ?");
	            List.add(productCode);
	            
	            try (Connection conn = DBConnection.getConnection()) {
	                PreparedStatement ps = conn.prepareStatement(sql.toString());

	                for (int i = 0; i < List.size(); i++) {
	                    ps.setObject(i + 1, List.get(i));
	                }

	                int updated = ps.executeUpdate();
	                if (updated > 0) {
	                    JOptionPane.showMessageDialog(null, "პროდუქტი წარმატებით განახლდა", "წარმატება", JOptionPane.INFORMATION_MESSAGE);
	                } else {
	                    JOptionPane.showMessageDialog(null, "ვერ განახლდა მონაცემები", "შეცდომა", JOptionPane.ERROR_MESSAGE);
	                }

	            } catch (Exception ex) {
	                ex.printStackTrace();
	                JOptionPane.showMessageDialog(null, "შეცდომა განახლებისას: " + ex.getMessage(), "შეცდომა", JOptionPane.ERROR_MESSAGE);
	            }
	            
			}
			
			
			
		});
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.setBounds(245,420,100,30);
		deleteButton.setFont(new Font("Arial",Font.BOLD,19));
		Frame.add(deleteButton);
		
		deleteButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedrow = Table.getSelectedRow();
				
				if(selectedrow != -1) {
					int ProductCode = Integer.parseInt(Table.getValueAt(selectedrow, 8).toString());
					
					try(Connection conn=DBConnection.getConnection()){
						String Delete = ("Delete From Products Where ProductCode = ?");
						PreparedStatement ps = conn.prepareStatement(Delete);
						
						ps.setInt(1, ProductCode);
						ps.executeUpdate();
						
						Model.removeRow(selectedrow);
						
						JOptionPane.showMessageDialog(null,"პროდუქტი წარმატებით წაიშალა ბაზიდან","წარმატება",JOptionPane.INFORMATION_MESSAGE);
						
						
						
						
						
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					
				}
				
			}
		});
		
		
		JButton Add1 = new JButton("Add");
		Add1.setBounds(650,420,80,30);
		Add1.setFont(new Font("Arial",Font.BOLD,19));
		Frame.add(Add1);
		
		Add1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel Model1 = (DefaultTableModel) Table1.getModel();
				Model1.addRow(new Object[] {""});
				
				
			}
				
		});
		
		JButton Edit = new JButton("Edit");
		Edit.setBounds(650,455,80,30);
		Edit.setFont(new Font("Arial",Font.BOLD,19));
		Frame.add(Edit);
		
		Edit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			int selectedrow = Table1.getSelectedRow();
			String CategoryName = Table1.getValueAt(selectedrow, 0).toString();
			int ID = Integer.parseInt(Table1.getValueAt(selectedrow, 1).toString());
			try(Connection conn = DBConnection.getConnection()){
				
				String Update = ("Update Category set CategoryName = ? where CategoryID = ? ");
				PreparedStatement ps = conn.prepareStatement(Update);
				
				ps.setString(1, CategoryName);
				ps.setInt(2, ID);
				ps.executeUpdate();
				JOptionPane.showMessageDialog(null,"წარმატებით განახლდა მონაცემი","წარმატება",JOptionPane.INFORMATION_MESSAGE);
				
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			
			
			
				
			}
		});
		
		JButton Delete = new JButton("Delete");
		Delete.setBounds(650,490,80,30);
		Delete.setFont(new Font("Arial",Font.BOLD,14));
		Frame.add(Delete);
		Delete.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
			int selectedrow = Table1.getSelectedRow();
			
			if(selectedrow != -1) {
				int Id = Integer.parseInt(Table1.getValueAt(selectedrow, 1).toString());
				
				try(Connection conm = DBConnection.getConnection()){
					String Delete = ("Delete From Category where CategoryID = ?");
					PreparedStatement ps = conm.prepareStatement(Delete);
					
					ps.setInt(1, Id);
					ps.executeUpdate();
					
					Model1.removeRow(selectedrow);
					JOptionPane.showMessageDialog(null,"წარმატებით წაიშალა მონაცემი","წარმატება",JOptionPane.INFORMATION_MESSAGE);
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
			}
				
			}
		});
		
		JButton Save = new JButton("Save");
		Save.setBounds(650,525,80,30);
		Save.setFont(new Font("Arial",Font.BOLD,19));
		Frame.add(Save);
		
		Save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedrow = Table1.getRowCount() - 1;
				
				 if (selectedrow == -1) {
			            JOptionPane.showMessageDialog(null, "გთხოვთ მონიშნოთ კატეგორიის ველი ცხრილში");
			            return;
			        }
				String CategoryName = Table1.getValueAt(selectedrow, 0).toString().trim();
				
				try(Connection conn = DBConnection.getConnection()){
					String insert = ("insert into Category(CategoryName) Values(?)");
					PreparedStatement ps = conn.prepareStatement(insert);
					
					ps.setString(1, CategoryName);
					JOptionPane.showMessageDialog(null,"მონაცემი ჩაემატა ბაზაში","წარმატება",JOptionPane.INFORMATION_MESSAGE);
					ps.executeUpdate();
					} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
				
			}
		});
		
		
			
		
		
			
		ImageIcon bgIcon1 = new ImageIcon(getClass().getResource("userbackground.jpg"));
		JLabel Picture1 = new JLabel(bgIcon1);
		Picture1.setBounds(10,190,580,350);
		Frame.add(Picture1);
		
		
		ImageIcon bgIcon = new ImageIcon(getClass().getResource("Side.jpg"));
		JLabel Picture = new JLabel(bgIcon);
		Picture.setBounds(0,30,1000,65);
		Picture.setLayout(null);
		Frame.add(Picture);
		
		Frame.setVisible(true);
	}

}
