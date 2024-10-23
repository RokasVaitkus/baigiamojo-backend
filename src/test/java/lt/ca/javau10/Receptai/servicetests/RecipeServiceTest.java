package lt.ca.javau10.Receptai.servicetests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import lt.ca.javau10.Receptai.entities.Recipe;
import lt.ca.javau10.Receptai.repositories.RecipeRepository;
import lt.ca.javau10.Receptai.services.RecipeService;
import lt.ca.javau10.Receptai.entities.Ingredients; // Make sure to import the Ingredients class

@ExtendWith(MockitoExtension.class)
public class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRep;

    @InjectMocks
    private RecipeService recipeService;

    private Recipe recipe;

    @BeforeEach
    public void setUp() {

        List<Ingredients> ingredients5 = Arrays.asList(
            new Ingredients("avokadas", 200),
            new Ingredients("pomidorai", 150), 
            new Ingredients("svogūnai", 50),
            new Ingredients("citrinų sultys", 20)
        );

        recipe = new Recipe("guacamole", "Šviežias guacamole su pomidorais ir svogūnais", 3, 10, ingredients5, "sumaišyti");
    }

    @Test
    public void testGetAllRecipes() {
        when(recipeRep.findAll()).thenReturn(Arrays.asList(recipe));

        var recipes = recipeService.getAllRecipes();

        assertEquals(1, recipes.size());
        assertEquals("guacamole", recipes.get(0).getName());
        verify(recipeRep, times(1)).findAll();
    }

    @Test
    public void testGetRecipeById_Exists() {
        when(recipeRep.findById(1L)).thenReturn(Optional.of(recipe));

        var foundRecipe = recipeService.getRecipeById(1L);

        assertNotNull(foundRecipe);
        assertEquals("guacamole", foundRecipe.getName());
        verify(recipeRep, times(1)).findById(1L);
    }

    @Test
    public void testGetRecipeById_NotExists() {
        when(recipeRep.findById(1L)).thenReturn(Optional.empty());

        var foundRecipe = recipeService.getRecipeById(1L);

        assertNotNull(foundRecipe);
        assertEquals(0, foundRecipe.getId()); // Assuming that the default id is 0
        verify(recipeRep, times(1)).findById(1L);
    }

    @Test
    public void testGetOneByName() {
        when(recipeRep.findOneByName("guacamole")).thenReturn(recipe);

        var foundRecipe = recipeService.getOneByName("guacamole");

        assertNotNull(foundRecipe);
        assertEquals("guacamole", foundRecipe.getName());
        verify(recipeRep, times(1)).findOneByName("guacamole");
    }

    @Test
    public void testSaveRecipe() {
        when(recipeRep.save(recipe)).thenReturn(recipe);

        var savedRecipe = recipeService.saveRecipe(recipe);

        assertNotNull(savedRecipe);
        assertEquals("guacamole", savedRecipe.getName());
        verify(recipeRep, times(1)).save(recipe);
    }

    @Test
    public void testDeleteRecipeById() {
        doNothing().when(recipeRep).deleteById(1L);

        recipeService.deleteRecipeById(1L);

        verify(recipeRep, times(1)).deleteById(1L);
    }

    @Test
    public void testPartialUpdateRecipe() {
        Recipe updates = new Recipe();
        updates.setName("Updated Guacamole");
        updates.setDescription("A new description");

        when(recipeRep.findById(1L)).thenReturn(Optional.of(recipe));
        when(recipeRep.save(recipe)).thenReturn(recipe);

        var updatedRecipe = recipeService.partialUpdateRecipe(1L, updates);

    }    
}