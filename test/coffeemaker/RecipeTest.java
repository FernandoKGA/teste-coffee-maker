package coffeemaker;

import coffeemaker.exceptions.InvalidValueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeTest {

//    private Recipe r3;
//
//    @BeforeEach
//    public void setUp() throws Exception {
//        r3 = new Recipe("Latte", 75, 3, 1, 1, 0);
//    }

    @Test
    public void testCreateValidRecipe() throws InvalidValueException {
        Recipe recipe = new Recipe("Coffee", 50, 4, 0, 1, 0);
        assertEquals(recipe.getName(), "Coffee");
        assertEquals(recipe.getPrice(), 50);
        assertEquals(recipe.getAmtCoffee(), 4);
        assertEquals(recipe.getAmtMilk(), 0);
        assertEquals(recipe.getAmtSugar(), 1);
        assertEquals(recipe.getAmtChocolate(), 0);
    }

    @Test
    public void testCreateInvalidRecipeAmtCoffee() throws InvalidValueException {
        InvalidValueException thrown = assertThrows(
                InvalidValueException.class, () -> new Recipe(
                        "Coffee", 50, -1, 1, 1, 1
                )
        );
        assertEquals("Units of coffee must be a positive integer", thrown.getMessage());
    }

    @Test
    public void testCreateInvalidRecipeAmtMilk() throws InvalidValueException {
        InvalidValueException thrown = assertThrows(
                InvalidValueException.class, () -> new Recipe(
                        "Milk", 50, 0, -1, 2, 0
                )
        );
        assertEquals("Units of milk must be a positive integer", thrown.getMessage());
    }

    @Test
    public void testCreateInvalidRecipeAmSugar() throws InvalidValueException {
        InvalidValueException thrown = assertThrows(
                InvalidValueException.class, () -> new Recipe(
                        "Coffee", 50, 2, 0, -1, 0
                )
        );
        assertEquals("Units of sugar must be a positive integer", thrown.getMessage());
    }

    @Test
    public void testCreateInvalidRecipeAmtChocolate() throws InvalidValueException {
        InvalidValueException thrown = assertThrows(
                InvalidValueException.class, () -> new Recipe(
                        "Coffee", 50, 0, 2, 0, -1
                )
        );
        assertEquals("Units of chocolate must be a positive integer", thrown.getMessage());
    }

    @Test
    public void testCreateInvalidRecipeZeroIngredients() throws InvalidValueException {
        InvalidValueException thrown = assertThrows(
                InvalidValueException.class, () -> new Recipe(
                        "Empty", 50, 0, 0, 0, 0
                )
        );
        assertEquals("Zero ingredients", thrown.getMessage());
    }

    @Test
    public void testCreateInvalidRecipePriceZero() throws InvalidValueException {
        InvalidValueException thrown = assertThrows(
                InvalidValueException.class, () -> new Recipe(
                        "Free", 0, 1, 0, 1, 0
                )
        );
        assertEquals("Price must be a positive integer", thrown.getMessage());
    }

    @Test
    public void testCreateInvalidRecipeNegativePrice() throws InvalidValueException {
        InvalidValueException thrown = assertThrows(
                InvalidValueException.class, () -> new Recipe(
                        "Gimme money", -1, 1, 0, 1, 0
                )
        );
        assertEquals("Price must be a positive integer", thrown.getMessage());
    }

    @Test
    public void testCreateInvalidRecipeEmptyName() throws InvalidValueException {
        InvalidValueException thrown = assertThrows(
                InvalidValueException.class, () -> new Recipe(
                        "", 50, 1, 0, 1, 0
                )
        );
        assertEquals("Invalid name", thrown.getMessage());
    }

    @Test
    public void testCreateInvalidRecipeNullName() throws InvalidValueException {
        InvalidValueException thrown = assertThrows(
                InvalidValueException.class, () -> new Recipe(
                        null, 50, 1, 0, 1, 0
                )
        );
        assertEquals("Invalid name", thrown.getMessage());
    }

    @Test
    public void testInvalidRecipeInfiniteAmountOfIngredients() throws InvalidValueException {
        assertThrows(InvalidValueException.class, () -> new Recipe("Coffee", 50, 4, 0, Integer.MAX_VALUE, 0));
    }
}