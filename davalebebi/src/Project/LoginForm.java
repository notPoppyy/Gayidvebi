package Project;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class LoginForm {

    public void Login() {

        JFrame LoginFrame = new JFrame("Authorization");
        LoginFrame.setSize(600, 500);
        LoginFrame.setLayout(null);
        LoginFrame.setResizable(false);

        JLabel loginLabel = new JLabel("LOGIN");
        loginLabel.setBounds(257, 20, 150, 150);
        loginLabel.setFont(new Font("Arial", Font.BOLD, 24));
        loginLabel.setForeground(Color.white);
        LoginFrame.add(loginLabel);

        JLabel loginField = new JLabel("Username:");
        loginField.setBounds(140, 180, 200, 30);
        loginField.setFont(new Font("Arial", Font.PLAIN, 16));
        loginField.setForeground(Color.white);
        LoginFrame.add(loginField);

        JLabel passwordField = new JLabel("Password:");
        passwordField.setBounds(140, 235, 200, 30);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setForeground(Color.WHITE);
        LoginFrame.add(passwordField);

        JTextField usernameTextBox = new JTextField();
        usernameTextBox.setBounds(250, 180, 200, 30);
        LoginFrame.add(usernameTextBox);

        JPasswordField passwordTextBox = new JPasswordField();
        passwordTextBox.setBounds(250, 235, 200, 30);
        LoginFrame.add(passwordTextBox);

        JButton loginButton = new JButton("LOGIN");
        loginButton.setBounds(250, 320, 100, 30);
        LoginFrame.add(loginButton);
        LoginFrame.getRootPane().setDefaultButton(loginButton);

        String[] roles = {"Admin", "User"};
        JComboBox<String> roleComboBox = new JComboBox<>(roles);
        roleComboBox.setBounds(250, 280, 100, 30);
        LoginFrame.add(roleComboBox);

        JLabel checkLabel = new JLabel("Role:");
        checkLabel.setForeground(Color.WHITE);
        checkLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        checkLabel.setBounds(140, 280, 200, 30);
        LoginFrame.add(checkLabel);

        ImageIcon icon = new ImageIcon(getClass().getResource("Icon.jpg"));
        LoginFrame.setIconImage(icon.getImage());

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameTextBox.getText();
                String password = passwordTextBox.getText();
                String selectedRole = (String) roleComboBox.getSelectedItem();

                AuthService auth = new AuthService();
                String role = auth.authenticate(username, password);
                int staffID = auth.getStaffID(username);
                Session.currentStaffID = staffID;
                Session.currentStaffName = auth.getStaffName(staffID);
                Session.currentStaffRole = role;

                if (role != null && role.equals(selectedRole)) {
                    if (role.equals("Admin")) {
                        AdminForm admin = new AdminForm();
                        admin.ShowForm();
                        LoginFrame.dispose();
                    } else if (role.equals("User")) {
                        UserForm user = new UserForm();
                        user.showForm();
                        LoginFrame.dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect username, password or role", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        ImageIcon bgIcon2 = new ImageIcon(getClass().getResource("3409297.jpg"));
        JLabel background2 = new JLabel(bgIcon2);
        background2.setBounds(0, 0, 600, 500);
        background2.setLayout(null);
        LoginFrame.add(background2);

        LoginFrame.setLocationRelativeTo(null);
        LoginFrame.setVisible(true);
    }
}
