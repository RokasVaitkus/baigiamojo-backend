package lt.ca.javau10.Receptai.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lt.ca.javau10.Receptai.entities.Recipe;
import lt.ca.javau10.Receptai.entities.RecipeImage;
import lt.ca.javau10.Receptai.entities.UserDto;
import lt.ca.javau10.Receptai.services.ImageService;
import lt.ca.javau10.Receptai.services.RecipeService;
import lt.ca.javau10.Receptai.services.UserService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/admin")
public class AdminController {
	
	UserService userService;
	RecipeService recipeService;
	ImageService imageService;
	
	public AdminController(UserService userService,RecipeService recipeService,ImageService imageService ) {
		this.userService=userService;
		this.recipeService=recipeService;
		this.imageService=imageService;
	}
	//
	//USER DALIS
	//
	//grazina visu vartotoju DTO
	@GetMapping("/allusers")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		List<UserDto> users = userService.getAllUsers();
		return new ResponseEntity<>(users, HttpStatus.OK);	
	}
	//ištrina vartotoja pagal id
	@DeleteMapping("/deleteuserbyid/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id){
		userService.deleteUser(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
// randa vartotoja pagal id
	@GetMapping("/finduserbyid/{id}")
	public ResponseEntity<UserDto> getUserById(@PathVariable Long id){	
		Optional<UserDto> userInBox = userService.getUserById(id);
		return userInBox
				.map( ResponseEntity::ok )
				.orElseGet( () -> ResponseEntity.notFound().build());
	}
	//
	//RECEPTU DALIS
	//
	//sukuria recepta su gautai duomenimis
    @PostMapping("/createrecipe")
    public Recipe createRecipe(@RequestBody Recipe recipe) {
        return recipeService.saveRecipe(recipe);
    }
    //ištrina recepta pagal id
    @DeleteMapping("/deleterecipebyid/{id}")
    public void deleteRecipe(@PathVariable Long id) {
    	recipeService.deleteRecipeById(id);
    	
    }
    //redaguoja recepta pagal id ir gauta body
    @PatchMapping("/editrecipebyid/{id}")
    public Recipe editRecipe(@PathVariable Long id, @RequestBody Recipe recipeUpdates) {
    	return recipeService.partialUpdateRecipe(id, recipeUpdates);
    }
  //redaguoja recepto nuotraukos linka pagal id ir gauta body
    @PatchMapping("/updaterecipeimagelink/{id}")
    public Recipe editRecipe(@PathVariable Long id, @RequestBody String link) {
    	System.out.println("Received link: " + link);

    	return recipeService.imageLinkUpdate(id, link);
    }
//
//IMAGE ENDPOINTS
//
    //įkelti nuotrauka
    @PostMapping("/createimage")
    public ResponseEntity<Long> storeFilesIntoDB(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
        	System.out.println("file is empty");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            
        }
        
        Long response = imageService.storeFile(file);  // Ensure imageService correctly handles the file
        
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    //ištrinti nuotrauka pagal id
	@DeleteMapping("/deleteimagebyid/{id}")
		public ResponseEntity<Object> deleteImageById(@PathVariable Long id) {
		imageService.deleteImageById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	//pakeisti nuotrauka pagal id
    @PatchMapping("/editimagebyid/{id}")
    public RecipeImage editRecipe(@PathVariable Long id, @RequestBody RecipeImage recipeImage) {
    	return imageService.partialUpdateImage(id, recipeImage);
    }

}
