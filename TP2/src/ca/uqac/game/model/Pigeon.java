package ca.uqac.game.model;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import java.util.Random;


public class Pigeon {

	private Point2D p;		// Position of the pigeon
	private Point2D upper;	// Position UpperLeft corner
	private Point2D lower;	// Position LowerRight corner
	public static double PIGEON_SIZE = 10.0;
	
	
	
	public Pigeon(double height, double width) {
		
		Random random = new Random();
		double x = PIGEON_SIZE + (width - 2 * PIGEON_SIZE)  * random.nextDouble();
		double y = PIGEON_SIZE + (height -2 * PIGEON_SIZE)  * random.nextDouble();
		this.p = new Point2D(x,y);
		
	}



	public Point2D getP() {
		return p;
	}

	public void setP(Point2D p) {
		this.p = p;
	}
	
}


