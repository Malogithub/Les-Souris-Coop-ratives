package engine;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import IHMGraphique.Options;
import divers.Orientation;
import divers.RandomValues;
import grid.Food;
import grid.Obstacle;
import grid.Grille;
import grid.Case;
import souris.CreatMouses;
import souris.Memory;
import souris.Mouse;
import souriscomportement.Comportement;
import souriscomportement.Cooperative;
import souriscomportement.Selfish;
import utility.GridParameters;
import utility.Position;
import utility.GridBuilder;

public class MouseManagement {
	
	
	private Grille grid;
	private GridParameters parameters;
	private static int simulationNbTurn,foodAddFreq;
	private ArrayList<Mouse> mouseToKill=new ArrayList<Mouse>();
	private ArrayList<Mouse> mouseToAdd=new ArrayList<Mouse>();
	private ArrayList<Food> foodPosition=new ArrayList<Food>();
	private ArrayList<Food> foodInGrid;
	public static ArrayList<String>names=new ArrayList<String>();
	private int foodFreq = GridParameters.getInstance().getQntFood();

	
	public MouseManagement(GridParameters parameters) {
		nomsSouris("/names.txt");
		this.parameters=parameters;
		simulationNbTurn=1;
		foodAddFreq=foodGenerFreq();
	}
	
	public GridParameters getParameters() {
		return parameters;
	}
	
	public Grille getGrid() {
		return grid;
	}
	
	public static int getSimulationTurn() {
		return simulationNbTurn;
	}
	
	public ArrayList<Mouse> getMousesToKill(){
		return mouseToKill;
	}
	
	public ArrayList<Food> getCheese(){
		return foodInGrid;
	}
	
	
	public ArrayList<Mouse> getMousesToAdd(){
		return mouseToAdd;
	}
	
	public void setGridParameters(GridParameters parameters) {
		this.parameters=parameters;
	}
	
