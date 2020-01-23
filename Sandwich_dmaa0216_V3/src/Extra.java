
/**
 * Class Accompaniment: Describes an accompaniment (extra) to a sandwich, in the context of ordering a sandwich from
 * a sandwich bar, deli etc. 
 * 
 * @author hnv@ucn.dk 
 * @version 2016.02.25
 */
public class Extra
{
    /** instance variables 
     * 
     */
    private int number;
    private String description;
    private double price;

    /**
     * Constructor for Accompaniment that takes parameters for all fields of the class
     * @param  number   The number in the menu system
     * @param  description  What is this extra?
     * @param  price   The price of the accompaniment
     */
    public Extra(int number, String description, double price)
    {
        this.number = number;
        this.description = description;
        this.price = price;
    }

    /**
     * Constructor for Accompaniment that takes no parameters. The fields are given default values
     */
    public Extra()
    {
        this.number = 2;
        this.description = "salat";
        this.price = 22.5;
    }

    // Getters and setters: Quick'n easy oneliners
    public int getNumber(){return number;}
    public String getDescription(){return description;}
    public double getPrice(){return price;}

    
    public void setNumber(int number){this.number = number;}
    public void setDescription(String description){this.description = description;}
    public void setPrice(double price){this.price = price;}
    
    /**
     * printInfo() prints relevant info about the state of the Accompaniment object to the console
     */
    public void printInfo()
    {
        System.out.println("\tExtra number: " + number);
        System.out.println("\tExtra description: " + description);
        System.out.println("\tPrice of this Extra is: " + price + " kr.");
        System.out.println("");
    }
}
