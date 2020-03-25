package IHMGraphique;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

import souris.Mouse;


public class JpanelInfo extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Mouse mouse;
	
	private JPanel panel = new JPanel();
	
	
	private Box mouseBox = Box.createVerticalBox();
	private Box mouseInfosBox = Box.createVerticalBox();


	private JPanel mouseNameLabelPanel = new JPanel();
	private JPanel ageLabelPanel = new JPanel();
	private JPanel lifeLabelPanel = new JPanel();
	private JPanel TypeLabelPanel = new JPanel();
	private JPanel reliabilityLabelPanel = new JPanel();
	
	
	private JLabel mouseNameLabel = new JLabel("Nom :");
	private JLabel ageLabel = new JLabel("Age :");
	private JLabel lifeLabel = new JLabel("Energie : ");
	private JLabel typeLabel = new JLabel("Type :");
	private JLabel behavoirLabel = new JLabel("Caractere :");


	private JTextArea mouseInfos = new JTextArea("");
	
	public JpanelInfo(IHM environment) {
		super();		
		init();
		this.add(panel);
	}
	
	public Mouse getMouse() {
		return mouse;
	}
	
	
	public void init() {
		
		mouseBox.add(mouseNameLabelPanel);
		mouseBox.add(mouseInfosBox);
		mouseInfosBox.add(ageLabelPanel);
		mouseInfosBox.add(lifeLabelPanel);
		mouseInfosBox.add(TypeLabelPanel);
		mouseInfosBox.add(reliabilityLabelPanel);

		
		
		mouseNameLabel.setFont(new Font("Tahoma", Font.BOLD , 20));
		mouseNameLabel.setBounds(50,5, 200, 50);
		
		ageLabel.setFont(new Font("Tahoma", Font.BOLD , 20));
		ageLabel.setBounds(50,270, 200, 50);
		
		lifeLabel.setFont(new Font("Tahoma", Font.BOLD , 20));
		lifeLabel.setBounds(50,325, 200, 50);
		
		typeLabel.setFont(new Font("Tahoma", Font.BOLD , 20));
		typeLabel.setBounds(50,380, 200, 50);
		
		behavoirLabel.setFont(new Font("Tahoma", Font.BOLD , 20));
		behavoirLabel.setBounds(50,435, 200, 50);
			
		
		mouseNameLabelPanel.add(mouseNameLabel);
		ageLabelPanel.add(ageLabel);
		lifeLabelPanel.add(lifeLabel);
		TypeLabelPanel.add(typeLabel);
		reliabilityLabelPanel.add(behavoirLabel);
		
		
		
		this.add(mouseBox);
	}
	
	public void updateInfos(Mouse m) {
		this.mouse = m;
		this.add(mouseBox);
		
		//test console
		System.out.println("Nom :" + mouse.getName());
		System.out.println("Age :" + m.getAge());
		System.out.println("Energie :" + m.getLife());
		
		//ihm graphique
		mouseNameLabel.setText("Nom :" + mouse.getName());
		ageLabel.setText("Age : " + m.getAge());
		lifeLabel.setText("Energie : " + m.getLife());
		
		if(m.isReceptive()) {
			typeLabel.setText("Type : Receptive");
		}
		else {
			typeLabel.setText("Type : Nihiliste");
		}
		behavoirLabel.setText("Caractère : "+m.getBehavior().type());
		
		if (mouse.getLife() <= 0)
			mouseInfos.setText(m.getName() + " est morte par manque de nourriture");
		if (mouse.getAge() >= 220)
			mouseInfos.setText(m.getName() + " est morte de vieillesse");

		this.revalidate();
		
	}
	
	public void updateInfos() {
		if(mouse != null)
			updateInfos(mouse);
	}

	
}
