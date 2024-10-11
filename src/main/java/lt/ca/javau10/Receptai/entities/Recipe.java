package lt.ca.javau10.Receptai.entities;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name="recipes")
public class Recipe {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String name;
	private String description;
	private int portions;
	private int howLongItTakesToMake;
	private String howToMakeIt;
	
	
	@ElementCollection
	@CollectionTable(name = "recipe_ingredients", joinColumns = @JoinColumn(name = "recipe_id"))
	private List<Ingredients> ingredients;
	public Recipe() {}

	public Recipe(long id, String name, String description, int portions, int howLongItTakesToMake,
			List<Ingredients> ingredients, String howToMakeIt) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		
		this.portions = portions;
		this.howLongItTakesToMake = howLongItTakesToMake;
		this.ingredients = ingredients;
		this.howToMakeIt = howToMakeIt;
	}
	public Recipe( String name, String description, int portions, int howLongItTakesToMake,
			List<Ingredients> ingredients, String howToMakeIt) {
		super();
		this.name = name;
		this.description = description;
		this.portions = portions;
		this.howLongItTakesToMake = howLongItTakesToMake;
		this.ingredients = ingredients;
		this.howToMakeIt = howToMakeIt;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPortions() {
		return portions;
	}

	public void setPortions(int portions) {
		this.portions = portions;
	}

	public int getHowLongItTakesToMake() {
		return howLongItTakesToMake;
	}

	public void setHowLongItTakesToMake(int howLongItTakesToMake) {
		this.howLongItTakesToMake = howLongItTakesToMake;
	}

	public String getHowToMakeIt() {
		return howToMakeIt;
	}

	public void setHowToMakeIt(String howToMakeIt) {
		this.howToMakeIt = howToMakeIt;
	}

	public List<Ingredients> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredients> ingredients) {
		this.ingredients = ingredients;
	}

	@Override
	public String toString() {
		return "Recipe [id=" + id + ", name=" + name + ", description=" + description + ", portions=" + portions
				+ ", howLongItTakesToMake=" + howLongItTakesToMake + ", howToMakeIt=" + howToMakeIt + ", ingredients="
				+ ingredients + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, howLongItTakesToMake, howToMakeIt, id, ingredients, name, portions);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Recipe other = (Recipe) obj;
		return Objects.equals(description, other.description) && howLongItTakesToMake == other.howLongItTakesToMake
				&& Objects.equals(howToMakeIt, other.howToMakeIt) && id == other.id
				&& Objects.equals(ingredients, other.ingredients) && Objects.equals(name, other.name)
				&& portions == other.portions;
	}
	
	
}
