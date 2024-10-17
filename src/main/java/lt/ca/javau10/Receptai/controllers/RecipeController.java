package lt.ca.javau10.Receptai.controllers;

import java.util.List;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lt.ca.javau10.Receptai.entities.Recipe;
import lt.ca.javau10.Receptai.services.RecipeService;


@RestController
@CrossOrigin
@RequestMapping("/api/recipe")
public class RecipeController {

	private RecipeService recipeService;
		RecipeController(RecipeService recipeService){
			this.recipeService=recipeService;
	}
	
	@GetMapping("/allRecipes")
    	public List<Recipe> getAllRecipes() {
			return recipeService.getAllRecipes();
    }
	@GetMapping("/recipeById/{id}")
		public Recipe getRecipeByID(@PathVariable Long id) {
			return recipeService.getRecipeById(id);
	}
	
//	@GetMapping("recipesByName/{name}")
//		public List<Recipe> getAllRecipeByName(@PathVariable String name) {
//			return recipeService.getAllByName(name);
//	}
//	@GetMapping("recipeByName/{name}")
//	public Recipe getOneRecipeByName(@PathVariable String name) {
//		return recipeService.getOneByName(name);
//	}
	

    @PostMapping("/create")
    public Recipe createRecipe(@RequestBody Recipe recipe) {
        return recipeService.saveRecipe(recipe);
    }
    
    @DeleteMapping("/deleteById/{id}")
    public void deleteRecipe(@PathVariable Long id) {
    	recipeService.deleteRecipeById(id);
    	
    }
    @PatchMapping("/editById/{id}")
    public Recipe editRecipe(@PathVariable Long id, @RequestBody Recipe recipeUpdates) {
    	return recipeService.partialUpdateRecipe(id, recipeUpdates);
    }
}
