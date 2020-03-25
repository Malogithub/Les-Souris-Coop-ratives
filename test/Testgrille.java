package test;

import grid.Grille;
import utility.GridParameters;
import utility.GridBuilder;

public class Testgrille {
		
		public static void main(String[] args) {
			
			GridParameters parameters = GridParameters.getInstance();
			
			GridBuilder buildGrid = new GridBuilder(parameters);
			
			Grille grid = buildGrid.getGrid();
			
			System.out.println(grid);
			
		}
	}
