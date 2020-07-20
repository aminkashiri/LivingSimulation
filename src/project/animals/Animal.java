package project.animals;

public class Animal extends Thread{
	int species;
	int x;
	int y;
	public Animal(int a, int b, int c) {
		x = a;
		y = b;
		species = c;
	}
	public int getSpecies() {
		return species;
	}

	@Override
	public void run() {
		
		super.run();
	}
	
}
