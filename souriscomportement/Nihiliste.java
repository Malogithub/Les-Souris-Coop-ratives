package souriscomportement;

import souris.Memory;
import souris.Mouse;
import utility.Position;

public class Nihiliste extends Mouse{

	public Nihiliste(Position position, Memory memory, Comportement behavior, int age, int trust) {
		super(position, memory, behavior, age, trust);
	}

	@Override
	public boolean isReceptive() {
		return false;
	}

	@Override
	public boolean isNihiliste() {
		return true;
	}

	@Override
	public void giveInformations(Mouse mouse, boolean information) {
		// TODO Auto-generated method stub
		
	}

}
