package ca.uqac.game.view;

import ca.uqac.game.model.Food;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GraphicalFood extends Food{
	
	public static int FOOD_SIZE = 5;
	private Field field;
	
	
	
	public GraphicalFood(Field field,Point2D foodPosition) {
		super(foodPosition);
		this.field = field;
		
		Draw();
	}
	
	public void Draw() {
		
		GraphicsContext gc = this.field.GetGraphics();
		if(this.isFresh()) {
			gc.setFill(Color.GREEN);
		}else {
			gc.setFill(Color.BROWN);
		}
		gc.fillOval(this.getP().getX(), this.getP().getY(), FOOD_SIZE * 2, FOOD_SIZE * 2);
	}

	public void Remove() {
		GraphicsContext gc = this.field.GetGraphics();
		gc.setFill(Color.BLACK);
		gc.fillOval(this.getP().getX(), this.getP().getY(), FOOD_SIZE * 2, FOOD_SIZE * 2);
	
	}
	

	
	
}
