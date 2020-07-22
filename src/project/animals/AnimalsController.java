package project.animals;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import project.utils.AllObjects;
import project.world.Territory;

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

	public AnimalsController(Territory[][] territories, int numberOfSpecies) {
		this.territories = territories;
		this.numberOfSpecies = numberOfSpecies;
		height = territories.length;
		width = territories[0].length;

		species = new CopyOnWriteArrayList[numberOfSpecies+1];
		for(int i = 0 ; i < numberOfSpecies+1 ; i++) {
			species[i] = new CopyOnWriteArrayList<Territory>();
		}


		AllObjects.getAllObjects().setAnimalsController(this);
		population = 0;
		lock = new Object();
	}

	public void kill() {
		for(int i = 0 ; i < height ; i++) {//kill excess animals
			for(int j = 0 ; j < width ; j++) {
				territories[i][j].starve();
			}
		}
		
		for(int i = 0 ; i < numberOfSpecies+1 ; i++) {//find species of every territory
			species[i].clear();
		}
		for(int i = 0 ; i < height ; i++) {//find species of every territory
			for(int j = 0 ; j < width ; j++) {
				species[territories[i][j].getSpecies()].add(territories[i][j]);
			}
		}
		for(int i = 1 ; i < numberOfSpecies/2 ; i++) {//eat each other!
			for(Territory territory: species[i]) {
				checkForPredators(territory, false);
			}
		}
		for(int i = numberOfSpecies/2+1 ; i < numberOfSpecies+1 ; i++) {//Monsters are here!
			for(Territory territory: species[i]) {
				checkForPredators(territory, true);
			}
		}

	}

	private void checkForPredators(Territory territory, boolean isMonster) {
		int [] temp = new int[numberOfSpecies+1];
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
				territory.die();
				break;
			}
		}

	}

	public void birth(int k) {
		for(int i = 0 ; i < height ; i++) {
			for(int j = 0 ; j < width ; j++) {
				territories[i][j].birth(k);
			}
		}
		waitForStop();
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

	synchronized public void sleep() throws InterruptedException {
//		synchronized (lock) {
			waiting++;
//			lock.wait();
//		}
			this.wait();
	}

	synchronized public void increasePopulation(int n) {
		population += n;
	}

	public boolean shouldStop() {
		return stop;
	}

	synchronized public void decreasePopultion(int n) {
		population -= n;
		waiting -= n;
	}

	synchronized public void resume() {
//		synchronized (lock) {
//			lock.notifyAll();
//		}		
		this.notifyAll();
		stop = false;

	}

	public void waitForStop() {
		//		System.out.println("Waiting for animals:"+waiting+"/"+population);
		while (waiting < population) {
			System.out.println("waiting for animals:"+waiting+"/"+population);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public void print() {
		System.out.println("^ "+population+","+waiting);
	}

	public void stop() {
		waiting = 0;
		stop = true;
		waitForStop();
	}

}
