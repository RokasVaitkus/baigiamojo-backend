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
	//ikelia nuotrauka i duomenu bazę
	public Long storeFile(MultipartFile file) throws IOException {
	    RecipeImage files = RecipeImage.builder()
	        .name(file.getOriginalFilename())
	        .type(file.getContentType())
	        .imageData(file.getBytes())
	        .build();
	    
	
	    files = imageRepository.save(files);
	    

	    return files.getId(); 
	}
//randa nuotrauka su id 
	public byte[] getImageById(Long id) {
		RecipeImage image= imageRepository.findById(id).orElse(new RecipeImage());
		
		return image.getImageData();
	}
	//istrina nuotrauka su id
    public void deleteImageById(Long id) {
		imageRepository.deleteById(id);
		
	}
    // partial nuotraukos atnaujinimas kur gali pakeisti tik viena ar kelis entitcio dalis ir kitos ateis is seno 
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
	
	
