package grid;

import javax.swing.ImageIcon;



public class Herbe extends Sol{
	

	public Herbe(int abscisse, int ordonne) {
		super(abscisse, ordonne);
		ImageIcon img = new ImageIcon(getClass().getResource("/images/terrain/Herbe.png"));
		image = img.getImage();
	}
	

	public int getQuantity() {
		return 0;
	}

	
	public boolean isHerbe() {
		return true;
	}

	
	public boolean isFood() {
		return false;
	}

	
	public boolean isObstacle() {
		return false;
	}


	public boolean setQuant() {
		return false;
	}


	public void setQuant(int quantity) {
		
	}

}
