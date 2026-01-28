package project.process.board;

import java.io.IOException;
import java.util.Scanner;

import project.process.animals.Animal;
import project.process.server.AnimalController;
import project.process.server.ServerController;
import project.thread.animals.AnimalsController;

public class World {
	Territory[][] territories;
	int maxResidnet;
	int width;
	int height;
	int numberOfSpecies;
	int initialPopulation;
	
	int year;
	ServerController serverController;
	
	public World(int r, int s, int n, int m, int k) {
		numberOfSpecies = r;
		initialPopulation = s;
		height = n;
		width = m;
		maxResidnet = k;
		
		year = 0;
		initialize();
		printWorld();
		serverController.startLife();

	}

	private void initialize() {
		territories = new Territory[height][width];
		serverController = new ServerController(territories, numberOfSpecies, numberOfSpecies*initialPopulation);
		for(int i = 0 ; i < height ; i++) {
			for(int j = 0 ; j < width ; j++) {
				territories[i][j] = new Territory(maxResidnet, i, j);
			}
		}

		int deltaX = height/numberOfSpecies;
		int deltaY = width/initialPopulation;
		int x;
		Animal animal;
		int ids = 0;
		for(int i = 0 ; i<numberOfSpecies ; i++) {//i+1 is species number
			x = deltaX*(i+1)-1;
			for(int j = 0 ; j<initialPopulation ; j++) {
				try {
					ids++;
					String classpath = System.getProperty("user.dir") + "/bin";
					String command = "java  -cp " + classpath + " project.process.animals.Animal "+x+" "+((j+1)*deltaY-1)+" "+(i+1) + " " + ids;
					Process p = Runtime.getRuntime().exec(command);
					AnimalController animalController = new AnimalController(x, (j+1)*deltaY-1, i+1, p);
					territories[x][(j+1)*deltaY-1].giveLife(animalController);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}
	}

	public void evolve() {
		year = (year % numberOfSpecies) +1;
//		System.out.println("before evolve");
		serverController.stop();
//		System.out.println("----------------[Before Death]----------------");
//		printWorld();
		serverController.kill();
//		System.out.println("----------------[After Death]----------------");
//		printWorld();
		serverController.birth(year);
//		System.out.println("----------------[Childs Are Born]----------------");
//		printWorld();
//		System.out.println("----------------[Next Generation]----------------\n");
		serverController.resume();
//		System.out.println("after evolve");
	}
	
	public void printWorld() {
		for(int i = 0 ; i < height ; i++) {
			for(int j = 0 ; j < width ; j++) {
				territories[i][j].print();
				System.out.print("  ");
			}
			System.out.println();
		}
		System.out.println();
//		animalsController.print();
	}

}
