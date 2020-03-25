package divers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

 // Les directions possibles des souris
 
public enum Orientation {
	 UP{
		public String toString() {
	        return "En Haut";
	    }
	}
	, DOWN{
			public String toString() {
		        return "En Bas";
		    }
		}
	, LEFT{
		public String toString() {
	        return "A Gauche";
	    }
	}
	, RIGHT{
		public String toString() {
	        return "A Droite";
	    }
	}
	;
	
	
	 private static final List<Orientation> VAL =
			  Collections.unmodifiableList(Arrays.asList(values()));
			  private static final int HEIGHT = VAL.size();
	
			  public static Orientation directionRand()  {
			    return VAL.get(RandomValues.randomInt1(HEIGHT));
			  }
}