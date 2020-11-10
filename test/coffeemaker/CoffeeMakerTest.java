package coffeemaker;

import coffeemaker.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.naming.InsufficientResourcesException;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

public class CoffeeMakerTest {

    private CoffeeMaker CM;
    private Recipe r1;
    private Recipe r2;
    private Recipe r3;
    private Recipe r4;

    @BeforeEach
    public  void setUp() throws Exception {
        CM = new CoffeeMaker();
        r1 = new Recipe("Coffee",50,4,0,1,0);
        r2 = new Recipe("Hot Chocolate",75,0,3,1,3);
        r3 = new Recipe("Latte",75,3,1,1,0);
        r4 = new Recipe("Mix",100,1,2,1,2);
    }

    //Add
    @Test
    public void testAddNull() {
        assertThrows(NullPointerException.class, () -> CM.addRecipe(null));
    }

    @Test
    public void testAddOneRecipe() throws AmountOfRecipeException, DuplicatedRecipeException {
        boolean ok = CM.addRecipe(r1);
        assertTrue(ok);
    }

    @Test
    public void testAddThreeRecipes() throws AmountOfRecipeException, DuplicatedRecipeException {
        boolean ok = CM.addRecipe(r1);
        assertTrue(ok);
        ok = CM.addRecipe(r2);
        assertTrue(ok);
        ok = CM.addRecipe(r3);
        assertTrue(ok);
    }

    @Test
    public void testAddMoreThanFourRecipes() throws AmountOfRecipeException, DuplicatedRecipeException {
        boolean ok = CM.addRecipe(r1);
        assertTrue(ok);
        ok = CM.addRecipe(r2);
        assertTrue(ok);
        ok = CM.addRecipe(r3);
        assertTrue(ok);
        assertThrows(AmountOfRecipeException.class, () -> CM.addRecipe(r4));
    }

    @Test
    public void testAddTwoRecipesSameName() throws AmountOfRecipeException, DuplicatedRecipeException, InvalidValueException {
        boolean ok = CM.addRecipe(r1);
        assertTrue(ok);
        r2.setName(r1.getName());
        assertThrows(DuplicatedRecipeException.class, () -> CM.addRecipe(r2));
    }

    @Test
    public void testAddTwoRecipesSameIngredients() throws AmountOfRecipeException, DuplicatedRecipeException, InvalidValueException {
        boolean ok = CM.addRecipe(r1);
        assertTrue(ok);
        Recipe r = new Recipe("Coffee2",50,4,0,1,0);
        assertThrows(DuplicatedRecipeException.class, () -> CM.addRecipe(r));
    }

    @Test
    public void testAddRecipesPriceZero() throws AmountOfRecipeException, DuplicatedRecipeException {
        boolean ok = CM.addRecipe(r1);
        assertTrue(ok);
        InvalidValueException thrown = assertThrows(
                InvalidValueException.class, () -> new Recipe("Coffee2",0,4,0,1,0)
        );
        assertEquals("Price must be a positive integer", thrown.getMessage());
    }

    @Test
    public void testAddTwoRecipesPriceNegative() throws AmountOfRecipeException, DuplicatedRecipeException {
        boolean ok = CM.addRecipe(r1);
        assertTrue(ok);
        InvalidValueException thrown = assertThrows(
                InvalidValueException.class, () -> new Recipe("Coffee2",-1,4,0,1,0)
        );
        assertEquals("Price must be a positive integer", thrown.getMessage());
    }

    //Remove
    @Test
    public void testRemoveOneRecipe() throws AmountOfRecipeException, DuplicatedRecipeException, RecipeException {
        boolean ok = CM.addRecipe(r1);
        assertTrue(ok);
        ok = CM.deleteRecipe("Coffee");
        assertTrue(ok);
    }

    @Test
    public void testRemoveMoreThanTwoRecipes() throws AmountOfRecipeException, DuplicatedRecipeException, RecipeException {
        boolean ok = CM.addRecipe(r1);
        assertTrue(ok);
        ok = CM.addRecipe(r2);
        assertTrue(ok);
        ok = CM.deleteRecipe("Coffee");
        assertTrue(ok);
        ok = CM.deleteRecipe("Hot Chocolate");
        assertTrue(ok);
    }

    @Test
    public void testRemoveTheSameRecipeTwice() throws AmountOfRecipeException, DuplicatedRecipeException, RecipeException {
        boolean ok = CM.addRecipe(r1);
        assertTrue(ok);
        ok = CM.deleteRecipe("Coffee");
        assertTrue(ok);
        assertThrows(RecipeException.class, () -> CM.deleteRecipe("Coffee"));
    }

