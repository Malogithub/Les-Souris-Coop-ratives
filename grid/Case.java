package grid;

import grid.Sol;

// composition de la case

public class Case {
	

	private boolean isFree;
	private Sol Type;
	
	
	public Case(Sol Type) {
		isFree = true;
		this.Type = Type;
	}
	
	public Sol getType() {
		return Type;
	}
	
	public void setType(Sol Type) {
		this.Type = Type;
	}
	
	public boolean getisFree() {
		return isFree;
	}
	
	public void setisFree(boolean isFree) {
		this.isFree = isFree;
	}		
	
	public static Case constructCase(Sol scandType) {
		return new Case(scandType);
	}
	

	public static Herbe constructHerbe(int abs,int ord) {
		return new Herbe(abs,ord);
	}
	
	
	public static Obstacle creatObstacle(int abs,int ord) {
		return new Obstacle(abs,ord);
	}
	
	public static Obstacle creatObstacle(int abscisse,int ordonne,int index) {
		return new Obstacle(abscisse,ordonne,index);
	}
	

	public static Food constructFood(int abs,int ord, String Turn) {
		return new Food(abs,ord,Turn);
	}
}
	
	
			