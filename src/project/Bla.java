package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class Bla {

	public static void main(String[] args)  {
		// TODO Auto-generated method stub
		try {
			File file = new File("src/logs/log.txt");
			file.createNewFile();
			PrintWriter printWriter;
			printWriter = new PrintWriter(file);
			printWriter.write("aminnin");
			printWriter.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