    @Test
    public void testRemoveRecipeThatIsNotPresentInCoffeeMaker() {
        assertThrows(RecipeException.class, () -> CM.deleteRecipe("Coffee"));
    }

    @Test
    public void testRemoveRecipeWithNull() {
        assertThrows(RecipeException.class, () -> CM.deleteRecipe(null));
    }

    @Test
    public void testRemoveRecipeWithEmptyName() {
        assertThrows(RecipeException.class, () -> CM.deleteRecipe(""));
    }

    //Get recipes
    @Test
    public void testGetZeroRecipes() {
        Vector<Recipe> recipes = CM.getRecipes();
        assertEquals(0, recipes.size());
    }

    @Test
    public void testGetOneRecipe() throws AmountOfRecipeException, DuplicatedRecipeException {
        boolean ok = CM.addRecipe(r1);
        assertTrue(ok);
        Vector<Recipe> recipes = CM.getRecipes();
        assertEquals(1, recipes.size());
    }

    @Test
    public void testGetThreeRecipes() throws AmountOfRecipeException, DuplicatedRecipeException {
        boolean ok = CM.addRecipe(r1);
        assertTrue(ok);
        ok = CM.addRecipe(r2);
        assertTrue(ok);
        ok = CM.addRecipe(r3);
        assertTrue(ok);
        Vector<Recipe> recipes = CM.getRecipes();
        assertEquals(3, recipes.size());
    }

    @Test
    public void testGetThreeRecipesAfterTryingTheFourth() throws AmountOfRecipeException, DuplicatedRecipeException {
        boolean ok = CM.addRecipe(r1);
        assertTrue(ok);
        ok = CM.addRecipe(r2);
        assertTrue(ok);
        ok = CM.addRecipe(r3);
        assertTrue(ok);
        assertThrows(AmountOfRecipeException.class, () -> CM.addRecipe(r4));
        Vector<Recipe> recipes = CM.getRecipes();
        assertEquals(3, recipes.size());
    }

    @Test
    public void testGetZeroRecipesAfterRemovingOneRecipe() throws AmountOfRecipeException, DuplicatedRecipeException, RecipeException {
        boolean ok = CM.addRecipe(r1);
        assertTrue(ok);
        Vector<Recipe> recipes = CM.getRecipes();
        assertEquals(1, recipes.size());
        ok = CM.deleteRecipe("Coffee");
        assertTrue(ok);
        recipes = CM.getRecipes();
        assertEquals(0, recipes.size());
    }

    //Check ingredients
    @Test
    public void testCheckInitialCoffeeInventory() {
        int coffeeInv = CM.checkCoffeeInventory();
        assertEquals(20, coffeeInv);
    }

    @Test
    public void testCheckInitialMilkInventory() {
        int milkInv = CM.checkMilkInventory();
        assertEquals(20, milkInv);
    }

    @Test
    public void testCheckInitialSugarInventory() {
        int sugarInv = CM.checkSugarInventory();
        assertEquals(20, sugarInv);
    }

    @Test
    public void testCheckInitialChocolateInventory() {
        int chocolateInv = CM.checkChocolateInventory();
        assertEquals(20, chocolateInv);
    }

    //Resupply Ingredients from Coffee Maker
    @Test
    public void testResupplyCoffeeInventory() throws InvalidValueException {
        int coffeeInv = CM.checkCoffeeInventory();
        assertEquals(20, coffeeInv);
        CM.addCoffeeInventory(50);
        coffeeInv = CM.checkCoffeeInventory();
        assertEquals(70, coffeeInv);
    }

    @Test
    public void testResupplyCoffeeInventoryLimit() throws InvalidValueException {
        int coffeeInv = CM.checkCoffeeInventory();
        assertEquals(20, coffeeInv);
        CM.addCoffeeInventory(80);
        coffeeInv = CM.checkCoffeeInventory();
        assertEquals(100, coffeeInv);
    }

    @Test
    public void testInvalidResupplyCoffeeInventoryInsertMoreThanAllowed() {
        int coffeeInv = CM.checkCoffeeInventory();
        assertEquals(20, coffeeInv);
        assertThrows(InvalidValueException.class, () -> CM.addCoffeeInventory(81));
    }

    @Test
    public void testInvalidResupplyCoffeeInventoryInsertNegativeValue() {
        int coffeeInv = CM.checkCoffeeInventory();
        assertEquals(20, coffeeInv);
        assertThrows(InvalidValueException.class, () -> CM.addCoffeeInventory(-1));
    }

