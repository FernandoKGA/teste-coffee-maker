package coffeemaker;

import coffeemaker.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    public void testAddRecipesPriceZero() throws AmountOfRecipeException, DuplicatedRecipeException, InvalidValueException {
        boolean ok = CM.addRecipe(r1);
        assertTrue(ok);
        InvalidValueException thrown = assertThrows(
                InvalidValueException.class, () -> new Recipe("Coffee2",0,4,0,1,0)
        );
        assertEquals("Price must be a positive integer", thrown.getMessage());
    }

    @Test
    public void testAddTwoRecipesPriceNegative() throws AmountOfRecipeException, DuplicatedRecipeException, InvalidValueException {
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
    public void testRemoveRecipeWithNullName() {
        assertThrows(RecipeException.class, () -> CM.deleteRecipe(null));
    }

    @Test
    public void testRemoveRecipeWithEmptyName() {
        assertThrows(RecipeException.class, () -> CM.deleteRecipe(""));
    }

    //Resupply Ingredients from Coffee Maker

}
