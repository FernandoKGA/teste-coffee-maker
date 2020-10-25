package coffeemaker;

import coffeemaker.exceptions.AmountOfRecipeException;
import coffeemaker.exceptions.DuplicatedRecipeException;
import coffeemaker.exceptions.RecipeException;

import java.util.Vector;


public class RecipeBook {
	
	/** Array of recipes in coffee maker*/
	private Vector<Recipe> recipeArray;
	//private Recipe [] recipeArray;
	/** Number of recipes in coffee maker */
	private final int NUM_RECIPES = 4; 
	
	/**
	 * Default constructor for a RecipeBook.
	 */
	public RecipeBook() {
		recipeArray = new Vector<Recipe>();
	}
	
	/**
	 * Returns the recipe array.
	 * @param r
	 * @return Recipe[]
	 */
	public synchronized Vector<Recipe> getRecipes() {
		return recipeArray;
	}
	
	private boolean recipeExists(Recipe r){
		for (Recipe recipe: recipeArray){
			if ((recipe.getAmtChocolate()==r.getAmtChocolate() && 
					recipe.getAmtCoffee()==r.getAmtCoffee() &&
					recipe.getAmtMilk()==r.getAmtMilk() &&
					recipe.getAmtSugar()==r.getAmtSugar()) || recipe.getName().equals(r.getName()))
				return true;
		}
		return false;
	}
	
	public synchronized boolean addRecipe(Recipe r) throws DuplicatedRecipeException, AmountOfRecipeException {
		
		if (recipeArray.size()==3){
			throw new AmountOfRecipeException("Number of recipes exceeded");
		}
		//Assume recipe doesn't exist in the array until 
		//find out otherwise
		boolean exists = recipeExists(r);
		
		//Assume recipe cannot be added until find an empty
		//spot
		boolean added = false;
		//Check for first empty spot in array
		if (!exists) {
			recipeArray.add(r);
			added=true;
		}
		else
		{
			throw new DuplicatedRecipeException("Recipe already exists");
		}
		return added;
	}

	/**
	 * Returns the name of the recipe deleted at the position specified
	 * and null if the recipe does not exist.
	 *
	 * @return String
	 */
	public synchronized boolean deleteRecipe(String recipeName) throws RecipeException {
		int recipeToDelete = -1;
		for (int i=0; i< recipeArray.size(); i++){
			if (recipeArray.get(i).getName().equals(recipeName)){
				recipeToDelete=i;				
			}				
		}
		
		if (recipeToDelete>=0){
			recipeArray.remove(recipeToDelete);
			return true;
		}
		else {
			throw new RecipeException("Unkown Recipe");
		}
		
	}
	
	
	public synchronized Recipe getRecipe(String recipeName){		
		for (Recipe recipe: recipeArray)
			if (recipeName.equals(recipe.getName()))
				return recipe;
		return null;
	}
	
	
	
	/**
	 * Returns the name of the recipe edited at the position specified
	 * and null if the recipe does not exist.
	 * @param recipeToEdit
	 * @param newRecipe
	 * @return String
	 */
	/*
	public synchronized String editRecipe(int recipeToEdit, Recipe newRecipe) {
		if (recipeArray[recipeToEdit] != null) {
			String recipeName = recipeArray[recipeToEdit].getName();
			newRecipe.setName("");
			recipeArray[recipeToEdit] = newRecipe;
			return recipeName;
		} else {
			return null;
		}
	}
*/
}