    @Test
    public void testInvalidResupplyCoffeeInventoryInsertZeroValue() {
        int coffeeInv = CM.checkCoffeeInventory();
        assertEquals(20, coffeeInv);
        assertThrows(InvalidValueException.class, () -> CM.addCoffeeInventory(0));
    }

    @Test
    public void testResupplyMilkInventory() throws InvalidValueException {
        int milkInv = CM.checkMilkInventory();
        assertEquals(20, milkInv);
        CM.addMilkInventory(50);
        milkInv = CM.checkMilkInventory();
        assertEquals(70, milkInv);
    }

    @Test
    public void testResupplyMilkInventoryLimit() throws InvalidValueException {
        int milkInv = CM.checkMilkInventory();
        assertEquals(20, milkInv);
        CM.addMilkInventory(80);
        milkInv = CM.checkMilkInventory();
        assertEquals(100, milkInv);
    }

    @Test
    public void testInvalidResupplyMilkInventoryInsertMoreThanAllowed() {
        int milkInv = CM.checkMilkInventory();
        assertEquals(20, milkInv);
        assertThrows(InvalidValueException.class, () -> CM.addMilkInventory(81));
    }

    @Test
    public void testInvalidResupplyMilkInventoryInsertNegativeValue() {
        int milkInv = CM.checkMilkInventory();
        assertEquals(20, milkInv);
        assertThrows(InvalidValueException.class, () -> CM.addMilkInventory(-1));
    }

    @Test
    public void testInvalidResupplyMilkInventoryInsertZeroValue() {
        int milkInv = CM.checkMilkInventory();
        assertEquals(20, milkInv);
        assertThrows(InvalidValueException.class, () -> CM.addMilkInventory(0));
    }

    @Test
    public void testResupplySugarInventory() throws InvalidValueException {
        int sugarInv = CM.checkSugarInventory();
        assertEquals(20, sugarInv);
        CM.addSugarInventory(50);
        sugarInv = CM.checkSugarInventory();
        assertEquals(70, sugarInv);
    }

    @Test
    public void testResupplySugarInventoryLimit() throws InvalidValueException {
        int sugarInv = CM.checkSugarInventory();
        assertEquals(20, sugarInv);
        CM.addSugarInventory(80);
        sugarInv = CM.checkSugarInventory();
        assertEquals(100, sugarInv);
    }

    @Test
    public void testInvalidResupplySugarInventoryInsertMoreThanAllowed() {
        int sugarInv = CM.checkSugarInventory();
        assertEquals(20, sugarInv);
        assertThrows(InvalidValueException.class, () -> CM.addSugarInventory(81));
    }

    @Test
    public void testInvalidResupplySugarInventoryInsertNegativeValue() {
        int sugarInv = CM.checkSugarInventory();
        assertEquals(20, sugarInv);
        assertThrows(InvalidValueException.class, () -> CM.addSugarInventory(-1));
    }

    @Test
    public void testInvalidResupplySugarInventoryInsertZeroValue() {
        int sugarInv = CM.checkSugarInventory();
        assertEquals(20, sugarInv);
        assertThrows(InvalidValueException.class, () -> CM.addSugarInventory(0));
    }

    @Test
    public void testResupplyChocolateInventory() throws InvalidValueException {
        int chocolateInv = CM.checkChocolateInventory();
        assertEquals(20, chocolateInv);
        CM.addChocolateInventory(50);
        chocolateInv = CM.checkChocolateInventory();
        assertEquals(70, chocolateInv);
    }

    @Test
    public void testResupplyChocolateInventoryLimit() throws InvalidValueException {
        int chocolateInv = CM.checkChocolateInventory();
        assertEquals(20, chocolateInv);
        CM.addChocolateInventory(80);
        chocolateInv = CM.checkChocolateInventory();
        assertEquals(100, chocolateInv);
    }

    @Test
    public void testInvalidResupplyChocolateInventoryInsertMoreThanAllowed() {
        int chocolateInv = CM.checkChocolateInventory();
        assertEquals(20, chocolateInv);
        assertThrows(InvalidValueException.class, () -> CM.addChocolateInventory(81));
    }

    @Test
    public void testInvalidResupplyChocolateInventoryInsertNegativeValue() {
        int chocolateInv = CM.checkChocolateInventory();
        assertEquals(20, chocolateInv);
        assertThrows(InvalidValueException.class, () -> CM.addChocolateInventory(-1));
    }

