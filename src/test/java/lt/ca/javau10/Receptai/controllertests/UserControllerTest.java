package lt.ca.javau10.Receptai.controllertests;

import lt.ca.javau10.Receptai.controllers.UserController;
import lt.ca.javau10.Receptai.entities.Recipe;
import lt.ca.javau10.Receptai.entities.UserDto;
import lt.ca.javau10.Receptai.services.ImageService;
import lt.ca.javau10.Receptai.services.RecipeService;
import lt.ca.javau10.Receptai.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private RecipeService recipeService;

    @Mock
    private ImageService imageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPatchUpdateUser_Success() {
        // Arrange
        Long userId = 1L;
        UserDto userDto = new UserDto(); // Populate this with necessary fields
        userDto.setUsername("updatedUser");
        
        when(userService.patchUpdateUser(userId, userDto)).thenReturn(Optional.of(userDto));

        // Act
        ResponseEntity<UserDto> response = userController.patchUpdateUser(userId, userDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDto, response.getBody());
        verify(userService, times(1)).patchUpdateUser(userId, userDto);
    }

    @Test
    void testPatchUpdateUser_NotFound() {
        // Arrange
        Long userId = 1L;
        UserDto userDto = new UserDto(); // Populate this with necessary fields

        when(userService.patchUpdateUser(userId, userDto)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<UserDto> response = userController.patchUpdateUser(userId, userDto);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(userService, times(1)).patchUpdateUser(userId, userDto);
    }

    @Test
    void testGetAllRecipes() {
        // Arrange
        Recipe recipe1 = new Recipe(); // Populate this with necessary fields
        Recipe recipe2 = new Recipe(); // Populate this with necessary fields
        when(recipeService.getAllRecipes()).thenReturn(Arrays.asList(recipe1, recipe2));

        // Act
        List<Recipe> recipes = userController.getAllRecipes();

        // Assert
        assertEquals(2, recipes.size());
        verify(recipeService, times(1)).getAllRecipes();
    }

    @Test
    void testGetRecipeById() {
        // Arrange
        Long recipeId = 1L;
        Recipe recipe = new Recipe(); // Populate this with necessary fields
        when(recipeService.getRecipeById(recipeId)).thenReturn(recipe);

        // Act
        Recipe result = userController.getRecipeByID(recipeId);

        // Assert
        assertEquals(recipe, result);
        verify(recipeService, times(1)).getRecipeById(recipeId);
    }

    @Test
    void testGetImage_Success() {
        // Arrange
        Long imageId = 1L;
        byte[] imageData = "mockImageData".getBytes(); // Mock image data
        when(imageService.getImageById(imageId)).thenReturn(imageData);

        // Act
        ResponseEntity<String> response = userController.getImage(imageId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Base64.getEncoder().encodeToString(imageData), response.getBody());
        verify(imageService, times(1)).getImageById(imageId);
    }

    @Test
    void testGetImage_NotFound() {
        // Arrange
        Long imageId = 1L;
        when(imageService.getImageById(imageId)).thenThrow(new RuntimeException("Image not found"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userController.getImage(imageId));
        verify(imageService, times(1)).getImageById(imageId);
    }
}
