package lt.ca.javau10.Receptai.services;



import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lt.ca.javau10.Receptai.entities.Recipe;
import lt.ca.javau10.Receptai.entities.RecipeImage;
import lt.ca.javau10.Receptai.repositories.ImageRepository;


@Service
public class ImageService {

	private ImageRepository imageRepository;
	
	public ImageService(ImageRepository imageRepository) {
		this.imageRepository=imageRepository;
	}
	
	public Long storeFile(MultipartFile file) throws IOException {
	    RecipeImage files = RecipeImage.builder()
	        .name(file.getOriginalFilename())
	        .type(file.getContentType())
	        .imageData(file.getBytes())
	        .build();
	    
	    // Save the file to the repository
	    files = imageRepository.save(files);
	    
	    // Return the ID of the saved image
	    return files.getId(); // Return the ID
	}

	public byte[] getImageById(Long id) {
		RecipeImage image= imageRepository.findById(id).orElse(new RecipeImage());
		
		return image.getImageData();
	}
	
    public void deleteImageById(Long id) {
		imageRepository.deleteById(id);
		
	}
    
	public RecipeImage partialUpdateImage(Long id, RecipeImage imageUpdates) {
		RecipeImage oldImage = imageRepository.findById(id)
	            .orElseThrow();

	    if (imageUpdates.getImageData() != null) {
	        oldImage.setImageData(imageUpdates.getImageData());
	    }
	    if (imageUpdates.getType() != null) {
	    	oldImage.setType(imageUpdates.getType());
	    }
	    if (imageUpdates.getName() != null) {
	    	oldImage.setName(imageUpdates.getName());
	    }
	    return imageRepository.save(oldImage);
	}
}
	
	
