package ca.uqac.game.model;

import javafx.geometry.Point2D;
import java.util.Random;

public class Food {

	private boolean isFresh ;
	private Point2D p;
	
	/**
	 * 
	 * @param p position of food
	 */
	public Food(Point2D p) {
		this.p = p;
		Random r = new Random();
		isFresh = r.nextBoolean();
	}
	
	public Point2D getP() {
		return p;
	}

	public boolean isFresh() {
		return isFresh;
	}
}
