package coffeemaker;

import coffeemaker.exceptions.InvalidValueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RecipeTest {

    private Recipe r3;

    @BeforeEach
    public  void setUp() throws Exception {
        r3 = new Recipe("Latte",75,3,1,1,0);
    }

    @Test
    public void testCreateValidRecipe() throws InvalidValueException {
        Recipe recipe = new Recipe("Coffee",50,4,0,1,0);
    }

    @Test
    public void testCreateInvalidRecipeAmtCoffee() throws InvalidValueException {
        assertThrows(InvalidValueException.class, () ->new Recipe("Coffee",50,-1,0,1,0));
    }

}
