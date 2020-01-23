import java.util.*;
import java.text.DecimalFormat;

/**
 * Class Order: Describes the ordering of the products of a sandwich shop: Sandwiches, Extras and Menus.
 * Menus consist of One Sandwich and 0 to many Extras
 * 
 * @author hnv@ucn.dk 
 * @version 2016.02.25
 */
public class Order
{
    /** instance variables 
     */
    private boolean isDelivery = false;
    private String address = "";
    private int postalCode;
    private double fee = 20.0;
    private HashMap<Integer, Integer> sandwichOrder;
    private HashMap<Integer, Integer> extraOrder;
    private HashMap<Integer, Integer> menuOrder;

    /**
     * Constructor for Order with no parameters - no delivery is assumed
     */
    public Order() 
    {
        sandwichOrder = new HashMap<Integer, Integer>();
        extraOrder = new HashMap<Integer, Integer>();
        menuOrder = new HashMap<Integer, Integer>();
    }

    /**
     * Constructor for Order that takes parameters for a delivery
     * @param  address   The number in the menu system
     * @param  postalCode The Sandwich object that is part of this menu
     */
    public Order(String address, int postalCode) 
    {
        sandwichOrder = new HashMap<Integer, Integer>();
        extraOrder = new HashMap<Integer, Integer>();
        menuOrder = new HashMap<Integer, Integer>();
        isDelivery = true;
        this.address = address;
        this.postalCode = postalCode;
    }

    // Getters and setters: Quick'n easy oneliners
    public String getAddress(){return address;}

    public int getPostalCode(){return postalCode;}

    public double getPrice()
    {
        return calcPrice();
    }

    public void addSandwichOrder(int sandwichID, int numberOfItems)
    {
        Integer existingNumber = sandwichOrder.get(sandwichID);
        if ( existingNumber == null) {
            sandwichOrder.put(sandwichID, numberOfItems);
        }
        else {
            sandwichOrder.put(sandwichID, numberOfItems + existingNumber);
        }
    }

    public void addExtraOrder(int extraID, int numberOfItems)
    {
        Integer existingNumber = extraOrder.get(extraID);
        if ( existingNumber == null) {
            extraOrder.put(extraID, numberOfItems);
        }
        else {
            extraOrder.put(extraID, numberOfItems + existingNumber);
        }
    }

    public void addMenuOrder(int menuID, int numberOfItems)
    {
        Integer existingNumber = menuOrder.get(menuID);
        if ( existingNumber == null) {
            menuOrder.put(menuID, numberOfItems);
        }
        else {
            menuOrder.put(menuID, numberOfItems + existingNumber);
        }
    }

    /**
     * printInfo() prints relevant info about the state of the Menu object to the console
     */
    public void printInfo()
    {
        System.out.println("\fDelivery info:");
        System.out.println("Delivery adress: " + address );
        System.out.println("Delivery postal code: " + postalCode );
        System.out.println("Delivery fee: " + fee + " kr." );
        printMenuInfo();
        printSandwichInfo();
        printExtraInfo();
        calcPrice();
        //DecimalFormat fmt1 = new DecimalFormat("#.##");
        DecimalFormat fmt2 = new DecimalFormat("0.00");

        double price = calcPrice();
        //System.out.println("The total price of the delivery is: " + price + " kr.");
        //System.out.println("The total price of the delivery is: " + fmt1.format(price) + " kr.");
        System.out.println("The total price of the delivery is: " + fmt2.format(price) + " kr.");
    }

    /**
     * printSandwichList() prints relevant info about each Sandwich offered by the shop.
     * It does so by delegating the print of specific Sandwich info to theSandwichList,
     * because this class knows about this info.
     */
    public void printSandwichList()
    {
        System.out.println("\fOur Sandwiches are:");
        SandwichList sl = SandwichList.getInstance();
        int maxSandwichID = sl.maximumID();
        for (int i=1;i <= maxSandwichID;i++)
        {
            sl.printInfo(i);
        }
    }

    /**
     * printExtraList() prints relevant info about each Extra offered by the shop.
     * It does so by delegating the print of specific Extra info to theExtraList,
     * because this class knows about this info.
     */
    public void printExtraList()
    {
        System.out.println("\fOur Extras are:");
        ExtraList el = ExtraList.getInstance();
        int maxExtraID = el.maximumID();
        for (int i=1;i <= maxExtraID;i++)
        {
            el.printInfo(i);
        }
    }

    /**
     * printExtraList() prints relevant info about each Extra offered by the shop.
     * It does so by delegating the print of specific Extra info to theExtraList,
     * because this class knows about this info.
     */
    public void printMenuList()
    {
        System.out.println("\fOur Menus are:");
        MenuList ml = MenuList.getInstance();
        int maxMenuID = ml.maximumID();
        for (int i=1;i <= maxMenuID;i++)
        {
            ml.printInfo(i);
        }
    }