	public void setGrid(Grille grid) {
		this.grid=grid;
	}
	

	
	public void generGrid() {
		
		GridBuilder buildGrid = new GridBuilder(parameters);
		
		grid = buildGrid.getGrid();

	}
	
	
	public void NextTurn() {
				
		foodInGrid=grid.getFood();
			
		for(Mouse m : grid.getMouses()) {
			if(!m.isDead()) {
				MouseAction(m);
			}
		}
		
		KillMouses();
		bornMouse();
		
		foodAddFreq--;
		foodGeneration();
		
		for(int i=0; i<foodInGrid.size(); i++) {
			Food cheese=foodInGrid.get(i);
			
			foodAction(cheese);
		}
		
		
		simulationNbTurn++;
		
	}

	
	public void MouseAction(Mouse m) {
		Position pos = m.getPosition();
		if(!m.getIsTalking()) {
			m.mousesEvolu();
			lookAround(m);
		}
		
		else {
			m.talk();
		}
		
		if(m.getAge()>100 && m.getLife()>=70 && !m.isPragant()) {
			m.setIsPregnant(true);
		}
		
		if(inFoodPosition(pos) && m.mouseStayInPositionToEat()) {
			if(grid.getCaseCoord(pos.getAbs(), pos.getOrd()).getType().setQuant()) {
				m.mouseEat();
			}else {
				Food f = grid.getFoodAt(pos.getAbs(), pos.getOrd());
				foodAction(f);
			}
			
		}
		else if(!m.getIsTalking()){
			m.setIsEating(false);
				if(m.getLife()<35) {
					
					m.getMemory().setFoodLocation(m.getVision().getFoodView());
					m.getMemory().setObstacleLocation(m.getVision().getObstacleView());
					m.getMemory().orderFoodMermory(m.getPosition(),m.getName());
					m.moveMouseToFoodLocation();
					MouseMove(m);
					
					if(m.getPosition().equals(m.getMemory().getfoodLocation(m.getPosition()))) {
						if(m.getMemory().getFoodLocationGivenByOthers().contains(m.getPosition())) {
							if(grid.isFoodPosition(m.getPosition())) {
								m.incrementTrust();
								m.getMemory().addFoodLocationStock(m.getMemory().getfoodLocation(m.getPosition()));
							}
							else {
								m.setFakePosition(true);
								m.getMemory().deleteFoodLocationGivenByOthers(pos);
								m.decrementTrust();
							}
						}
						else {
							m.getMemory().addFoodLocationStock(m.getMemory().getfoodLocation(m.getPosition()));
						}
					}
					
				
				}	
				else {
					moveTo(m,m.getLastDirection());
				}
		}
		
		if(m.isDead()) {	
			m.getBehavior().setPicture("Dead",m.getAge());
			mouseToKill.add(m);
			System.out.println(m.getName() + " est morte et ajouté dans la liste");
		}
		
		if(m.bornMouse()) {
			birthMouse(m);
			m.setIsPregnant(false);
			m.setPregnancyTime(50);
		}
		

		if(inFoodPosition(pos)) {
			m.getMemory().addFoodLocationStock(pos);
		}
		
		if(!inFoodPosition(pos) && m.getMemory().getFoodLocationStock().contains(pos)) {
			m.getMemory().getFoodLocationStock().remove(pos);
		}
		
		if(!inFoodPosition(pos) && m.getMemory().getFoodLocationGivenByOthers().contains(pos)) {
			m.getMemory().getFoodLocationGivenByOthers().remove(pos);
		}
		
		m.getMemory().removeFoodLocation();
		
	}
	

	
	public void MouseMove(Mouse m) {
		Orientation dir = m.getLastDirection();
		Position mPos = m.getPosition();
		Position nextPos;
		
		if (dir == Orientation.LEFT) {
			nextPos = new Position(mPos.getAbs() - 1, mPos.getOrd());
			choseTypeOfMove(nextPos,m);
		}else if (dir == Orientation.RIGHT) {
			nextPos = new Position(mPos.getAbs() + 1, mPos.getOrd());
			choseTypeOfMove(nextPos,m);
		}else if (dir == Orientation.UP) {
			nextPos = new Position(mPos.getAbs(), mPos.getOrd() - 1);
			choseTypeOfMove(nextPos,m);
		}else {
			nextPos = new Position(mPos.getAbs(), mPos.getOrd() + 1);
			choseTypeOfMove(nextPos,m);
		}
	} 
	

	
	public boolean inFoodPosition(Position pos) {
		return grid.getCaseCoord(pos.getAbs(), pos.getOrd()).getType().isFood();
	}
	
	public boolean canWeMoveit(Position nextPos, Mouse m) {
		boolean move=false;
		
		if(grid.getCaseCoord(nextPos.getAbs(), nextPos.getOrd()) != null
				&& grid.getCaseCoord(nextPos.getAbs(), nextPos.getOrd()).getType().isFood()) {
			
			m.getBehavior().setPicture("Food",m.getAge());
			
			move = true;
		}else if (grid.getCaseCoord(nextPos.getAbs(), nextPos.getOrd()) != null
				&& grid.getCaseCoord(nextPos.getAbs(), nextPos.getOrd()).getisFree()) {
			
			m.getBehavior().setPicture(m.getLastDirection().toString(),m.getAge());
				
			move = true;
		}
		return move;
	}
	

	
	public void moveRandomly(Mouse m) {
		 moveTo(m, Orientation.directionRand());
	}
	

	
	public void moveTo(Mouse m, Orientation dir) {
		m.setLastDirection(dir);
		Position mPos = m.getPosition();
		Position nextPos;
		
		if (dir == Orientation.LEFT) {
			nextPos = new Position(mPos.getAbs() - 1, mPos.getOrd());
		}else if (dir == Orientation.RIGHT) {
			nextPos = new Position(mPos.getAbs() + 1, mPos.getOrd());
		}else if (dir == Orientation.UP) {
			nextPos = new Position(mPos.getAbs(), mPos.getOrd() - 1);
		}else if(dir == Orientation.DOWN){
			nextPos = new Position(mPos.getAbs(), mPos.getOrd() + 1);
		}else {
			nextPos = new Position(mPos.getAbs(), mPos.getOrd());
		}
		
		if(canWeMoveit(nextPos,m)) {
			m.setPosition(nextPos);
		}else {
			moveRandomly(m);
		}
	}
	
