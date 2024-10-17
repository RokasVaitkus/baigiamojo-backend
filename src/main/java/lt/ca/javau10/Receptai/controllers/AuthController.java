		package lt.ca.javau10.Receptai.controllers;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lt.ca.javau10.Receptai.entities.UserDto;
import lt.ca.javau10.Receptai.payload.requests.LoginRequest;
import lt.ca.javau10.Receptai.payload.requests.SignupRequest;
import lt.ca.javau10.Receptai.payload.responses.JwtResponse;
import lt.ca.javau10.Receptai.payload.responses.MessageResponse;
import lt.ca.javau10.Receptai.services.AuthService;
import lt.ca.javau10.Receptai.services.UserService;


@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	
	AuthService authService;
	UserService userService;
	
	public AuthController(AuthService authService, UserService userService) {
		this.userService = userService;
		this.authService = authService;
	}	
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser (@RequestBody LoginRequest loginRequest){
        JwtResponse jwtResponse = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(jwtResponse);		
	}
	
	@PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
		logger.info("Trying to signup \n" + signUpRequest);
		
        try {
            MessageResponse response = authService.registerUser(signUpRequest);
            return ResponseEntity.ok(response);
        } catch (ResponseStatusException e) {
        	
            return ResponseEntity.status(e.getStatusCode())
            		.body(new MessageResponse(e.getReason()));
        }
    }
	
	@GetMapping("/allusers")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		List<UserDto> users = userService.getAllUsers();
		return new ResponseEntity<>(users, HttpStatus.OK);	
	}
	
	@GetMapping("/findById/{id}")
	public ResponseEntity<UserDto> getUserById(@PathVariable Long id){	
		Optional<UserDto> userInBox = userService.getUserById(id);
		return userInBox
				.map( ResponseEntity::ok )
				.orElseGet( () -> ResponseEntity.notFound().build());
	}
	
	@PatchMapping("/editById/{id}")
	public ResponseEntity<UserDto> patchUpdateUser(
	                                  @PathVariable Long id, 
	                                  @RequestBody UserDto userDto) {
	    Optional<UserDto> updatedUser = userService.patchUpdateUser(id, userDto);

	    return updatedUser
	            .map(ResponseEntity::ok)
	            .orElseGet(() -> ResponseEntity.notFound().build());
	}

	
	@DeleteMapping("/deleteById/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id){
		userService.deleteUser(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}