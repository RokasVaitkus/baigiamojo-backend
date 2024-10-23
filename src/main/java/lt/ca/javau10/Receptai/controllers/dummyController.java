package lt.ca.javau10.Receptai.controllers;


import java.util.HashSet;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lt.ca.javau10.Receptai.entities.Ingredients;
import lt.ca.javau10.Receptai.entities.Recipe;
import lt.ca.javau10.Receptai.entities.UserDto;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/jsondummy")
public class dummyController {

	List<Ingredients> labas;
	
	@GetMapping("/recipe")
	public Recipe getSimpleRecipe() {
		return new Recipe( "Simple name", "Simple Description", 0,0,labas, "How to make tutorial");
	}
	
    @GetMapping("/signup")
    public UserDto getSimpleUser() {
        return new UserDto("dummyUser", "dummy@example.com", "password123", new HashSet<>());
    }

}
