package grid;

import java.util.*;

import divers.RandomValues;



public enum ObstacleType {

	TYPE1{
		public String toString() {
	        return "Obstacle1";
	    }
	}
	, TYPE2{
		public String toString() {
	        return "Obstacle2";
	    }
	};
	
	private static final List<ObstacleType> VAL = Collections.unmodifiableList(Arrays.asList(values()));
	private static final int HEIGHT = VAL.size();
	
	  public static ObstacleType typeRand()  {
		    return VAL.get(RandomValues.randomInt1(HEIGHT));
		  }
		  
		
}
