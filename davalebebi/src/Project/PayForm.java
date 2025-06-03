package Project;

import java.awt.Color;

import java.awt.Font;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.Format;

import javax.smartcardio.Card;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
	;
public class PayForm {
	

	public void PayFormm() {
		
		
		
		JFrame Frame = new JFrame("Payment");
		Frame.setSize(350,250);
		Frame.setLayout(null);
		Frame.setResizable(false);
		Frame.setLocationRelativeTo(null);
		ImageIcon Icon = new ImageIcon(getClass().getResource("PaymentLogo.jpg"));
		Frame.setIconImage(Icon.getImage());
		
		
		
		
		JButton CashButton = new JButton("Cash");
		CashButton.setBounds(118,50,100,30);
		Frame.add(CashButton);
		
		CashButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PayWithCash();
				
				Frame.dispose();
				
			}
		});
		
		JButton CardButton = new JButton("Card");
	    CardButton.setBounds(118,100,100,30);
	    Frame.add(CardButton);
	    CardButton.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,"მიუწვდომელია", "შეცდომა",JOptionPane.ERROR_MESSAGE);
				
			}
		});
		
		
		
		ImageIcon bgIcon = new ImageIcon(getClass().getResource("userbackground.jpg"));
		JLabel Background = new JLabel(bgIcon);
		Background.setBounds(0,0,600,400);
		Frame.add(Background);
		Frame.setVisible(true);
		
		
		
		
		
	}
	public void PayWithCash() {
	    JFrame Frame = new JFrame("Payment With Cash");
	    Frame.setSize(400, 300);
	    Frame.setLayout(null);
	    Frame.setLocationRelativeTo(null);
	    Frame.setResizable(false);

	    String amountPlaceholder = "Total Amount";
	    String moneyPlaceholder = "Money";
	    String changePlaceholder = "Change";

	    JTextField amountField = new JTextField(amountPlaceholder);
	    amountField.setForeground(Color.GRAY);
	    amountField.setFont(new Font("Arial", Font.BOLD, 20));
	    amountField.setBounds(133, 40, 130, 30);
	    Frame.add(amountField);

	    amountField.addFocusListener(new FocusAdapter() {
	        public void focusGained(FocusEvent e) {
	            if (amountField.getText().equals(amountPlaceholder)) {
	                amountField.setText("");
	                amountField.setForeground(Color.BLACK);
	            }
	        }

	        public void focusLost(FocusEvent e) {
	            if (amountField.getText().trim().isEmpty()) {
	                amountField.setText(amountPlaceholder);
	                amountField.setForeground(Color.GRAY);
	            }
	        }
	    });

	    JTextField moneyField = new JTextField(moneyPlaceholder);
	    moneyField.setForeground(Color.GRAY);
	    moneyField.setFont(new Font("Arial", Font.BOLD, 20));
	    moneyField.setBounds(133, 80, 130, 30);
	    Frame.add(moneyField);

	    moneyField.addFocusListener(new FocusAdapter() {
	        public void focusGained(FocusEvent e) {
	            if (moneyField.getText().equals(moneyPlaceholder)) {
	                moneyField.setText("");
	                moneyField.setForeground(Color.BLACK);
	            }
	        }

	        public void focusLost(FocusEvent e) {
	            if (moneyField.getText().trim().isEmpty()) {
	                moneyField.setText(moneyPlaceholder);
	                moneyField.setForeground(Color.GRAY);
	            }
	        }
	    });

	    JTextField changeField = new JTextField(changePlaceholder);
	    changeField.setForeground(Color.GRAY);
	    changeField.setFont(new Font("Arial", Font.BOLD, 20));
	    changeField.setBounds(133, 120, 130, 30);
	    changeField.setEditable(false);
	    Frame.add(changeField);

	    JButton payButton = new JButton("Pay");
	    payButton.setFont(new Font("Arial", Font.BOLD, 20));
	    payButton.setBounds(133, 160, 130, 30);
	    Frame.add(payButton);

	    payButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            try {
	                double totalAmount = Double.parseDouble(amountField.getText().trim());
	                double money = Double.parseDouble(moneyField.getText().trim());

	                if (money < totalAmount) {
	                    JOptionPane.showMessageDialog(null, "არასაკმარისი თანხა", "შეცდომა", JOptionPane.ERROR_MESSAGE);
	                    return;
	                }

	                double change = money - totalAmount;
	                DecimalFormat df = new DecimalFormat("#.00");
	                changeField.setText(df.format(change));
	                changeField.setForeground(Color.BLACK);

	                
	                
	                

	                JOptionPane.showMessageDialog(null, "გადახდა წარმატებით განხორციელდა");

	            } catch (NumberFormatException ex) {
	                JOptionPane.showMessageDialog(null, "გთხოვთ შეიყვანოთ მხოლოდ რიცხვები", "შეცდომა", JOptionPane.ERROR_MESSAGE);
	            }
	        }
	    });
	    
	    

	    ImageIcon bgIcon = new ImageIcon(getClass().getResource("Side.jpg"));
	    JLabel Background = new JLabel(bgIcon);
	    Background.setSize(400, 300);
	    Frame.add(Background);

	    Frame.setVisible(true);
	}

	
	public void PaywithCard() {
		
		
		
	}

}
