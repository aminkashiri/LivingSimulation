package project.world;

import project.animals.Animal;
import project.animals.AnimalsController;

public class World {
	Territory[][] territories;
	public boolean shouldMove;
	int maxResidnet;
	int width;
	int height;
	int numberOfSpecies;
	int initialPopulation;
	
	int year;
	AnimalsController animalsController;
	
	public World(int r, int s, int n, int m, int k, int t) {
		numberOfSpecies = r;
		initialPopulation = s;
		height = n;
		width = m;
		maxResidnet = k;
		shouldMove = false;
		
		year = 0;
		initialize();
	}

	private void initialize() {
		territories = new Territory[height][width];
		animalsController = new AnimalsController(territories);
		int deltaY = height/numberOfSpecies;
		int deltaX = width/initialPopulation;
		int y;
		Animal animal;
		for(int i = 0 ; i<numberOfSpecies ; i++) {//i+1 is species number
			y = deltaY*(i+1)-1;
			for(int j = 0 ; j<initialPopulation ; j++) {
				animal = new Animal((j+1)*deltaX-1, y, i+1);
				territories[(j+1)*deltaX-1][y] = new Territory(animal);
			}
		}
	}

	public void evolve() {
		year++;
		animalsController.kill();
	}
}
