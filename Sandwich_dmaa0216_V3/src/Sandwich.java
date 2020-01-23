import java.util.*;

/**
 * Class Sandwich: Describes a sandwich that can be ordered in a sandwich bar, deli etc.
 * The sandwich has a list number, a short name, a price and a list of ingredients.
 * In this implementation, the ingredient list is contained in a freeform String.
 * 
 * @author hnv@ucn.dk 
 * @version 2016.02.25
 */
public class Sandwich
{
    /** instance variables 
     * 
     */
    private int number;
    private String name;
    private ArrayList<String> ingredients;
    private double price;

    /**
     * Constructor for Sandwich that takes parameters for all fields of the class
     * @param  number   The number in the menu system
     * @param  name   The name of the sandwich
     * @param  ingredients   What's in this sandwich?
     * @param  price   The price of the sandwich
     */
    public Sandwich(int number, String name, double price)
    {
        // initialise instance variables
        this.number = number;
        this.name = name;
        this.ingredients = new ArrayList<String>();
        this.price = price;
    }

    /**
     * Constructor for Sandwich that takes no a parameters. The fields are given default values
     */
    public Sandwich()
    {
        // initialise instance variables
        number = 1;
        name = "æg";
        ingredients = new ArrayList<String>();
        ingredients.add("æg");
        ingredients.add("rejer");
        ingredients.add("bacon");
        price = 45.5;
    }

    // Getters and setters: Quick'n easy oneliners
    public int getNumber(){return number;}

    public String getName(){return name;}

    public ArrayList<String> getIngredients(){return ingredients;}

    public double getPrice(){return price;}

    public void setNumber(int number){this.number = number;}

    public void setName(String name){this.name = name;}

    public void setIngredients(ArrayList<String> ingredients)
    {
        this.ingredients = ingredients;
    }

    public void addIngredient(String newIngredient)
    {
        ingredients.add(newIngredient);
    }

    public void setPrice(double price){this.price = price;}

    /**
     * printInfo() prints relevant info about the state of the Sandwich object to the console
     */
    public void printInfo()
    {
        System.out.println("\tSandwich number: " + number);
        System.out.println("\tSandwich name: " + name);
        System.out.println("\tIngredients: ");
        System.out.print("\t");

        Iterator<String> it = ingredients.iterator();
        while (it.hasNext()) {
            String localIngredient = it.next();
            System.out.print(localIngredient);
            if (it.hasNext())
            {
                System.out.print(", ");
            }
            else {
                System.out.print("\n");
            }

        }
       System.out.println("\tThe Sandwich price is: " + price + " kr.");
       System.out.println("");

    }

    public boolean validIndex(int index)
    {
        if (ingredients.size() == 0) {
            return false;
        }
        else {
            if ( (index >= 0) && (index < ingredients.size()) ) {
                return true;
            }
            else {
                return false;
            }
        }
    }

    public void removeIngredient(int index)
    {
        if (validIndex(index)){
            ingredients.remove(index);
        }           
    }

    public void removeIngredient(String txt)
    {
        ingredients.remove(txt);
    }

}
