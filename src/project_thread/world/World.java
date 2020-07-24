package project_thread.world;

import project_thread.animals.Animal;
import project_thread.animals.AnimalsController;

public class World {
	Territory[][] territories;
	int maxResidnet;
	int width;
	int height;
	int numberOfSpecies;
	int initialPopulation;
	
	int year;
	AnimalsController animalsController;
	
	public World(int r, int s, int n, int m, int k) {
		numberOfSpecies = r;
		initialPopulation = s;
		height = n;
		width = m;
		maxResidnet = k;
		
		year = 0;
		initialize();
		printWorld();
		animalsController.startLife();
	}

	private void initialize() {
		territories = new Territory[height][width];
		for(int i = 0 ; i < height ; i++) {
			for(int j = 0 ; j < width ; j++) {
				territories[i][j] = new Territory(maxResidnet, i, j);
			}
		}
		
		animalsController = new AnimalsController(territories, numberOfSpecies, numberOfSpecies*initialPopulation);
		
		int deltaX = height/numberOfSpecies;
		int deltaY = width/initialPopulation;
		int x;
		Animal animal;
		for(int i = 0 ; i<numberOfSpecies ; i++) {//i+1 is species number
			x = deltaX*(i+1)-1;
			for(int j = 0 ; j<initialPopulation ; j++) {
				animal = new Animal(x, (j+1)*deltaY-1, i+1);
				territories[x][(j+1)*deltaY-1].giveLife(animal);
			}
		}
	}

	public void evolve() {
		year = (year % numberOfSpecies) +1;
//		System.out.println("before evolve");
		animalsController.stop();
//		System.out.println("----------------[Before Death]----------------");
//		printWorld();
		animalsController.kill();
//		System.out.println("----------------[After Death]----------------");
//		printWorld();
		animalsController.birth(year);
//		System.out.println("----------------[Childs Are Born]----------------");
//		printWorld();
//		System.out.println("----------------[Next Generation]----------------\n");
		animalsController.resume();
	}
	
	public void printWorld() {
		for(int i = 0 ; i < height ; i++) {
			for(int j = 0 ; j < width ; j++) {
				territories[i][j].print();
				System.out.print("  ");
			}
			System.out.println();
		}
		System.out.println();
//		animalsController.print();
	}

}
