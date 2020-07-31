package project.process.animals;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Animal extends Thread{
	int species;
	int x;
	int y;
	boolean stop;
	int id;
	
//	static File file;
	static BufferedWriter bufferedWriter;
	
	public static void main(String[] args) {
		try {
			bufferedWriter.write("animal ");
			bufferedWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Animal animal = new Animal(Integer.valueOf(args[0]), Integer.valueOf(args[1]), Integer.valueOf(args[2]), Integer.valueOf(args[3]));
		System.out.println("AMIN");
//		System.out.println("started");
		animal.log("started1");
		Scanner scanner = new Scanner(System.in);
		animal.log("started2");
//		String command = scanner.nextLine();
//		if(command == "ready?") {
//			System.out.println("yes sir");
//		}else {
//			animal.log("error 1");
//		}
		String command = scanner.nextLine();
//		command = scanner.nextLine();
		if(command == "start") {
			System.out.println("started");
			animal.start();
			animal.log("finally");
		}else {
			animal.log("error 2");
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
	
	private void log(String string) {
		synchronized(bufferedWriter) {
			try {
				bufferedWriter.write("animal "+id+" : "+string);
				bufferedWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public Animal(int a, int b, int c, int id) {
		x = a;
		y = b;
		species = c;
		this.id = id;
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

	public static void initialize() {
//		file = new File("src/logs/log.txt");
		String fileName = "src/logs/log.txt";
		try {
//			file.createNewFile();
			bufferedWriter = new BufferedWriter(new FileWriter(fileName, true));
//			printWriter = new PrintWriter(file);
//			printWriter.write("Start \n");
//			printWriter.flush();
			bufferedWriter.write("StarT \n");
			bufferedWriter.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
