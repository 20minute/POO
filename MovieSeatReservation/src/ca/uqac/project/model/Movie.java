package ca.uqac.project.model;

import java.io.Serializable;

public class Movie implements Serializable {
	private String name;
	private char rating;
	public Movie(String name, char rating) {
		this.name = name;
		this.rating = rating;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public char getRating() {
		return rating;
	}
	public void setRating(char rating) {
		this.rating = rating;
	}
}
