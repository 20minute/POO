package application;

import java.util.ArrayList;

import ca.uqac.project.model.Movie;
import ca.uqac.project.model.Session;
import ca.uqac.project.model.Time;

public class DataBase {

	private ArrayList<Session> movies = new ArrayList<Session>();
	public DataBase() {
		createData();
	}
	
	private void createData()
	{
        movies.add(new Session(new Movie("SCP: The Movie", 'R'), new Time(16, 30)));
		movies.add(new Session(new Movie("The Predator", 'M'), new Time(4, 45, 30)));
		movies.add(new Session(new Movie("Justice League", 'G'), new Time(4, 30)));
		movies.add(new Session(new Movie("Deadpool 2", 'R'), new Time(4, 30)));
		movies.add(new Session(new Movie("Avengers: Infinity War", 'G'), new Time(2)));
		movies.add(new Session(new Movie("Logan", 'R'), new Time(7, 30)));
		movies.add(new Session(new Movie("Slenderman", 'M'), new Time(2, 30, 25)));
		movies.add(new Session(new Movie("Avenger End", 'G'), new Time(1, 45, 30)));
		movies.add(new Session(new Movie("Despasito", 'M'), new Time()));
	}
	
	public ArrayList<Session> getSessions() {
		return movies;
	}
	
}
