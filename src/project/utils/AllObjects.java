package project.utils;

import java.util.Random;

import project.process.server.ServerController;
import project.thread.animals.AnimalsController;


public class AllObjects {
	static AllObjects allObjects;
	Random random;
	AnimalsController animalsController;
	ServerController serverController;

	
	public project.process.server.ServerController getserverController() {
		return serverController;
	}

	public void setAnimalsController(ServerController serverController) {
		this.serverController = serverController;
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
		return animalsController;
	}
	
	public void setAnimalsControllerThread(AnimalsController animalsController) {
		this.animalsController = animalsController;
	}
}
