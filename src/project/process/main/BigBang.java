package project.process.main;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import project.process.board.World;

public class BigBang {
	static int timeInterval = 2000;

	public static void main(String[] args) {
		System.out.println("Input these values in this order (separated by spaces):");
		System.out.println("r (number of species), s (initial population per species), n (world height), m (world width), k (max residents per territory), t (print interval)");
		Scanner scanner = new Scanner(System.in);
		int r = scanner.nextInt();	
		int s = scanner.nextInt();	
		int n = scanner.nextInt();	
		int m = scanner.nextInt();	
		int k = scanner.nextInt();	
		int t = scanner.nextInt();
//		int r = 3;
//		int s = 2;
//		int n = 6;
//		int m = 6;
//		int k = 3;
//		double t = 1;
		
		World world = new World(r, s, n, m, k);
		bigBang(world, t);
//		scanner.close();
	}
	
	private static void bigBang(World world, double t) {
		TimerTask evolve = new TimerTask() {

			int counter = 0;

			@Override
			public void run() {
				counter++;
//				if(counter % 4 == 0) {
					world.evolve();
//				}
				if (counter % t == 0) {
					System.out.print("\033[H\033[2J");
					System.out.flush();
					System.out.println("-------------------- Time:"+ counter + " --------------------");
					world.printWorld();
				}
			}
		};
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(evolve, 0, timeInterval);
	}

}
