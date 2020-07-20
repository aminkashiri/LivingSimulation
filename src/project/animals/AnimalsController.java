package project.animals;

import project.world.Territory;

public class AnimalsController {

	Territory[][] territories;
	int height;
	int width;
	
	boolean stop;
	
	public AnimalsController(Territory[][] territories) {
		this.territories = territories;
		height = territories.length;
		width = territories[0].length;
		
	}

	public void kill() {
		
	}
	
	public void move(Animal animal, int x, int y) {
		territories[x][y].requestMoving(animal);
	}
	
	
	
}
