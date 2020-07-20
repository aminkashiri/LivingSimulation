package project.world;

import java.util.ArrayList;

import project.animals.Animal;

public class Territory {
	int species;
	ArrayList<Animal> animals;
	
	public Territory() {
		animals = new ArrayList<Animal>();
		species = 0;
	}
	
	public Territory(Animal animal) {
		animals = new ArrayList<Animal>();
		setAnimal(animal);
	}
	
	
	public void setAnimal(Animal animal) {
		species = animal.getSpecies();
		addAnimal(animal);
	}
	
	public void addAnimal(Animal animal) {
		animals.add(animal);
	}
	
	synchronized public void requestMoving(Animal animal) {
		
	}
	
}
