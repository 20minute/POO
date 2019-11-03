package ca.uqac.game.model;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;

import java.util.ArrayList;
import java.util.Random;


/**
 * @author zlj
 *
 */
public class Pigeon {

	public static double SPEED_PIXEL = 1;
	
	private Point2D p;		// Position of the pigeon
	public static double PIGEON_SIZE = 10.0;
		
	/**
	 * This constructor will create a random position of a pigeon in the pane
	 * @param height of pane
	 * @param width of pane
	 */
	public Pigeon(double height, double width) {
		
		//create a new random position
		Random random = new Random();
		double x = PIGEON_SIZE + (width - 2 * PIGEON_SIZE)  * random.nextDouble();
		double y = PIGEON_SIZE + (height -2 * PIGEON_SIZE)  * random.nextDouble();
		this.p = new Point2D(Math.floor(x),Math.floor(y));
		
		
	}

	/**
	 * Move to the freshest food with the speed of 1 pixel.
	 * @param newFoodPosition position of the freshest food
	 */
	public void Move(Point2D newFoodPosition) {
		
		if(newFoodPosition!=null) {
			if(newFoodPosition.getX() > p.getX()) {
				this.setP(new Point2D(p.getX()+SPEED_PIXEL,p.getY()));
			}else if(newFoodPosition.getX() < p.getX()){
				this.setP(new Point2D(p.getX()-SPEED_PIXEL,p.getY()));
			}
			
			if(newFoodPosition.getY() > p.getY()) {
				this.setP(new Point2D(p.getX(),p.getY()+SPEED_PIXEL));
			}else if(newFoodPosition.getY() < p.getY()){
				this.setP(new Point2D(p.getX(),p.getY()-SPEED_PIXEL));
			}
		}
		
	}
	
	public void Move() {
		Random random = new Random();
		boolean up = random.nextBoolean();
		boolean left = random.nextBoolean();
		
		if(left) {
			this.setP(new Point2D(p.getX()-SPEED_PIXEL * 3,p.getY()));
		}else {
			this.setP(new Point2D(p.getX()+SPEED_PIXEL * 3,p.getY()));
		}
		
		if(up) {
			this.setP(new Point2D(p.getX(),p.getY()-SPEED_PIXEL * 3));
		}else {
			this.setP(new Point2D(p.getX(),p.getY()+SPEED_PIXEL * 3));

		}
	}

	public boolean canEatFood(Point2D newFoodPosition) {
		if(p.getX() == newFoodPosition.getX() && p.getY() == newFoodPosition.getY()) {
			return true;
		}
		return false;
	}
	public Point2D getP() {
		return p;
	}

	public void setP(Point2D p) {
		this.p = p;
	}	
}


