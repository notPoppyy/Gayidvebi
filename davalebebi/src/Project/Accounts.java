package Project;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class Accounts {

    JFrame frame;
    JTable accountTable, staffTable;
    DefaultTableModel accountModel, staffModel;
    JButton btnAddAccountRow, btnSaveAccount, btnUpdateAccount, btnDeleteAccount;
    JButton btnAddStaffRow, btnSaveStaff, btnUpdateStaff, btnDeleteStaff, btnBack;

    public void ShowForm() {
        frame = new JFrame("Accounts & Staff Management");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);

        JLabel lblAccounts = new JLabel("Accounts Table");
        lblAccounts.setFont(new Font("Arial", Font.BOLD, 16));
        lblAccounts.setBounds(20, 0, 200, 30);
        frame.add(lblAccounts);

        accountModel = new DefaultTableModel(new String[]{"Username", "Password", "Role", "StaffID"}, 0);
        accountTable = new JTable(accountModel);
        JScrollPane accountScroll = new JScrollPane(accountTable);
        accountScroll.setBounds(20, 30, 420, 200);
        frame.add(accountScroll);

        JLabel lblStaff = new JLabel("Staff Table");
        lblStaff.setFont(new Font("Arial", Font.BOLD, 16));
        lblStaff.setBounds(460, 0, 200, 30);
        frame.add(lblStaff);

        staffModel = new DefaultTableModel(new String[]{"StaffID", "Name", "Lastname"}, 0);
        staffTable = new JTable(staffModel);
        JScrollPane staffScroll = new JScrollPane(staffTable);
        staffScroll.setBounds(460, 30, 400, 200);
        frame.add(staffScroll);

        btnAddAccountRow = new JButton(" Add Account Row");
        btnAddAccountRow.setBounds(20, 250, 160, 30);
        frame.add(btnAddAccountRow);

        btnSaveAccount = new JButton(" Save Account");
        btnSaveAccount.setBounds(190, 250, 160, 30);
        frame.add(btnSaveAccount);

        btnUpdateAccount = new JButton(" Update Account");
        btnUpdateAccount.setBounds(20, 290, 160, 30);
        frame.add(btnUpdateAccount);

        btnDeleteAccount = new JButton(" Delete Account");
        btnDeleteAccount.setBounds(190, 290, 160, 30);
        frame.add(btnDeleteAccount);

        btnAddStaffRow = new JButton(" Add Staff Row");
        btnAddStaffRow.setBounds(460, 250, 160, 30);
        frame.add(btnAddStaffRow);

        btnSaveStaff = new JButton(" Save Staff");
        btnSaveStaff.setBounds(630, 250, 160, 30);
        frame.add(btnSaveStaff);

        btnUpdateStaff = new JButton(" Update Staff");
        btnUpdateStaff.setBounds(460, 290, 160, 30);
        frame.add(btnUpdateStaff);

        btnDeleteStaff = new JButton(" Delete Staff");
        btnDeleteStaff.setBounds(630, 290, 160, 30);
        frame.add(btnDeleteStaff);

        btnBack = new JButton("⬅️ Back");
        btnBack.setBounds(20, 500, 100, 30);
        btnBack.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				AdminForm ad = new AdminForm();
				ad.ShowForm();
				frame.dispose();
				
			}
		});
        frame.add(btnBack);

        btnDeleteAccount.addActionListener(e -> {
            int selectedrow = accountTable.getSelectedRow();
            if (selectedrow != -1) {
                try (Connection conn = DBConnection.getConnection()) {
                    String Delete = ("Delete from Account where StaffID = ?");
                    PreparedStatement ps = conn.prepareStatement(Delete);
                    int staffID = (int) accountTable.getValueAt(selectedrow, 3);
                    ps.setInt(1, staffID);
                    ps.executeUpdate();
                    accountModel.removeRow(selectedrow);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        btnDeleteStaff.addActionListener(e -> {
            int selectedRow = staffTable.getSelectedRow();
            if (staffTable.getSelectedRow() != -1) {
                try (Connection conn = DBConnection.getConnection()) {
                    String Delete = ("Delete from Staff where StaffID = ?");
                    PreparedStatement ps = conn.prepareStatement(Delete);
                    int staffId = (int) staffTable.getValueAt(selectedRow, 0);
                    ps.setInt(1, staffId);
                    ps.executeUpdate();
                    staffModel.removeRow(selectedRow);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        try (Connection conn = DBConnection.getConnection()) {
            String SQL = ("SELECT * FROM Account");
            PreparedStatement ps = conn.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String Username = rs.getString("Username");
                String Password = rs.getString("Password");
                String Role = rs.getString("Role");
                int StaffID = rs.getInt("StaffID");
                accountModel.addRow(new Object[]{Username, Password, Role, StaffID});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection con = DBConnection.getConnection()) {
            String SQL = ("SELECT * FROM Staff");
            PreparedStatement ps = con.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int StaffID = rs.getInt("StaffID");
                String Name = rs.getString("Name");
                String Lastname = rs.getString("Lastname");
                staffModel.addRow(new Object[]{StaffID, Name, Lastname});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        btnAddAccountRow.addActionListener(e -> accountModel.addRow(new Object[]{"", "", "", 0}));
        btnAddStaffRow.addActionListener(e -> staffModel.addRow(new Object[]{0, "", ""}));

        btnSaveAccount.addActionListener(e -> {
            int selectedRow = accountTable.getSelectedRow() ;
            if (selectedRow != -1) {
                String username = accountModel.getValueAt(selectedRow, 0).toString();
                String password = accountModel.getValueAt(selectedRow, 1).toString();
                String role = accountModel.getValueAt(selectedRow, 2).toString();
                int staffID = Integer.parseInt(accountModel.getValueAt(selectedRow, 3).toString());

                try (Connection conn = DBConnection.getConnection()) {
                    String sql = "INSERT INTO Account (Username, Password, Role, StaffID) VALUES (?, ?, ?, ?)";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, username);
                    ps.setString(2, password);
                    ps.setString(3, role);
                    ps.setInt(4, staffID);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(frame, "Account added successfully");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error adding account: " + ex.getMessage());
                }
            }
        });

        btnSaveStaff.addActionListener(e -> {
            int selectedRow = staffTable.getRowCount() -1 ;
            if (selectedRow != -1) {
                String name = staffModel.getValueAt(selectedRow, 1).toString();
                String lastname = staffModel.getValueAt(selectedRow, 2).toString();

                try (Connection conn = DBConnection.getConnection()) {
                    String sql = "INSERT INTO Staff (Name, Lastname) VALUES (?, ?)";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, name);
                    ps.setString(2, lastname);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(frame, "Staff added successfully");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error adding staff: " + ex.getMessage());
                }
            }
        });

        btnUpdateAccount.addActionListener(e -> {
            int selectedRow = accountTable.getSelectedRow();
            if (selectedRow != -1) {
                String username = accountModel.getValueAt(selectedRow, 0).toString();
                String password = accountModel.getValueAt(selectedRow, 1).toString();
                String role = accountModel.getValueAt(selectedRow, 2).toString();
                int staffID = Integer.parseInt(accountModel.getValueAt(selectedRow, 3).toString());

                try (Connection conn = DBConnection.getConnection()) {
                    String sql = "UPDATE Account SET Username = ?, Password = ?, Role = ? WHERE StaffID = ?";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, username);
                    ps.setString(2, password);
                    ps.setString(3, role);
                    ps.setInt(4, staffID);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(frame, "Account updated successfully");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error updating account: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Select a row to update");
            }
        });

        btnUpdateStaff.addActionListener(e -> {
            int selectedRow = staffTable.getSelectedRow();
            if (selectedRow != -1) {
                int staffID = Integer.parseInt(staffModel.getValueAt(selectedRow, 0).toString());
                String name = staffModel.getValueAt(selectedRow, 1).toString();
                String lastname = staffModel.getValueAt(selectedRow, 2).toString();

                try (Connection conn = DBConnection.getConnection()) {
                    String sql = "UPDATE Staff SET Name = ?, Lastname = ? WHERE StaffID = ?";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, name);
                    ps.setString(2, lastname);
                    ps.setInt(3, staffID);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(frame, "Staff updated successfully");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error updating staff: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Select a row to update");
            }
        });

        frame.getContentPane().setBackground(new Color(240, 248, 255));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
