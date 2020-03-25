package souriscomportement;

import java.awt.Image;

import javax.swing.ImageIcon;

import souris.Micepics;

	public class Cooperative extends Comportement{


		public final static String COOPERATIVE_PICTURES_LOCATION="/images/SourisNoires/";
		private Image pics;
		public Cooperative(int age) {
			ImageIcon img;
			
				img = new ImageIcon(getClass().getResource(Selfish.SELFISH_PICTURES_LOCATION+Micepics.MOUSE_DOWN));
			
			pics = img.getImage();
			
		}

		public Image getPicture() {
			return pics;
		}
	public void setPicture(String mouseToWhere, int age) {
		ImageIcon img = null;
		
		switch(mouseToWhere) {
			case "A Gauche":
				
					img = new ImageIcon(getClass().getResource(Cooperative.COOPERATIVE_PICTURES_LOCATION
																		+Micepics.MOUSE_TO_LEFT));
			
				break;
			case "En Haut":
					img = new ImageIcon(getClass().getResource(Cooperative.COOPERATIVE_PICTURES_LOCATION
						 												+Micepics.MOUSE_UP));
				
				 break;
			case "A Droite":
					img = new ImageIcon(getClass().getResource(Cooperative.COOPERATIVE_PICTURES_LOCATION
																		+Micepics.MOUSE_TO_RIGHT));
				
				break;
			case "En Bas":
					img = new ImageIcon(getClass().getResource(Cooperative.COOPERATIVE_PICTURES_LOCATION
																		+Micepics.MOUSE_DOWN));
				
				break;
			case "Food":
					img = new ImageIcon(getClass().getResource(Cooperative.COOPERATIVE_PICTURES_LOCATION
																		+Micepics.MOUSE_FIND_FOOD));
				
				break;
			case "Dead":
				img = new ImageIcon(getClass().getResource(Micepics.MOUSE_DEAD));
				
				break;
			
		}
		
		pics = img.getImage();
	}
					
	public boolean giveInfo() {
		return true;
	}
	
	public String type() {
		return "Cooperative";
	}
}