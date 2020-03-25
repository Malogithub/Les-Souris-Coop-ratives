package divers;

// Valeurs aléatoires

public class RandomValues {
	
	public static int randomInt1(int max) {
		return (int)(Math.random() * max);
	}
	
	public static int randomInt( int min ,int max) {
		
		return (int)( Math.random()*( max - min + 1 ) ) + min;
	}
	
}
