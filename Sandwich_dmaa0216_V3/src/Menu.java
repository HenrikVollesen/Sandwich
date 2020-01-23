import java.util.*;
/**
 * Class Menu: Describes a menu that can be ordered in a sandwich bar, deli etc.
 * The menu consists of one sandwich item and, optionally, one Extra item.
 * In this implementation, the Accompaniment object can be null whereas the Sandwich
 * object cannot be null.
 * 
 * @author hnv@ucn.dk 
 * @version 2016.02.25
 */
public class Menu
{
    /** instance variables 
     */
    private Sandwich theSandwich = new Sandwich();
    private ArrayList<Extra> theExtras;
    private String name;
    private int number;
    private double price = 0.0;

    /**
     * Constructor for Menu that takes parameters for number and Sandwich object
     * @param  number   The number in the menu system
     * @param  sandwich The Sandwich object that is part of this menu
     */
    public Menu(int number, Sandwich sandwich) throws Exception
    {
        // We will not allow a null sandwich
        if (sandwich == null)
        {
            throw new Exception();
        }
        this.number = number;
        theSandwich = sandwich;
        calcPrice();
    }

    /**
     * Constructor for Menu that takes parameters for number and Sandwich object
     * @param  number   The number in the menu system
     * @param  sandwich The Sandwich object that is part of this menu
     */
    public Menu(int number, Sandwich sandwich, ArrayList<Extra> theExtras) throws Exception
    {
        // We will not allow a null sandwich
        if ( (sandwich == null) || (theExtras == null) || (theExtras.size() < 1) )
        {
            throw new Exception();
        }
        this.number = number;
        theSandwich = sandwich;
        this.theExtras = theExtras;
        calcPrice();
    }

    // Getters and setters: Quick'n easy oneliners
    public int getNumber(){return number;}

    public String getName(){return name;}

    public Sandwich getSandwich(){return theSandwich;}

    public ArrayList<Extra>  getExtras(){return theExtras;}

    public double getPrice(){return price;}

    public void setNumber(int number){this.number = number;}

    public void setName(String name){this.name = name;}
    // setters modify the price
    public void setSandwich(Sandwich sandwich)
    {
        // We will not allow a null sandwich
        if (sandwich != null) {
            theSandwich = sandwich;
            calcPrice();
        }
        else {
            System.out.println("Cannot set Sandwich object to null.");
            System.out.println("Sandwich was not modified");
        }
    }

    public void addExtra(Extra newExtra)
    {
        theExtras.add(newExtra);
        calcPrice();
    }

    /**
     * printInfo() prints relevant info about the state of the Menu object to the console
     */
    public void printInfo()
    {
        int nExtras = theExtras.size();
        String info = "\tThe menu consists of one sandwich";
        if (nExtras != 0) {
            info += " and " + nExtras + " extra(s)";
        }
        info += ".";

        System.out.println("Menu info:");
        System.out.println(info);
        theSandwich.printInfo();
        for (int i=0;i< nExtras;i++) {
            theExtras.get(i).printInfo();
        }
        System.out.println("\tThe price of the menu is: " + price + " kr.");
        System.out.println("");
    }

    private void calcPrice()
    {
        price = theSandwich.getPrice();
        for (int i=0;i< theExtras.size();i++) {
            price += theExtras.get(i).getPrice();
        }
    }

}
