import java.util.*;
import java.io.*;
import java.text.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
//import java.io.FileNotFoundException;

/**
 * Class SandwichList: Used to maintain a list of Sandwich items offered for
 * sale. The class must make sure that the items are numbered sequentially from
 * 1 and upwards, and it must ensure that a sandwich name cannot be found twice
 * in the list
 * 
 * @author hnv@ucn.dk
 * @version 2016.03.14
 */
public class SandwichList {
	// instance variables
	private static SandwichList theInstance = null; // Singleton
	private ArrayList<Sandwich> theSandwiches;
	private Scanner theInput;
	private static int sandwichNumber = 0;
	FileOutputStream fos;
	FileInputStream fis;

	/**
	 * Constructor for objects of class SandwichList - private to support the
	 * Singleton pattern
	 */
	private SandwichList() {
		// initialise instance variables
		theSandwiches = new ArrayList<Sandwich>();
		theInput = new Scanner(System.in);
		// This project uses a file to persist the menus.
		// You must call saveSandwiches() before closing the program if you wish to use
		// persistence.
		loadSandwiches();
	}

	/**
	 * Get the one instance - Singleton pattern
	 */
	public static SandwichList getInstance() {
		if (theInstance == null) {
			theInstance = new SandwichList();
		}
		return theInstance;
	}

	/**
	 * Create a new sandwich and add it to the list This method has a basic TUI
	 */
	public boolean createSandwich() {
		System.out.println("\fCreate sandwich"); // print prompt
		System.out.print("Sandwich name: "); // print prompt
		String sandwichName = theInput.nextLine().trim();
		System.out.print("Sandwich price: "); // print prompt
		String thePrice = theInput.nextLine().trim().toLowerCase();
		Double sandwichPrice = Double.parseDouble(thePrice);

		return addSandwich(sandwichName, sandwichPrice);
	}

	/**
	 * addSandwich is private and currently only called from createSandwich()
	 * 
	 * @param sandwichName
	 *            the name of the sandwich
	 * @param sandwichPrice
	 *            the price of this sandwich Note: Currently no ingredients are
	 *            added in createSandwich() Ingredients can be added using
	 *            addIngredients()
	 */
	private boolean addSandwich(String sandwichName, double sandwichPrice) {
		for (int i = 0; i < theSandwiches.size(); i++) {
			if (theSandwiches.get(i).getName().equals(sandwichName)) {
				// A sandwich with that name already exists - no go!
				return false;
			}
		}
		sandwichNumber++;
		Sandwich theNewSandwich = new Sandwich(sandwichNumber, sandwichName, sandwichPrice);
		theSandwiches.add(theNewSandwich);
		return true;
	}

	/**
	 * Add ingredients to an existing sandwich This method has a basic TUI
	 * 
	 * @param sandwichID
	 *            must be between 1 and maximumID()
	 */
	public boolean addIngredients(int sandwichID) {
		/**
		 * This block was wrong! It assumed sandwichID was 0-indexed. It was replaced by
		 * the block below
		 */
		/*
		 * if ( (sandwichID < 0) || (sandwichID > theSandwiches.size()-1) ) {
		 * System.out.println("\fSandwich #" + sandwichID + " not found!"); // print
		 * prompt return false; }
		 */
		/**
		 * The block above was wrong and was replaced by the search block below:
		 */
		boolean found = false;
		int index = -1;
		for (int i = 0; ((i < theSandwiches.size()) && !(found)); i++) {
			if (theSandwiches.get(i).getNumber() == sandwichID) {
				found = true;
				index = i;
			}
		}
		if (index == -1) {
			return false;
		}

		ArrayList<String> theIngredients = theSandwiches.get(sandwichID).getIngredients();
		System.out.println("\fSandwich #" + sandwichID + " ingredients:"); // print prompt
		for (int i = 0; i < theIngredients.size(); i++) {
			System.out.println(theIngredients.get(i));
		}
		System.out.println("\fAdd ingredient (or enter 'bye' to exit): ");
		boolean finished = false;
		while (!finished) {
			String nextIngredient = theInput.nextLine().trim();
			String byebye = nextIngredient.toLowerCase();
			if (byebye.equals("bye")) {
				finished = true;
			} else {
				theIngredients.add(nextIngredient);
			}
		}
		theSandwiches.get(sandwichID).setIngredients(theIngredients);
		return true;
	}

