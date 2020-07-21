package project.animals;

import java.util.concurrent.ThreadLocalRandom;

import project.utils.AllObjects;

public class Animal extends Thread{
	int species;
	int x;
	int y;
	AnimalsController animalsController;
	AllObjects allObjects;
	
	public Animal(int a, int b, int c, AnimalsController animalsController) {
		x = a;
		y = b;
		species = c;
		this.animalsController = animalsController;
		allObjects = AllObjects.getAllObjects();
	}
	

	@Override
	public void run() {
		animalsController.increasePopulation(1);
		int toX;
		int toY;
		while(true) {
			if(animalsController.shouldStop()) {
				try {
					animalsController.sleep();
				} catch (InterruptedException e) {
//					e.printStackTrace();
					break;
				}
			}
			
			if(Thread.currentThread().isInterrupted()) {
				break;
			}
			
			toX = ThreadLocalRandom.current().nextInt(-1, 2) + x;
			toY = ThreadLocalRandom.current().nextInt(-1, 2) + y;
			animalsController.move(this, toX, toY);
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		animalsController.decreasePopultion(1);
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getSpecies() {
		return species;
	}
}
