package project.process.server;

import java.io.PrintWriter;
import java.util.Scanner;

import project.utils.AllObjects;

public class AnimalController extends Thread{
	int x;
	int y;
	int species;
	Process p;

	PrintWriter pw;
	Scanner scanner;

	ServerController serverController;
	public boolean died;

	public AnimalController(int x, int y, int species, Process p) throws Exception {
		super();
		this.x = x;
		this.y = y;
		this.species = species;
		this.p = p;
		pw = new PrintWriter(p.getOutputStream(),true);
		scanner = new Scanner(p.getInputStream());
		died = false;
		serverController = AllObjects.getAllObjects().getserverController();
	}

	@Override
	public void run() {
		String command;
		pw.println("start");
//		System.out.println("animal controller started");
		while(true) {
			if(serverController.isStop()) {
//				System.out.println("animal controller: server is in stop mode");
				if(scanner.hasNext()) {
					scanner.nextLine();
				}
//				System.out.println("animal controller: sending stop");
				pw.println("stop");
			}
			command = scanner.next();
//			System.out.println("animal controller received " + command);
			if(Thread.currentThread().isInterrupted()) {
				pw.println("die");
				break;
//				serverController.decreasePopultion(1);
			}
			synchronized (this) {
				switch (command) {
				case "stopped":
					try {
//						System.out.println("animal controller: animal is stopped");
						serverController.sleep();
//						System.out.println("animal controller: server resumes, sending to animal");
						pw.println("resume");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					scanner.nextLine();
					break;
				case "moveTo":
					int x = Integer.valueOf(scanner.next());
					int y = Integer.valueOf(scanner.next());
					pw.printf("move ");
//					System.out.print("---> ("+x+" "+y+")");
					if(serverController.move(this, x, y)) {
//						System.out.println(" passed");
						pw.printf("pass\n");
					}else {
//						System.out.println(" failed");
						pw.printf("fail\n");
					}
					scanner.nextLine();
					break;

				}
			}
		}

	}

	public int getSpecies() {
		return species;
	}

	public int getY() {
		return y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
//	public void die() {
//		pw.println("die");
//		serverController.decreasePopultion(1);
//		
//	}

}
