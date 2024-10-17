package lt.ca.javau10.Receptai.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import lt.ca.javau10.Receptai.entities.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Long>{
	
	
    @Query("SELECT r FROM Recipe r WHERE LOWER(r.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Recipe findOneByName(@Param("name") String name);
}
