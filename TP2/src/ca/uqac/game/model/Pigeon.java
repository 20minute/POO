package ca.uqac.game.model;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;

import java.util.ArrayList;
import java.util.Random;


public class Pigeon {

	
	public static double SPEED_PIXEL = 2;
	
	private Point2D p;		// Position of the pigeon
	private Point2D upper;	// Position UpperLeft corner
	private Point2D lower;	// Position LowerRight corner
	public static double PIGEON_SIZE = 10.0;
	
	private ArrayList<Food> foods = new ArrayList<Food>();
	
	
	/* 
	 * height, width : field size
	 *
	 * 
	 * 
	 * 
	 */
	public Pigeon(double height, double width) {
		
		//create a new random position
		Random random = new Random();
		double x = PIGEON_SIZE + (width - 2 * PIGEON_SIZE)  * random.nextDouble();
		double y = PIGEON_SIZE + (height -2 * PIGEON_SIZE)  * random.nextDouble();
		this.p = new Point2D(x,y);
		
	}

	public void Move() {
		Food food = getNewFood();
		if(food!=null) {
			if(food.getP().getX() > p.getX()) {
				this.setP(new Point2D(p.getX()+SPEED_PIXEL,p.getY()));
			}else if(food.getP().getX() < p.getX()){
				this.setP(new Point2D(p.getX()-SPEED_PIXEL,p.getY()));
			}
			
			if(food.getP().getY() > p.getY()) {
				this.setP(new Point2D(p.getX(),p.getY()+SPEED_PIXEL));
			}else if(food.getP().getY() < p.getY()){
				this.setP(new Point2D(p.getX(),p.getY()-SPEED_PIXEL));
			}
		}
		
	}

	public void AddFood(Food food) {
		foods.add(food);
	}
	
	public Food getNewFood() {
		int index = foods.size()-1;
		if(index < 0 ) {
			return null;
		}else {
			return foods.get(index);

		}
	}
	
	public Point2D getP() {
		return p;
	}

	public void setP(Point2D p) {
		this.p = p;
	}	
}


