package project.process.animals;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Animal extends Thread{
	int species;
	int x;
	int y;
	boolean stop;
	
	public static void main(String[] args) throws Exception {
		Animal animal = new Animal(Integer.valueOf(args[0]), Integer.valueOf(args[1]), Integer.valueOf(args[2]));

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
				animal.stop = false;
				synchronized (animal) {
					animal.notify();
				}
				scanner.nextLine();
				break;
			case "die":
				animal.interrupt();
				scanner.nextLine();
				break;
			case "move":
				if(scanner.next() == "pass") {
					animal.x = Integer.valueOf(scanner.next());
					animal.y = Integer.valueOf(scanner.next());
					synchronized (animal) {
						animal.notify();
					}
				}else {
					synchronized (animal) {
						animal.notify();
					}
				}
				scanner.nextLine();
				break;
			case "stop":
				animal.stop = true;
				animal.notify();
				scanner.nextLine();
				break;
			}
		}
	}
	
	public Animal(int a, int b, int c) {
		x = a;
		y = b;
		species = c;
		stop = true;
	}
	
	@Override
	public void run() {
		int toX;
		int toY;
		while(true) {
			if(stop) {
				synchronized (this) {
					try {
						System.out.println("stopped");
						this.wait();
					} catch (InterruptedException e) {
						//					e.printStackTrace();
						break;
					}
				}
			}

			if(Thread.currentThread().isInterrupted()) {
				break;
			}

			toX = ThreadLocalRandom.current().nextInt(-1, 2) + x;
			toY = ThreadLocalRandom.current().nextInt(-1, 2) + y;
			move(this, toX, toY);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace(); //TODO: check here
				break;
			}
		}

	}

	synchronized private void move(Animal animal, int toX, int toY) {
		System.out.println("moveTo "+toX + " " + toY);
		try {
			this.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
