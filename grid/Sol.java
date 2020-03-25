package grid;

import java.awt.Image;

import utility.Position;


// Cette classe est contenu dans chaque case et permet de recuperer le type

public abstract class Sol {
		
	public Position position;
	public Image image;
	
	public Sol(int abscisse, int ordonne) {
		position = new Position(abscisse,ordonne);
	}

	public int getAbs() {
		return position.getAbs();
	}
	public int getOrd() {
		return position.getOrd();
	}
	public Image getPics() {
		return image;
	}
				
	public void setAbscisse(int abscisse) {
		position.setAbs(abscisse);
	}
	public void setOrdonne(int ordonne) {
		position.setOrd(ordonne);
	}
			
	public abstract boolean isHerbe();
	public abstract boolean isFood();
	public abstract boolean isObstacle();
	public abstract int getQuantity();
	public abstract boolean setQuant();
	public abstract void setQuant(int quantity);
}
