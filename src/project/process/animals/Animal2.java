package project.process.animals;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Animal2 {

	public static void main(String[] args) {
		String fileName = "src/logs/log.txt";
		BufferedWriter bufferedWriter;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(fileName, true));
			bufferedWriter.write("Start 2 \n"+args[0]+" "+args[2]);
			bufferedWriter.flush();
			Scanner scanner = new Scanner(System.in);
			String c = scanner.nextLine();
			bufferedWriter.write("second "+ c);
			bufferedWriter.flush();
			System.out.println("Finished");
			bufferedWriter.write("done");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