	public boolean choseTypeOfMove(Position nextPos, Mouse m) {
		if(canWeMoveit(nextPos,m)) {
			m.setPosition(nextPos);
			
			return true;
		}else {
			moveRandomly(m);
			return false;
		}
	}
	

	
	public boolean moveDeadMouse(Mouse m) {
		Position mPos = m.getPosition();
		Position nextPos;
		
		nextPos = new Position(mPos.getAbs(), mPos.getOrd() - 1);
		
			m.getBehavior().setPicture("Dead1",m.getAge());
			m.setPosition(nextPos);
			return true;
		
		
	}
	
	
	public void KillMouses() {
		for(int i=0; i< mouseToKill.size(); i++) {
			Mouse m = mouseToKill.get(i);
			if(!moveDeadMouse(m)) 
				grid.deleteMouse(m);
			}	
	}
	
	
	public void bornMouse() {
		for(int i=0; i<mouseToAdd.size(); i++) {
			Mouse m = mouseToAdd.get(i);
			if(grid.getMouses().size() <= 60)
				grid.addMouse(m);
		}
		mouseToAdd.clear();
	}
	

	
	public void birthMouse(Mouse m) {
		Position pos = m.getPosition();
		Comportement behavior = m.getBehavior();
		
		
		if(behavior.type().equals("Selfish")) {
			if(m.isReceptive()) {
				mouseToAdd.add(CreatMouses.creatReceptiveMouse(pos,new Memory(),new Selfish(0),0,5));
			}else {
				mouseToAdd.add(CreatMouses.creatNihilisteMouse(pos,new Memory(),new Selfish(0),0,5));
			}
		}else {
			if(m.isReceptive()) {
				mouseToAdd.add(CreatMouses.creatReceptiveMouse(pos,new Memory(),new Cooperative(0),0,10));
			}else {
				mouseToAdd.add(CreatMouses.creatNihilisteMouse(pos,new Memory(),new Cooperative(0),0,10));
			}
		}
		
	}
	

	
	public void lookAround(Mouse m) {
		Mouse m1 = null;
		Position northPosition = new Position(m.getPosition().getAbs(),m.getPosition().getOrd()-1);
		Position southPosition = new Position(m.getPosition().getAbs(),m.getPosition().getOrd()+1);
		Position eastPosition = new Position(m.getPosition().getAbs()+1,m.getPosition().getOrd());
		Position westPosition = new Position(m.getPosition().getAbs()-1,m.getPosition().getOrd());
		
		if(!m.isDead())
			if(grid.isMousePosition(northPosition)) {
				m1 = grid.getMouseAt(northPosition);
				if(!m1.getIsTalking() && !m1.isDead() && m.getMemory().getTalkRound(m1)+20 <= simulationNbTurn);
			}
			else if(grid.isMousePosition(southPosition)) {
				m1 = grid.getMouseAt(southPosition);
				if(!m1.getIsTalking() && !m1.isDead() && m.getMemory().getTalkRound(m1)+20 <= simulationNbTurn);	
			}
			else if(grid.isMousePosition(eastPosition)) {
				m1 = grid.getMouseAt(eastPosition);
				if(!m1.getIsTalking() && !m1.isDead() && m.getMemory().getTalkRound(m1)+20 <= simulationNbTurn);
			}
			else if(grid.isMousePosition(westPosition)) {
				m1 = grid.getMouseAt(westPosition);
				if(!m1.getIsTalking() && !m1.isDead() && m.getMemory().getTalkRound(m1)+20 <= simulationNbTurn);
			}
		
		if(m1 != null && !m.getIsTalking() && !m1.isDead() && m.getMemory().getTalkRound(m1)+20 <= simulationNbTurn) {
			if(!m1.getIsTalking()) {
				m.setIsTalking(true);
				m1.setIsTalking(true);
				m.talkWith(m1);
				m.getMemory().iHadMeetThisMouse(m1, simulationNbTurn);
				m1.getMemory().iHadMeetThisMouse(m, simulationNbTurn);
				
				if(m1.getAge()>20 && m.getAge()>20)
					if(!m.isPragant()) {
						m1.setMeet(true);
						m.setMeet(true);
						m.setIsPregnant(true);
						
					}
			}
		}
	}	
	
