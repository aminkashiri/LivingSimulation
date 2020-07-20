package project.main;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import project.world.World;

public class BigBang extends Timer{
	static int timeInterval= 1000;
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int r = scanner.nextInt();	
		int s = scanner.nextInt();	
		int n = scanner.nextInt();	
		int m = scanner.nextInt();	
		int k = scanner.nextInt();	
		int t = scanner.nextInt();	
		World world = new World(r,s,n,m,k,t);
		bigBang(world);
	}

	private static void bigBang(World world) {
		TimerTask evolve = new TimerTask() {
			
			@Override
			public void run() {
				world.evolve();
			}
		};
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(evolve, 0, timeInterval);
	}
	
}
