package IHMGraphique;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import divers.Orientation;
import divers.RandomValues;
import engine.MouseManagement;
import grid.Case;
import grid.Food;
import grid.Obstacle;
import souris.CreatMouses;
import souris.Memory;
import souris.Mouse;
import souriscomportement.Cooperative;
import souriscomportement.Selfish;
import utility.GridParameters;
import utility.Position;

@SuppressWarnings("serial")
public class Windows extends JFrame implements Runnable,MouseListener{

	private static final int THREAD_MAP = GridParameters.threadMap(GridParameters.getInstance().getSol());
	private MouseManagement moteur;
	private IHM environment = new IHM();
	private JpanelInfo infosMice;
	private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private JButton playButton;
	private boolean stop = true;
	private JList<String> storyList;
	private static JComboBox comboBoxDidMice;
	public Windows(String title) {
		super(title);
		setFocusable(true);
		GridParameters parameters = GridParameters.getInstance();
		moteur = new MouseManagement(parameters);
		moteur.generGrid();
		environment.setGrid(moteur.getGrid());
		infosMice = new JpanelInfo(environment);
		environment.addMouseListener(this);
		init();
		launchGUI();
	}
	
	private void launchGUI() {
		stop = false;		
		Thread chronoThread = new Thread(this);
		chronoThread.start();
	}
	
	
	
