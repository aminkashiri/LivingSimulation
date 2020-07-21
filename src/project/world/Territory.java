package project.world;

import java.util.ArrayList;

import project.animals.Animal;
import project.animals.AnimalsController;

public class Territory {
	public static final String RESET = "\033[0m";
	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	
	public static final String ANSI_BLACK = "\u001B[30m";
	
	int species;
	int maxResident;
	ArrayList<Animal> animals;
	int x;
	int y;
	
	public Territory(int maxResidnet, int x, int y) {
		animals = new ArrayList<Animal>();
		this.maxResident = maxResidnet;
		this.x = x;
		this.y = y;
		species = 0;
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
//		System.out.println("\nBefore removeing"+animals.size());
		animals.remove(animal);
//		System.out.println("After Removing"+animals.size()+" prio:"+ Thread.currentThread().getPriority()+"\n");
		
		if(animals.size() == 0) {
			species = 0;
		}
	}

	public void kill() {
		while(animals.size() > maxResident) {
			animals.get(animals.size()-1).interrupt();
			animals.remove(animals.size()-1);
		}
	}

	public void birth(AnimalsController animalsController) {
		int size = animals.size();
		for(int i = 0 ; i < size ; i++) {
			Animal animal = new Animal(x,y,species, animalsController);
			animals.add(animal);
			animal.start();
		}
	}

	public void live() {
		for(Animal animal : animals) {
			animal.start();
		}
	}

	public void print() {
		switch (species) {
		case 1:	
			System.out.print(ANSI_BLACK_BACKGROUND+animals.size()+RESET);
			break;
		case 2:	
			System.out.print(ANSI_RED_BACKGROUND+animals.size()+RESET);
//			System.out.print(ANSI_RED_BACKGROUND+animals.size()+RESET);
			break;
		case 3:	
			System.out.print(ANSI_YELLOW_BACKGROUND+ANSI_BLACK+animals.size()+RESET);
			break;
		case 4:	
			System.out.print(ANSI_BLUE_BACKGROUND+animals.size()+RESET);
			break;
		default:
			System.out.print(animals.size());
			break;
		}
//		System.out.print("[species"+species+" *"+animals.size()+"]");
	}

//	public void setAnimal(Animal animal) {
//		addAnimal(animal);
//	}
//	
//	public void addAnimal(Animal animal) {
//		animals.add(animal);
//	}
}
