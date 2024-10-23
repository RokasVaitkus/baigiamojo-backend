package lt.ca.javau10.Receptai.controllertests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import lt.ca.javau10.Receptai.controllers.AdminController;
import lt.ca.javau10.Receptai.entities.Recipe;
import lt.ca.javau10.Receptai.entities.RecipeImage;
import lt.ca.javau10.Receptai.entities.UserDto;
import lt.ca.javau10.Receptai.services.ImageService;
import lt.ca.javau10.Receptai.services.RecipeService;
import lt.ca.javau10.Receptai.services.UserService;

public class AdminControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private RecipeService recipeService;

    @Mock
    private ImageService imageService;

    @InjectMocks
    private AdminController adminController;

    private UserDto userDto;
    private Recipe recipe;
    private RecipeImage recipeImage;
    private MultipartFile multipartFile;

    @BeforeEach
    public void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        userDto = new UserDto(1L, "username", "email@example.com", null, null);
        recipe = new Recipe(); // Initialize with default values as necessary
        recipeImage = new RecipeImage(); // Initialize with default values as necessary

        // Mock MultipartFile behavior
        multipartFile = mock(MultipartFile.class);
        when(multipartFile.getOriginalFilename()).thenReturn("test-image.jpg");
        when(multipartFile.getBytes()).thenReturn(new byte[1]);
        when(multipartFile.isEmpty()).thenReturn(false);
    }

    @Test
    public void testGetAllUsers() {
        when(userService.getAllUsers()).thenReturn(Arrays.asList(userDto));

        ResponseEntity<List<UserDto>> response = adminController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    public void testDeleteUser() {
        ResponseEntity<Void> response = adminController.deleteUser(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).deleteUser(1L);
    }

    @Test
    public void testGetUserById_Found() {
        when(userService.getUserById(1L)).thenReturn(Optional.of(userDto));

        ResponseEntity<UserDto> response = adminController.getUserById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDto, response.getBody());
    }

    @Test
    public void testGetUserById_NotFound() {
        when(userService.getUserById(1L)).thenReturn(Optional.empty());

        ResponseEntity<UserDto> response = adminController.getUserById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testCreateRecipe() {
        when(recipeService.saveRecipe(any(Recipe.class))).thenReturn(recipe);

        Recipe response = adminController.createRecipe(recipe);

        assertNotNull(response);
        verify(recipeService, times(1)).saveRecipe(any(Recipe.class));
    }



    @Test
    public void testEditRecipe() {
        when(recipeService.partialUpdateRecipe(1L, recipe)).thenReturn(recipe);

        Recipe response = adminController.editRecipe(1L, recipe);

        assertNotNull(response);
        verify(recipeService, times(1)).partialUpdateRecipe(1L, recipe);
    }

    @Test
    public void testUpdateRecipeImageLink() {
        String link = "http://example.com/image.jpg";
        when(recipeService.imageLinkUpdate(1L, link)).thenReturn(recipe);

        Recipe response = adminController.editRecipe(1L, link);

        assertNotNull(response);
        verify(recipeService, times(1)).imageLinkUpdate(1L, link);
    }

    @Test
    public void testStoreFilesIntoDB_Success() throws IOException {
        when(imageService.storeFile(multipartFile)).thenReturn(1L);

        ResponseEntity<Long> response = adminController.storeFilesIntoDB(multipartFile);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody());
        verify(imageService, times(1)).storeFile(multipartFile);
    }

    @Test
    public void testStoreFilesIntoDB_FileEmpty() throws IOException {
        when(multipartFile.isEmpty()).thenReturn(true);

        ResponseEntity<Long> response = adminController.storeFilesIntoDB(multipartFile);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(imageService, never()).storeFile(multipartFile);
    }

    @Test
    public void testDeleteImageById() {
        ResponseEntity<Object> response = adminController.deleteImageById(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(imageService, times(1)).deleteImageById(1L);
    }

    @Test
    public void testEditImage() {
        when(imageService.partialUpdateImage(1L, recipeImage)).thenReturn(recipeImage);

        RecipeImage response = adminController.editRecipe(1L, recipeImage);

        assertNotNull(response);
        verify(imageService, times(1)).partialUpdateImage(1L, recipeImage);
    }
}
