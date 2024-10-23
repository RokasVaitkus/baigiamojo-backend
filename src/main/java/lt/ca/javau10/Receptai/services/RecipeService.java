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
	//šita funkcija gražina visus imanomus receptus is duopmenu bazes
	public List<Recipe> getAllRecipes() {
		return recipeRep.findAll();
	}

	//si funkcija randa recepta su id is duopmenu bazes
	public Recipe getRecipeById(Long id) {
		return recipeRep.findById(id).orElse(new Recipe());
	}


	public Recipe getOneByName(String name) {
		return recipeRep.findOneByName(name);
	    }
	//ši funkcija issaugo receptą
    public Recipe saveRecipe(Recipe recipe) {
    	return recipeRep.save(recipe);
    }
    //ištrina receptą su id
    public void deleteRecipeById(Long id) {
		recipeRep.deleteById(id);
		
	}
//recepto editinimas kuris pakeičia tik tuos laukus kurie buvo atsiusti kiti laukeliai paiimami is seno entitcio
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
	    if (recipeUpdates.getLinkToImage() != null) {
	    	oldRecipe.setLinkToImage(recipeUpdates.getLinkToImage());
	    }

	    return recipeRep.save(oldRecipe);
	}
	//atnaujina recepto nuotraukos nuoroda
	public Recipe imageLinkUpdate(Long id, String link) {
	    Recipe recipeEnt = recipeRep.findById(id)
	            .orElseThrow();

	    if (link != null) {
	        // Trim whitespace and remove any extraneous characters
	        String cleanedLink = link.trim().replaceAll("\"", "");
	        recipeEnt.setLinkToImage(cleanedLink);
	    }

	    return recipeRep.save(recipeEnt);
	}

}
