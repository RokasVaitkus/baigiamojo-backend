package lt.ca.javau10.Receptai.controllers;


import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lt.ca.javau10.Receptai.entities.Ingredients;
import lt.ca.javau10.Receptai.entities.Recipe;
//import lt.ca.javau10.Receptai.entities.User;

@RestController
@RequestMapping("/api")
public class ApiController {

	List<Ingredients> labas;
	
	@GetMapping("/jsondummy/recipe")
	public Recipe getSimpleRecipe() {
		return new Recipe( "Simple name", "Simple Description", 0,0,labas, "How to make tutorial");
	}
//	@GetMapping("/jsondummy/user")
//	public User getSimpleUser() {
//		return new User("userName", "email", true, "password");
//	}
}