    @Test
    public void testInvalidResupplyChocolateInventoryInsertZeroValue() {
        int chocolateInv = CM.checkChocolateInventory();
        assertEquals(20, chocolateInv);
        assertThrows(InvalidValueException.class, () -> CM.addChocolateInventory(0));
    }

    //Make recipe
    @Test
    public void testMakeCoffeeWithOneRecipeWithInitialInventoryAndCorrectAmount() throws AmountOfRecipeException, DuplicatedRecipeException, InvalidValueException, InsufficientAmountOfMoneyException, InventoryException, RecipeException {
        boolean ok = CM.addRecipe(r1);
        assertTrue(ok);
        int change = CM.makeCoffee("Coffee", 50);
        assertEquals(0, change);
    }

    @Test
    public void testMakeCoffeeWithOneRecipeWithInitialInventoryAndOverAmount() throws AmountOfRecipeException, DuplicatedRecipeException, InvalidValueException, InsufficientAmountOfMoneyException, InventoryException, RecipeException {
        boolean ok = CM.addRecipe(r1);
        assertTrue(ok);
        int change = CM.makeCoffee("Coffee", 60);
        assertEquals(10, change);
    }

    @Test
    public void testMakeCoffeeWithOneRecipeWithInitialInventoryAndUnderAmount() throws AmountOfRecipeException, DuplicatedRecipeException, InvalidValueException, InsufficientAmountOfMoneyException, InventoryException, RecipeException {
        boolean ok = CM.addRecipe(r1);
        assertTrue(ok);
        assertThrows(InsufficientAmountOfMoneyException.class, () -> CM.makeCoffee("Coffee", 40));
    }

    @Test
    public void testMakeCoffeeWithOneRecipeWithInitialInventoryInsufficientIngredients() throws AmountOfRecipeException, DuplicatedRecipeException, InvalidValueException, InsufficientAmountOfMoneyException, InventoryException, RecipeException {
        boolean ok = CM.addRecipe(r1);
        assertTrue(ok);
        int change = CM.makeCoffee("Coffee", 50);
        assertEquals(0, change);
        change = CM.makeCoffee("Coffee", 50);
        assertEquals(0, change);
        change = CM.makeCoffee("Coffee", 50);
        assertEquals(0, change);
        change = CM.makeCoffee("Coffee", 50);
        assertEquals(0, change);
        change = CM.makeCoffee("Coffee", 50);
        assertEquals(0, change);
        assertThrows(InventoryException.class, () -> CM.makeCoffee("Coffee", 50)); //cannot get change
    }

    @Test
    public void testInvalidMakeCoffeeWithUnknownRecipe() throws AmountOfRecipeException, DuplicatedRecipeException, InvalidValueException, InsufficientAmountOfMoneyException, InventoryException, RecipeException {
        boolean ok = CM.addRecipe(r1);
        assertTrue(ok);
        assertThrows(RecipeException.class, () -> CM.makeCoffee("Top", 50)); //cannot get change
    }

    @Test
    public void testInvalidMakeCoffeeWithRecipeAndZeroAmount() throws AmountOfRecipeException, DuplicatedRecipeException, InvalidValueException, InsufficientAmountOfMoneyException, InventoryException, RecipeException {
        boolean ok = CM.addRecipe(r1);
        assertTrue(ok);
        assertThrows(InsufficientAmountOfMoneyException.class, () -> CM.makeCoffee("Coffee", 0));
    }

    @Test
    public void testInvalidMakeCoffeeWithRecipeAndMinIntegerValueAmount() throws AmountOfRecipeException, DuplicatedRecipeException, InvalidValueException, InsufficientAmountOfMoneyException, InventoryException, RecipeException {
        boolean ok = CM.addRecipe(r1);
        assertTrue(ok);
        InvalidValueException exception = assertThrows(
                InvalidValueException.class,
                () -> CM.makeCoffee("Coffee", -1)
        ); //cannot get change
        assertEquals("Payment must be positive or less than 500 cents", exception.getMessage());
    }

    @Test
    public void testInvalidMakeCoffeeWithRecipeAndMaxValueAmount() throws AmountOfRecipeException, DuplicatedRecipeException, InvalidValueException, InsufficientAmountOfMoneyException, InventoryException, RecipeException {
        boolean ok = CM.addRecipe(r1);
        assertTrue(ok);
        assertThrows(InvalidValueException.class, () -> CM.makeCoffee("Coffee", Integer.MAX_VALUE)); //cannot get change and its above its capacity
    }
}
