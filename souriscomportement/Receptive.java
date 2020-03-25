package souriscomportement;

import java.util.ArrayList;
import java.util.Iterator;

import IHMGraphique.Options;
import divers.RandomValues;
import souris.Memory;
import souris.Mouse;
import utility.Position;

public class Receptive extends Mouse{

	public Receptive(Position position, Memory memory, Comportement behavior, int age, int trust) {
		super(position, memory, behavior, age,trust);
	}

	@Override
	public boolean isReceptive() {
		return true;
	}

	@Override
	public boolean isNihiliste() {
		return false;
	}

	@Override
	public void giveInformations(Mouse mouse, boolean information) {
		// TODO Auto-generated method stub
		
	}

}
