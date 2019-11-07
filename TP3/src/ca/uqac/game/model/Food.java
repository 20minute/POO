package ca.uqac.game.model;

import javafx.geometry.Point2D;
import java.util.Random;

public class Food {

	private Point2D p;
	
	/**
	 * 
	 * @param p position of food
	 */
	public Food(Point2D p) {
		this.p = p;
	}
	
	public Point2D getP() {
		return p;
	}
}
