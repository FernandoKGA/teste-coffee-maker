package coffeemaker;

import coffeemaker.exceptions.InvalidValueException;

/**
 * @author   Sarah Heckman
 */
public class Recipe {
    private String name;
    private int price;
    private int amtCoffee;
    private int amtMilk;
    private int amtSugar;
    private int amtChocolate;
    
    
    
    public Recipe(String name, int price, int amtCoffee, int amtMilk,
			int amtSugar, int amtChocolate) throws InvalidValueException {
		super();
		setName(name);
		if (amtCoffee+amtMilk+amtSugar+amtChocolate==0)
			throw new InvalidValueException("Zero ingredients");
		setPrice(price);
		setAmtCoffee(amtCoffee);
		setAmtMilk(amtMilk);
		setAmtSugar(amtSugar);
		setAmtChocolate(amtChocolate);
	}

    
    /**
	 * @return   Returns the amtChocolate.
	 */
    public int getAmtChocolate() {
		return amtChocolate;
	}
    /**
	 * @param amtChocolate   The amtChocolate to set.
	 */
    public void setAmtChocolate(int amtChocolate) throws InvalidValueException {
		if (amtChocolate >= 0) {
			this.amtChocolate = amtChocolate;
		} else {
			throw new InvalidValueException("Units of chocolate must be a positive integer");
		}
	}
    /**
	 * @return   Returns the amtCoffee.
	 */
    public int getAmtCoffee() {
		return amtCoffee;
	}
    /**
	 * @param amtCoffee   The amtCoffee to set.
	 */
    public void setAmtCoffee(int amtCoffee) throws InvalidValueException {
    	if (amtCoffee >= 0) {
			this.amtCoffee = amtCoffee;
		} else {
			throw new InvalidValueException("Units of coffee must be a positive integer");
		}
	}
    /**
	 * @return   Returns the amtMilk.
	 */
    public int getAmtMilk() {
		return amtMilk;
	}
    /**
	 * @param amtMilk   The amtMilk to set.
	 */
    public void setAmtMilk(int amtMilk) throws InvalidValueException {
		if (amtMilk >= 0) {
			this.amtMilk = amtMilk;
		} else {
			throw new InvalidValueException("Units of milk must be a positive integer");
		}
	}
    /**
	 * @return   Returns the amtSugar.
	 */
    public int getAmtSugar() {
		return amtSugar;
	}
    /**
	 * @param amtSugar   The amtSugar to set.
	 */
    public void setAmtSugar(int amtSugar) throws InvalidValueException {
		if (amtSugar >= 0) {
			this.amtSugar = amtSugar;
		} else {
			throw new InvalidValueException("Units of sugar must be a positive integer");
		}
	}
    /**
	 * @return   Returns the name.
	 */
    public String getName() {
		return name;
	}
    /**
	 * @param name   The name to set.
     * @throws InvalidValueException
	 */
    public void setName(String name) throws InvalidValueException {
    	if(name != null && !name.equals("")) {
    		this.name = name;
    	}
    	else
    		throw new InvalidValueException("Invalid name");
	}
    /**
	 * @return   Returns the price.
	 */
    public int getPrice() {
		return price;
	}
    /**
	 * @param price   The price to set.
	 */
    public void setPrice(int price) throws InvalidValueException {
		if (price > 0) {
			this.price = price;
		} else {
			throw new InvalidValueException("Price must be a positive integer");
		}
	} 
    
    

}
