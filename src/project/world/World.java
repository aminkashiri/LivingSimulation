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
//		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		initialize();
		startLife();
	}

	private void startLife() {
		animalsController.stop();
		System.out.println("------------------------[life begins]------------------------");
		printWorld();
		for(int i = 0 ; i < height ; i++) {
			for(int j = 0 ; j < width ; j++) {
				territories[i][j].live();
			}
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		animalsController.resume();
	}

	private void initialize() {
		territories = new Territory[height][width];
		for(int i = 0 ; i < height ; i++) {
			for(int j = 0 ; j < width ; j++) {
				territories[i][j] = new Territory(maxResidnet, i, j);
			}
		}
		
		animalsController = new AnimalsController(territories, numberOfSpecies);
		
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
		animalsController.stop();
		System.out.println("----------------[Before Death]----------------");
		printWorld();
		animalsController.kill();
		System.out.println("----------------[After Death]----------------");
		printWorld();
		animalsController.birth(year);
		System.out.println("----------------[Childs Are Born]----------------");
		printWorld();
		System.out.println("----------------[Next Generation]----------------\n");
		animalsController.resume();
	}

	public void printWorld() {
		for(int i = 0 ; i < height ; i++) {
			for(int j = 0 ; j < width ; j++) {
				territories[i][j].print();
				System.out.print("  ");
			}
//			System.out.println("---"+Thread.currentThread().getPriority());
			System.out.println();
		}
		System.out.println();
		animalsController.print();
	}

}
