package grid;

import java.util.ArrayList;
import java.util.Iterator;

import IHMGraphique.Options;
import souris.Mouse;
import utility.GridParameters;
import utility.Position;



public class Grille {
	
	public int dim;
	
	private Case[][] cases;
	private ArrayList<Mouse> mouses=new ArrayList<Mouse>();;
	private ArrayList<Obstacle> obstacle;
	private ArrayList<Food> food;
	private GridParameters param;
	
	public Grille() {
		dim = Options.getDimension();
		cases = new Case[dim][dim];
		obstacle=new ArrayList<Obstacle>();
		food=new ArrayList<Food>();
	}


	
	public Case getCaseCoord(int abscisse,int ordonne) {
		return cases[abscisse][ordonne];
	}
	
	
	public void setBoxAt(int abscisse ,int ordonne,Case box) {
		cases[abscisse][ordonne] = box;
		
	}
	
	public void setBoxAtFree(int x, int  y, boolean bl) {
		cases[x][y].setisFree(bl);
	}
	
	public void setBox(int x , int y ,Sol gr) {
		cases[x][y].setType(gr);
	}
	
	public Case[][] getCases(){
		return cases;
	}
	
	public void setCases(Case[][] boxs){
		this.cases =boxs;
	}
	
	public int getDim() {
		return dim;
	}
	
	public ArrayList<Mouse> getMouses(){
		return mouses;
	}
	
	public ArrayList<Obstacle> getObstacle(){
		return obstacle;
	}
	
	public ArrayList<Food> getFood(){
		return food;
	}
	

	public Food getFoodAt(int abscisse, int ordonne) {
		Food foodPos=null;
		Position pos = new Position(abscisse, ordonne);
		
		for(Food f : food) {
			if(f.getPosition().equals(pos))
				foodPos=f;
		}
		
		return foodPos;
	}
	

	public Mouse getMouse(int index) {
		return mouses.get(index);
	}
	

	public Position getMoisePosition(Mouse m) {
		return m.getPosition();
	}
	

	public Mouse getMouseAt(Position p) {
		Mouse m = null;
		for(Iterator<Mouse>it=mouses.iterator(); it.hasNext();) {
			Mouse courantMouse = it.next();
			if(courantMouse.getPosition().equals(p)) {
				m = courantMouse;
			}
		}
		
		return m;
	}
	
	
	public GridParameters getGridParameters() {
		return param;
	}
	
				
	public void setGridParameters(GridParameters parameters) {
		this.param=parameters;
	}

	
	public void addMouse(Mouse m) {
		if(!mouses.contains(m))
			mouses.add(m);
	}
	

	public void addFood(Food f) {
		if(!food.contains(f))
			food.add(f);
	}
	

	public void addObstacle(Obstacle o) {
		if(!obstacle.contains(o))
			obstacle.add(o);
	}
	
	
	public void deleteMouse(Mouse m) {
		for(int i=0; i<mouses.size();i++)
			if(m.equals(mouses.get(i)))
				mouses.remove(i);
	}
	
	
	public void deleteFood(Food f) {
		for(int i=0; i<food.size();i++)
			if(f.equals(food.get(i))) 
				food.remove(f);
	}
	

	public boolean isMousePosition(Position p) {
		boolean isMousePosition = false;
		for(Iterator<Mouse>it = mouses.iterator(); it.hasNext(); ) {
			Position position = it.next().getPosition();
			if(position.equals(p)) {
				isMousePosition=true;
			}
		}
		return isMousePosition;
	}
	

	public boolean isFoodPosition(Position p) {
		boolean isMousePosition = false;
		for(Iterator<Food>it = food.iterator(); it.hasNext(); ) {
			Position position = it.next().getPosition();
			if(position.equals(p)) {
				isMousePosition=true;
			}
		}
		return isMousePosition;
	}
	
	public boolean prefDistanceObstacle(Position p) {
		
		boolean isInGoodPlace=true;
		
		for(int i=0; i<getObstacle().size(); i++) {
			Position p1 = getObstacle().get(i).position;
			if(p1.distance(p) <= 2d)
				isInGoodPlace = false;
		}
		return isInGoodPlace;
	}
	
	public boolean prefDistanceFood(Position p) {
		
		boolean isInGoodPlace=true;
		
		for(int i=0; i<getFood().size(); i++) {
			Position p1 = getFood().get(i).position;
			if(p1.distance(p) <= 2d)
				isInGoodPlace = false;
		}
		return isInGoodPlace;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\t\t\t\t\t\t\t\t\t");
		for(int i=0; i<dim; i++) {
			for(int j=0; j<dim; j++) {
				Sol soil = getCaseCoord(i, j).getType();
					 if(soil.isHerbe()) {
						sb.append(" ");
					}else if(soil.isFood()) {
						sb.append('f');
					}else if(soil.isObstacle()) {
						sb.append("o");
					}
			}
			sb.append("\n");
			sb.append("\t\t\t\t\t\t\t\t\t");
		}
		return sb.toString();
	}
}