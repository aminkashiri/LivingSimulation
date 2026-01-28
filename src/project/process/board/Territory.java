package project.process.board;

import java.io.IOException;
import java.util.ArrayList;

import project.process.animals.Animal;
import project.process.server.AnimalController;
import project.process.server.ServerController;
import project.utils.AllObjects;
import project.utils.Colors;

public class Territory{
	int species;
	int maxResident;
	ArrayList<AnimalController> animalControllers;
	int x;
	int y;
	ServerController serverController;
	
	public Territory(int maxResidnet, int x, int y) {
		animalControllers = new ArrayList<AnimalController>();
		this.maxResident = maxResidnet;
		this.x = x;
		this.y = y;
		species = 0;
		serverController = AllObjects.getAllObjects().getserverController();
	}
	
	public void giveLife(AnimalController animalController) {
		species = animalController.getSpecies();
		animalControllers.add(animalController);
	}
	
	synchronized public boolean requestMoving(AnimalController animalController) {
		if(species == 0) {
			animalControllers.add(animalController);
			species = animalController.getSpecies();
		}else if(species == animalController.getSpecies() &&  animalControllers.size()<maxResident) {
			animalControllers.add(animalController);
		}else {
			return false;
		}
		return true;
	}

	synchronized public void removeAnimal(AnimalController animalController) {
		animalControllers.remove(animalController);
		if(animalControllers.size() == 0) {
			species = 0;
		}
	}

	public int starve() {
		int temp = 0;
		while(animalControllers.size() > maxResident) {
			animalControllers.get(animalControllers.size()-1).interrupt();
//			animalControllers.get(animalControllers.size()-1).die();
//			animalControllers.get(animalControllers.size()-1).died = true;
			animalControllers.remove(animalControllers.size()-1);
			temp++;
		}
		return temp;
	}

	public int birth(int k) {
		int size = 0;
		if(species != 0 && (k%species) == 0) {
			size = animalControllers.size();
			for(int i = 0 ; i < size ; i++) {
				Process p;
				try {
					String classpath = System.getProperty("user.dir") + "/bin";
					p = Runtime.getRuntime().exec("java  -cp " + classpath + " project.process.animals.Animal "+x+" "+y+" "+species+" "+(serverController.getPopulation()+1));
					AnimalController animalController = new AnimalController(x, y, species, p);
					animalController.start();
					animalControllers.add(animalController);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return size;
	}

	public int live() {
		for(AnimalController animalController : animalControllers) {
			animalController.start();
			return 1;
		}
		return 0;
	}

	public int die() {
		int temp = animalControllers.size();
		while(animalControllers.size() > 0) {
			animalControllers.get(animalControllers.size()-1).interrupt();;
//			animalControllers.get(animalControllers.size()-1).die();;
//			animalControllers.get(animalControllers.size()-1).died = true;
			animalControllers.remove(animalControllers.size()-1);
		}
		species = 0;
		return temp;
	}
	
	public int getSpecies() {
		return species;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getCount() {
		return animalControllers.size();
	}

	public int getPower() {
		return animalControllers.size()*species;
	}

	public void print() {
		switch (species) {
		case 1:	
			System.out.print(""+Colors.BLACK_BACKGROUND+animalControllers.size()+Colors.RESET);
			break;
		case 2:	
			System.out.print(""+Colors.RED_BACKGROUND+animalControllers.size()+Colors.RESET);
			break;
		case 3:	
			System.out.print(""+Colors.BLACK+Colors.YELLOW_BACKGROUND+animalControllers.size()+Colors.RESET);
			break;
		case 4:	
			System.out.print(""+Colors.BLUE_BACKGROUND+animalControllers.size()+Colors.RESET);
			break;
		case 5:	
			System.out.print(""+Colors.BLACK+Colors.GREEN_BACKGROUND+animalControllers.size()+Colors.RESET);
			break;
		case 6:	
			System.out.print(""+Colors.BLACK+Colors.WHITE_BACKGROUND+animalControllers.size()+Colors.RESET);
			break;
		case 7:	
			System.out.print(""+Colors.MAGENTA_BACKGROUND+animalControllers.size()+Colors.RESET);
			break;
		case 8:	
			System.out.print(""+Colors.BLACK+Colors.CYAN_BACKGROUND+animalControllers.size()+Colors.RESET);
			break;
		default:
			System.out.print(""+animalControllers.size()+Colors.RESET);
			break;
		}
	}

//	public void stop() {
//		for(AnimalController animalController: animalControllers) {
//			animalController.sendStop();
//		}
//			
//	}
}
