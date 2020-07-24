package project.utils;

import java.util.Random;

import project.thread.animals.AnimalsController;

public class AllObjects {
	static AllObjects allObjects;
	Random random;
	AnimalsController animalsControllerThread;
	project.process.server.AnimalsController animalsController;

	
	public project.process.server.AnimalsController getAnimalsController() {
		return animalsController;
	}

	public void setAnimalsController(project.process.server.AnimalsController animalsController) {
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
	
	public AnimalsController getAnimalsControllerThread() {
		return animalsControllerThread;
	}
	
	public void setAnimalsControllerThread(AnimalsController animalsController) {
		this.animalsControllerThread = animalsController;
	}
}
