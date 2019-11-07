package ca.uqac.game.model;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GraphicalFood extends Food{

	private static int FOOD_SIZE = 5;
	private Circle c ;
	private Pane foodPane;
	
	public GraphicalFood(double x,double y,Pane foodPane) {
		super(new Point2D(x,y));
		c = new Circle();
		this.foodPane = foodPane;
		
		Draw();
	}
	
	public void Draw()
	{
		
		c.setCenterX(this.getP().getX());
		c.setCenterY(this.getP().getY());
		c.setRadius(FOOD_SIZE);
		c.setFill(Color.GREEN);
		foodPane.getChildren().add(c);
	}
}
