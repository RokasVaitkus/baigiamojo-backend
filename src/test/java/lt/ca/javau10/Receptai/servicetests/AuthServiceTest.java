package lt.ca.javau10.Receptai.servicetests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import lt.ca.javau10.Receptai.entities.ERole;
import lt.ca.javau10.Receptai.entities.Role;
import lt.ca.javau10.Receptai.entities.UserDto;
import lt.ca.javau10.Receptai.entities.UserEntity;
import lt.ca.javau10.Receptai.payload.requests.LoginRequest;
import lt.ca.javau10.Receptai.payload.requests.SignupRequest;
import lt.ca.javau10.Receptai.payload.responses.JwtResponse;
import lt.ca.javau10.Receptai.payload.responses.MessageResponse;
import lt.ca.javau10.Receptai.repositories.RoleRepository;
import lt.ca.javau10.Receptai.repositories.UserRepository;
import lt.ca.javau10.Receptai.security.JwtUtils;
import lt.ca.javau10.Receptai.services.AuthService;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private AuthService authService;

    private LoginRequest loginRequest;
    private SignupRequest signUpRequest;
    private UserEntity userEntity;
    private UserDto userDto;
    private Role userRole;
    private Set<Role> roles;

    @BeforeEach
    public void setUp() {
        loginRequest = new LoginRequest();
        signUpRequest = new SignupRequest();
        userEntity = new UserEntity("username", "encodedPassword", "email@example.com");
        userDto = new UserDto(1L, "username", "email@example.com");
        userRole = new Role(ERole.ROLE_USER);
        roles = new HashSet<>();
        roles.add(userRole);
    }

    

    @Test
    public void testRegisterUser_Success() {
        when(userRepository.existsByUsername(signUpRequest.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(signUpRequest.getEmail())).thenReturn(false);
        when(encoder.encode(signUpRequest.getPassword())).thenReturn("encodedPassword");
        when(roleRepository.findByName(ERole.ROLE_USER)).thenReturn(Optional.of(userRole));
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        MessageResponse response = authService.registerUser(signUpRequest);

        assertEquals("User registered successfully!", response.getMessage());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    public void testRegisterUser_UsernameTaken() {
        when(userRepository.existsByUsername(signUpRequest.getUsername())).thenReturn(true);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            authService.registerUser(signUpRequest);
        });

        assertEquals("Error: Username is already taken!", exception.getReason());
    }

    @Test
    public void testRegisterUser_EmailInUse() {
        when(userRepository.existsByUsername(signUpRequest.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(signUpRequest.getEmail())).thenReturn(true);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            authService.registerUser(signUpRequest);
        });

        assertEquals("Error: Email is already in use!", exception.getReason());
    }

    @Test
    public void testGetInitialRoles_DefaultRole() {
        signUpRequest.setRole(null);
        when(roleRepository.findByName(ERole.ROLE_USER)).thenReturn(Optional.of(userRole));

        Set<Role> roles = authService.getInitialRoles(signUpRequest);

        assertEquals(1, roles.size());
        assertTrue(roles.contains(userRole));
    }

}