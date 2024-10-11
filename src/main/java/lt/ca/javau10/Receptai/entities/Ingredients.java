package lt.ca.javau10.Receptai.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;


@Embeddable
public class Ingredients {

	private String name;
	private int weight;
	
	public Ingredients() {}
	
	public Ingredients(String name, int weight) {
		this.name=name;
		this.weight=weight;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return  name + ": " + weight + "g.";
	}
	
}
