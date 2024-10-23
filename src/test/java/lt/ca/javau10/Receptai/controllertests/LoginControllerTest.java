package lt.ca.javau10.Receptai.controllertests;

import lt.ca.javau10.Receptai.controllers.LoginController;
import lt.ca.javau10.Receptai.payload.requests.LoginRequest;
import lt.ca.javau10.Receptai.payload.requests.SignupRequest;
import lt.ca.javau10.Receptai.payload.responses.JwtResponse;
import lt.ca.javau10.Receptai.payload.responses.MessageResponse;
import lt.ca.javau10.Receptai.services.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

class LoginControllerTest {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAuthenticateUser_Success() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();
        JwtResponse expectedResponse = new JwtResponse("mockJwtToken", 1L, "testUser", "test@example.com", List.of("ROLE_USER"));
        
        when(authService.authenticateUser(loginRequest)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<?> response = loginController.authenticateUser(loginRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        verify(authService, times(1)).authenticateUser(loginRequest);
    }



    @Test
    void testRegisterUser_Success() {
        // Arrange
        SignupRequest signUpRequest = new SignupRequest();
        MessageResponse expectedResponse = new MessageResponse("User registered successfully!");
        
        when(authService.registerUser(signUpRequest)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<?> response = loginController.registerUser(signUpRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        verify(authService, times(1)).registerUser(signUpRequest);
    }

    @Test
    void testRegisterUser_Failure() {
        // Arrange
        SignupRequest signUpRequest = new SignupRequest();
        when(authService.registerUser(signUpRequest)).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Username is already taken!"));

        // Act
        ResponseEntity<?> response = loginController.registerUser(signUpRequest);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error: Username is already taken!", ((MessageResponse) response.getBody()).getMessage());
        verify(authService, times(1)).registerUser(signUpRequest);
    }
}
