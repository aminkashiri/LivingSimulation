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

	public AnimalController(int x, int y, int species, Process p) throws Exception {
		super();
		this.x = x;
		this.y = y;
		this.species = species;
		this.p = p;
		pw = new PrintWriter(p.getOutputStream());
		scanner = new Scanner(p.getInputStream());
//		Scanner scanner = new Scanner(p.getInputStream());
		pw.write("loooop");
		pw.flush();
		System.out.println("1");
		System.out.println(scanner.nextLine());
		System.out.println("2");
		System.exit(0);
		
		serverController = AllObjects.getAllObjects().getserverController();
	}

	@Override
	public void run() {
		String command = scanner.nextLine();
		System.out.println(command);
		pw.write("start");
		System.out.println(p.isAlive());
		if(command != "started") {
			try {
				throw new Exception();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		while(true) {
			command = scanner.next();
			synchronized (this) {
				switch (command) {
				case "stopped":
					try {
						serverController.sleep();
						pw.write("resume");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					scanner.nextLine();
					break;
				case "move":
					pw.write("move ");
					int x = Integer.valueOf(scanner.next());
					int y = Integer.valueOf(scanner.next());
					if(serverController.move(this, x, y)) {
						pw.write("pass");
					}else {
						pw.write("fail");
					}
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

	public void sendStop() {
		synchronized (this) {
			if(scanner.hasNext()) {
				scanner.nextLine();
			}
			pw.write("stop");
		}
	}

	public void kill() {
		synchronized (this) {
			if(scanner.hasNext()) {
				scanner.nextLine();
			}
			pw.write("die");
			serverController.decreasePopultion(1);
		}
		
	}


}
