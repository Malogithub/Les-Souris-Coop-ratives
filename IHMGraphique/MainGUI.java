package IHMGraphique;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainGUI extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private JFrame frame;

	public static void main(String[] args) {
		new MainGUI();
	}

	public MainGUI() {
		super();
		initialize();
	}


	private void initialize() {
		frame = new JFrame("Les souris cooperatives");
		frame.setResizable(false);
		frame.getContentPane().setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		frame.setBounds(100, 100, 500, 300);
		frame.getContentPane().setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(frame.getParent());
		
		JButton buttonSelectPlayMode = new JButton("Start");
		buttonSelectPlayMode.addActionListener(this);
		
		buttonSelectPlayMode.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		buttonSelectPlayMode.setBounds(195,95, 130, 25);
		frame.getContentPane().add(buttonSelectPlayMode);
		
		JButton aide = new JButton("Infos");
		aide.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		aide.setBounds(195, 135, 130, 25);
		frame.getContentPane().add(aide);
		aide.addActionListener(this);
		
		JLabel labelImage = new JLabel("");
		labelImage.setIcon(new ImageIcon(MainGUI.class.getResource("/images/Jerry.jpg")));
		labelImage.setBounds(-10, 0, 500, 300);
		frame.getContentPane().add(labelImage);
		frame.setVisible(true);
	}
	
	
	public void actionPerformed(ActionEvent event) {
		
		
		if(event.getActionCommand().equals("Start")) {
			new Options();
			frame.dispose();
		}else if(event.getActionCommand().equals("Infos")) {
			new JOptionPane();
		JOptionPane.showMessageDialog(null,"Auteurs\n"+"RAULIN Malo\n"+"ZOUAOUI Mahdi\n"+"ZITOUNI Melissa","Genie Logiciel 2020",JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
}