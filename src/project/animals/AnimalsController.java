package project.animals;

import project.world.Territory;

public class AnimalsController {

	Territory[][] territories;
	int height;
	int width;
	
	boolean stop;
	int population;
	int waiting;
	
	Object lock;
	
	public AnimalsController(Territory[][] territories) {
		this.territories = territories;
		height = territories.length;
		width = territories[0].length;
		
		population = 0;
		lock = new Object();
	}

	public void kill() {
		stop();
		
		for(int i = 0 ; i < height ; i++) {
			for(int j = 0 ; j < width ; j++) {
				territories[i][j].kill();
			}
		}
		
	}
	
	public void birth() {
		for(int i = 0 ; i < height ; i++) {
			for(int j = 0 ; j < width ; j++) {
				territories[i][j].birth(this);
			}
		}
		stop();
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
	
	public void sleep() throws InterruptedException {
		synchronized (lock) {
			waiting++;
			lock.wait();
		}
		
	}
	
	public void increasePopulation(int n) {
		population += n;
	}

	public boolean shouldStop() {
		return stop;
	}

	public void decreasePopultion(int n) {
		population -= n;
		waiting -= 1;
	}

	public void resume() {
		synchronized (lock) {
			waiting = 0;
			lock.notifyAll();
		}		
		stop = false;

	}

	public void stop() {
		stop = true;
//		System.out.println("Waiting for animals:"+waiting+"/"+population);
		while (waiting < population) {
//			System.out.println("waiting for animals:"+waiting+"/"+population);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
	
}
