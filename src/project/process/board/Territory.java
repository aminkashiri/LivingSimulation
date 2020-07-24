package project.process.board;

import java.util.ArrayList;

import project.process.animals.Animal;
import project.process.server.AnimalsController;
import project.utils.AllObjects;
import project.utils.Colors;

public class Territory extends Thread{
	int species;
	int maxResident;
	ArrayList<Process> processes;
	int x;
	int y;
	AnimalsController animalsController;
	
	public Territory(int maxResidnet, int x, int y) {
		processes = new ArrayList<Process>();
		this.maxResident = maxResidnet;
		this.x = x;
		this.y = y;
		species = 0;
		animalsController = AllObjects.getAllObjects().getAnimalsController();

	}
	
	public void giveLife(Process process) {
//		species = animal.getSpecies();
//		processes.add(animal);
	}
	
	synchronized public boolean requestMoving(Animal animal) {
		if(species == 0) {
			processes.add(animal);
			species = animal.getSpecies();
		}else if(species == animal.getSpecies() &&  processes.size()<maxResident) {
			processes.add(animal);
		}else {
			return false;
		}
		return true;
	}

	synchronized public void removeAnimal(Animal animal) {
		processes.remove(animal);
		if(processes.size() == 0) {
			species = 0;
		}
	}

	public int starve() {
		int temp = 0;
		while(processes.size() > maxResident) {
			processes.get(processes.size()-1).interrupt();
			processes.remove(processes.size()-1);
			temp++;
		}
		return temp;
	}

	public int birth(int k) {
		int size = 0;
		if(species != 0 && (k%species) == 0) {
			size = processes.size();
			for(int i = 0 ; i < size ; i++) {
				Animal animal = new Animal(x,y,species);
				processes.add(animal);
				animal.start();
			}
		}
		return size;
	}

	public void live() {
		for(Animal animal : processes) {
			animal.start();
		}
	}

	public int die() {
		int temp = processes.size();
		while(processes.size() > 0) {
			processes.get(processes.size()-1).interrupt();
			processes.remove(processes.size()-1);
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
		return processes.size();
	}

	public int getPower() {
		return processes.size()*species;
	}

	public void print() {
		switch (species) {
		case 1:	
			System.out.print(""+Colors.BLACK_BACKGROUND+processes.size()+Colors.RESET);
			break;
		case 2:	
			System.out.print(""+Colors.RED_BACKGROUND+processes.size()+Colors.RESET);
			break;
		case 3:	
			System.out.print(""+Colors.BLACK+Colors.YELLOW_BACKGROUND+processes.size()+Colors.RESET);
			break;
		case 4:	
			System.out.print(""+Colors.BLUE_BACKGROUND+processes.size()+Colors.RESET);
			break;
		case 5:	
			System.out.print(""+Colors.BLACK+Colors.GREEN_BACKGROUND+processes.size()+Colors.RESET);
			break;
		case 6:	
			System.out.print(""+Colors.BLACK+Colors.WHITE_BACKGROUND+processes.size()+Colors.RESET);
			break;
		case 7:	
			System.out.print(""+Colors.MAGENTA_BACKGROUND+processes.size()+Colors.RESET);
			break;
		case 8:	
			System.out.print(""+Colors.BLACK+Colors.CYAN_BACKGROUND+processes.size()+Colors.RESET);
			break;
		default:
			System.out.print(""+processes.size()+Colors.RESET);
			break;
		}
	}
}