	public void init() {
		
		new JFrame("Les souris coopératives");
		setResizable(false);
		setSize(1600,800);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		
		playButton = new JButton(" Pause ");
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (stop) {
					playButton.setText(" Pause ");
					launchGUI();
				} else {
					stop = true; 
					playButton.setText(" Play ");
					playButton.setFocusable(false);
				}
			}			
		});
		
			
		JButton nextTurnButton = new JButton("Next");
		nextTurnButton.setFocusable(false);
		nextTurnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moteur.NextTurn();
				updateGUI();
			}
		});
			
	
		JPanel operationZone = new JPanel();
		operationZone.setLayout(null);
		operationZone.setBounds(5,5,390,590);
		operationZone.add(playButton);

		// deplacer la fenetre infos
		
		JPanel statPanel = new JPanel();
		statPanel.setBounds(1200,5,385,470);
		statPanel.add(infosMice);
		
		add(statPanel);
		
		JPanel generPane = new JPanel();
		generPane.setLayout(null);
		generPane.setBounds(5,5,390,590);
		
		JLabel title = new JLabel("Elements à ajouter");
		title.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		title.setBounds(100,5,300,20);
		generPane.add(title);
					
		JLabel lblFrequencefood = new JLabel("Ajouter de la nourriture");
		lblFrequencefood.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		lblFrequencefood.setBounds(20,230,300,20);
		generPane.add(lblFrequencefood);
		
		JTextField tfFood = new JTextField();
		tfFood.setText(""+Options.getFreqFood());
		tfFood.setBounds(130,260,70,25);
		generPane.add(tfFood);
		
		JLabel lblDensityOfObstacles = new JLabel("Ajouter des obstacles");
		lblDensityOfObstacles.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		lblDensityOfObstacles.setBounds(20,320,300,20);
		generPane.add(lblDensityOfObstacles);
		
		JTextField tfObstacles = new JTextField();
		tfObstacles.setText(""+Options.getFreqObstacles());
		tfObstacles.setBounds(130,350,70,25);
		generPane.add(tfObstacles);
	
		JLabel lblNumberOfMouses = new JLabel("Ajouter des souris");
		lblNumberOfMouses.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		lblNumberOfMouses.setBounds(20,60,300,20); 
		generPane.add(lblNumberOfMouses);
		
		// nombres de souris a ajouter
		
		JTextField tfMouse = new JTextField();
		tfMouse.setText(""+Options.getNbMouses());
		tfMouse.setBounds(130,90,70,25); 
		generPane.add(tfMouse);

		
		ButtonGroup behaviorbuttons = new ButtonGroup();
		ButtonGroup typeMousebuttons = new ButtonGroup();
		
		JRadioButton cooperativeRadButn = new JRadioButton("COOPERATIVE",true);
		cooperativeRadButn.setActionCommand("Cooperative");
		cooperativeRadButn.setBounds(75,155,120,25);
		behaviorbuttons.add(cooperativeRadButn);
		generPane.add(cooperativeRadButn);		
		
		JRadioButton selfishRadButn = new JRadioButton("EGOISTE");
		selfishRadButn.setActionCommand("Selfish");
		selfishRadButn.setBounds(75,175,90,25);
		behaviorbuttons.add(selfishRadButn);
		generPane.add(selfishRadButn);		

		JRadioButton nehilistRadButn = new JRadioButton("NIHILISTE");
		nehilistRadButn.setActionCommand("Nihilist");
		nehilistRadButn.setBounds(75,115,90,25);
		typeMousebuttons.add(nehilistRadButn);
		generPane.add(nehilistRadButn);
		
		JRadioButton receptiveRadButn = new JRadioButton("RECEPTIVE",true);
		receptiveRadButn.setActionCommand("Receptive");
		receptiveRadButn.setBounds(75,135,90,25);
		typeMousebuttons.add(receptiveRadButn);
		generPane.add(receptiveRadButn);
		
		JButton addMouseButn = new JButton("Ajouter");
		addMouseButn.setFocusable(false);
		addMouseButn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Mouse mouse;
				stop=true;
				
				try {
					int miceNumber = Integer.parseInt(tfMouse.getText());
					for(int i = 0; i< miceNumber; i++) {
						Position choosePoin=null;
						int x,y;
						do {
							x= RandomValues.randomInt(1,Options.getDimension()-2);
							y= RandomValues.randomInt(1,Options.getDimension()-2);
						}while(!moteur.getGrid().getCaseCoord(x, y).getisFree());
						choosePoin = new Position(x, y);	
				
						if(receptiveRadButn.isSelected()) {
							if(selfishRadButn.isSelected()) {
								mouse  = CreatMouses.creatReceptiveMouse(choosePoin,new Memory(),new Selfish(0),0,50);
							}else {
								mouse = CreatMouses.creatReceptiveMouse(choosePoin,new Memory(),new Cooperative(0),0,100);
							}
						}else {
							if(selfishRadButn.isSelected()) {
								mouse  = CreatMouses.creatReceptiveMouse(choosePoin,new Memory(),new Selfish(0),0,50);
							}else {
								mouse = CreatMouses.creatReceptiveMouse(choosePoin,new Memory(),new Cooperative(0),0,100);
				
							}
						}
						moteur.getGrid().addMouse(mouse);;
						}
				}catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null,"Vous n'avez pas mis de valeur", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
				updateGUI();
				stop=false;
			}			
		});
		addMouseButn.setBounds(115,200,100,25);
		generPane.add(addMouseButn);
		
		JButton addFoodButn = new JButton("Ajouter");
		addFoodButn.setFocusable(false);
		addFoodButn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				stop=true;
				try {
					int foodNumber = Integer.parseInt(tfFood.getText());
						int x,y;
						for(int i = 0 ; i<foodNumber ; i++ ) {
					
							do
							{
								x= RandomValues.randomInt(1,moteur.getParameters().getDim()-2);
								y= RandomValues.randomInt(1,moteur.getParameters().getDim()-2);
							}
							while(!moteur.getGrid().getCaseCoord(x, y).getisFree() && !moteur.getGrid().isFoodPosition(new Position(x,y)));
								Food f = Case.constructFood(x, y,"Start");
								moteur.getGrid().getCaseCoord(x, y).setType(f);
								moteur.getGrid().addFood(f);
						}
				}catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null,"Nombre autoriser depasser", "ERREUR", JOptionPane.ERROR_MESSAGE);
				}
				updateGUI();
				stop=false;
			}
		});
		addFoodButn.setBounds(115,290,100,25);
		generPane.add(addFoodButn);
		
		JButton addObstacleButn = new JButton("Ajouter");
		addObstacleButn.setFocusable(false);
		addObstacleButn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				stop=true;
				try {
					int NbObs = Integer.parseInt(tfObstacles.getText());
					if(NbObs+moteur.getGrid().getObstacle().size() > 50) {
						JOptionPane.showMessageDialog(null,"Attention aux nombres d'obstacles !");
					}else {
						moteur.randomObstaclePos(NbObs);
					}
				}catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(null,"Entrez un nombre svp!", "ERREUR", JOptionPane.ERROR_MESSAGE);
				}
				updateGUI();
				stop=false;
			}			
		});
		addObstacleButn.setBounds(115,380,100,25);
		generPane.add(addObstacleButn);
		
		JPanel storyPane = new JPanel();
		storyPane.setLayout(null);
		storyList = new JList<String>();
		JScrollPane listStoryScroll = new JScrollPane(storyList);
		listStoryScroll.setBounds(10,10,100,200);

		// placer dans la fenetre
		environment.setBounds(400,5,750,750);
		nextTurnButton.setBounds(145,251,100,24);
		
		
		storyList = new JList<String>();
		listStoryScroll.setBounds(10,10,100,200);
		
		storyList.addMouseListener(this);
		playButton.setBounds(0,430,150,30);
		generPane.add(playButton);
		nextTurnButton.setBounds(235,430,150,30);
		generPane.add(nextTurnButton);
		
		
		getContentPane().add(environment);
		getContentPane().add(generPane);
		getContentPane().add(operationZone);


		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	

	public void mousePressed(java.awt.event.MouseEvent e) {
			Position p = new Position(e.getX()/30, e.getY()/30); // 30 = pas de distance entre les cases
					
			if(e.getClickCount() == 1) {
				Case b = moteur.getGrid().getCaseCoord(p.getAbs(), p.getOrd());
				if(b.getType().isFood()) {
					environment.setSelectedBox(p);
				}
				else if(moteur.getGrid().isMousePosition(p)){
					environment.setSelectedMice(p);
					infosMice.updateInfos(moteur.getGrid().getMouseAt(p));
				
				}
				repaint();
			}	

	}
	
	
	public void updateGUI() {
		infosMice.updateInfos();
		environment.updateUI();
		environment.repaint();
	}
	

	public void run() {
		while(!stop) {	
			moteur.NextTurn();
			updateGUI();
			try {
				Thread.sleep(THREAD_MAP);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	public void mouseClicked(java.awt.event.MouseEvent arg0) {
		
	}
	@Override
	public void mouseEntered(java.awt.event.MouseEvent e) {
		
	}
	@Override
	public void mouseExited(java.awt.event.MouseEvent e) {
		
	}
	@Override
	public void mouseReleased(java.awt.event.MouseEvent e) {
		
	}
}