package lt.ca.javau10.Receptai.services;

import java.util.List;

import org.springframework.stereotype.Service;

import lt.ca.javau10.Receptai.entities.Recipe;
import lt.ca.javau10.Receptai.repositories.RecipeRepository;



@Service
public class RecipeService {
	
	
	private RecipeRepository recipeRep;
	

	
	RecipeService(RecipeRepository recipeRep){
		this.recipeRep=recipeRep;
	}
	
	public List<Recipe> getAllRecipes() {
		return recipeRep.findAll();
	}

	
	public Recipe getRecipeById(Long id) {
		return recipeRep.findById(id).orElse(new Recipe());
	}


	public Recipe getOneByName(String name) {
		return recipeRep.findOneByName(name);
	    }
	
    public Recipe saveRecipe(Recipe recipe) {
    	return recipeRep.save(recipe);
    }
    public void deleteRecipeById(Long id) {
		recipeRep.deleteById(id);
		
	}

	public Recipe partialUpdateRecipe(Long id, Recipe recipeUpdates) {
		Recipe oldRecipe = recipeRep.findById(id)
	            .orElseThrow();

	    if (recipeUpdates.getName() != null) {
	        oldRecipe.setName(recipeUpdates.getName());
	    }
	    if (recipeUpdates.getDescription() != null) {
	        oldRecipe.setDescription(recipeUpdates.getDescription());
	    }
	    if (recipeUpdates.getPortions() != 0) {
	        oldRecipe.setPortions(recipeUpdates.getPortions());
	    }
	    if (recipeUpdates.getHowLongItTakesToMake() != 0) {
	        oldRecipe.setHowLongItTakesToMake(recipeUpdates.getHowLongItTakesToMake());
	    }
	    if(recipeUpdates.getIngredients() != null) {
	    	oldRecipe.setIngredients(recipeUpdates.getIngredients());
	    }
	    if (recipeUpdates.getHowToMakeIt() != null) {
	        oldRecipe.setHowToMakeIt(recipeUpdates.getHowToMakeIt());
	    }

	    return recipeRep.save(oldRecipe);
	}
}
