package project.process.main;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import project.process.board.World;

public class BigBang {
	static int timeInterval = 1000;

	public static void main(String[] args) {
//		Scanner scanner = new Scanner(System.in);
//		int r = scanner.nextInt();	
//		int s = scanner.nextInt();	
//		int n = scanner.nextInt();	
//		int m = scanner.nextInt();	
//		int k = scanner.nextInt();	
//		int t = scanner.nextInt();
		int r = 1;
		int s = 1;
		int n = 4;
		int m = 4;
		int k = 4;
		double t = 1;
		
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
				if(counter % 4 == 0) {
					world.evolve();
				}
				if (counter % t == 0) {
					System.out.println("----------------------------------- Time:"+ (counter/4));
					world.printWorld();
				}
			}
		};
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(evolve, 0, timeInterval);
	}

}
