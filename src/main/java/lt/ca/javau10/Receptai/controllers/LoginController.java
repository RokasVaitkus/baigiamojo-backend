package lt.ca.javau10.Receptai.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lt.ca.javau10.Receptai.payload.requests.LoginRequest;
import lt.ca.javau10.Receptai.payload.requests.SignupRequest;
import lt.ca.javau10.Receptai.payload.responses.JwtResponse;
import lt.ca.javau10.Receptai.payload.responses.MessageResponse;
import lt.ca.javau10.Receptai.services.AuthService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	
	AuthService authService;
	
	
	public LoginController(AuthService authService) {
		this.authService = authService;
	}
	
	//sis endpointas naudojamas prisijungimui
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser (@RequestBody LoginRequest loginRequest){
        JwtResponse jwtResponse = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(jwtResponse);		
	}
	//šis endpointas vartotojo susukurimui
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
	
}
