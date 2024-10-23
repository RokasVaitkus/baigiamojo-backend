package lt.ca.javau10.Receptai.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lt.ca.javau10.Receptai.entities.RecipeImage;


@Repository
public interface ImageRepository extends JpaRepository<RecipeImage, Long>{

}