    private double calcPrice()
    {
        double price = 0;
        Integer nxt;
        Integer nOrdered;
        
        SandwichList sl = SandwichList.getInstance();
        Set<Integer> theSandwichSet = sandwichOrder.keySet();
        Iterator itSandwich = theSandwichSet.iterator();
        while (itSandwich.hasNext())
        {
            nxt = (Integer)itSandwich.next();
            nOrdered = sandwichOrder.get(nxt);
            price += nOrdered * sl.getPrice(nxt);
        }

        ExtraList el = ExtraList.getInstance();
        Set<Integer> theExtraSet = extraOrder.keySet();
        Iterator itExtra = theExtraSet.iterator();
        while (itExtra.hasNext())
        {
            nxt = (Integer)itExtra.next();
            nOrdered = extraOrder.get(nxt);
            price += nOrdered * el.getPrice(nxt);
        }

        MenuList ml = MenuList.getInstance();
        Set<Integer> theMenuSet = menuOrder.keySet();
        Iterator itMenu = theMenuSet.iterator();
        while (itMenu.hasNext())
        {
            nxt = (Integer)itMenu.next();
            nOrdered = menuOrder.get(nxt);
            price += nOrdered * ml.getPrice(nxt);
        }

        price += fee;
        return price;
    }

    /*
    public void printMenuInfo()
    {
    for (Menu menu : theMenus)
    {
    menu.printInfo();
    }

    }
     */

    public void printMenuInfo()
    {
        Set<Integer> theSet = menuOrder.keySet();
        Iterator it = theSet.iterator();
        while (it.hasNext())
        {
            Integer nxt = (Integer)it.next();
            System.out.println("Menu number " + nxt + ": " + menuOrder.get(nxt) + " ordered");
        }
    }

    public void printSandwichInfo()
    {
        Set<Integer> theSet = sandwichOrder.keySet();
        Iterator it = theSet.iterator();
        while (it.hasNext())
        {
            Integer nxt = (Integer)it.next();
            System.out.println("Sandwich number " + nxt + ": " + sandwichOrder.get(nxt) + " ordered");
        }
    }

    public void printExtraInfo()
    {
        Set<Integer> theSet = extraOrder.keySet();
        Iterator it = theSet.iterator();
        while (it.hasNext())
        {
            Integer nxt = (Integer)it.next();
            System.out.println("Extra number " + nxt + ": " + extraOrder.get(nxt) + " ordered");
        }
    }

    /*
    public void printAccompanimentInfo()
    {
    for (Accompaniment accompaniment : theAccompaniments)
    {
    accompaniment.printInfo();
    }
    }
     */
    /*
    public double findMinSandwichPrice()
    {
    return findPrice(0);
    }
     */
    /*
    public double findMaxSandwichPrice()
    {
    return findPrice(1);
    }
     */
    /*
    public double findAverageSandwichPrice()
    {
    return findPrice(2);
    }
     */
    /*
    private double findPrice(int priceType)
    {
    double answer = 0.0;
    if (priceType == 0) // min price
    {
    for (int i=0;i<theMenus.size();i++)
    {
    if (i==0) {
    answer = theMenus.get(i).getSandwich().getPrice();
    }
    else {
    double tmp = theMenus.get(i).getSandwich().getPrice();
    if (tmp < answer) {
    answer = tmp;
    }
    }
    }
    for (int i=0;i<theSandwiches.size();i++)
    {
    if ( (i==0) && (answer == 0.0) ) {
    answer = theSandwiches.get(i).getPrice();
    }
    else {
    double tmp = theSandwiches.get(i).getPrice();
    if (tmp < answer) {
    answer = tmp;
    }
    }
    }
    }

    if (priceType == 1) // max price
    {
    for (int i=0;i<theMenus.size();i++)
    {
    if (i==0) {
    answer = theMenus.get(i).getSandwich().getPrice();
    }
    else {
    double tmp = theMenus.get(i).getSandwich().getPrice();
    if (tmp > answer) {
    answer = tmp;
    }
    }
    }
    for (int i=0;i<theSandwiches.size();i++)
    {
    if ( (i==0) && (answer == 0.0) ) {
    answer = theSandwiches.get(i).getPrice();
    }
    else {
    double tmp = theSandwiches.get(i).getPrice();
    if (tmp > answer) {
    answer = tmp;
    }
    }
    }
    }

    if (priceType == 2) // average price
    {
    int nSandwiches = 0;
    for (int i=0;i<theMenus.size();i++)
    {
    answer += theMenus.get(i).getSandwich().getPrice();
    nSandwiches++;
    }
    for (int i=0;i<theSandwiches.size();i++)
    {
    answer += theSandwiches.get(i).getPrice();
    nSandwiches++;
    }

    if (nSandwiches > 0) {
    answer /= nSandwiches;
    }
    }

    return answer;
    }
     */
}
