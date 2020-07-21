package project.utils;

import java.util.Random;

public class AllObjects {
	static AllObjects allObjects;
	Random random;

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
