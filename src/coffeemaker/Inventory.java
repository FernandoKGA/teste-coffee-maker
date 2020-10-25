package coffeemaker;

import coffeemaker.exceptions.InvalidValueException;


/**
 * @author Sarah Heckman
 *
 * Inventory for the coffee maker
 */
public class Inventory {
    
    private int coffee;
    private int milk;
    private int sugar;
    private int chocolate;
    
    /**
     * Creates a coffee maker inventory object and
     * fills each item in the inventory with 15 units.
     * @throws InvalidValueException
     */
    public Inventory() throws InvalidValueException {
    	setCoffee(20);
    	setMilk(20);
    	setSugar(20);
    	setChocolate(20);
    }
    
    /**
     * Returns the current number of chocolate units in 
     * the inventory.
     * @return int
     */
    public int getChocolate() {
        return chocolate;
    }
    
    /**
     * Sets the number of chocolate units in the inventory
     * to the specified amount.
     * @param chocolate
     */
    public synchronized void setChocolate(int chocolate) throws InvalidValueException{
    	if(chocolate >= 0 && chocolate<=100) {
    		this.chocolate = chocolate;
    	}
    	else
    		throw new InvalidValueException("Invalid amount of chocolate");
        
    }
    
    /**
     * Add the number of chocolate units in the inventory 
     * to the current amount of chocolate units.
     *

     */
    public synchronized void addChocolate(int amtChocolate) throws InvalidValueException{    	    	
		if (amtChocolate >= 0 && amtChocolate+this.chocolate<=100) {
			this.chocolate += amtChocolate;
		} else {
			throw new InvalidValueException("Units of chocolate must be a positive integer and less equal than 100");
		}
    }
    
    /**
     * Returns the current number of coffee units in
     * the inventory.
     * @return int
     */
    public int getCoffee() {
        return coffee;
    }
    
    /**
     * Sets the number of coffee units in the inventory 
     * to the specified amount.
     * @param coffee
     */
    public synchronized void setCoffee(int coffee)throws InvalidValueException{ 
    	if(coffee >= 0 && coffee<=100) {
    		this.coffee = coffee;
    	}
    	else
    		throw new InvalidValueException("Invalid amount of coffee");
    }
    
    /**
     * Add the number of coffee units in the inventory 
     * to the current amount of coffee units.
     */
    public synchronized void addCoffee(int amtCoffee) throws InvalidValueException{    	
		if (amtCoffee >= 0 && amtCoffee+this.coffee<=100) {
			this.coffee += amtCoffee;
		} else {
			throw new InvalidValueException("Units of coffee must be a positive integer and less equal than 100");
		}
    }
    
    /**
     * Returns the current number of milk units in
     * the inventory.
     * @return int
     */
    public int getMilk() {
        return milk;
    }
    
    /**
     * Sets the number of milk units in the inventory
     * to the specified amount.
     * @param milk
     */
    public synchronized void setMilk(int milk) throws InvalidValueException{
    	if(milk >= 0 && milk<=100) {
    		this.milk = milk;
    	} else {
			throw new InvalidValueException("Invalid amount of milk");
		}
    }
    
    /**
     * Add the number of milk units in the inventory 
     * to the current amount of milk units.
     */
    public synchronized void addMilk(int amtMilk) throws InvalidValueException{    	
		if (amtMilk >= 0 && amtMilk + this.milk<=100) {
			this.milk += amtMilk;
		} else {
			throw new InvalidValueException("Units of milk must be a positive integer and less equal than 100");
		}
    }
    
    /**
     * Returns the current number of sugar units in 
     * the inventory.
     * @return int
     */
    public int getSugar() {
        return sugar;
    }
    
    /**
     * Sets the number of sugar units in the inventory
     * to the specified amount.
     * @param sugar
     */
    public synchronized void setSugar(int sugar) throws InvalidValueException{
    	if(sugar >= 0 && sugar<=100) {
    		this.sugar = sugar;
    	}else {
			throw new InvalidValueException("Invalid amount of sugar");
		}
    }
    
    /**
     * Add the number of sugar units in the inventory 
     * to the current amount of sugar units.
     */
    public synchronized void addSugar(int amtSugar) throws InvalidValueException {    	
		if (amtSugar >= 0 && amtSugar+this.sugar<=100) {
			this.sugar += amtSugar;
		} else {
			throw new InvalidValueException("Units of sugar must be a positive integer");
		}
    }
    
    /**
     * Returns true if there are enough ingredients to make
     * the beverage.
     * @param r
     * @return boolean
     */
    protected synchronized boolean enoughIngredients(Recipe r) {
        boolean isEnough = true;
        if(this.coffee < r.getAmtCoffee()) {
            isEnough = false;
        }
        if(this.milk < r.getAmtMilk()) {
            isEnough = false;
        }
        if(this.sugar < r.getAmtSugar()) {
            isEnough = false;
        }
        if(this.chocolate < r.getAmtChocolate()) {
            isEnough = false;
        }
        return isEnough;
    }
    
    /**
     * Removes the ingredients used to make the specified 
     * recipe.  Assumes that the user has checked that there
     * are enough ingredients to make 
     * @param r
     */
    public synchronized boolean useIngredients(Recipe r) {
    	if (enoughIngredients(r)) {
    		this.coffee -= r.getAmtCoffee();
    		this.milk -= r.getAmtMilk();
    		this.sugar -= r.getAmtSugar();
    		this.chocolate -= r.getAmtChocolate();
	    	return true;
    	} else {
    		return false;
    	}
    }
    

}
