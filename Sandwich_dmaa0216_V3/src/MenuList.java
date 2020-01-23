import java.util.*;
import java.io.*;
import java.text.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class MenuList: Used to maintain a list of Menu items offered for sale.
 * The class must make sure that the items are numbered sequentially from 1 and upwards,
 * and it must ensure that a menu name cannot be found twice in the list
 * 
 * @author hnv@ucn.dk 
 * @version 2016.03.14
 */
public class MenuList
{
    // instance variables
    private static MenuList theInstance = null; // Singleton
    private ArrayList<Menu> theMenus;
    private Scanner theInput;
    private static int menuNumber = 0;
    FileOutputStream fos;
    FileInputStream fis;
    /**
     * Constructor for objects of class MenuList - private to support the Singleton pattern
     */
    private MenuList()
    {
        // initialise instance variables
        theMenus = new ArrayList<Menu>();
        theInput = new Scanner(System.in);
        menuNumber = 0;
        // This project uses a file to persist the menus.
        // You must call saveMenus() before closing the program if you wish to use persistence.
        loadMenus();
    }

    /**
     * Get the one instance - Singleton pattern
     */
    public static MenuList getInstance()
    {
        if (theInstance == null) {
            theInstance = new MenuList();
        }
        return theInstance;
    }

    /**
     * Create a new Menu and add it to the list
     * This method has a basic TUI
     */
    public boolean createMenu()
    {
        boolean result = true;
        SandwichList sl = SandwichList.getInstance();
        ExtraList el = ExtraList.getInstance();
        ArrayList<Integer> extrasList = new ArrayList<Integer>();

        System.out.println("\fCreate Menu");                // print prompt
        System.out.print("Menu name: ");                // print prompt
        String menuName = theInput.nextLine().trim();
        System.out.print("Sandwich number: (1 - " + sl.maximumID() + ") ");                // print prompt
        String sandwichIDString = theInput.nextLine().trim();
        int sandwichID = Integer.parseInt(sandwichIDString);
        System.out.println("Extra number: (1 - " + el.maximumID() + ") ");  
        String extraIDString = theInput.nextLine().trim();
        extrasList.add(Integer.parseInt(extraIDString));
        boolean finished = false;
        System.out.println("Add Extra (or enter 'bye' to exit): ");  
        while (!finished) {
            String nextExtraIDString = theInput.nextLine().trim();
            String byebye = nextExtraIDString.toLowerCase();
            if (byebye.equals("bye")) {
                finished = true; 
            }
            else {
                extrasList.add(Integer.parseInt(nextExtraIDString));
            }
        }
        return addMenu(menuName, sandwichID ,extrasList);
    }
    
    /**
     * addMenu is private and currently only called from createMenu()
     * @param menuName the name of the menu
     * @param sandwichID the ID of the one and only sandwich in this menu
     * @param extrasList the list of 1 or more extras
     * Note: There is currently no check of the length of extrasList. The
     * TUI in createMenu() makes it difficult not to add at least one extra
     */
    private boolean addMenu(String menuName, int sandwichID, ArrayList<Integer> extrasList)
    {
        SandwichList sl = SandwichList.getInstance();
        ExtraList el = ExtraList.getInstance();
        Sandwich theNewSandwich = sl.getSandwich(sandwichID);
        ArrayList<Extra> theNewExtras = new ArrayList<Extra>();
        
        for (int i = 0;i<theMenus.size();i++) {
            if (theMenus.get(i).getName().equals(menuName)) {
                // A menu with that name already exists - no go!
                return false; 
            }
        }
        
        for (Integer extraID: extrasList)
        {
            Extra theNewExtra = el.getExtra(extraID);
            theNewExtras.add(theNewExtra);
        }
        
        int n = menuNumber;
        try {
            menuNumber++;
            Menu theNewMenu = new Menu(menuNumber,theNewSandwich,theNewExtras);
            theNewMenu.setName(menuName);
            theMenus.add(theNewMenu);
        }
        catch (Exception e) {
            menuNumber = n;
            return false;
        }
        return true;
    }

   /**
    * maximumID returns the maximum menu ID. Since the ID's are 1-indexed, it is also the 
    * number of existing menus.
     */
    public int maximumID()
    {
        return theMenus.size();
    }

   /**
    * getPrice returns the sum of the sandwich price and the prices of the extras.
    * @param menuID must be between 1 and maximumID()
     */
    public double getPrice(int menuID)
    {
        double answer = 0.0;
        for (int i=0;i<theMenus.size();i++)
        {
            if (theMenus.get(i).getNumber() == menuID) {
                answer = theMenus.get(i).getPrice();
            }
        }
        return answer;
    }

    /**
     * printInfo() prints relevant info about the state of the Menu object to the console
     * @param menuID must be between 1 and maximumID()
     */
    public void printInfo(int menuID)
    {
        for (int i=0;i<theMenus.size();i++)
        {
            if (theMenus.get(i).getNumber() == menuID) {
                theMenus.get(i).printInfo();
            }
        }
    }

    /**
     * saveMenus() works in pair with loadMenus() to save (persist) the menu list to a file
     * and retrieve it again. This implementation is quite primitive and could easily fail.
     */
    public void saveMenus()
    {
        String outString;
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("Menus.txt"));
            // Write these lines to the file.
            // ... We call newLine to insert a newline character.
            try {
                outString = String.format("%d",theMenus.size());
                writer.write(outString);
                writer.newLine();
            }
            catch (Exception e) {

            }
            for (Menu mn : theMenus) {

                try {
                    writer.write(mn.getName());
                    writer.newLine();
                    Integer id = mn.getSandwich().getNumber();
                    writer.write(id.toString());
                    writer.newLine();
                    id = mn.getExtras().size();
                    writer.write(id.toString());
                    writer.newLine();
                    for (Extra ext : mn.getExtras()) {
                        id = ext.getNumber();
                        writer.write(id.toString());
                        writer.newLine();
                    }
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
     * loadMenus() works in pair with saveMenus() to save (persist) the menu list to a file
     * and retrieve it again. This implementation is quite primitive and could easily fail.
     */
    public void loadMenus()
    {
        String inString, inName, nextID;
        SandwichList sl = SandwichList.getInstance();
        ExtraList el = ExtraList.getInstance();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("Menus.txt"));
            try {
                inString = reader.readLine().trim();
                int nMenus = Integer.parseInt(inString);
                theMenus = new ArrayList<Menu>(); 
                menuNumber = 0;
                for (int i=0;i<nMenus;i++) {

                    try {
                        inName = reader.readLine();
                        nextID = reader.readLine().trim();
                        int theSandwichID = Integer.parseInt(nextID);
                        Sandwich theNewSandwich = sl.getSandwich(theSandwichID);
                        ArrayList<Extra> theNewExtras = new ArrayList<Extra>();
                        inString = reader.readLine().trim();
                        int nextras = Integer.parseInt(inString);
                        for (int j = 0;j<nextras;j++) {
                            nextID = reader.readLine().trim();
                            int theExtraID = Integer.parseInt(nextID);
                            Extra theNewExtra = el.getExtra(theExtraID);
                            theNewExtras.add(theNewExtra);
                        }
                        menuNumber++;
                        Menu theNewMenu = new Menu(menuNumber,theNewSandwich,theNewExtras);
                        theNewMenu.setName(inName);
                        theMenus.add(theNewMenu);
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
