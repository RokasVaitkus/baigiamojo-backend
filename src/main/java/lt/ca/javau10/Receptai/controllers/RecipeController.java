package lt.ca.javau10.Receptai.controllers;

import java.util.List;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lt.ca.javau10.Receptai.entities.Recipe;
import lt.ca.javau10.Receptai.services.RecipeService;


@RestController
@CrossOrigin 
public class RecipeController {

	private RecipeService recipeService;
		RecipeController(RecipeService recipeService){
			this.recipeService=recipeService;
	}
	
	@GetMapping("/recipes")
    	public List<Recipe> getAllRecipes() {
			return recipeService.getAllRecipes();
    }
	@GetMapping("/recipe/{id}")
		public Recipe getRecipeByID(@PathVariable Long id) {
			return recipeService.getRecipeById(id);
	}
	
//	@GetMapping("recipes/{name}")
//		public List<Recipe> getAllRecipeByName(@PathVariable String name) {
//			return recipeService.getAllByName(name);
//	}
	@GetMapping("recipe/{name}")
	public Recipe getOneRecipeByName(@PathVariable String name) {
		return recipeService.getOneByName(name);
	}
	

    @PostMapping("/create")
    public Recipe createRecipe(@RequestBody Recipe recipe) {
        return recipeService.saveRecipe(recipe);
    }
    
    @DeleteMapping("/delete/{id}")
    public void deleteRecipe(@PathVariable Long id) {
    	recipeService.deleteRecipeById(id);
    	
    }
    @PatchMapping("/edit/{id}")
    public Recipe editRecipe(@PathVariable Long id, @RequestBody Recipe recipeUpdates) {
    	return recipeService.partialUpdateRecipe(id, recipeUpdates);
    }
}
