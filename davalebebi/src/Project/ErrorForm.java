package Project;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ErrorForm {
	
	public void ShowForm() {
		
		JFrame Frame = new JFrame("Error");
		Frame.setSize(500,90);
		Frame.setLayout(null);
		Frame.setLocationRelativeTo(null);
		
		
		
		JLabel Error = new JLabel("დაფიქსირდა შეცდომა ავტორიზაციისას");
		Error.setBounds(95,10,300,30);
		Error.setFont(new Font(null, Font.CENTER_BASELINE, 14));
		Frame.setResizable(false);
		Frame.add(Error);
		
	
		ImageIcon Icon = new ImageIcon(getClass().getResource("Error.jpg"));
		Frame.setIconImage(Icon.getImage());
		Frame.setVisible(true);
	}
}
