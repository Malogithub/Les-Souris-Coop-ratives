package grid;

import javax.swing.ImageIcon;



public class Obstacle extends Sol{

	private ObstacleType type;
	public Obstacle(int abscisse, int ordonne) {
		super(abscisse, ordonne);
		
		type=ObstacleType.typeRand();
		ImageIcon img = new ImageIcon(getClass().getResource("/images/terrain/"+type+".png"));
		image = img.getImage();
	}
	public Obstacle(int abscisse, int ordonne,int index) {
		super(abscisse, ordonne);
		index=index+1;
		ImageIcon img = new ImageIcon(getClass().getResource("/images/terrain/Obstacle"+index+".png"));
		image = img.getImage();
	}
	public int getQuantity() {
		return 0;
	}

	public boolean isHerbe() {
		return false;
	}

	public boolean isFood() {
		return false;
	}
	
	public boolean isObstacle() {
		return true;
	}
	
	public boolean setQuant() {
		return false;
	}
	
	public void setQuant(int quantity) {
		
	}
}
