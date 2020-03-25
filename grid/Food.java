package grid;

import java.awt.Image;

import javax.swing.ImageIcon;

import utility.GridParameters;
import utility.Position;


public class Food extends Sol {
	

	private int quant;
	private int clock;
	private int quantMouseEat;

	public Food(int abscisse, int ordonne, String Turn) {
		super(abscisse, ordonne);
		
		ImageIcon img = new ImageIcon(getClass().getResource("/images/terrain/food.png"));
		image = img.getImage();
		
		quant=GridParameters.foodQuantity(Turn);
		clock=GridParameters.foodTime(Turn);
		quantMouseEat=0;
	}

	public int getQuantMouseEat() {
		return quantMouseEat;
	}
	
	public int getClock() {
		return clock;
	}
	
	public int getQuantity() {
		return quant;
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
	public void setQuantMouseEat(int numberOfMouseEaten) {
		this.quantMouseEat=numberOfMouseEaten;
	}
	
	public void setClock(int time) {
		this.clock=time;
	}
	
	public void setQuant(int quantity) {
		this.quant=quantity;
	}
	
	
	public Position getPosition() {
		return new Position(getAbs(),getOrd());
	}
	public void mouseEatHere() {
		quantMouseEat++;
	}
	

	
	
	public void foodToEatTime() {
		foodByTime();
	}

	public void foodByTime() {
		if(quantMouseEat<2) 
			clock-=1;
		else if(quantMouseEat<4) 
			clock-=2;
		else if(quantMouseEat<6) 
			clock-=3;
		else
			clock-=4;
	}
	
	public boolean isVacant() {
		return (quant<=0);
	}
	
	public boolean foodOutdated() {
		return (clock<=0);
	}
	
	public void addQuant() {
		quant++;
	}
	
	public void addQuant(int quant) {
		this.quant+=quant;
	}
	

	public boolean consumed() {
		boolean consumed=true;
		if(!isVacant()) {
			quant--;
		}else
			consumed=false;
		
		return consumed;
	}
	
	
	public boolean isHerbe() {
		return false;
	}

	
	public boolean isFood() {
		return true;
	}
	
	public boolean isObstacle() {
		return false;
	}

	
	public boolean setQuant() {
		mouseEatHere();
		return consumed();
	}
	
	public String toString() {
		return "Food: "+quant+" + "+clock;
	}
}
