package project.thread.animals;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Semaphore;

import project.thread.world.Territory;
import project.utils.AllObjects;

public class AnimalsController {

	Territory[][] territories;
	CopyOnWriteArrayList<Territory> [] species; 
	int height;
	int width;
	int numberOfSpecies;

	boolean stop;
	int population;
	int waiting;
	Object lock;
	int newDeaths;
	int newPopulation;
	Semaphore animalSemaphore;
	Semaphore controllSemaphore;

	boolean TABSAREH = true;

	public AnimalsController(Territory[][] territories, int numberOfSpecies, int newBirths) {
		this.territories = territories;
		this.numberOfSpecies = numberOfSpecies;
		height = territories.length;
		width = territories[0].length;
		this.newPopulation = newBirths;

		species = new CopyOnWriteArrayList[numberOfSpecies+1];
		for(int i = 0 ; i < numberOfSpecies+1 ; i++) {
			species[i] = new CopyOnWriteArrayList<Territory>();
		}


		AllObjects.getAllObjects().setAnimalsControllerThread(this);
		population = 0;
		lock = new Object();
		animalSemaphore = new Semaphore(0);
		controllSemaphore = new Semaphore(0);
		stop = true;
	}

	public void kill() {
		int deaths = 0;
		synchronized (this) {
//						System.out.println("before killing");
			for(int i = 0 ; i < height ; i++) {//kill excess animals
				for(int j = 0 ; j < width ; j++) {
					deaths += territories[i][j].starve();
				}
			}

			for(int i = 0 ; i < numberOfSpecies+1 ; i++) {//find species of every territory (clear last round)
				species[i].clear();
			}
			for(int i = 0 ; i < height ; i++) {//find species of every territory
				for(int j = 0 ; j < width ; j++) {
					species[territories[i][j].getSpecies()].add(territories[i][j]);
				}
			}
			if(TABSAREH == true) {//kill animals, with help to bigger ones
				for(int i = 1 ; i < numberOfSpecies/2 ; i++) {//eat each other!
					for(Territory territory: species[i]) {
						deaths += checkForPredators(territory, false);
					}
				}
				for(int i = numberOfSpecies/2+1 ; i < numberOfSpecies+1 ; i++) {//Monsters are here!
					for(Territory territory: species[i]) {
						deaths += checkForPredators(territory, true);
					}
				}
			}else {//kill animals (without help to big ones)
				for(int i = 0 ; i < height ; i++) {
					for(int j = 0 ; j < width ; j++) {
						deaths += checkForPredators(territories[i][j], (territories[i][j].getSpecies()>= numberOfSpecies/2 +1) );
					}
				}
			}
			this.newDeaths = deaths;
		}
		if (deaths != 0) {
			waitForAnimalsStop();
		}
		//		System.out.println("after killing");

	}

	public void birth(int k) {
		int currPopulation = population;
		int births = 0;
		//		System.out.println("before birth");
		synchronized (lock) {
			for(int i = 0 ; i < height ; i++) {
				for(int j = 0 ; j < width ; j++) {
					births += territories[i][j].birth(k);
				}
			}
			this.newPopulation = currPopulation+births;
			//			System.out.println("birth count:"+births);
		}
		if (births != 0) {
			waitForAnimalsStop();
		}
		//		System.out.println("after birth");
	}

	public void sleep() throws InterruptedException {
		synchronized (lock) {
			waiting++;
			if(waiting == newPopulation) {
				//		if(newBirths != 0 && waiting == population) {
				controllSemaphore.release();
				//				System.out.println("RELEASING");
			}else {
				//				System.out.println("in sleep");
				//				System.out.println(waiting);
				//				System.out.println(population);
				//				System.out.println(newPopulation);
			}
		}

		animalSemaphore.acquire();
	}

	synchronized public void increasePopulation(int n) {
		population += n;
	}

	synchronized public void decreasePopultion(int n) {
		waiting -= n;
		if(waiting == population-newDeaths) {
			population = waiting;
			controllSemaphore.release();
		}else {
			//			System.out.println("in decrease ");
			//			System.out.println(waiting);
			//			System.out.println(population);
		}
	}

	public void resume() {
		//		System.out.println("before resume");
		stop = false;
		animalSemaphore.release(population);
		//		System.out.println("after resume");
	}

	public void stop() {
		waiting = 0;
		stop = true;
		waitForAnimalsStop();
	}

	public void waitForAnimalsStop() {
		//		System.out.println("Waiting for animals:"+waiting+"/"+population);
		try {
			controllSemaphore.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//		System.out.println("Waiting finished for animals:"+waiting+"/"+population);
	}

	private int checkForPredators(Territory territory, boolean isMonster) {
		int [] temp = new int[numberOfSpecies+1];
		if(TABSAREH == false) {
			isMonster = false;
		}
		if(isMonster) {
			for(int i = territory.getX()-1 ; i < territory.getX()+2 ; i++) {
				for(int j = territory.getY()-1 ; j < territory.getY()+2 ; j++) {
					if(i >= 0 && i < height && j >= 0 && j < width && !(i==territory.getX() && j==territory.getY())) {
						if(territories[i][j].getSpecies() < territory.getSpecies()) {
							if(i!=territory.getX() && j!=territory.getY()) {
								continue;
							}
						}
						temp[territories[i][j].getSpecies()] += territories[i][j].getPower();
					}
				}
			}
		}else {
			for(int i = territory.getX()-1 ; i < territory.getX()+2 ; i++) {
				for(int j = territory.getY()-1 ; j < territory.getY()+2 ; j++) {
					if(i >= 0 && i < height && j >= 0 && j < width && !(i==territory.getX() && j==territory.getY())) {
						temp[territories[i][j].getSpecies()] += territories[i][j].getPower();
					}
				}			
			}

		}
		for(int i = 1; i<=numberOfSpecies ; i++) {
			if(temp[i] > territory.getPower() && i!=territory.getSpecies()) {
				return territory.die();
			}
		}

		return 0;

	}

	public boolean move(Animal animal, int x, int y) {
		if(x >= 0 && x < height && y >= 0 && y < width) {
			if(territories[x][y].requestMoving(animal)) {
				territories[animal.getX()][animal.getY()].removeAnimal(animal);
				animal.setX(x);
				animal.setY(y);
				return true;
			}
		}
		return false;
	}

	public void print() {
		System.out.println("^ "+population+","+waiting);
	}

	public void setStop() {
		stop = true;
	}

	public boolean isStop() {
		return stop;
	}

	synchronized public void startLife() {
		System.out.println("------------------------[life begins]------------------------");
		for(int i = 0 ; i < height ; i++) {
			for(int j = 0 ; j < width ; j++) {
				territories[i][j].live();
			}
		}
		newPopulation = population;
		waitForAnimalsStop();
		resume();
	}

}
