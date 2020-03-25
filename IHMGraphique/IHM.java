package IHMGraphique;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import divers.Orientation;
import grid.Case;
import grid.Food;
import grid.Grille;
import souris.Mouse;
import utility.Position;

@SuppressWarnings("serial")
public class IHM extends JPanel {

	private Grille grid;
	private ImageIcon pics;
	private Mouse mouse;
	private Position selectedBox = null;
	private Position selectedMice = null;
	
	public IHM() {
		super();
		}

	public Position getSelectedBox() {
		return selectedBox;
	}
	
	public Position getSelectedMice() {
		return selectedMice;
	}
	
	public Grille getGrid() {
		return grid;
	}
			
	public void setMouseSelected(Mouse m) {
		this.mouse=m;
	}
	public void setSelectedBox(Position selectedBox) {
		this.selectedBox=selectedBox;
	}
	
	public void setSelectedMice(Position selectedMice) {
		this.selectedMice=selectedMice;
	}
	
	public void setGrid(Grille grid) {
		this.grid=grid;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
			paintGrid(g);

	}
	


	public void paintGrid(Graphics g) {

		Graphics g2 = (Graphics2D) g;
		int dimension = grid.getDim();
		Case box;
		for(int y=0;y<dimension;y++) {
			for(int x=0;x<dimension;x++) {
				box = grid.getCaseCoord(x, y);
				if(box.getType().isHerbe()){
					drawGround(new Position(x,y),g2,box);
				}
				else if(!box.getisFree()) {
					drawObstacle(new Position(x,y),g2,box);
				}
			}
		}
		
		for(int i=0; i<grid.getFood().size(); i++) {
			Food f = grid.getFood().get(i);
			drawFood(f, g);
		}
		
		for (int i=0; i<grid.getMouses().size(); i++) {
			Mouse m = grid.getMouse(i);
				drawMouse(m, g);
		}
		for(int y=0;y<dimension;y=y+30) {
			for(int x=0;x<dimension;x=x+30) {
			//	drawLine(g2);
				
			}
		}
	}
	

	public void drawMouse(Mouse mouse, Graphics g) {
		
		Image img = mouse.getBehavior().getPicture();
		Position pos = mouse.getPosition();
	
		g.drawImage(img,pos.getAbs()*30,pos.getOrd()*30, null);
	}
	

	
	public void drawNewMouse(Mouse mouse, Graphics g) {
		g.setColor(Color.RED);
		Image img = mouse.getBehavior().getPicture();
		Position pos = mouse.getPosition();
	
		g.drawImage(img,pos.getAbs()*30,pos.getOrd()*30, null);
		

	}

	public void drawTalkingMouse(Mouse mouse, Graphics g) {
		Image img = mouse.getBehavior().getPicture();
		Position pos = mouse.getPosition();
		Orientation dir = mouse.getLastDirection();
		
		g.drawImage(img,pos.getAbs()*30,pos.getOrd()*30, null);
		
	}
	
	
	public void drawFood(Food f,Graphics g) {
		pics = new ImageIcon(getClass().getResource("/images/terrain/Herbe.png"));
		Image t = pics.getImage();

		g.drawImage(t,f.getAbs()*30,f.getOrd()*30,null);
		g.drawImage(f.getPics(),f.getAbs()*30,f.getOrd()*30,null);
		
	}
	
	
	public void drawObstacle(Position p,Graphics g,Case box) {
		pics = new ImageIcon(getClass().getResource("/images/terrain/Herbe.png"));
		Image t = pics.getImage();
		g.drawImage(t,p.getAbs()*30,p.getOrd()*30,null);
		g.drawImage(box.getType().getPics(),p.getAbs()*30,p.getOrd()*30,null);
	}

	
	public void drawGround(Position p,Graphics g,Case box) {
		pics = new ImageIcon(getClass().getResource("/images/terrain/Herbe.png"));
		Image img = pics.getImage();
		g.drawImage(img,p.getAbs()*30,p.getOrd()*30,null);
	}
	
}
