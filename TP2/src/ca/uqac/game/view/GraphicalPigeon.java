package ca.uqac.game.view;


import ca.uqac.game.model.Pigeon;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.geometry.Point2D;


public class GraphicalPigeon extends Pigeon implements Runnable {

	private Field field;
	
	public GraphicalPigeon(Field field) {
		super(field.getHeight(),field.getWidth());
		this.field = field;
	}
	
	public void Draw() {
		
		GraphicsContext gc = this.field.GetGraphics();
		gc.setFill(Color.WHITE);
		gc.fillOval(this.getP().getX(), this.getP().getY(), this.PIGEON_SIZE * 2, this.PIGEON_SIZE * 2);
	}
	
	public void Remove() {
		GraphicsContext gc = this.field.GetGraphics();
		gc.setFill(Color.BLACK);
		gc.fillOval(this.getP().getX(), this.getP().getY(), this.PIGEON_SIZE * 2, this.PIGEON_SIZE * 2);
	
	}
	@Override
	public void run() {
		this.Draw();
	}
}
