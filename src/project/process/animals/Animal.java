package project.process.animals;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import project.process.server.AnimalsController;
import project.utils.AllObjects;

public class Animal extends Thread{
	int species;
	int x;
	int y;
	boolean stop;
	
	public static void main(String[] args) throws Exception {
		Animal animal = new Animal(Integer.valueOf(args[0]), Integer.valueOf(args[1]), Integer.valueOf(args[2]));
		communicate(animal);
	}
	
	public Animal(int a, int b, int c) {
		x = a;
		y = b;
		species = c;
		stop = true;
	}
	
	private static void communicate(Animal animal) throws Exception {
		Scanner scanner = new Scanner(System.in);
		String command = scanner.nextLine();
		if(command == "ready?") {
			System.out.println("yes sir");
		}else {
			throw new Exception();
		}
		command = scanner.nextLine();
		if(command == "start") {
			System.out.println("started");
			animal.start();
		}else {
			throw new Exception();
		}
		while(true) {
			command = scanner.next();
			switch(command) {
			case "resume":
				break;
			}
			
		}
		
		
	}


	@Override
	public void run() {
		//		System.out.println("amim1");
		int toX;
		int toY;
		while(true) {
			if(stop) {
				try {
					//					System.out.println("amim2");
					animalsController.sleep();
				} catch (InterruptedException e) {
					//					e.printStackTrace();
					//					System.out.println("Interrupted");
					break;
				}
			}

			if(Thread.currentThread().isInterrupted()) {
				break;
			}

			toX = ThreadLocalRandom.current().nextInt(-1, 2) + x;
			toY = ThreadLocalRandom.current().nextInt(-1, 2) + y;
			animalsController.move(this, toX, toY);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		animalsController.decreasePopultion(1);
	}

	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getSpecies() {
		return species;
	}
}
