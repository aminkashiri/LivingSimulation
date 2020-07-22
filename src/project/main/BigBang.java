package project.main;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import project.world.World;

public class BigBang extends Timer{
	static int timeInterval= 1000;
	public static void main(String[] args) {
//		Scanner scanner = new Scanner(System.in);
//		int r = scanner.nextInt();	
//		int s = scanner.nextInt();	
//		int n = scanner.nextInt();	
//		int m = scanner.nextInt();	
//		int k = scanner.nextInt();	
//		int t = scanner.nextInt();	
		int r = 8;	
		int s = 4;	
		int n = 32;	
		int m = 32;	
		int k = 4;	
		int t = 1;	
		World world = new World(r,s,n,m,k,t);
		bigBang(world);
//		scanner.close();
	}

	private static void bigBang(World world) {
		TimerTask evolve = new TimerTask() {
			
			int counter = 0;
			@Override
			public void run() {
				counter++;
				world.printWorld();
				if(counter%2 == 0) {
					world.evolve();
				}
			}
		};
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(evolve, 0, timeInterval);
	}
	
}
