package project_thread.utils;

import java.util.Random;

import project_thread.animals.AnimalsController;

public class AllObjects {
	static AllObjects allObjects;
	Random random;
	AnimalsController animalsController;

	public AnimalsController getAnimalsController() {
		return animalsController;
	}

	public void setAnimalsController(AnimalsController animalsController) {
		this.animalsController = animalsController;
	}

	private AllObjects() {
		random = new Random();
	}

	static public AllObjects getAllObjects() {
		if(allObjects == null) {
			allObjects = new AllObjects();
		}
		return allObjects;
	}
	
	public int getRandom() {//generates a random number between [-1,1]
		return random.nextInt(3)-1;
	}
}
