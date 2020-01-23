import java.util.*;
import java.io.*;
import java.text.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class ExtraList: Used to maintain a list of Extra items offered for sale.
 * The class must make sure that the items are numbered sequentially from 1 and upwards,
 * and it must ensure that an Extra name cannot be found twice in the list
 * 
 * @author hnv@ucn.dk 
 * @version 2016.03.14
 */
public class ExtraList
{
    // instance variables
    private static ExtraList theInstance = null; // Singleton
    private ArrayList<Extra> theExtras;
    private Scanner theInput;
    private static int extraNumber = 0;
    FileOutputStream fos;
    FileInputStream fis;

    /**
     * Constructor for objects of class ExtraList - private to support the Singleton pattern
     */
    private ExtraList()
    {
        // initialise instance variables
        theExtras = new ArrayList<Extra>();
        theInput = new Scanner(System.in);
        // This project uses a file to persist the menus.
        // You must call saveExtras() before closing the program if you wish to use persistence.
        loadExtras();
    }

    /**
     * Get the one instance - Singleton pattern
     */
    public static ExtraList getInstance()
    {
        if (theInstance == null) {
            theInstance = new ExtraList();
        }
        return theInstance;
    }

   /**
    * maximumID returns the maximum Extra ID. Since the ID's are 1-indexed, it is also the 
    * number of existing Extras.
     */
    public int maximumID()
    {
        return theExtras.size();
    }

   /**
    * getPrice returns the price of the extra.
    * @param extraID must be between 1 and maximumID()
     */
    public double getPrice(int extraID)
    {
        double answer = 0.0;
        for (int i=0;i<theExtras.size();i++)
        {
            if (theExtras.get(i).getNumber() == extraID) {
                answer = theExtras.get(i).getPrice();
            }
        }
        return answer;
    }

   /**
    * getExtra returns a sandwich.
    * @param extraID must be between 1 and maximumID()
    * This method is used by Menu to retrieve info of an Extra
     */
    public Extra getExtra(int extraID)
    {
        Extra answer = null;
        for (int i=0;i<theExtras.size();i++)
        {
            if (theExtras.get(i).getNumber() == extraID) {
                answer = theExtras.get(i);
            }
        }
        return answer;
    }

    /**
     * Create a new Extra and add it to the list
     * This method has a basic TUI
     */
    public boolean createExtra()
    {
        System.out.println("\fCreate extra");                // print prompt
        System.out.print("Extra description: ");                // print prompt
        String extraName = theInput.nextLine().trim();
        System.out.print("Extra price: ");                // print prompt
        String thePrice = theInput.nextLine().trim().toLowerCase();
        Double extraPrice = Double.parseDouble(thePrice);

        return addExtra(extraName,extraPrice);
    }

    /**
     * addExtra is private and currently only called from createExtra()
     * @param description the description of the Extra
     * @param extraPrice the price of this Extra
     */
    private boolean addExtra(String description, double extraPrice)
    {
        for (int i = 0;i<theExtras.size();i++) {
            if (theExtras.get(i).getDescription().equals(description)) {
                // An Extra with that name already exists - no go!
                return false; 
            }
        }
        extraNumber++;
        Extra theNewExtra = new Extra(extraNumber,description,extraPrice);
        theExtras.add(theNewExtra);
        return true;
    }

    /**
     * printInfo() prints relevant info about the state of the Extra object to the console
     */
    public void printInfo(int extraID)
    {
        for (int i=0;i<theExtras.size();i++)
        {
            if (theExtras.get(i).getNumber() == extraID) {
                theExtras.get(i).printInfo();
            }
        }
    }

    /**
     * saveExtras() works in pair with loadExtras() to save (persist) the Extra list to a file
     * and retrieve it again. This implementation is quite primitive and could easily fail.
     */
    public void saveExtras()
    {
        String outString;
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("Extras.txt"));
            // Write these lines to the file.
            // ... We call newLine to insert a newline character.
            try {
                outString = String.format("%d",theExtras.size());
                writer.write(outString);
                writer.newLine();
            }
            catch (Exception e) {

            }
            for (Extra xt : theExtras) {

                try {
                    writer.write(xt.getDescription());
                    writer.newLine();
                    Double p = xt.getPrice();
                    writer.write(p.toString());
                    writer.newLine();
                }
                catch (Exception e) {

                }
            }
            writer.flush();
        }
        catch (Exception e) {

        }

    }

    /**
     * loadExtras() works in pair with saveExtras() to save (persist) the Extra list to a file
     * and retrieve it again. This implementation is quite primitive and could easily fail.
     */
    public void loadExtras()
    {
        String inString, description, price;

        try {
            BufferedReader reader = new BufferedReader(new FileReader("Extras.txt"));
            try {
                inString = reader.readLine().trim();
                int nExtras = Integer.parseInt(inString);
                theExtras = new ArrayList<Extra>(); 
                extraNumber = 0;
                for (int i=0;i<nExtras;i++) {
                    try {
                    description = reader.readLine();
                    price = reader.readLine().trim();
                    double thePrice = Double.parseDouble(price);
                    extraNumber++;
                    Extra theNewExtra = new Extra(extraNumber, description, thePrice);
                    theExtras.add(theNewExtra);
                    }
                    catch (Exception e) {

                    }
                }
            }
            catch (Exception e) {

            }
        }
        catch (Exception e) {

        }

    }
}
