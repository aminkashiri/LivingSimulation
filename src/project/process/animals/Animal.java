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
	private boolean stop;
	int id;

	//	static File file;
	static BufferedWriter bufferedWriter;

	public static void main(String[] args) {
		Animal animal = new Animal(Integer.valueOf(args[0]), Integer.valueOf(args[1]), Integer.valueOf(args[2]), Integer.valueOf(args[3]));
		animal.log("\ncreated");
		Scanner scanner = new Scanner(System.in);
		String command;
loop:   while(true) {
			animal.log("listening 1");
			command = scanner.next();
			animal.log(command);
			animal.log("listening 2");

			switch(command) {
			case "start":
				animal.log("started");
				animal.start();
				scanner.nextLine();
				break;
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
				break loop;
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
				synchronized (animal) {
					animal.stop = true;
					animal.notify();
					scanner.nextLine();
				}
				break;
			}
		}
	}

	@Override
	public void run() {
		int toX;
		int toY;
		while(true) {
			log("begining of the loop");
			if(this.stop) {
				log("i should stop");
				synchronized (this) {
					try {
						log("stopping");
						System.out.println("stopped");
						this.wait();
					} catch (InterruptedException e) {
						break;
					}
				}
			}
			log("stop finished/ or no need to stop");

			if(Thread.currentThread().isInterrupted()) {
				break;
			}

			toX = ThreadLocalRandom.current().nextInt(-1, 2) + x;
			toY = ThreadLocalRandom.current().nextInt(-1, 2) + y;
			move(this, toX, toY);

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace(); //TODO: check here
				break;
			}
		}

	}

	private void move(Animal animal, int toX, int toY) {
		synchronized (this) {
			if(!stop) {
				System.out.println("moveTo "+toX + " " + toY);
				log("sending move req: "+toX + " " + toY);
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	void log(String string) {
		synchronized(bufferedWriter) {
			try {
				bufferedWriter.write("animal "+id+" : "+string+"\n");
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
		initialize();
	}

	private void initialize() {
		String fileName = "src/logs/log.txt";
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(fileName, true));
		} catch (IOException e) {
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
