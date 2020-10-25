package coffeemaker;

import coffeemaker.exceptions.*;

import java.util.Vector;

/**
 * @author Sarah Heckman
 */
public class CoffeeMaker {
	/** Array of recipes in coffee maker */
	private  RecipeBook recipeBook;
	/** Inventory of the coffee maker */
    private Inventory inventory;
	
    /**
     * Constructor for the coffee maker
     *
     */
	public CoffeeMaker() {
	    recipeBook = new RecipeBook();
		try {
			inventory = new Inventory();
		} catch (InvalidValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns true if the recipe is added to the
	 * list of recipes in the CoffeeMaker and false
	 * otherwise.
	 * @param r
	 * @return boolean
	 */
	public boolean addRecipe(Recipe r) throws AmountOfRecipeException, DuplicatedRecipeException {
		return recipeBook.addRecipe(r);
	}		
	
	/*
	public boolean addRecipe(String name, int price, int amtCoffee, int amtMilk, int amtSugar, int amtChocolate){
		Recipe recipe = new Recipe(name,price,amtCoffee,amtMilk,amtSugar,amtChocolate);
		return addRecipe(recipe);
	}
	*/
	
	/**
	 * Returns the name of the successfully deleted recipe
	 * or null if the recipe cannot be deleted.
	 * 

	 */
	public boolean deleteRecipe(String recipeName) throws RecipeException {
		return recipeBook.deleteRecipe(recipeName);
	}
	
	/**
	 * Returns the name of the successfully edited recipe
	 * or null if the recipe cannot be edited.
	 * @param recipeToEdit
	 * @param r
	 * @return String
	 */
	/*
	public String editRecipe(int recipeToEdit, Recipe r) {
		return recipeBook.editRecipe(recipeToEdit, r);
	}*/
    
    /**
     * Returns true if inventory was successfully added

     */
	
	public synchronized void addCoffeeInventory(int amtCoffee) throws InvalidValueException {
		inventory.addCoffee(amtCoffee);
	}
	
	public synchronized void addMilkInventory(int amtMilk) throws InvalidValueException {
		inventory.addMilk(amtMilk);
	}
	
	public synchronized void addSugarInventory(int amtSugar) throws InvalidValueException {
		inventory.addSugar(amtSugar);
	}
	
	public synchronized void addChocolateInventory(int amtChocolate) throws InvalidValueException {
		inventory.addChocolate(amtChocolate);
	}
    
    public synchronized int checkCoffeeInventory(){
    	return inventory.getCoffee();
    }
    
    public synchronized int checkMilkInventory(){
    	return inventory.getMilk();
    }
    
    public synchronized int checkSugarInventory(){
    	return inventory.getSugar();
    }
    
    public synchronized int checkChocolateInventory(){
    	return inventory.getChocolate();
    }
    
    
    /**
     * Returns the change of a user's beverage purchase, or
     * the user's money if the beverage cannot be made

     */
    public synchronized int makeCoffee(String recipeName, int amtPaid) throws InvalidValueException,InsufficientAmountOfMoneyException, RecipeException, InventoryException {
		if (amtPaid < 0 && amtPaid > 500) {
			throw new InvalidValueException("Payment must be positive or less than 500 cents");
		} else {
			int change = 0;
			Recipe recipe = recipeBook.getRecipe(recipeName);
			if (recipe == null) {
				change = amtPaid;
				throw new RecipeException("Unknown Recipe");
			} else if (recipe.getPrice() <= amtPaid) {
				if (inventory.useIngredients(recipe)) {
					change = amtPaid - recipe.getPrice();
				} else {
					change = amtPaid;
					throw new InventoryException("Insuficcient amount of coffee, choco, milk or sugar");
				}
			} else {
				change = amtPaid;
				throw new InsufficientAmountOfMoneyException("Insufficient money");
			}

			return change;
		}
    }

	/**
	 * Returns the list of Recipes in the RecipeBook.
	 * @return Recipe []
	 */
	public synchronized Vector<Recipe> getRecipes() {
		return recipeBook.getRecipes();
	}
}