	/**
	 * maximumID returns the maximum sandwich ID. Since the ID's are 1-indexed, it
	 * is also the number of existing sandwiches.
	 */
	public int maximumID() {
		return theSandwiches.size();
	}

	/**
	 * getPrice returns the sandwich price.
	 * 
	 * @param sandwichID
	 *            must be between 1 and maximumID()
	 */
	public double getPrice(int sandwichID) {
		double answer = 0.0;
		for (int i = 0; i < theSandwiches.size(); i++) {
			if (theSandwiches.get(i).getNumber() == sandwichID) {
				answer = theSandwiches.get(i).getPrice();
			}
		}
		return answer;
	}

	/**
	 * getSandwich returns a sandwich.
	 * 
	 * @param sandwichID
	 *            must be between 1 and maximumID() This method is used by Menu to
	 *            retrieve info of a sandwich
	 */
	public Sandwich getSandwich(int sandwichID) {
		Sandwich answer = null;
		for (int i = 0; i < theSandwiches.size(); i++) {
			if (theSandwiches.get(i).getNumber() == sandwichID) {
				answer = theSandwiches.get(i);
			}
		}
		return answer;
	}

	/**
	 * printInfo() prints relevant info about the state of the Sandwich object to
	 * the console
	 * 
	 * @param sandwichID
	 *            must be between 1 and maximumID()
	 */
	public void printInfo(int sandwichID) {
		for (int i = 0; i < theSandwiches.size(); i++) {
			if (theSandwiches.get(i).getNumber() == sandwichID) {
				theSandwiches.get(i).printInfo();
			}
		}
	}

	/**
	 * saveSandwiches() works in pair with loadSandwiches() to save (persist) the
	 * Sandwich list to a file and retrieve it again. This implementation is quite
	 * primitive and could easily fail.
	 */
	public void saveSandwiches() {
		String outString;
		try(BufferedWriter writer = new BufferedWriter(new FileWriter("Sandwiches.txt"));) {
			
			// Write these lines to the file.
			// ... We call newLine to insert a newline character.
			try {
				outString = String.format("%d", theSandwiches.size());
				writer.write(outString);
				writer.newLine();
			} catch (Exception e) {

			}
			for (Sandwich sw : theSandwiches) {

				try {
					writer.write(sw.getName());
					writer.newLine();
					Double p = sw.getPrice();
					writer.write(p.toString());
					writer.newLine();
					writer.write(sw.getIngredients().size());
					writer.newLine();
					for (String ing : sw.getIngredients()) {
						writer.write(ing);
						writer.newLine();
					}
				} catch (Exception e) {

				}
			}
			writer.flush();
			//writer.close();
		} catch (Exception e) {

		}
	}

	/**
	 * loadSandwiches() works in pair with saveSandwiches() to save (persist) the
	 * Sandwich list to a file and retrieve it again. This implementation is quite
	 * primitive and could easily fail.
	 */
	public void loadSandwiches() {
		String inString, name, price;

		try {
			BufferedReader reader = new BufferedReader(new FileReader("Sandwiches.txt"));
			try {
				inString = reader.readLine().trim();
				int nSandwiches = Integer.parseInt(inString);
				theSandwiches = new ArrayList<Sandwich>();
				sandwichNumber = 0;
				for (int i = 0; i < nSandwiches; i++) {

					try {
						name = reader.readLine();
						price = reader.readLine().trim();
						double thePrice = Double.parseDouble(price);
						inString = reader.readLine().trim();
						int nIngredients = Integer.parseInt(inString);
						sandwichNumber++;
						Sandwich theNewSandwich = new Sandwich(sandwichNumber, name, thePrice);
						for (int j = 0; j < nIngredients; j++) {
							inString = reader.readLine().trim();
							theNewSandwich.addIngredient(inString);
						}
						theSandwiches.add(theNewSandwich);
					} catch (Exception e) {

					}
				}
			} catch (Exception e) {

			}
		} catch (Exception e) {

		}

	}
}