	public void foodGeneration() {
		if(foodAddFreq <= 0 && grid.getFood().size() < 30) {
			simulationTurnAndFood(simulationNbTurn);
			for(Food cheese : foodPosition) {
				grid.addFood(cheese);
			}
			foodAddFreq=foodFreq;
			foodPosition.clear();
		}
	}
	
	
	public int foodGenerFreq() {
		int foodAdd = 0;
			switch (getParameters().getQntFood()) {
			case 25:
				foodAdd=15;
				break;
			case 15:
				foodAdd=20;
				break;
			case 10:
				foodAdd=25;
				break;
		}
		return foodAdd;
	}
	
	
	public void adjustFoodToAdd(String Turn) {		

		int foodToAdd;
		
		if(getParameters().getQntFood()==25) {
			foodToAdd=RandomValues.randomInt(5, 15);
		}else if(getParameters().getQntFood()==15) {
			foodToAdd=RandomValues.randomInt(5, 10);
		}else {
			foodToAdd=RandomValues.randomInt(3, 6);;
		}
		
		randomFoodPos(Turn,foodToAdd);
		
	}
	
	
	public void randomFoodPos(String Turn,int foodNumber) {
		int x,y;
		for(int i = 0 ; i<foodNumber ; i++ ) {
			
			do
			{
				 x= RandomValues.randomInt(1,getParameters().getDim()-2);
				 y= RandomValues.randomInt(1,getParameters().getDim()-2);
			}
			while(!grid.getCaseCoord(x, y).getisFree() && !grid.isFoodPosition(new Position(x,y)));
			
				Food f = Case.constructFood(x, y, Turn);
				grid.getCaseCoord(x, y).setType(f);
				foodPosition.add(f);
		}		
	}

	
	public void randomObstaclePos(int NbObs) {

		int x,y;
		Position p;
		for(int i = 0 ; i< NbObs ; i++ ) {
			do
			{
				 x= RandomValues.randomInt(2,Options.getDimension()-3);
				 y= RandomValues.randomInt(2,Options.getDimension()-3);
				 p= new Position(x,y);
			}
			while(!grid.getCaseCoord(x, y).getisFree() || !grid.prefDistanceObstacle(p));
			Obstacle o = Case.creatObstacle(x, y);
			grid.addObstacle(o);
			grid.setBox(x,y,o);
			grid.setBoxAtFree(x, y, false);
		}
		
	}

	public void simulationTurnAndFood(int Turn) {
			adjustFoodToAdd("Start");
	}
	

	public void foodAction(Food cheese) {

		cheese.foodByTime();
		
		if(cheese.foodOutdated() || cheese.isVacant()) {
			grid.deleteFood(cheese);
			int x = cheese.getAbs();
			int y = cheese.getOrd();
			grid.getCaseCoord(x, y).setisFree(true);
			grid.getCaseCoord(x, y).setType(Case.constructHerbe(x, y));
		}
	}
	
	public void nomsSouris(String title) {
		
		URL url = getClass().getResource(title);
		String ligne;
		try {
			URLConnection ucon = url.openConnection();
			BufferedReader read = new BufferedReader(new InputStreamReader(ucon.getInputStream()));
			while((ligne=read.readLine()) != null) {
					names.add(ligne);
			}
			read.close();
		} catch (Exception e) {

		}
	}
}