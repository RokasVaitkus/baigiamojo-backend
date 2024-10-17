package lt.ca.javau10.Receptai;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lt.ca.javau10.Receptai.entities.Ingredients;
import lt.ca.javau10.Receptai.entities.Recipe;
import lt.ca.javau10.Receptai.repositories.RecipeRepository;

@Component
public class RecipeDBSeeder implements CommandLineRunner {

	private RecipeRepository recipeRepository;

	RecipeDBSeeder(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		if (recipeRepository.count() == 0) {
			List<Ingredients> ingredients = Arrays.asList(
					new Ingredients("farasas", 400),
					new Ingredients("kumpis", 100),
					new Ingredients("miltai", 1400), 
					new Ingredients("padazas", 400));
			List<Ingredients> ingredients1 = Arrays.asList(
					new Ingredients("vištiena", 500),
					new Ingredients("daržovės", 300), 
					new Ingredients("prieskoniai", 50),
					new Ingredients("sojos padažas", 100));
			List<Ingredients> ingredients2 = Arrays.asList(
					new Ingredients("jautiena", 600),
					new Ingredients("pomidorai", 200), 
					new Ingredients("česnakai", 30),
					new Ingredients("bazilikas", 20));
			List<Ingredients> ingredients3 = Arrays.asList(
					new Ingredients("miltai", 500),
					new Ingredients("cukrus", 200), 
					new Ingredients("kiaušiniai", 4), 
					new Ingredients("pienas", 300));
			List<Ingredients> ingredients4 = Arrays.asList(
					new Ingredients("krevetės", 400),
					new Ingredients("česnakai", 20), 
					new Ingredients("alyvuogių aliejus", 100),
					new Ingredients("citrina", 50));
			List<Ingredients> ingredients5 = Arrays.asList(
					new Ingredients("avokadas", 200),
					new Ingredients("pomidorai", 150), 
					new Ingredients("svogūnai", 50),
					new Ingredients("citrinų sultys", 20));
			List<Recipe> receptasTest = Arrays.asList(
					new Recipe("pica", "Skani pica su suriu", 4, 45, ingredients, "iskepti"),
					new Recipe("vištienos troškinys", "Skanus vištienos troškinys su daržovėmis", 4, 60, ingredients1,"ištroškinti"),
					new Recipe("jautienos kepsnys", "Sultingas jautienos kepsnys su pomidorais ir česnakais", 2, 30,ingredients2, "kepti"),
					new Recipe("blynai", "Skanūs blynai su cukrumi", 6, 25, ingredients3, "iškepti"),
					new Recipe("krevečių salotos", "Gaivios krevečių salotos su citrina ir česnakais", 2, 15,ingredients4, "sumaišyti"),
					new Recipe("guacamole", "Šviežias guacamole su pomidorais ir svogūnais", 3, 10, ingredients5,"sumaišyti"));
			recipeRepository.saveAll(receptasTest);
			System.out.println("Initial data populated"+receptasTest.size()+" recipes added.");
		} else {
			System.out.println("Database already contains data, no initial recipes added.");
		}
		
	}
	
}
