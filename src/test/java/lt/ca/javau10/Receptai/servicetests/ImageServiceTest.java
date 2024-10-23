package lt.ca.javau10.Receptai.servicetests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import lt.ca.javau10.Receptai.entities.RecipeImage;
import lt.ca.javau10.Receptai.repositories.ImageRepository;
import lt.ca.javau10.Receptai.services.ImageService;

@ExtendWith(MockitoExtension.class)
public class ImageServiceTest {

    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private ImageService imageService;

    private MultipartFile multipartFile;
    private RecipeImage recipeImage;

    @BeforeEach
    public void setUp() throws IOException {
        // Set up a mock MultipartFile
        multipartFile = new MockMultipartFile("file", "testImage.jpg", "image/jpeg", "image data".getBytes());

        // Set up RecipeImage
        recipeImage = RecipeImage.builder()
                .id(1L)
                .name("testImage.jpg")
                .type("image/jpeg")
                .imageData("image data".getBytes())
                .build();
    }

    @Test
    public void testStoreFile() throws IOException {
        when(imageRepository.save(any(RecipeImage.class))).thenReturn(recipeImage);

        Long imageId = imageService.storeFile(multipartFile);

        assertNotNull(imageId);
        assertEquals(1L, imageId);
        verify(imageRepository, times(1)).save(any(RecipeImage.class));
    }

    @Test
    public void testGetImageById_Exists() {
        when(imageRepository.findById(1L)).thenReturn(Optional.of(recipeImage));

        byte[] imageData = imageService.getImageById(1L);

        assertNotNull(imageData);
        assertArrayEquals("image data".getBytes(), imageData);
        verify(imageRepository, times(1)).findById(1L);
    }



    @Test
    public void testDeleteImageById() {
        doNothing().when(imageRepository).deleteById(1L);

        imageService.deleteImageById(1L);

        verify(imageRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testPartialUpdateImage() {
        RecipeImage updatedImage = RecipeImage.builder()
                .name("updatedImage.jpg")
                .type("image/png")
                .imageData("new image data".getBytes())
                .build();

        when(imageRepository.findById(1L)).thenReturn(Optional.of(recipeImage));
        when(imageRepository.save(any(RecipeImage.class))).thenReturn(updatedImage);

        RecipeImage result = imageService.partialUpdateImage(1L, updatedImage);

        assertNotNull(result);
        assertEquals("updatedImage.jpg", result.getName());
        assertEquals("image/png", result.getType());
        assertArrayEquals("new image data".getBytes(), result.getImageData());
        verify(imageRepository, times(1)).findById(1L);
        verify(imageRepository, times(1)).save(any(RecipeImage.class));
    }


    }

