package lt.ca.javau10.Receptai.controllers;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lt.ca.javau10.Receptai.entities.Recipe;
import lt.ca.javau10.Receptai.entities.UserDto;
import lt.ca.javau10.Receptai.services.ImageService;
import lt.ca.javau10.Receptai.services.RecipeService;
import lt.ca.javau10.Receptai.services.UserService;


@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController {
	
	UserService userService;
	RecipeService recipeService;
	ImageService imageService;
	
	public UserController(UserService userService, RecipeService recipeService,ImageService imageService) {
		this.userService=userService;
		this.recipeService=recipeService;
		this.imageService=imageService;
	}
//
// USER ENDPOINTS
//

	@PatchMapping("/edituserbyid/{id}")
	public ResponseEntity<UserDto> patchUpdateUser(
	                                  @PathVariable Long id, 
	                                  @RequestBody UserDto userDto) {
    Optional<UserDto> updatedUser = userService.patchUpdateUser(id, userDto);

	    return updatedUser
	            .map(ResponseEntity::ok)
	            .orElseGet(() -> ResponseEntity.notFound().build());
	}

	
//
// RECIPE ENDPOINTS
//
	@GetMapping("/allrecipes")
	public List<Recipe> getAllRecipes() {
		return recipeService.getAllRecipes();
	}
	@GetMapping("/findrecipebyid/{id}")
	public Recipe getRecipeByID(@PathVariable Long id) {
		return recipeService.getRecipeById(id);
	}
	
//
//IMAGE ENDPOINTS
//
	@GetMapping("/findimagebyid/{id}")
	public ResponseEntity<String> getImage(@PathVariable Long id) {
	    byte[] imageData = imageService.getImageById(id);
	    
	    // Convert byte array to Base64 string
	    String base64Image = Base64.getEncoder().encodeToString(imageData);
	    
	    return ResponseEntity.status(HttpStatus.OK).body(base64Image);
	}
}
