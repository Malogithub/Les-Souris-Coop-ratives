package IHMGraphique;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import IHMGraphique.Windows;
import utility.GridParameters;

public class Options extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	
	
	private JFrame frameMode;
	private static JTextField textNumberMouses;
	@SuppressWarnings("rawtypes")
	private static JComboBox comboBoxFood, comboBoxObstacles;
	
	
	
	public Options() {
		initialize();
	}

	
	
	
	
	public static int getFreqFood() {
		int freqFood = valueOfFreq(comboBoxFood.getSelectedItem().toString());
		return freqFood;
	}
	public static int getFreqObstacles() {
		int freqObstacles = valueOfFreq(comboBoxObstacles.getSelectedItem().toString());
		return freqObstacles;
	}
	
	// dim grille
	
	public static int getDimension() {
		return 25;
	}
	public static int getNbMouses() {
		return Integer.parseInt(textNumberMouses.getText());
	}
				
	public static int valueOfFreq(String freq) {
		switch (freq) {
			case "25" :
				return 25;
			case "15" :
				return 15;
			case "5" :
				return 5;
		}
		return 0;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initialize() {
		frameMode = new JFrame();
		frameMode.setTitle("Les souris cooperatives");
		frameMode.setResizable(false);
		frameMode.setBounds(100, 100,700,300);
		frameMode.setVisible(true);
		frameMode.setBackground(Color.WHITE);
		frameMode.getContentPane().setBackground(Color.LIGHT_GRAY);
		frameMode.getContentPane().setLayout(null);
		frameMode.setLocationRelativeTo(frameMode.getParent());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JLabel lblSelectYourGame = new JLabel("Parametres de la partie");
		lblSelectYourGame.setForeground(Color.WHITE);
		lblSelectYourGame.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		lblSelectYourGame.setBounds(25, 11, 400, 22);
		frameMode.getContentPane().add(lblSelectYourGame);
		
		JLabel lblFrequencefood = new JLabel("Nourritures :");
		lblFrequencefood.setForeground(Color.WHITE);
		lblFrequencefood.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		lblFrequencefood.setBounds(10, 104, 210, 22);
		frameMode.getContentPane().add(lblFrequencefood);
		
		comboBoxFood = new JComboBox();
		comboBoxFood.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		comboBoxFood.addItem("25");
		comboBoxFood.addItem("15");
		comboBoxFood.addItem("5");
		comboBoxFood.setForeground(Color.WHITE);
		comboBoxFood.setBackground(Color.DARK_GRAY);
		comboBoxFood.setBounds(160, 107, 100, 20);
		frameMode.getContentPane().add(comboBoxFood);
		
		JLabel lblDensityOfObstacles = new JLabel("Obstacles :");
		lblDensityOfObstacles.setForeground(Color.WHITE);
		lblDensityOfObstacles.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		lblDensityOfObstacles.setBounds(400, 104, 210, 22);
		frameMode.getContentPane().add(lblDensityOfObstacles);
		
		comboBoxObstacles = new JComboBox();
		comboBoxObstacles.addItem("25");
		comboBoxObstacles.addItem("15");
		comboBoxObstacles.addItem("5");
		comboBoxObstacles.setForeground(Color.WHITE);
		comboBoxObstacles.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		comboBoxObstacles.setBackground(Color.DARK_GRAY);
		comboBoxObstacles.setBounds(550, 107, 100, 20);
		frameMode.getContentPane().add(comboBoxObstacles);
		
		JLabel lblNumberOfMouses = new JLabel("Souris :");
		lblNumberOfMouses.setForeground(Color.WHITE);
		lblNumberOfMouses.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		lblNumberOfMouses.setBounds(300, 185, 210, 22);
		frameMode.getContentPane().add(lblNumberOfMouses);
		
		textNumberMouses = new JTextField("10");
		textNumberMouses.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		textNumberMouses.setBounds(370, 185, 27, 20);
		frameMode.getContentPane().add(textNumberMouses);
		textNumberMouses.setColumns(8);
		
		
		JButton buttonBack = new JButton("<- retour");
		buttonBack.addActionListener(this);
		buttonBack.setBounds(95,220,100,20);
		frameMode.getContentPane().add(buttonBack);
		
		
		JButton buttonRun = new JButton("Start");
		buttonRun.addActionListener(this);
		buttonRun.setActionCommand("run");
		buttonRun.setBounds(500, 220, 100,20);
		frameMode.getContentPane().add(buttonRun);
				
		JLabel labelImage = new JLabel("");
		labelImage.setVerticalAlignment(SwingConstants.BOTTOM);
		labelImage.setIcon(new ImageIcon(Options.class.getResource("/images/parametres.png")));
		labelImage.setBounds(0, 0, 1200, 296);
		frameMode.getContentPane().add(labelImage);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		try {
			if(Integer.parseInt(textNumberMouses.getText())<0 || Integer.parseInt(textNumberMouses.getText())>40) {
				JOptionPane.showMessageDialog(this, "ENTREZ UN NOMBRE SVP!", "ERREUR", JOptionPane.ERROR_MESSAGE);
				new MainGUI();
			}
			else
				if(event.getActionCommand().equals("run")) {
					GridParameters.getInstance().setAll(getFreqObstacles(), getFreqFood(), getNbMouses(), getDimension(),"Sol");
					new Windows("Les souris cooperatives ");
				}else if(event.getActionCommand().equals("<- back")) {
					new MainGUI();
				}
		
				frameMode.dispose();
		} catch(NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Entrez un nombre !", "ERREUR", JOptionPane.ERROR_MESSAGE);
		}		
	}
}