package ca.uqac.game.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class Field extends Canvas{
	private GraphicsContext gc;
	public Field(double height,double width) {
		super(height,width);
		this.gc = this.getGraphicsContext2D();
		
	}
	public GraphicsContext GetGraphics()
	{
		return this.gc;
	}
}
