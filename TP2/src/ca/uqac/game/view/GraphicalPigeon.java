package ca.uqac.game.view;


import java.util.Observable;
import java.util.Observer;

import ca.uqac.game.model.Pigeon;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.application.Platform;
import javafx.geometry.Point2D;


public class GraphicalPigeon extends Pigeon implements Observer,Runnable {

	private Field field;
	private GraphicalFoodListener foodUpdate;
	
	
	
	public GraphicalPigeon(Field field) {
		super(field.getHeight(),field.getWidth());
		this.field = field;
	}
	
	@Override
	public void update(Observable observable, Object args) {
		foodUpdate = (GraphicalFoodListener) observable;
		
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
		while(true)
		{			
			
			try{
				synchronized (this) {
					Thread.sleep(300);		
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			this.Remove();
			if(foodUpdate!=null) {
				if(foodUpdate.getFoodList().size() != 0) {
					this.Move(foodUpdate.getNewFood().getP());
					
					if(this.EatFood(foodUpdate.getNewFood().getP())) {
						foodUpdate.removeNewFood();
					}
				}else if(foodUpdate.getFoodList().size() == 0) {
					System.out.println("hello");
				}
				
			}
			this.Draw();
		}
			
			
		
	}
}
