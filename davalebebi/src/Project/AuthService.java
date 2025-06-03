package Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthService {
    public String authenticate(String username, String password) {
        String role = null;
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT Role FROM Account WHERE Username = ? AND Password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                role = rs.getString("Role");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return role;
    }

    public int getStaffID(String username) {
        int staffID = 1;
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT Account.StaffID FROM Account WHERE Account.Username = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                staffID = rs.getInt("StaffID");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return staffID;
    }

    public String getStaffName(int staffID) {
        String name = "";
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT Name FROM Staff WHERE StaffID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, staffID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                name = rs.getString("Name");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return name;
    }

    public String getStaffRole(int staffID) {
        String role = "";
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT Role FROM Account WHERE StaffID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, staffID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                role = rs.getString("Role");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return role;
    }
}
