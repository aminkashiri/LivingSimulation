package project.thread.world;

import java.util.ArrayList;

import project.thread.animals.Animal;
import project.thread.animals.AnimalsController;
import project.utils.AllObjects;
import project.utils.Colors;

public class Territory {
	int species;
	int maxResident;
	ArrayList<Animal> animals;
	int x;
	int y;
	AnimalsController animalsController;
	
	public Territory(int maxResidnet, int x, int y) {
		animals = new ArrayList<Animal>();
		this.maxResident = maxResidnet;
		this.x = x;
		this.y = y;
		species = 0;
		animalsController = AllObjects.getAllObjects().getAnimalsControllerThread();

	}
	
	public void giveLife(Animal animal) {
		species = animal.getSpecies();
		animals.add(animal);
	}
	
	synchronized public boolean requestMoving(Animal animal) {
		if(species == 0) {
			animals.add(animal);
			species = animal.getSpecies();
		}else if(species == animal.getSpecies() &&  animals.size()<maxResident) {
			animals.add(animal);
		}else {
			return false;
		}
		return true;
	}

	synchronized public void removeAnimal(Animal animal) {
		animals.remove(animal);
		if(animals.size() == 0) {
			species = 0;
		}
	}

	public int starve() {
		int temp = 0;
		while(animals.size() > maxResident) {
			animals.get(animals.size()-1).interrupt();
			animals.remove(animals.size()-1);
			temp++;
		}
		return temp;
	}

	public int birth(int k) {
		int size = 0;
		if(species != 0 && (k%species) == 0) {
			size = animals.size();
			for(int i = 0 ; i < size ; i++) {
				Animal animal = new Animal(x,y,species);
				animals.add(animal);
				animal.start();
			}
		}
		return size;
	}

	public void live() {
		for(Animal animal : animals) {
			animal.start();
		}
	}

	public int die() {
		int temp = animals.size();
		while(animals.size() > 0) {
			animals.get(animals.size()-1).interrupt();
			animals.remove(animals.size()-1);
		}
		species = 0;
		return temp;
	}
	
	public int getSpecies() {
		return species;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getCount() {
		return animals.size();
	}

	public int getPower() {
		return animals.size()*species;
	}

	public void print() {
		switch (species) {
		case 1:	
			System.out.print(""+Colors.BLACK+Colors.CYAN_BACKGROUND+animals.size()+Colors.RESET);
			break;
		case 2:	
			System.out.print(""+Colors.RED_BACKGROUND+animals.size()+Colors.RESET);
			break;
		case 3:	
			System.out.print(""+Colors.BLACK+Colors.YELLOW_BACKGROUND+animals.size()+Colors.RESET);
			break;
		case 4:	
			System.out.print(""+Colors.BLUE_BACKGROUND+animals.size()+Colors.RESET);
			break;
		case 5:	
			System.out.print(""+Colors.BLACK+Colors.GREEN_BACKGROUND+animals.size()+Colors.RESET);
			break;
		case 6:	
			System.out.print(""+Colors.BLACK+Colors.WHITE_BACKGROUND+animals.size()+Colors.RESET);
			break;
		case 7:	
			System.out.print(""+Colors.MAGENTA_BACKGROUND+animals.size()+Colors.RESET);
			break;
		case 8:	
			System.out.print(""+Colors.BLACK_BACKGROUND+animals.size()+Colors.RESET);
			break;
		default:
			System.out.print(""+animals.size()+Colors.RESET);
			break;
		}
	}
}
