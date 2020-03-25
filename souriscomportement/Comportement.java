package souriscomportement;

import java.awt.Image;

public abstract class Comportement {

	public abstract boolean giveInfo();
	public abstract Image getPicture();
	public abstract void setPicture(String mouseToWhere,int age);
	public abstract String type();
}
